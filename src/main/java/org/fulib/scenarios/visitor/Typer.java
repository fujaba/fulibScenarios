package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.MapAccessExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.DoubleLiteral;
import org.fulib.scenarios.ast.expr.primary.IntLiteral;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

public enum Typer implements Expr.Visitor<Object, Type>, Name.Visitor<Object, Type>
{
   INSTANCE;

   // --------------- Expr.Visitor ---------------

   @Override
   public Type visit(ErrorExpr errorExpr, Object par)
   {
      return errorExpr.getType();
   }

   // --------------- PrimitiveExpr.Visitor ---------------

   @Override
   public Type visit(IntLiteral intLiteral, Object par)
   {
      return PrimitiveType.INT;
   }

   @Override
   public Type visit(DoubleLiteral doubleLiteral, Object par)
   {
      return PrimitiveType.DOUBLE;
   }

   @Override
   public Type visit(StringLiteral stringLiteral, Object par)
   {
      return PrimitiveType.STRING;
   }

   @Override
   public Type visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   // --------------- Misc. Expr.Visitor ---------------

   @Override
   public Type visit(AttributeAccess attributeAccess, Object par)
   {
      return attributeAccess.getName().accept(this, par);
   }

   @Override
   public Type visit(ExampleAccess exampleAccess, Object par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public Type visit(CreationExpr creationExpr, Object par)
   {
      return creationExpr.getType();
   }

   @Override
   public Type visit(CallExpr callExpr, Object par)
   {
      final Decl method = callExpr.getName().getDecl();
      if (method != null && method.getType() != null)
      {
         return method.getType();
      }

      final AnswerSentence answerSentence = callExpr.getBody().accept(GetAnswerSentence.INSTANCE, par);
      if (answerSentence == null)
      {
         return PrimitiveType.VOID;
      }

      final Expr expr = answerSentence.getResult();
      return expr != null ? expr.accept(this, par) : PrimitiveType.VOID;
   }

   // --------------- ConditionalExpr.Visitor ---------------

   @Override
   public Type visit(ConditionalExpr conditionalExpr, Object par)
   {
      return PrimitiveType.BOOLEAN;
   }

   // --------------- CollectionExpr.Visitor ---------------

   @Override
   public Type visit(ListExpr listExpr, Object par)
   {
      Type commonType = null;
      for (Expr element : listExpr.getElements())
      {
         Type elementType = element.accept(this, par);
         if (elementType instanceof ListType)
         {
            elementType = ((ListType) elementType).getElementType();
         }

         if (commonType == null)
         {
            commonType = elementType;
         }
         else if (!commonType.equals(elementType))
         {
            // no common type -> use Object
            return ListType.of(PrimitiveType.OBJECT);
         }
      }

      assert commonType != null : "empty list expression";
      return ListType.of(commonType);
   }

   @Override
   public Type visit(RangeExpr rangeExpr, Object par)
   {
      // TODO common type
      final Type elementType = rangeExpr.getStart().accept(this, par);
      return ListType.of(elementType);
   }

   @Override
   public Type visit(MapAccessExpr mapAccessExpr, Object par)
   {
      final Type attributeType = mapAccessExpr.getName().getDecl().getType();
      if (attributeType instanceof ListType)
      {
         return attributeType;
      }

      return ListType.of(attributeType);
   }

   @Override
   public Type visit(FilterExpr filterExpr, Object par)
   {
      return filterExpr.getSource().accept(this, par);
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Type visit(UnresolvedName unresolvedName, Object par)
   {
      return PrimitiveType.ERROR;
   }

   @Override
   public Type visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl().getType();
   }
}
