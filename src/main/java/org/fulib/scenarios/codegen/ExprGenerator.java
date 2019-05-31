package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

import java.util.List;
import java.util.stream.Collectors;

public enum ExprGenerator implements Expr.Visitor<CodeGenerator, Object>
{
   INSTANCE, NO_LIST;

   @Override
   public Object visit(Expr expr, CodeGenerator par)
   {
      return null;
   }

   @Override
   public Object visit(AttributeAccess attributeAccess, CodeGenerator par)
   {
      attributeAccess.getReceiver().accept(this, par);
      par.bodyBuilder.append(".get").append(StrUtil.cap(attributeAccess.getName().accept(Namer.INSTANCE, null)))
                      .append("()");
      return null;
   }

   @Override
   public Object visit(ExampleAccess exampleAccess, CodeGenerator par)
   {
      exampleAccess.getExpr().accept(this, par);
      return null;
   }

   @Override
   public Object visit(CreationExpr creationExpr, CodeGenerator par)
   {
      final String className = creationExpr.accept(new Typer(null), null);
      final Clazz clazz = par.modelManager.haveClass(className);

      par.bodyBuilder.append("new ").append(className).append("()");
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         generateSetterCall(par, clazz, attribute);
      }
      return null;
   }

   static void generateSetterCall(CodeGenerator par, Clazz clazz, NamedExpr attribute)
   {
      final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
      final String attributeType = attribute.getExpr().accept(new Typer(par.modelManager.getClassModel()), null);

      final boolean wither;
      final AssocRole existingRole = clazz.getRole(attributeName);
      if (existingRole != null)
      {
         // role exists, use it to find out if cardinality many
         wither = existingRole.getCardinality() > 1;
      }
      else if (attributeType.startsWith("List<"))
      {
         // new value is multi-valued

         final String otherType = attributeType.substring(5, attributeType.length() - 1); // strip List< and >
         final Clazz otherClazz = clazz.getModel().getClazz(otherType);

         if (otherClazz == null)
         {
            // was element type that we have no control over, e.g. List<String>
            // TODO we need an aggregate attribute (?)
            clazz.getImportList().add("import java.util.List;");
            par.modelManager.haveAttribute(clazz, attributeName, attributeType);
            wither = false;
         }
         else
         {
            par.modelManager.haveRole(clazz, attributeName, otherClazz, ClassModelBuilder.MANY);
            wither = true;
         }
      }
      else
      {
         par.modelManager.haveAttribute(clazz, attributeName, attributeType);
         wither = false;
      }

      par.bodyBuilder.append(wither ? ".with" : ".set").append(StrUtil.cap(attributeName)).append("(");
      attribute.getExpr().accept(wither ? NO_LIST : INSTANCE, par);
      par.bodyBuilder.append(")");
   }

   @Override
   public Object visit(CallExpr callExpr, CodeGenerator par)
   {
      final Expr receiver = callExpr.getReceiver();
      final String methodName = callExpr.getName().accept(Namer.INSTANCE, null);
      final List<Sentence> body = callExpr.getBody().getItems();

      if (receiver != null)
      {
         receiver.accept(ExprGenerator.INSTANCE, par);
      }
      else
      {
         par.bodyBuilder.append("this");
      }

      par.bodyBuilder.append('.');
      par.bodyBuilder.append(methodName);
      par.bodyBuilder.append('(');

      final List<NamedExpr> arguments = callExpr.getArguments();
      this.emitList(par, arguments.stream().map(NamedExpr::getExpr).collect(Collectors.toList()));

      par.bodyBuilder.append(')');

      // generate method

      final String returnType = callExpr.accept(new Typer(par.modelManager.getClassModel()), null);

      final CodeGenerator bodyGen = new CodeGenerator(par.config);
      bodyGen.group = par.group;
      bodyGen.modelManager = par.modelManager;
      bodyGen.testManager = par.testManager;

      if (receiver != null)
      {
         final String targetClassName = receiver.accept(new Typer(par.modelManager.getClassModel()), null);

         bodyGen.clazz = par.modelManager.haveClass(targetClassName);
      }
      else
      {
         bodyGen.clazz = par.clazz; // == test class // TODO not within recursive call
      }

      bodyGen.method = new FMethod().setClazz(bodyGen.clazz).writeName(methodName).writeReturnType(returnType);
      bodyGen.bodyBuilder = new StringBuilder();

      for (final NamedExpr argument : arguments)
      {
         final String name = argument.getName().accept(Namer.INSTANCE, null);
         final String type = argument.getExpr().accept(new Typer(par.modelManager.getClassModel()), null);
         bodyGen.method.readParams().put(name, type);
      }

      for (Sentence sentence : body)
      {
         sentence.accept(SentenceGenerator.INSTANCE, bodyGen);
      }

      bodyGen.method.setMethodBody(bodyGen.bodyBuilder.toString());
      return null;
   }

   @Override
   public Object visit(PrimaryExpr primaryExpr, CodeGenerator par)
   {
      return null;
   }

   @Override
   public Object visit(NameAccess nameAccess, CodeGenerator par)
   {
      par.bodyBuilder.append(nameAccess.getName().accept(Namer.INSTANCE, null));
      return null;
   }

   @Override
   public Object visit(NumberLiteral numberLiteral, CodeGenerator par)
   {
      return par.bodyBuilder.append(numberLiteral.getValue());
   }

   @Override
   public Object visit(StringLiteral stringLiteral, CodeGenerator par)
   {
      par.emitStringLiteral(stringLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(ConditionalExpr conditionalExpr, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AttributeCheckExpr attributeCheckExpr, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CollectionExpr collectionExpr, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ListExpr listExpr, CodeGenerator par)
   {
      if (this != NO_LIST)
      {
         par.addImport("java.util.ArrayList");
         par.addImport("java.util.Arrays");

         par.bodyBuilder.append("new ArrayList<>(Arrays.asList(");
      }

      final List<Expr> elements = listExpr.getElements();

      this.emitList(par, elements);

      if (this != NO_LIST)
      {
         par.bodyBuilder.append("))");
      }
      return null;
   }

   private void emitList(CodeGenerator par, List<Expr> elements)
   {
      elements.get(0).accept(this, par);
      for (int i = 1; i < elements.size(); i++)
      {
         par.bodyBuilder.append(", ");
         elements.get(i).accept(this, par);
      }
   }
}
