package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.type.*;

import java.util.List;

public enum Namer implements Type.Visitor<Object, String>, Expr.Visitor<Object, String>, Name.Visitor<Object, String>
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
      return attributeAccess.getName().accept(this, par);
   }

   @Override
   public String visit(ListAttributeAccess listAttributeAccess, Object par)
   {
      return listAttributeAccess.getName().accept(this, par);
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
      final List<Sentence> body = callExpr.getBody().getItems();
      if (body.isEmpty())
      {
         return null;
      }

      final Sentence lastSentence = body.get(body.size() - 1);
      if (!(lastSentence instanceof AnswerSentence))
      {
         return null;
      }

      final AnswerSentence answerSentence = (AnswerSentence) lastSentence;
      final String varName = answerSentence.getVarName();
      if (varName != null)
      {
         return varName;
      }

      final Expr result = answerSentence.getResult();
      return result.accept(this, null);
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public String visit(ListExpr listExpr, Object par)
   {
      // TODO maybe "e1,e2,e3" should be named "es" (i.e. common prefix)?
      return null;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public String visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl().getName();
   }

   @Override
   public String visit(UnresolvedName unresolvedName, Object par)
   {
      return unresolvedName.getValue();
   }
}
