package org.fulib.scenarios.codegen;

import org.fulib.FulibTools;
import org.fulib.Generator;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.Scenario;
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

public class CodeGenerator implements ScenarioGroup.Visitor<Object, Object>, Scenario.Visitor<Object, Object>
{
   final Config config;

   ClassModelManager modelManager;
   private ClassModelBuilder testBuilder;

   private ClassBuilder classBuilder;

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
      this.classBuilder.getClazz().getImportList().add("import " + s + ";");
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      final String modelDir = this.config.getModelDir();
      final String packageDir = scenarioGroup.getName().replace('.', '/');

      this.modelManager = new ClassModelManager().havePackageName(scenarioGroup.getName()).haveMainJavaDir(modelDir);
      this.testBuilder = new ClassModelBuilder(scenarioGroup.getName(), this.config.getTestDir());

      for (final Scenario scenario : scenarioGroup.getScenarios())
      {
         scenario.accept(this, par);
      }

      new Generator().generate(this.modelManager.getClassModel());
      new Generator().generate(this.testBuilder.getClassModel());

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

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Object par)
   {
      final String className = scenario.getName().replaceAll("\\W", "");
      this.classBuilder = this.testBuilder.buildClass(className);
      this.bodyBuilder = new StringBuilder();

      for (final Sentence sentence : scenario.getSentences())
      {
         sentence.accept(SentenceGenerator.INSTANCE, this);
      }

      if (this.config.isObjectDiagram() || this.config.isObjectDiagramSVG())
      {
         // TODO store symbol table in Scenario
         final Map<String, Decl> symbolTable = new TreeMap<>();
         scenario.accept(new SymbolCollector(symbolTable), null);

         final List<Expr> exprs = symbolTable.values().stream().map(it -> NameAccess.of(ResolvedName.of(it)))
                                             .collect(Collectors.toList());
         final ListExpr listExpr = ListExpr.of(exprs);

         if (this.config.isObjectDiagram())
         {
            DiagramSentence.of(listExpr, className + ".png").accept(SentenceGenerator.INSTANCE, this);
         }
         if (this.config.isObjectDiagramSVG())
         {
            DiagramSentence.of(listExpr, className + ".svg").accept(SentenceGenerator.INSTANCE, this);
         }
      }

      this.addImport("org.junit.Test");

      final FMethod testMethod = new FMethod().writeName("test").writeReturnType("void").setAnnotations("@Test")
                                              .setMethodBody(this.bodyBuilder.toString());
      this.classBuilder.getClazz().withMethods(testMethod);

      return null;
   }
}
