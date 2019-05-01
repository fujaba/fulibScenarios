package org.fulib.scenarios.transform;

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
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.ThereSentence;
import org.fulib.scenarios.tool.Config;

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
}
