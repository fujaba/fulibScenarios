package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.*;

import static org.fulib.scenarios.visitor.resolve.DeclResolver.resolveClass;

public enum TypeResolver implements Type.Visitor<Scope, Type>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public Type visit(UnresolvedType unresolvedType, Scope par)
   {
      final String primitiveName = this.getPrimitiveNameFromText(unresolvedType);

      final PrimitiveType primitive = PrimitiveType.fromJavaName(primitiveName);
      if (primitive != null)
      {
         return primitive;
      }

      final String name = unresolvedType.getName();
      final String singularName = unresolvedType.getPlural() ? depluralize(name) : name;

      final PrimitiveType primitive2 = PrimitiveType.fromJavaName(singularName);
      if (primitive2 != null)
      {
         return primitive2;
      }

      return resolveClass(par, singularName, unresolvedType.getPosition()).getType();
   }

   private String getPrimitiveNameFromText(UnresolvedType unresolvedType)
   {
      final String primitiveName;
      final String text = unresolvedType.getText();
      if (text.startsWith("java/lang/"))
      {
         primitiveName = text.substring(10);
      }
      else if (unresolvedType.getPlural())
      {
         primitiveName = depluralize(text);
      }
      else
      {
         primitiveName = text;
      }
      return primitiveName;
   }

   private static String depluralize(String caps)
   {
      return caps.endsWith("s") ? caps.substring(0, caps.length() - 1) : caps;
   }

   @Override
   public Type visit(PrimitiveType primitiveType, Scope par)
   {
      return primitiveType;
   }

   @Override
   public Type visit(ClassType classType, Scope par)
   {
      return classType;
   }

   @Override
   public Type visit(ListType listType, Scope par)
   {
      listType.setElementType(listType.getElementType().accept(this, par));
      return listType;
   }
}
