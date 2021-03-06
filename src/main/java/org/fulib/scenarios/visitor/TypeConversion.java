package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.PlaceholderExpr;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.visitor.resolve.SentenceResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.fulib.scenarios.ast.type.PrimitiveType.*;
import static org.fulib.scenarios.diagnostic.Marker.error;

public enum TypeConversion implements Expr.Visitor<Type, Expr>
{
   INSTANCE;

   // =============== Static Methods ===============

   public static boolean isConvertible(Type from, Type to)
   {
      if (TypeComparer.isSuperType(to, from))
      {
         return true;
      }

      if (to == STRING)
      {
         return true;
      }

      if (from == STRING && to instanceof PrimitiveType)
      {
         return PrimitiveType.isPrimitiveOrWrapperValue(to);
      }

      return from == primitiveToWrapper(to) || to == primitiveToWrapper(from);
   }

   public static Expr convert(Expr expr, Type to)
   {
      return expr.accept(INSTANCE, to);
   }

   public static Expr convert(Expr expr, Type to, Scope scope, String code)
   {
      final Expr converted = convert(expr, to);
      if (converted != null)
      {
         return converted;
      }

      final Marker error = error(expr.getPosition(), code, expr.getType().getDescription(), to.getDescription());
      SentenceResolver.addStringLiteralTypoNotes(scope, expr, error);
      scope.report(error);
      return expr;
   }

   private static Expr staticCall(Type type, String method, Expr arg, Type returnType)
   {
      final NameAccess receiver = NameAccess.of(UnresolvedName.of(type.accept(Namer.INSTANCE, null), null));
      return methodCall(receiver, method, arg, returnType);
   }

   private static Expr methodCall(Expr receiver, String method, Expr arg, Type returnType)
   {
      final Name name = UnresolvedName.of(method, null);
      final List<NamedExpr> args = Collections.singletonList(NamedExpr.of(null, arg));
      final SentenceList body = SentenceList.of(Collections.singletonList(
         AnswerSentence.of(null, ErrorExpr.of(returnType), null)));
      return CallExpr.of(name, receiver, args, body);
   }

   // =============== Methods ===============

   @Override
   public Expr visit(Expr expr, Type to)
   {
      final Type from = expr.getType();
      if (TypeComparer.isSuperType(to, from))
      {
         return expr;
      }

      if (to == STRING)
      {
         return staticCall(STRING, "valueOf", expr, STRING);
      }
      if (from == STRING && to instanceof PrimitiveType)
      {
         switch ((PrimitiveType) to)
         {
         case BOOLEAN:
            return staticCall(BOOLEAN_WRAPPER, "parseBoolean", expr, BOOLEAN);
         case BYTE:
            return staticCall(BYTE_WRAPPER, "parseByte", expr, BYTE);
         case SHORT:
            return staticCall(SHORT_WRAPPER, "parseShort", expr, SHORT);
         case INT:
            return staticCall(INT_WRAPPER, "parseInt", expr, INT);
         case LONG:
            return staticCall(LONG_WRAPPER, "parseLong", expr, LONG);
         case FLOAT:
            return staticCall(FLOAT_WRAPPER, "parseFloat", expr, FLOAT);
         case DOUBLE:
            return staticCall(DOUBLE_WRAPPER, "parseDouble", expr, DOUBLE);
         case BOOLEAN_WRAPPER:
         case BYTE_WRAPPER:
         case SHORT_WRAPPER:
         case INT_WRAPPER:
         case LONG_WRAPPER:
         case FLOAT_WRAPPER:
         case DOUBLE_WRAPPER:
            return staticCall(to, "valueOf", expr, to);
         case CHAR:
         case CHAR_WRAPPER:
            // <expr>.charAt(0)
            return methodCall(expr, "charAt", IntLiteral.of(0), to);
         }
      }
      if (from == primitiveToWrapper(to) || to == primitiveToWrapper(from))
      {
         return expr;
      }

      return null;
   }

   @Override
   public Expr visit(ErrorExpr errorExpr, Type par)
   {
      errorExpr.setType(par);
      return errorExpr;
   }

   @Override
   public Expr visit(PlaceholderExpr placeholderExpr, Type par)
   {
      placeholderExpr.setType(par);
      return placeholderExpr;
   }

   @Override
   public Expr visit(IntLiteral intLiteral, Type par)
   {
      if (!(par instanceof PrimitiveType))
      {
         return null;
      }

      switch ((PrimitiveType) par)
      {
      case OBJECT:
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
      case INT:
      case INT_WRAPPER:
      case LONG:
      case FLOAT:
      case DOUBLE:
         return intLiteral;
      case STRING:
         final StringLiteral stringLiteral = StringLiteral.of(Integer.toString(intLiteral.getValue()));
         stringLiteral.setPosition(intLiteral.getPosition());
         return stringLiteral;
      }

      return null;
   }

   @Override
   public Expr visit(DoubleLiteral doubleLiteral, Type par)
   {
      if (!(par instanceof PrimitiveType))
      {
         return null;
      }

      switch ((PrimitiveType) par)
      {
      case OBJECT:
      case DOUBLE:
      case DOUBLE_WRAPPER:
         return doubleLiteral;
      case STRING:
         final StringLiteral stringLiteral = StringLiteral.of(Double.toString(doubleLiteral.getValue()));
         stringLiteral.setPosition(doubleLiteral.getPosition());
         return stringLiteral;
      }

      return null;
   }

   @Override
   public Expr visit(BooleanLiteral booleanLiteral, Type par)
   {
      if (!(par instanceof PrimitiveType))
      {
         return null;
      }

      switch ((PrimitiveType) par)
      {
      case OBJECT:
      case BOOLEAN:
      case BOOLEAN_WRAPPER:
         return booleanLiteral;
      case STRING:
         final StringLiteral stringLiteral = StringLiteral.of(Boolean.toString(booleanLiteral.getValue()));
         stringLiteral.setPosition(booleanLiteral.getPosition());
         return stringLiteral;
      }

      return null;
   }

   @Override
   public Expr visit(CharLiteral charLiteral, Type par)
   {
      if (!(par instanceof PrimitiveType))
      {
         return null;
      }

      switch ((PrimitiveType) par)
      {
      case OBJECT:
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
      case INT:
      case LONG:
      case FLOAT:
      case DOUBLE:
         return charLiteral;
      case STRING:
         final StringLiteral stringLiteral = StringLiteral.of(String.valueOf(charLiteral.getValue()));
         stringLiteral.setPosition(charLiteral.getPosition());
         return stringLiteral;
      }
      return null;
   }

   @Override
   public Expr visit(StringLiteral stringLiteral, Type par)
   {
      if (!(par instanceof PrimitiveType))
      {
         return null;
      }

      final String value = stringLiteral.getValue();
      switch ((PrimitiveType) par)
      {
      case OBJECT:
      case STRING:
         return stringLiteral;
      case INT:
      case INT_WRAPPER:
         final int intValue;
         try
         {
            intValue = Integer.parseInt(value);
         }
         catch (NumberFormatException ex)
         {
            return null;
         }

         final IntLiteral intLiteral = IntLiteral.of(intValue);
         intLiteral.setPosition(stringLiteral.getPosition());
         return intLiteral;
      case DOUBLE:
      case DOUBLE_WRAPPER:
         final double doubleValue;
         try
         {
            doubleValue = Double.parseDouble(value);
         }
         catch (NumberFormatException ex)
         {
            return null;
         }

         final DoubleLiteral doubleLiteral = DoubleLiteral.of(doubleValue);
         doubleLiteral.setPosition(stringLiteral.getPosition());
         return doubleLiteral;
      case CHAR:
      case CHAR_WRAPPER:
         if (value.length() == 1)
         {
            final CharLiteral charLiteral = CharLiteral.of(value.charAt(0));
            charLiteral.setPosition(stringLiteral.getPosition());
            return charLiteral;
         }
         return null;
      }

      return null;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Type par)
   {
      final Expr value = exampleAccess.getValue().accept(this, par);
      if (value == null)
      {
         return null;
      }
      exampleAccess.setValue(value);
      return exampleAccess;
   }

   // --------------- CollectionExpr.Visitor ---------------

   @Override
   public Expr visit(ListExpr listExpr, Type par)
   {
      if (par == OBJECT)
      {
         return listExpr;
      }
      if (par == STRING)
      {
         return staticCall(STRING, "valueOf", listExpr, STRING);
      }
      if (!(par instanceof ListType))
      {
         return null;
      }

      final Type elementType = ((ListType) par).getElementType();

      final List<Expr> oldElements = listExpr.getElements();
      final List<Expr> newElements = new ArrayList<>(oldElements.size());

      for (final Expr oldElement : oldElements)
      {
         final Expr newElement = oldElement.accept(this, elementType);
         if (newElement == null)
         {
            return null;
         }

         newElements.add(newElement);
      }

      listExpr.setElements(newElements);
      return listExpr;
   }

   @Override
   public Expr visit(RangeExpr rangeExpr, Type par)
   {
      if (par == OBJECT)
      {
         return rangeExpr;
      }
      if (par == STRING)
      {
         return staticCall(STRING, "valueOf", rangeExpr, STRING);
      }
      if (!(par instanceof ListType))
      {
         return null;
      }

      final Type elementType = ((ListType) par).getElementType();

      final Expr start = rangeExpr.getStart().accept(this, elementType);
      if (start == null)
      {
         return null;
      }

      final Expr end = rangeExpr.getEnd().accept(this, elementType);
      if (end == null)
      {
         return null;
      }

      rangeExpr.setStart(start);
      rangeExpr.setEnd(end);
      return rangeExpr;
   }
}
