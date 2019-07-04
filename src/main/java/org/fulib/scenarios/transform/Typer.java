package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.FilterExpr;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

public enum Typer implements Expr.Visitor<Object, Type>, Name.Visitor<Object, Type>
{
   INSTANCE;

   // --------------- Expr.Visitor ---------------

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
   public Type visit(FilterExpr filterExpr, Object par)
   {
      return filterExpr.getSource().accept(this, par);
   }

   @Override
   public Type visit(CreationExpr creationExpr, Object par)
   {
      return creationExpr.getType();
   }

   @Override
   public Type visit(CallExpr callExpr, Object par)
   {
      final Expr expr = callExpr.getBody().accept(ReturnExpr.INSTANCE, par);
      return expr != null ? expr.accept(Typer.INSTANCE, par) : PrimitiveType.VOID;
   }

   @Override
   public Type visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public Type visit(NumberLiteral numberLiteral, Object par)
   {
      return PrimitiveType.DOUBLE;
   }

   @Override
   public Type visit(StringLiteral stringLiteral, Object par)
   {
      return PrimitiveType.STRING;
   }

   @Override
   public Type visit(ConditionalExpr conditionalExpr, Object par)
   {
      return PrimitiveType.BOOLEAN;
   }

   @Override
   public Type visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      return PrimitiveType.BOOLEAN;
   }

   @Override
   public Type visit(ListAttributeAccess listAttributeAccess, Object par)
   {
      final Type attributeType = listAttributeAccess.getName().accept(ExtractDecl.INSTANCE, null).getType();
      return ListType.of(attributeType);
   }

   @Override
   public Type visit(ConditionalOperatorExpr conditionalOperatorExpr, Object par)
   {
      return PrimitiveType.BOOLEAN;
   }

   @Override
   public Type visit(ListExpr listExpr, Object par)
   {
      Type commonType = null;
      for (Expr element : listExpr.getElements())
      {
         final Type elementType = element.accept(this, par);
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
      final Type wrapperType = primitiveToWrapper(commonType);
      return ListType.of(wrapperType);
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Type visit(UnresolvedName unresolvedName, Object par)
   {
      // TODO diagnostic
      throw new IllegalStateException("unresolved name " + unresolvedName.getValue());
   }

   @Override
   public Type visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl().getType();
   }

   // =============== Static Methods ===============

   public static Type primitiveToWrapper(Type primitive)
   {
      if (!(primitive instanceof PrimitiveType))
      {
         return primitive;
      }

      switch ((PrimitiveType) primitive)
      {
      // @formatter:off
      case VOID: return PrimitiveType.VOID_WRAPPER;
      case BOOLEAN: return PrimitiveType.BOOLEAN_WRAPPER;
      case BYTE: return PrimitiveType.BYTE_WRAPPER;
      case SHORT: return PrimitiveType.SHORT_WRAPPER;
      case CHAR: return PrimitiveType.CHAR_WRAPPER;
      case INT: return PrimitiveType.INT_WRAPPER;
      case LONG: return PrimitiveType.LONG_WRAPPER;
      case FLOAT: return PrimitiveType.FLOAT_WRAPPER;
      case DOUBLE: return PrimitiveType.DOUBLE_WRAPPER;
      default: return primitive;
      // @formatter:on
      }
   }

   public static boolean isNumeric(Type type)
   {
      if (!(type instanceof PrimitiveType))
      {
         return false;
      }

      switch ((PrimitiveType) type)
      {
         // @formatter:off
         case BYTE: case BYTE_WRAPPER:
         case SHORT: case SHORT_WRAPPER:
         case CHAR: case CHAR_WRAPPER:
         case INT: case INT_WRAPPER:
         case LONG: case LONG_WRAPPER:
         case FLOAT: case FLOAT_WRAPPER:
         case DOUBLE: case DOUBLE_WRAPPER:
         // @formatter:on
         return true;
      }
      return false;
   }
}
