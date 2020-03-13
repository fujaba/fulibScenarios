package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.pattern.Pattern;

import java.util.Set;

public class PatternReferenceCollectingScope extends DelegatingScope
{
   private final Set<Pattern> patterns;

   public PatternReferenceCollectingScope(Scope outer, Set<Pattern> patterns)
   {
      super(outer);
      this.patterns = patterns;
   }

   @Override
   public Decl resolve(String name)
   {
      final Decl result = super.resolve(name);
      if (result instanceof VarDecl)
      {
         final Pattern pattern = ((VarDecl) result).getPattern();
         if (pattern != null)
         {
            this.patterns.add(pattern);
         }
      }
      return result;
   }
}
