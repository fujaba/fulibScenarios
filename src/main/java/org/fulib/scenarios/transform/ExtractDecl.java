package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;

public enum ExtractDecl implements Name.Visitor<Object, Decl>
{
   INSTANCE;

   @Override
   public Decl visit(Name name, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Decl visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl();
   }

   @Override
   public Decl visit(UnresolvedName unresolvedName, Object par)
   {
      return null;
   }
}
