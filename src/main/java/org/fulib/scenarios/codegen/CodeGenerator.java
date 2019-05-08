package org.fulib.scenarios.codegen;

import org.fulib.Generator;
import org.fulib.StrUtil;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.DiagramSentence;
import org.fulib.scenarios.ast.sentence.ExpectSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.ThereSentence;
import org.fulib.scenarios.tool.Config;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

public class CodeGenerator implements ScenarioGroup.Visitor<Object, Object>, Scenario.Visitor<Object, Object>,
                                         Sentence.Visitor<Object, Object>, Expr.Visitor<Object, Object>
{
   private final Config config;

   private ClassModelManager modelManager;
   private ClassModelBuilder testBuilder;

   private ClassBuilder classBuilder;

   private StringBuilder bodyBuilder;

   public CodeGenerator(Config config)
   {
      this.config = config;
   }

   // =============== Methods ===============

   public void emit(String code)
   {
      this.bodyBuilder.append(code);
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      this.modelManager = new ClassModelManager().havePackageName(scenarioGroup.getName())
                                                 .haveMainJavaDir(this.config.getModelDir());
      this.testBuilder = new ClassModelBuilder(scenarioGroup.getName(), this.config.getTestDir());

      for (final Scenario scenario : scenarioGroup.getScenarios())
      {
         scenario.accept(this, par);
      }

      new Generator().generate(this.modelManager.getClassModel());
      new Generator().generate(this.testBuilder.getClassModel());
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
         sentence.accept(this, par);
      }

      final FMethod testMethod = new FMethod().writeName("test").writeReturnType("void")
            .setAnnotations("@Test")
            .setMethodBody(this.bodyBuilder.toString());
      this.classBuilder.getClazz().withMethods(testMethod);
      this.classBuilder.getClazz().getImportList().add("import org.junit.Test;");
      this.classBuilder.getClazz().getImportList().add("import static org.junit.Assert.assertEquals;");

      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Object par)
   {
      for (VarDecl var : thereSentence.getVars())
      {
         final String type = var.accept(new Typer(this.modelManager.getClassModel()), null);
         this.bodyBuilder.append("      ").append(type).append(' ').append(var.getName()).append(" = ");
         var.getExpr().accept(this, par);
         this.bodyBuilder.append(";\n");
      }
      return null;
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Object par)
   {
      for (ConditionalExpr expr : expectSentence.getPredicates())
      {
         this.bodyBuilder.append("      ");
         expr.accept(AssertionGenerator.INSTANCE, this);
         this.bodyBuilder.append(";\n");
      }
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Object par)
   {
      this.classBuilder.getClazz().getImportList().add("import org.fulib.FulibTools;");

      this.bodyBuilder.append("      ");
      this.bodyBuilder.append("FulibTools.objectDiagrams().dumpPng(");

      // TODO escape string literal
      this.bodyBuilder.append('"');
      this.bodyBuilder.append(diagramSentence.getFileName());
      this.bodyBuilder.append('"');

      this.bodyBuilder.append(", ");
      diagramSentence.getObject().accept(this, par);
      this.bodyBuilder.append(");\n");

      return null;
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public Object visit(Expr expr, Object par)
   {
      return null;
   }

   @Override
   public Object visit(AttributeAccess attributeAccess, Object par)
   {
      attributeAccess.getReceiver().accept(this, par);
      this.bodyBuilder.append(".get").append(StrUtil.cap(attributeAccess.getName().accept(Namer.INSTANCE, null)))
                      .append("()");
      return null;
   }

   @Override
   public Object visit(ExampleAccess exampleAccess, Object par)
   {
      exampleAccess.getExpr().accept(this, par);
      return null;
   }

   @Override
   public Object visit(CreationExpr creationExpr, Object par)
   {
      final String className = creationExpr.accept(new Typer(null), null);
      final Clazz clazz = this.modelManager.haveClass(className);

      this.bodyBuilder.append("new ").append(className).append("()");
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
         final String attributeType = attribute.getExpr().accept(new Typer(this.modelManager.getClassModel()), null);

         this.modelManager.haveAttribute(clazz, attributeName, attributeType);

         this.bodyBuilder.append(".set").append(StrUtil.cap(attributeName)).append("(");
         attribute.getExpr().accept(this, par);
         this.bodyBuilder.append(")");
      }
      return null;
   }

   @Override
   public Object visit(PrimaryExpr primaryExpr, Object par)
   {
      return null;
   }

   @Override
   public Object visit(NameAccess nameAccess, Object par)
   {
      this.bodyBuilder.append(nameAccess.getName().accept(Namer.INSTANCE, null));
      return null;
   }

   @Override
   public Object visit(NumberLiteral numberLiteral, Object par)
   {
      return this.bodyBuilder.append(numberLiteral.getValue());
   }

   @Override
   public Object visit(StringLiteral stringLiteral, Object par)
   {
      // TODO escape characters
      return this.bodyBuilder.append('"').append(stringLiteral.getValue()).append('"');
   }

   @Override
   public Object visit(ConditionalExpr conditionalExpr, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      throw new UnsupportedOperationException();
   }
}
