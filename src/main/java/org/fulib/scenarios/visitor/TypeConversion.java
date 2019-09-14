package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.primary.DoubleLiteral;
import org.fulib.scenarios.ast.expr.primary.IntLiteral;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.Collections;

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

   private static Expr staticCall(Type type, String method, Expr arg)
   {
      final NameAccess receiver = NameAccess.of(UnresolvedName.of(type.accept(Namer.INSTANCE, null), null));
      return methodCall(receiver, method, arg);
   }

   private static Expr methodCall(Expr receiver, String method, Expr arg)
   {
      return CallExpr
                .of(UnresolvedName.of(method, null), receiver, Collections.singletonList(NamedExpr.of(null, arg)),
                    null);
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
         return staticCall(STRING, "valueOf", expr);
      }
      if (from == STRING && to instanceof PrimitiveType)
      {
         switch ((PrimitiveType) to)
         {
         case BYTE:
            return staticCall(BYTE_WRAPPER, "parseByte", expr);
         case SHORT:
            return staticCall(SHORT_WRAPPER, "parseShort", expr);
         case INT:
            return staticCall(INT_WRAPPER, "parseInt", expr);
         case LONG:
            return staticCall(LONG_WRAPPER, "parseLong", expr);
         case FLOAT:
            return staticCall(FLOAT_WRAPPER, "parseFloat", expr);
         case DOUBLE:
            return staticCall(DOUBLE_WRAPPER, "parseDouble", expr);
         case BYTE_WRAPPER:
         case SHORT_WRAPPER:
         case INT_WRAPPER:
         case LONG_WRAPPER:
         case FLOAT_WRAPPER:
         case DOUBLE_WRAPPER:
            return staticCall(to, "valueOf", expr);
         case CHAR:
         case CHAR_WRAPPER:
            // <expr>.charAt(0)
            return methodCall(expr, "charAt", IntLiteral.of(1));
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
