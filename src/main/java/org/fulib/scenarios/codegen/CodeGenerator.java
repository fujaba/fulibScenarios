package org.fulib.scenarios.codegen;

import org.fulib.FulibTools;
import org.fulib.Generator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.DiagramSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.tool.Config;
import org.fulib.scenarios.transform.SymbolCollector;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CodeGenerator implements ScenarioGroup.Visitor<Object, Object>, ScenarioFile.Visitor<Object, Object>,
                                         Scenario.Visitor<Object, Object>
{
   final Config config;

   ScenarioGroup group;

   ClassModelManager modelManager;
   ClassModelManager testManager;

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

      this.modelManager = new ClassModelManager().havePackageName(packageName).haveMainJavaDir(modelDir);
      this.testManager = new ClassModelManager().havePackageName(packageName)
                                                .haveMainJavaDir(this.config.getTestDir());

      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         file.accept(this, par);
      }

      new Generator().generate(this.modelManager.getClassModel());
      new Generator().generate(this.testManager.getClassModel());

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
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Object par)
   {
      this.clazz = this.testManager.haveClass(scenarioFile.getClassDecl().getName());

      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         scenario.accept(this, par);
      }

      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Object par)
   {
      final String methodName = scenario.getMethodDecl().getName();

      this.method = new FMethod().setClazz(this.clazz).writeName(methodName).writeReturnType("void")
                                 .setAnnotations("@Test");
      this.bodyBuilder = new StringBuilder();

      this.addImport("org.junit.Test");

      scenario.getBody().accept(SentenceGenerator.INSTANCE, this);

      if (this.config.isObjectDiagram() || this.config.isObjectDiagramSVG())
      {
         // collect top-level variables
         final Map<String, Decl> symbolTable = new TreeMap<>();
         for (final Sentence item : scenario.getBody().getItems())
         {
            item.accept(SymbolCollector.INSTANCE, symbolTable);
         }

         if (!symbolTable.isEmpty())
         {
            final List<Expr> exprs = symbolTable.values().stream().map(it -> NameAccess.of(ResolvedName.of(it)))
                                                .collect(Collectors.toList());
            final ListExpr listExpr = ListExpr.of(exprs);

            if (this.config.isObjectDiagram())
            {
               DiagramSentence.of(listExpr, methodName + ".png").accept(SentenceGenerator.INSTANCE, this);
            }
            if (this.config.isObjectDiagramSVG())
            {
               DiagramSentence.of(listExpr, methodName + ".svg").accept(SentenceGenerator.INSTANCE, this);
            }
         }
      }

      this.method.setMethodBody(this.bodyBuilder.toString());

      return null;
   }
}
