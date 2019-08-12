package org.fulib.scenarios.visitor.describe;

import org.fulib.scenarios.ast.decl.AssociationDecl;
import org.fulib.scenarios.ast.decl.AttributeDecl;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;

public enum DeclDescriber implements Decl.Visitor<Void, String>
{
   INSTANCE;

   // =============== Methods ===============

   @Override
   public String visit(AttributeDecl attributeDecl, Void par)
   {
      return describeAttribute(attributeDecl.getType());
   }

   @Override
   public String visit(AssociationDecl associationDecl, Void par)
   {
      return describeAssociation(associationDecl.getCardinality(), associationDecl.getTarget());
   }

   // =============== Static Methods ===============

   public static String describeAttribute(Type type)
   {
      if (type instanceof ListType)
      {
         final Type elementType = ((ListType) type).getElementType();
         return Marker.localize("attribute.desc.*", elementType.accept(TypeDescriber.INSTANCE, null));
      }
      return Marker.localize("attribute.desc.1", type.accept(TypeDescriber.INSTANCE, null));
   }

   public static String describeAssociation(int cardinality, ClassDecl target)
   {
      return Marker.localize(cardinality == 1 ? "association.desc.1" : "association.desc.*", target.getName());
   }
}
