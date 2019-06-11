package org.fulib.scenarios.ast.type;

import org.fulib.scenarios.ast.Node;

public enum PrimitiveType implements Type
{
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
   ;

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

   // =============== Classes ===============

   interface Visitor<P, R>
   {
      // =============== Methods ===============
      R visit(PrimitiveType primitiveType, P par);
   }
}
