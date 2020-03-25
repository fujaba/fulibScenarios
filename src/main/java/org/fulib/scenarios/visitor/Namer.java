package org.fulib.scenarios.visitor;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.MapAccessExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.type.*;

public enum Namer implements Type.Visitor<Object, String>, Expr.Visitor<Object, String>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public String visit(UnresolvedType unresolvedType, Object par)
   {
      return unresolvedType.getName();
   }

   @Override
   public String visit(ClassType classType, Object par)
   {
      return classType.getClassDecl().getName();
   }

   @Override
   public String visit(ListType listType, Object par)
   {
      return "List";
   }

   @Override
   public String visit(PrimitiveType primitiveType, Object par)
   {
      return primitiveType.getJavaName();
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public String visit(Expr expr, Object par)
   {
      return null;
   }

   @Override
   public String visit(AttributeAccess attributeAccess, Object par)
   {
      return attributeAccess.getName().getValue();
   }

   @Override
   public String visit(MapAccessExpr mapAccessExpr, Object par)
   {
      return mapAccessExpr.getName().getValue();
   }

   @Override
   public String visit(ExampleAccess exampleAccess, Object par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public String visit(CreationExpr creationExpr, Object par)
   {
      // the name of the first named expression, or else the class name
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         final String exprName = attribute.getExpr().accept(this, par);
         if (exprName != null)
         {
            return exprName;
         }
      }
      return StrUtil.downFirstChar(creationExpr.getType().accept(this, par));
   }

   @Override
   public String visit(CallExpr callExpr, Object par)
   {
      final AnswerSentence answerSentence = callExpr.getBody().accept(GetAnswerSentence.INSTANCE, par);
      if (answerSentence == null)
      {
         return "result++";
      }

      final String varName = answerSentence.getVarName();
      if (varName != null)
      {
         return varName;
      }

      final Expr expr = answerSentence.getResult();
      return expr != null ? expr.accept(Namer.INSTANCE, par) : "result++";
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().getValue();
   }

   @Override
   public String visit(ListExpr listExpr, Object par)
   {
      // TODO maybe "e1,e2,e3" should be named "es" (i.e. common prefix)?
      return null;
   }
}
