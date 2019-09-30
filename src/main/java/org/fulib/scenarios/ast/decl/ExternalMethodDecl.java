package org.fulib.scenarios.ast.decl;

import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.Type;

import java.util.List;

public class ExternalMethodDecl extends MethodDecl.Impl
{
   // =============== Fields ===============

   private final LazyType type = new LazyType();

   // =============== Constructors ===============

   public ExternalMethodDecl()
   {
   }

   public ExternalMethodDecl(ClassDecl owner, String name, List<ParameterDecl> parameters, Type type,
      SentenceList body)
   {
      // super constructor sets Impl.type field instead of calling setter.
      super(owner, name, parameters, null, body);
      this.setType(type);
   }

   // =============== Properties ===============

   @Override
   public Type getType()
   {
      return this.type.get(this.getOwner().getGroup());
   }

   @Override
   public void setType(Type type)
   {
      this.type.set(type);
   }
}
