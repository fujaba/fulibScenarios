package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;

public enum ExtractClassDecl implements Type.Visitor<Void, ClassDecl>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public ClassDecl visit(ClassType classType, Void par)
   {
      return classType.getClassDecl();
   }

   @Override
   public ClassDecl visit(ListType listType, Void par)
   {
      return listType.getElementType().accept(this, par);
   }
}
