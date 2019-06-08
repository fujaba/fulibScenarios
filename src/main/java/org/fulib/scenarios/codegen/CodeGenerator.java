package org.fulib.scenarios.codegen;

import org.fulib.FulibTools;
import org.fulib.Generator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.DiagramSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.tool.Config;
import org.fulib.scenarios.transform.SymbolCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CodeGenerator implements ScenarioGroup.Visitor<Object, Object>, ScenarioFile.Visitor<Object, Object>
{
   final Config config;

   ScenarioGroup group;

   ClassModelManager modelManager;

   Clazz   clazz;
   FMethod method;

   StringBuilder bodyBuilder;

   public CodeGenerator(Config config)
   {
      this.config = config;
   }

   // =============== Methods ===============

   void emit(String code)
   {
      this.bodyBuilder.append(code);
   }

   void emitStringLiteral(String text)
   {
      // TODO escape string literal
      this.bodyBuilder.append('"').append(text).append('"');
   }

   void emitIndent()
   {
      // TODO support multiple levels (required for if, for, ...)
      this.bodyBuilder.append("      ");
   }

   void addImport(String s)
   {
      this.clazz.getImportList().add("import " + s + ";");
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      this.group = scenarioGroup;

      final String modelDir = this.config.getModelDir();
      final String packageDir = scenarioGroup.getPackageDir();
      final String packageName = packageDir.replace('/', '.');

      // generate model
      this.modelManager = new ClassModelManager().havePackageName(packageName).haveMainJavaDir(modelDir);

      for (ClassDecl classDecl : scenarioGroup.getClasses().values())
      {
         classDecl.accept(DeclGenerator.INSTANCE, this);
      }

      new Generator().generate(this.modelManager.getClassModel());

      if (this.config.isClassDiagram())
      {
         FulibTools.classDiagrams()
                   .dumpPng(this.modelManager.getClassModel(), modelDir + "/" + packageDir + "/classDiagram.png");
      }
      if (this.config.isClassDiagramSVG())
      {
         FulibTools.classDiagrams()
                   .dumpSVG(this.modelManager.getClassModel(), modelDir + "/" + packageDir + "/classDiagram.svg");
      }

      // generate test
      this.modelManager = new ClassModelManager().havePackageName(packageName)
                                                 .haveMainJavaDir(this.config.getTestDir());

      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         file.accept(this, par);
      }

      new Generator().generate(this.modelManager.getClassModel());

      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Object par)
   {
      this.clazz = this.modelManager.haveClass(scenarioFile.getClassDecl().getName());

      // before class gen: add diagram sentences if necessary
      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         this.addDiagramSentences(scenario);
      }

      scenarioFile.getClassDecl().accept(DeclGenerator.INSTANCE, this);

      // after class gen: add @Test and import to scenario methods
      this.addImport("org.junit.Test");

      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         final String methodName = scenario.getMethodDecl().getName();
         getFMethod(this.clazz, methodName).setAnnotations("@Test");
      }

      return null;
   }

   private void addDiagramSentences(Scenario scenario)
   {
      if (!this.config.isObjectDiagram() && !this.config.isObjectDiagramSVG())
      {
         return;
      }

      final String methodName = scenario.getMethodDecl().getName();
      final List<Sentence> sentences = scenario.getBody().getItems();

      // collect top-level variables
      final Map<String, Decl> symbolTable = new TreeMap<>();
      for (final Sentence item : sentences)
      {
         item.accept(SymbolCollector.INSTANCE, symbolTable);
      }

      if (symbolTable.isEmpty())
      {
         return;
      }

      final Map<String, ClassDecl> classes = scenario.getFile().getGroup().getClasses();
      final List<Expr> exprs = new ArrayList<>();
      for (Decl it : symbolTable.values())
      {
         // only add variables with types from the data model (i.e. exclude String, double, ... variables)
         if (classes.get(it.getType()) != null)
         {
            exprs.add(NameAccess.of(ResolvedName.of(it)));
         }
      }
      final ListExpr listExpr = ListExpr.of(exprs);

      if (this.config.isObjectDiagram())
      {
         final DiagramSentence diagramSentence = DiagramSentence.of(listExpr, methodName + ".png");
         sentences.add(diagramSentence);
      }
      if (this.config.isObjectDiagramSVG())
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
      throw new RuntimeException("method " + clazz.getName() + "." + name + " not found");
   }
}
