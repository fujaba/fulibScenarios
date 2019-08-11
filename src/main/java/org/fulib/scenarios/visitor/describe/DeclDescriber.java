package org.fulib.scenarios.visitor.describe;

import org.fulib.scenarios.ast.decl.AssociationDecl;
import org.fulib.scenarios.ast.decl.AttributeDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;

public enum DeclDescriber implements Decl.Visitor<Void, String>
{
   INSTANCE;

   private static String visit(Type type, String oneKey, String manyKey)
   {
      if (type instanceof ListType)
      {
         final Type elementType = ((ListType) type).getElementType();
         return Marker.localize(manyKey, elementType.accept(TypeDescriber.INSTANCE, null));
      }
      return Marker.localize(oneKey, type.accept(TypeDescriber.INSTANCE, null));
   }

   @Override
   public String visit(AttributeDecl attributeDecl, Void par)
   {
      final Type type = attributeDecl.getType();
      return visit(type, "attribute.desc.1", "attribute.desc.*");
   }

   @Override
   public String visit(AssociationDecl associationDecl, Void par)
   {
      final Type type = associationDecl.getType();
      return visit(type, "association.desc.1", "association.desc.*");
   }
}

