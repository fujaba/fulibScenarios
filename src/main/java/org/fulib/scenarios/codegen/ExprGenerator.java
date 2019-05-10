package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.classmodel.Clazz;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

import java.util.List;

public enum ExprGenerator implements Expr.Visitor<CodeGenerator, Object>
{
   INSTANCE;

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
         final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
         final String attributeType = attribute.getExpr().accept(new Typer(par.modelManager.getClassModel()), null);

         par.modelManager.haveAttribute(clazz, attributeName, attributeType);

         par.bodyBuilder.append(".set").append(StrUtil.cap(attributeName)).append("(");
         attribute.getExpr().accept(this, par);
         par.bodyBuilder.append(")");
      }
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
      par.addImport("java.util.ArrayList");
      par.addImport("java.util.Arrays");

      par.bodyBuilder.append("new ArrayList<>(Arrays.asList(");

      final List<Expr> elements = listExpr.getElements();

      elements.get(0).accept(this, par);
      for (int i = 1; i < elements.size(); i++)
      {
         par.bodyBuilder.append(", ");
         elements.get(i).accept(this, par);
      }

      par.bodyBuilder.append("))");
      return null;
   }
}
