package org.fulib.scenarios.ast.decl;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.scope.GlobalScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.resolve.TypeResolver;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExternalClassDecl extends ClassDecl.Impl
{
   // =============== Fields ===============

   private final    Object  attributesLock     = new Object();
   private volatile boolean attributesResolved = true;

   // =============== Constructors ===============

   public ExternalClassDecl()
   {
   }

   public ExternalClassDecl(ScenarioGroup group, String name, Type type, Type superType,
      Map<String, AttributeDecl> attributes, Map<String, AssociationDecl> associations, List<MethodDecl> methods)
   {
      super(group, name, type, superType, attributes, associations, methods);
   }

   // =============== Methods ===============

   public void markUnresolved()
   {
      this.attributesResolved = false;
   }

   private void resolveAttributes()
   {
      if (!this.attributesResolved)
      {
         synchronized (this.attributesLock)
         {
            if (!this.attributesResolved)
            {
               this.filterAttributes();
               this.attributesResolved = true;
            }
         }
      }
   }

   private void filterAttributes()
   {
      final Scope scope = new GlobalScope(this.getGroup().getContext());

      for (Iterator<AttributeDecl> iterator = super.getAttributes().values().iterator(); iterator.hasNext(); )
      {
         final AttributeDecl attribute = iterator.next();
         final Type type = attribute.getType().accept(TypeResolver.INSTANCE, scope);
         final ClassDecl otherClass = type.accept(ExtractClassDecl.INSTANCE, null);

         if (otherClass == null)
         {
            attribute.setType(type);
            continue;
         }

         // convert to association
         final int cardinality = type instanceof ListType ? org.fulib.builder.Type.MANY : 1;
         final String name = attribute.getName();
         final AssociationDecl assoc = AssociationDecl.of(this, name, cardinality, otherClass, type, null);

         iterator.remove();
         super.getAssociations().put(name, assoc);
      }
   }

   // =============== Properties ===============

   @Override
   public Map<String, AttributeDecl> getAttributes()
   {
      this.resolveAttributes();
      return super.getAttributes();
   }

   @Override
   public Map<String, AssociationDecl> getAssociations()
   {
      this.resolveAttributes();
      return super.getAssociations();
   }
}
