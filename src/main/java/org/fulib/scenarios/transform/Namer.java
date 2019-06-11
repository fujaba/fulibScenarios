package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
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
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.type.*;

import java.util.List;

public enum Namer implements Type.Visitor<Object, String>, Expr.Visitor<Object, String>, Name.Visitor<Object, String>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public String visit(Type type, Object par)
   {
      throw new UnsupportedOperationException();
   }

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
      return creationExpr.getClassName().accept(this, par);
   }

   @Override
   public String visit(CallExpr callExpr, Object par)
   {
      final List<Sentence> body = callExpr.getBody().getItems();

      final Sentence lastSentence;
      if (!body.isEmpty() && (lastSentence = body.get(body.size() - 1)) instanceof AnswerSentence)
      {
         final Expr result = ((AnswerSentence) lastSentence).getResult();
         return result.accept(this, null);
      }
      else
      {
         return null;
      }
   }

   @Override
   public String visit(PrimaryExpr primaryExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public String visit(NumberLiteral numberLiteral, Object par)
   {
      return null;
   }

   @Override
   public String visit(StringLiteral stringLiteral, Object par)
   {
      return null;
   }

   @Override
   public String visit(ConditionalExpr conditionalExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(CollectionExpr collectionExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(ListExpr listExpr, Object par)
   {
      // TODO maybe "e1,e2,e3" should be named "es" (i.e. common prefix)?
      return null;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public String visit(Name name, Object par)
   {
      return null;
   }

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
