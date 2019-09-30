package org.fulib.scenarios.ast.decl;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.scope.EmptyScope;
import org.fulib.scenarios.ast.scope.GroupScope;
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

   private volatile boolean attributesResolved;

   // =============== Constructors ===============

   public ExternalClassDecl()
   {
   }

   public ExternalClassDecl(ScenarioGroup group, String name, Type type, Map<String, AttributeDecl> attributes,
      Map<String, AssociationDecl> associations, List<MethodDecl> methods)
   {
      super(group, name, type, attributes, associations, methods);
   }

   // =============== Methods ===============

   private void resolveAttributes()
   {
      if (!this.attributesResolved)
      {
         synchronized (this)
         {
            if (!this.attributesResolved)
            {
               this.attributesResolved = true;
               this.filterAttributes();
            }
         }
      }
   }

   private void filterAttributes()
   {
      final Scope scope = new GroupScope(EmptyScope.INSTANCE, this.getGroup());

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
         final int cardinality = type instanceof ListType ? ClassModelBuilder.MANY : 1;
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
