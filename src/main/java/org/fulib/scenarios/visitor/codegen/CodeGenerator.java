package org.fulib.scenarios.visitor.codegen;

import org.apache.commons.text.StringEscapeUtils;
import org.fulib.FulibTools;
import org.fulib.Generator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.sentence.DiagramSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.tool.Config;
import org.fulib.scenarios.visitor.resolve.SymbolCollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public enum CodeGenerator
   implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<CodeGenDTO, Object>,
                 ScenarioFile.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   // --------------- CompilationContext.Visitor ---------------

   @Override
   public Object visit(CompilationContext compilationContext, Object par)
   {
      compilationContext.getGroups().values().parallelStream().forEach(it -> {
         final CodeGenDTO dto = new CodeGenDTO();
         dto.config = compilationContext.getConfig();
         it.accept(this, dto);
      });
      return null;
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

      final boolean modelClassesToGenerate = !scenarioGroup.getClasses().values().stream()
                                                           .allMatch(ClassDecl::getExternal);
      final boolean testClassesToGenerate = !scenarioGroup.getFiles().values().stream()
                                                          .allMatch(ScenarioFile::getExternal);

      if (!modelClassesToGenerate && !testClassesToGenerate)
      {
         // nothing to do.
         return null;
      }

      if (sameFile(modelDir, testDir))
      {
         // model and test share the same output directory, so they have to share a class model.

         par.modelManager = new ClassModelManager().havePackageName(packageName).haveMainJavaDir(modelDir);

         if (modelClassesToGenerate)
         {
            this.generateModel(scenarioGroup, par, modelDir, packageDir);
         }
         if (testClassesToGenerate)
         {
            this.generateTests(scenarioGroup, par);
         }

         new Generator().generate(par.modelManager.getClassModel());

         return null;
      }

      // model and test use different output directories, and thus different class models.

      if (modelClassesToGenerate)
      {
         par.modelManager = new ClassModelManager().havePackageName(packageName).haveMainJavaDir(modelDir);

         this.generateModel(scenarioGroup, par, modelDir, packageDir);

         new Generator().generate(par.modelManager.getClassModel());
      }

      if (testClassesToGenerate)
      {
         par.modelManager = new ClassModelManager().havePackageName(packageName).haveMainJavaDir(testDir);

         this.generateTests(scenarioGroup, par);

         new Generator().generate(par.modelManager.getClassModel());
      }

      return null;
   }

   private void generateTests(ScenarioGroup scenarioGroup, CodeGenDTO par)
   {
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         file.accept(this, par);
      }
   }

   private void generateModel(ScenarioGroup scenarioGroup, CodeGenDTO par, String modelDir, String packageDir)
   {
      for (ClassDecl classDecl : scenarioGroup.getClasses().values())
      {
         classDecl.accept(DeclGenerator.INSTANCE, par);
      }

      if (par.config.isClassDiagram())
      {
         FulibTools.classDiagrams()
                   .dumpPng(par.modelManager.getClassModel(), modelDir + "/" + packageDir + "/classDiagram.png");
      }
      if (par.config.isClassDiagramSVG())
      {
         FulibTools.classDiagrams()
                   .dumpSVG(par.modelManager.getClassModel(), modelDir + "/" + packageDir + "/classDiagram.svg");
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
      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         this.addDiagramSentences(scenario, par);
      }

      scenarioFile.getClassDecl().accept(DeclGenerator.INSTANCE, par);

      // after class gen: add @Test and import to scenario methods
      par.addImport("org.junit.Test");

      for (final Scenario scenario : scenarioFile.getScenarios().values())
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

      final List<Sentence> sentences = scenario.getBody().getItems();
      final String methodName = scenario.getMethodDecl().getName();
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
         if (name.equals(fMethod.readName()))
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
   ScenarioGroup     group;
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
      this.clazz.getImportList().add("import " + s + ";");
   }
}
