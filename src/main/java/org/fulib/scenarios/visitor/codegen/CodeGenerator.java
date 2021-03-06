package org.fulib.scenarios.visitor.codegen;

import org.apache.commons.text.StringEscapeUtils;
import org.fulib.FulibTools;
import org.fulib.Generator;
import org.fulib.TablesGenerator;
import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelDecorators;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.MethodDecl;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.sentence.DiagramSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.tool.Config;
import org.fulib.scenarios.visitor.resolve.SymbolCollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public enum CodeGenerator
   implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<CodeGenDTO, Object>,
                 ScenarioFile.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   // --------------- CompilationContext.Visitor ---------------

   @Override
   public Object visit(CompilationContext context, Object par)
   {
      final Config config = context.getConfig();
      if (config.isDryRun())
      {
         return null;
      }

      final List<Class<? extends ClassModelDecorator>> decoratorClasses = resolveDecoratorClasses(
         config.getDecoratorClasses());

      context.getGroups().values().parallelStream().forEach(it -> {
         final CodeGenDTO dto = new CodeGenDTO();
         dto.config = config;
         dto.decoratorClasses = decoratorClasses;
         it.accept(this, dto);
      });

      final Set<String> otherPackageNames = this.getOtherPackageNames(context, decoratorClasses);

      for (final String packageName : otherPackageNames)
      {
         final ClassModelManager manager = new ClassModelManager()
            .setPackageName(packageName)
            .setMainJavaDir(config.getModelDir());

         decorate(manager, decoratorClasses);

         this.dumpClassDiagrams(manager, config);

         if (config.isGenerateTables())
         {
            new TablesGenerator().generate(manager.getClassModel());
         }

         new Generator().generate(manager.getClassModel());
      }

      return null;
   }

   private Set<String> getOtherPackageNames(CompilationContext context,
      List<Class<? extends ClassModelDecorator>> decoratorClasses)
   {
      final Set<String> result = new LinkedHashSet<>();
      for (final Class<? extends ClassModelDecorator> decoratorClass : decoratorClasses)
      {
         final String packageName = decoratorClass.getPackage().getName();
         final String packageDir = packageName.replace('.', '/');
         if (context.getGroups().containsKey(packageDir))
         {
            continue;
         }

         result.add(packageName);
      }
      return result;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, CodeGenDTO par)
   {
      par.group = scenarioGroup;

      final String modelDir = par.config.getModelDir();
      final String testDir = par.config.getTestDir();
      final String packageDir = scenarioGroup.getPackageDir();
      final String packageName = packageDir.replace('/', '.');

      par.modelManager = new ClassModelManager().setPackageName(packageName).setMainJavaDir(modelDir);

      final boolean modelClasses = this.populateModel(scenarioGroup, par);
      if (modelClasses)
      {
         this.dumpClassDiagrams(par.modelManager, par.config);

         if (par.config.isGenerateTables())
         {
            // generate tables before tests because we don't want table classes for test classes
            new TablesGenerator().generate(par.modelManager.getClassModel());
         }
      }

      if (sameFile(modelDir, testDir))
      {
         // model and test share the same output directory, so they have to share a class model.

         final boolean testClasses = this.populateTests(scenarioGroup, par);

         if (modelClasses || testClasses)
         {
            new Generator().generate(par.modelManager.getClassModel());
         }

         return null;
      }

      // model and test use different output directories, and thus different class models.

      if (modelClasses)
      {
         new Generator().generate(par.modelManager.getClassModel());
      }

      par.modelManager = new ClassModelManager().setPackageName(packageName).setMainJavaDir(testDir);

      final boolean testClasses = this.populateTests(scenarioGroup, par);

      if (testClasses)
      {
         new Generator().generate(par.modelManager.getClassModel());
      }

      return null;
   }

   private boolean populateTests(ScenarioGroup scenarioGroup, CodeGenDTO par)
   {
      final Map<String, ScenarioFile> files = scenarioGroup.getFiles();
      for (final ScenarioFile file : files.values())
      {
         file.accept(this, par);
      }

      return files.values().stream().anyMatch(f -> !f.getExternal());
   }

   private boolean populateModel(ScenarioGroup scenarioGroup, CodeGenDTO par)
   {
      final Map<String, ClassDecl> classes = scenarioGroup.getClasses();
      for (ClassDecl classDecl : classes.values())
      {
         classDecl.accept(DeclGenerator.INSTANCE, par);
      }

      boolean decorators = decorate(par.modelManager, par.decoratorClasses);
      return decorators || classes.values().stream().anyMatch(c -> !c.getExternal());
   }

   private void dumpClassDiagrams(ClassModelManager modelManager, Config config)
   {
      final ClassModel classModel = modelManager.getClassModel();
      if (config.isClassDiagram())
      {
         FulibTools.classDiagrams().dumpPng(classModel, classModel.getPackageSrcFolder() + "/classDiagram.png");
      }
      if (config.isClassDiagramSVG())
      {
         FulibTools.classDiagrams().dumpSVG(classModel, classModel.getPackageSrcFolder() + "/classDiagram.svg");
      }
   }

   private static boolean sameFile(String modelDir, String testDir)
   {
      try
      {
         return Files.isSameFile(Paths.get(modelDir), Paths.get(testDir));
      }
      catch (IOException e)
      {
         final File modelFile = new File(modelDir);
         final File testFile = new File(testDir);
         try
         {
            return modelFile.getCanonicalPath().equals(testFile.getCanonicalPath());
         }
         catch (IOException e2)
         {
            return modelFile.getAbsolutePath().equals(testFile.getAbsolutePath());
         }
      }
   }

   private static boolean decorate(ClassModelManager manager,
      List<Class<? extends ClassModelDecorator>> decoratorClasses)
   {
      final String packageName = manager.getClassModel().getPackageName();
      final List<Class<? extends ClassModelDecorator>> filteredDecoratorClasses = getDecoratorClassesForPackage(
         packageName, decoratorClasses);

      if (filteredDecoratorClasses.isEmpty())
      {
         return false;
      }

      RuntimeException failure = null;

      for (final Class<? extends ClassModelDecorator> decoratorClass : filteredDecoratorClasses)
      {
         try
         {
            final ClassModelDecorator decorator = decoratorClass.newInstance();
            decorator.decorate(manager);
         }
         catch (Exception e)
         {
            if (failure == null)
            {
               failure = new RuntimeException("class model decoration failed");
            }
            failure.addSuppressed(e);
         }
      }

      if (failure != null)
      {
         throw failure;
      }

      return true;
   }

   private static List<Class<? extends ClassModelDecorator>> getDecoratorClassesForPackage(String packageName,
      List<Class<? extends ClassModelDecorator>> decoratorClasses)
   {
      final Package thePackage = Package.getPackage(packageName);
      if (thePackage != null)
      {
         final ClassModelDecorators decorators = thePackage.getAnnotation(ClassModelDecorators.class);
         if (decorators != null)
         {
            return Arrays.asList(decorators.value());
         }
      }

      return decoratorClasses
         .stream()
         .filter(c -> packageName.equals(c.getPackage().getName()))
         .collect(Collectors.toList());
   }

   public static List<Class<? extends ClassModelDecorator>> resolveDecoratorClasses(Set<String> decoratorClassNames)
   {
      final List<Class<? extends ClassModelDecorator>> result = new ArrayList<>();
      for (String decoratorClassName : decoratorClassNames)
      {
         final Class<?> decoratorClass;
         try
         {
            decoratorClass = Class.forName(decoratorClassName);
         }
         catch (ClassNotFoundException ignored)
         {
            continue;
         }

         if (ClassModelDecorator.class.isAssignableFrom(decoratorClass))
         {
            result.add((Class<? extends ClassModelDecorator>) decoratorClass);
         }
      }

      return result;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, CodeGenDTO par)
   {
      if (scenarioFile.getExternal())
      {
         return null;
      }

      par.clazz = par.modelManager.haveClass(scenarioFile.getClassDecl().getName());

      // before class gen: add diagram sentences if necessary
      for (final Scenario scenario : scenarioFile.getScenarios())
      {
         par.scenario = scenario;
         this.addDiagramSentences(scenario, par);
      }

      par.scenario = null;
      scenarioFile.getClassDecl().accept(DeclGenerator.INSTANCE, par);

      // after class gen: add @Test and import to scenario methods
      par.addImport("org.junit.Test");

      for (final Scenario scenario : scenarioFile.getScenarios())
      {
         final String methodName = scenario.getMethodDecl().getName();
         getFMethod(par.clazz, methodName).setAnnotations("@Test");
      }

      return null;
   }

   private void addDiagramSentences(Scenario scenario, CodeGenDTO par)
   {
      if (!par.config.isObjectDiagram() && !par.config.isObjectDiagramSVG())
      {
         return;
      }

      final ListExpr listExpr = SymbolCollector.getRoots(scenario);
      if (listExpr == null)
      {
         return;
      }

      final MethodDecl methodDecl = scenario.getMethodDecl();
      final List<Sentence> sentences = methodDecl.getBody().getItems();
      final String methodName = methodDecl.getName();
      if (par.config.isObjectDiagram())
      {
         final DiagramSentence diagramSentence = DiagramSentence.of(listExpr, methodName + ".png");
         sentences.add(diagramSentence);
      }
      if (par.config.isObjectDiagramSVG())
      {
         final DiagramSentence diagramSentence = DiagramSentence.of(listExpr, methodName + ".svg");
         sentences.add(diagramSentence);
      }
   }

   private static FMethod getFMethod(Clazz clazz, String name)
   {
      for (final FMethod fMethod : clazz.getMethods())
      {
         if (name.equals(fMethod.getName()))
         {
            return fMethod;
         }
      }
      throw new IllegalStateException("method " + clazz.getName() + "." + name + " not found");
   }
}

class CodeGenDTO
{
   // =============== Constants ===============

   private static final String INDENT = "   ";

   // =============== Fields ===============

   // set or null depending on the level we are generating
   Config            config;
   List<Class<? extends ClassModelDecorator>> decoratorClasses;
   ScenarioGroup     group;
   Scenario scenario;
   ClassModelManager modelManager;
   Clazz             clazz;
   StringBuilder     bodyBuilder;

   int indentLevel = 2;

   // =============== Methods ===============

   void emit(String code)
   {
      this.bodyBuilder.append(code);
   }

   void emitLine(String line)
   {
      this.emitIndent();
      this.emit(line);
      this.bodyBuilder.append('\n');
   }

   void emitStringLiteral(String text)
   {
      this.bodyBuilder.append('"').append(StringEscapeUtils.escapeJava(text)).append('"');
   }

   void emitIndent()
   {
      for (int i = 0; i < this.indentLevel; i++)
      {
         this.bodyBuilder.append(INDENT);
      }
   }

   void addImport(String s)
   {
      this.clazz.withImports(s);
   }
}
