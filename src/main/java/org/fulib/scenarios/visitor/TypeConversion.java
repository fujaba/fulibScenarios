package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.primary.DoubleLiteral;
import org.fulib.scenarios.ast.expr.primary.IntLiteral;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.fulib.scenarios.ast.type.PrimitiveType.*;

public enum TypeConversion implements Expr.Visitor<Type, Expr>
{
   INSTANCE;

   // =============== Static Methods ===============

   public static boolean isConvertible(Type from, Type to)
   {
      return convert(ErrorExpr.of(from), to) != null;
   }

   public static Expr convert(Expr expr, Type to)
   {
      return expr.accept(INSTANCE, to);
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
      final Type from = expr.accept(Typer.INSTANCE, null);
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
            return methodCall(expr, "charAt", IntLiteral.of(1), to);
         }
      }
      if (from == Typer.primitiveToWrapper(to) || to == Typer.primitiveToWrapper(from))
      {
         return expr;
      }

      return null;
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
      case INT:
      case INT_WRAPPER:
      case LONG:
      case LONG_WRAPPER:
      case FLOAT:
      case FLOAT_WRAPPER:
      case DOUBLE:
      case DOUBLE_WRAPPER:
         return intLiteral;
      case STRING:
         return StringLiteral.of(Integer.toString(intLiteral.getValue()));
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
         return StringLiteral.of(Double.toString(doubleLiteral.getValue()));
      }

      return null;
   }
}
