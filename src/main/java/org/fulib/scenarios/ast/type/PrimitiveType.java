package org.fulib.scenarios.ast.type;

import org.fulib.scenarios.ast.Node;
import org.fulib.scenarios.diagnostic.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PrimitiveType implements Type
{
   ERROR("ERROR"),
   VOID("void"),
   BOOLEAN("boolean"),
   // numeric types
   BYTE("byte"),
   SHORT("short"),
   CHAR("char"),
   INT("int"),
   LONG("long"),
   FLOAT("float"),
   DOUBLE("double"),
   // reference types
   OBJECT("Object"),
   STRING("String"),
   // wrapper types
   VOID_WRAPPER("Void"),
   BOOLEAN_WRAPPER("Boolean"),
   BYTE_WRAPPER("Byte"),
   SHORT_WRAPPER("Short"),
   CHAR_WRAPPER("Character"),
   INT_WRAPPER("Integer"),
   LONG_WRAPPER("Long"),
   FLOAT_WRAPPER("Float"),
   DOUBLE_WRAPPER("Double"),
   ;

   // =============== Constants ===============

   public static final Map<String, PrimitiveType> javaNameMap;

   static
   {
      final PrimitiveType[] values = values();
      final Map<String, PrimitiveType> map = new HashMap<>(values.length);

      for (final PrimitiveType value : values)
      {
         map.put(value.javaName, value);
      }

      javaNameMap = Collections.unmodifiableMap(map);
   }

   // =============== Fields ===============

   private final String javaName;

   // =============== Constructors ===============

   PrimitiveType(String javaName)
   {
      this.javaName = javaName;
   }

   // =============== Properties ===============

   public String getJavaName()
   {
      return this.javaName;
   }

   @Override
   public Position getPosition()
   {
      return null;
   }

   @Override
   public void setPosition(Position position)
   {
      throw new UnsupportedOperationException();
   }

   // =============== Methods ===============

   // --------------- Visitor ---------------

   @Override
   public <P, R> R accept(Node.Visitor<P, R> visitor, P par)
   {
      return visitor.visit(this, par);
   }

   @Override
   public <P, R> R accept(Type.Visitor<P, R> visitor, P par)
   {
      return visitor.visit(this, par);
   }

   public <P, R> R accept(Visitor<P, R> visitor, P par)
   {
      return visitor.visit(this, par);
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
      case VOID: return VOID_WRAPPER;
      case BOOLEAN: return BOOLEAN_WRAPPER;
      case BYTE: return BYTE_WRAPPER;
      case SHORT: return SHORT_WRAPPER;
      case CHAR: return CHAR_WRAPPER;
      case INT: return INT_WRAPPER;
      case LONG: return LONG_WRAPPER;
      case FLOAT: return FLOAT_WRAPPER;
      case DOUBLE: return DOUBLE_WRAPPER;
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

   public static boolean isIntegral(Type type)
   {
      return type instanceof PrimitiveType && isIntegral((PrimitiveType) type);
   }

   public static boolean isIntegral(PrimitiveType type)
   {
      switch (type)
      {
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
      case INT:
      case INT_WRAPPER:
      case LONG:
      case LONG_WRAPPER:
         return true;
      default:
         return false;
      }
   }

   public static boolean isPrimitiveOrWrapperValue(Type type)
   {
      return type instanceof PrimitiveType && isPrimitiveOrWrapperValue((PrimitiveType) type);
   }

   public static boolean isPrimitiveOrWrapperValue(PrimitiveType type)
   {
      final int ordinal = type.ordinal();
      return ordinal >= BOOLEAN.ordinal() && ordinal <= DOUBLE.ordinal()
             || ordinal >= BOOLEAN_WRAPPER.ordinal() && ordinal <= DOUBLE_WRAPPER.ordinal();
   }

   // =============== Classes ===============

   interface Visitor<P, R>
   {
      // =============== Methods ===============
      R visit(PrimitiveType primitiveType, P par);
   }
}
