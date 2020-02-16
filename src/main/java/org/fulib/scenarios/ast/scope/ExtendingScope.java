package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExtendingScope extends DelegatingScope
{
   private final Map<String, ? extends Decl> names;

   public ExtendingScope(Decl decl, Scope outer)
   {
      this(decl.getName(), decl, outer);
   }

   public ExtendingScope(String name, Decl decl, Scope outer)
   {
      super(outer);
      this.names = Collections.singletonMap(name, decl);
   }

   public ExtendingScope(Decl[] decls, Scope outer)
   {
      this(buildMap(decls), outer);
   }

   public ExtendingScope(Map<String, ? extends Decl> names, Scope outer)
   {
      super(outer);
      this.names = names;
   }

   private static Map<String, Decl> buildMap(Decl[] decls)
   {
      return Arrays.stream(decls).collect(Collectors.toMap(Decl::getName, Function.identity()));
   }

   @Override
   public Decl resolve(String name)
   {
      if (this.names.containsKey(name))
      {
         return this.names.get(name);
      }
      return super.resolve(name);
   }
}
