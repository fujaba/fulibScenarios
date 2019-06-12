package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.AssociationDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.transform.ExtractDecl;
import org.fulib.scenarios.transform.Namer;

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
      final String className = creationExpr.getType().accept(TypeGenerator.INSTANCE, null);

      par.bodyBuilder.append("new ").append(className).append("()");
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         generateSetterCall(par, attribute);
      }
      return null;
   }

   static void generateSetterCall(CodeGenerator par, NamedExpr attribute)
   {
      final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
      final Decl decl = attribute.getName().accept(ExtractDecl.INSTANCE, null);
      final boolean wither = decl instanceof AssociationDecl && ((AssociationDecl) decl).getCardinality() != 1;

      par.bodyBuilder.append(wither ? ".with" : ".set").append(StrUtil.cap(attributeName)).append("(");
      attribute.getExpr().accept(wither ? NO_LIST : INSTANCE, par);
      par.bodyBuilder.append(")");
   }

   @Override
   public Object visit(CallExpr callExpr, CodeGenerator par)
   {
      callExpr.getReceiver().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append('.');
      par.bodyBuilder.append(callExpr.getName().accept(Namer.INSTANCE, null));
      par.bodyBuilder.append('(');

      final List<NamedExpr> arguments = callExpr.getArguments();
      if (arguments.size() > 0) {
         this.emitList(par, arguments.stream().map(NamedExpr::getExpr).collect(Collectors.toList()));
      }
      par.bodyBuilder.append(')');

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
   public Object visit(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenerator par)
   {
      // TODO like AssertionGenerator#visit(ConditionalOperatorExpr, CodeGenerator)
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
