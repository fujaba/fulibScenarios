package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.*;
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

import java.util.List;

public enum Typer implements Expr.Visitor<Object, String>, Name.Visitor<Object, String>
{
   INSTANCE;

   // --------------- Expr.Visitor ---------------

   @Override
   public String visit(Expr expr, Object par)
   {
      throw new UnsupportedOperationException();
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
      return StrUtil.cap(creationExpr.getClassName().accept(Namer.INSTANCE, null));
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
         return "void";
      }
   }

   @Override
   public String visit(PrimaryExpr primaryExpr, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public String visit(NumberLiteral numberLiteral, Object par)
   {
      return "double";
   }

   @Override
   public String visit(StringLiteral stringLiteral, Object par)
   {
      return "String";
   }

   @Override
   public String visit(ConditionalExpr conditionalExpr, Object par)
   {
      return "boolean";
   }

   @Override
   public String visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      return "boolean";
   }

   @Override
   public String visit(CollectionExpr collectionExpr, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public String visit(ListExpr listExpr, Object par)
   {
      String commonType = null;
      for (Expr element : listExpr.getElements())
      {
         final String elementType = element.accept(this, par);
         if (commonType == null)
         {
            commonType = elementType;
         }
         else if (!commonType.equals(elementType))
         {
            // no common type -> use Object
            return "List<Object>";
         }
      }

      assert commonType != null : "empty list expression";
      return "List<" + primitiveToWrapper(commonType) + ">";
   }

   // --------------- Name.Visitor ---------------

   @Override
   public String visit(Name name, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public String visit(UnresolvedName unresolvedName, Object par)
   {
      // TODO diagnostic
      throw new IllegalStateException("unresolved name " + unresolvedName.getValue());
   }

   @Override
   public String visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl().getType();
   }

   // =============== Static Methods ===============

   public static String primitiveToWrapper(String primitive)
   {
      switch (primitive)
      {
      case "byte":
         return "Byte";
      case "short":
         return "Short";
      case "char":
         return "Character";
      case "int":
         return "Integer";
      case "long":
         return "Long";
      case "float":
         return "Float";
      case "double":
         return "Double";
      case "void":
         return "Void";
      default:
         return primitive;
      }
   }
}
