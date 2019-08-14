package org.fulib.scenarios.visitor.resolve;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.visitor.*;
import org.fulib.scenarios.visitor.describe.DeclDescriber;
import org.fulib.scenarios.visitor.describe.TypeDescriber;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.fulib.scenarios.diagnostic.Marker.*;

public class DeclResolver
{
   // =============== Constants ===============

   protected static final String ENCLOSING_CLASS = "<enclosing:class>";

   // =============== Static Methods ===============

   // --------------- Classes ---------------

   static ClassDecl getEnclosingClass(Scope scope)
   {
      return (ClassDecl) scope.resolve(ENCLOSING_CLASS);
   }

   static ClassDecl resolveClass(Scope scope, String name, Position position)
   {
      final Decl resolved = scope.resolve(name);
      if (resolved instanceof ClassDecl)
      {
         return (ClassDecl) resolved;
      }
      if (resolved != null)
      {
         // TODO find an example that causes this warning.
         //      it "should" not appear because class names are normalized to UpperCamelCase while all other
         //      declarations are lowerCamelCase.
         scope.report(warning(position, "class.name.shadow.other.decl", name, kindString(resolved)));
      }

      final ClassDecl decl = ClassDecl.of(null, name, null, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                          new ArrayList<>());
      decl.setPosition(position);
      decl.setExternal(getEnclosingClass(scope).getExternal());
      decl.setType(ClassType.of(decl));
      scope.add(decl);
      return decl;
   }

   // --------------- Methods ---------------

   private static MethodDecl getMethod(ClassDecl owner, String name)
   {
      for (final MethodDecl decl : owner.getMethods())
      {
         if (name.equals(decl.getName()))
         {
            return decl;
         }
      }
      return null;
   }

   static MethodDecl resolveMethod(Scope scope, Position position, ClassDecl owner, String name)
   {
      final MethodDecl existing = getMethod(owner, name);
      return existing != null ? existing : createMethod(scope, position, owner, name);
   }

   private static MethodDecl createMethod(Scope scope, Position position, ClassDecl owner, String name)
   {
      if (owner.getExternal())
      {
         scope.report(error(position, "method.unresolved.external", name, owner.getName()));
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "method.unresolved.frozen", name, owner.getName()));
      }

      return createMethod(position, owner, name);
   }

   private static MethodDecl createMethod(Position position, ClassDecl owner, String name)
   {
      final SentenceList body = SentenceList.of(new ArrayList<>());
      final MethodDecl decl = MethodDecl.of(owner, name, new ArrayList<>(), null, body);
      decl.setPosition(position);
      owner.getMethods().add(decl);
      return decl;
   }

   // --------------- Properties ---------------

   static Name getAttributeOrAssociation(Scope scope, Expr receiver, Name name)
   {
      final Type type = receiver.accept(Typer.INSTANCE, null);
      return getAttributeOrAssociation(scope, type, name);
   }

   static Name getAttributeOrAssociation(Scope scope, Type owner, Name name)
   {
      if (owner == PrimitiveType.ERROR)
      {
         return name;
      }

      final ClassDecl ownerClass = owner.accept(ExtractClassDecl.INSTANCE, null);
      if (ownerClass != null)
      {
         return getAttributeOrAssociation(scope, ownerClass, name);
      }

      scope.report(
         error(name.getPosition(), "property.unresolved.primitive", owner.accept(TypeDescriber.INSTANCE, null),
               name.accept(Namer.INSTANCE, null)));
      return name;
   }

   static Name getAttributeOrAssociation(Scope scope, ClassDecl owner, Name name)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         return name;
      }

      final String nameValue = name.accept(Namer.INSTANCE, null);
      final Decl decl = getAttributeOrAssociation(owner, nameValue);

      if (decl != null)
      {
         return ResolvedName.of(decl);
      }

      scope.report(error(name.getPosition(), "property.unresolved", owner.getName(), nameValue));
      return name; // unresolved
   }

   static Decl getAttributeOrAssociation(ClassDecl owner, String name)
   {
      final AttributeDecl attribute = owner.getAttributes().get(name);
      return attribute != null ? attribute : owner.getAssociations().get(name);
   }

   static Name resolveAttributeOrAssociation(Scope scope, ClassDecl classDecl, Name name, Expr rhs)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         // already resolved
         return name;
      }

      final Decl decl = resolveAttributeOrAssociation(scope, classDecl, name.accept(Namer.INSTANCE, null), rhs,
                                                      name.getPosition());
      return decl != null ? ResolvedName.of(decl) : name;
   }

   static Decl resolveAttributeOrAssociation(Scope scope, ClassDecl classDecl, String attributeName, Expr rhs,
      Position position)
   {
      final Type attributeType = rhs.accept(Typer.INSTANCE, null);

      if (attributeType instanceof ListType)
      {
         // new value is multi-valued

         final Type otherType = ((ListType) attributeType).getElementType();
         if (otherType instanceof ClassType)
         {
            return resolveAssociation(scope, classDecl, attributeName, ClassModelBuilder.MANY,
                                      ((ClassType) otherType).getClassDecl(), position);
         }
         else
         {
            // was element type that we have no control over, e.g. List<String>
            return resolveAttribute(scope, classDecl, attributeName, attributeType, position);
         }
      }
      else if (attributeType instanceof ClassType)
      {
         return resolveAssociation(scope, classDecl, attributeName, 1, ((ClassType) attributeType).getClassDecl(),
                                   position);
      }
      else
      {
         return resolveAttribute(scope, classDecl, attributeName, attributeType, position);
      }
   }

   // --------------- Attributes ---------------

   static Decl resolveAttribute(Scope scope, ClassDecl owner, String name, Type type, Position position)
   {
      final AttributeDecl existingAttribute = owner.getAttributes().get(name);
      if (existingAttribute != null)
      {
         final Type existingType = existingAttribute.getType();
         if (!TypeComparer.equals(type, existingType))
         {
            final String newDesc = DeclDescriber.describeAttribute(type);
            scope.report(conflict(position, owner, name, existingAttribute, newDesc));
         }

         return existingAttribute;
      }
      final AssociationDecl existingAssociation = owner.getAssociations().get(name);
      if (existingAssociation != null)
      {
         final String newDesc = DeclDescriber.describeAttribute(type);
         scope.report(conflict(position, owner, name, existingAssociation, newDesc));

         return existingAssociation;
      }

      return createAttribute(scope, position, owner, name, type);
   }

   private static AttributeDecl createAttribute(Scope scope, Position position, ClassDecl owner, String name,
      Type type)
   {
      if (owner.getExternal())
      {
         scope.report(error(position, "attribute.unresolved.external", name, owner.getName()));
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "attribute.unresolved.frozen", name, owner.getName()));
      }

      return createAttribute(position, owner, name, type);
   }

   private static AttributeDecl createAttribute(Position position, ClassDecl owner, String name, Type type)
   {
      final AttributeDecl attribute = AttributeDecl.of(owner, name, type);
      attribute.setPosition(position);
      owner.getAttributes().put(name, attribute);
      return attribute;
   }

   // --------------- Associations ---------------

   static AssociationDecl resolveAssociation(Scope scope, ClassDecl owner, String name, int cardinality,
      ClassDecl otherClass, Position position)
   {
      return resolveAssociation(scope, owner, name, cardinality, otherClass, null, 0, position, null);
   }

   static AssociationDecl resolveAssociation(Scope scope, ClassDecl owner, String name, int cardinality,
      ClassDecl otherClass, String otherName, int otherCardinality, Position position, Position otherPosition)
   {
      final AttributeDecl existingAttribute = owner.getAttributes().get(name);
      if (existingAttribute != null)
      {
         final String newDesc = DeclDescriber.describeAssociation(cardinality, otherClass);
         scope.report(conflict(position, owner, name, existingAttribute, newDesc));

         return null;
      }

      final AssociationDecl existing = owner.getAssociations().get(name);
      if (existing != null)
      {
         // uses < because redeclaration as to-one when it was to-many is ok.
         // TODO investigate this claim

         if (existing.getTarget() != otherClass || existing.getCardinality() < cardinality)
         {
            final String newDesc = DeclDescriber.describeAssociation(cardinality, otherClass);
            scope.report(conflict(position, owner, name, existing, newDesc));
         }
         else if (otherName != null)
         {
            final AssociationDecl other = existing.getOther();
            if (other == null)
            {
               final Marker error = error(otherPosition, "association.reverse.late", otherName, owner.getName(),
                                          name);
               final Marker note = firstDeclaration(existing.getPosition(), owner, name);
               scope.report(error.note(note));
            }
            else if (!otherName.equals(other.getName()) || otherCardinality != other.getCardinality())
            {
               final String existingDesc = other.accept(DeclDescriber.INSTANCE, null);
               final String newDesc = DeclDescriber.describeAssociation(otherCardinality, owner);
               final Marker error = error(otherPosition, "association.reverse.conflict", owner.getName(), name,
                                          otherClass.getName(), other.getName(), existingDesc,
                                          otherClass.getName(), otherName, newDesc);
               final Marker note = firstDeclaration(other.getPosition(), other.getOwner(), other.getName());
               scope.report(error.note(note));
            }
         }

         return existing;
      }

      final AssociationDecl association = createAssociation(scope, position, owner, name, cardinality, otherClass);

      if (otherClass == owner && name.equals(otherName))
      {
         if (cardinality != otherCardinality)
         {
            scope.report(error(position, "association.self.cardinality.mismatch", owner.getName(), name));
         }

         // self-association
         association.setOther(association);
      }
      else if (otherName != null)
      {
         final AssociationDecl other = createAssociation(scope, otherPosition, otherClass, otherName,
                                                         otherCardinality, owner);

         association.setOther(other);
         other.setOther(association);
      }

      return association;
   }

   private static AssociationDecl createAssociation(Scope scope, Position position, ClassDecl owner, String name,
      int cardinality, ClassDecl target)
   {
      if (owner.getExternal())
      {
         scope.report(error(position, "association.unresolved.external", name, owner.getName()));
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "association.unresolved.frozen", name, owner.getName()));
      }

      return createAssociation(position, owner, name, cardinality, target);
   }

   private static AssociationDecl createAssociation(Position position, ClassDecl owner, String name, int cardinality,
      ClassDecl target)
   {
      final Type type = createType(cardinality, target);
      final AssociationDecl association = AssociationDecl.of(owner, name, cardinality, target, type, null);
      association.setPosition(position);

      owner.getAssociations().put(association.getName(), association);
      return association;
   }

   // --------------- Helpers ---------------

   private static Type createType(int cardinality, ClassDecl target)
   {
      return cardinality != 1 ? ListType.of(target.getType()) : target.getType();
   }

   static Marker firstDeclaration(Position position, ClassDecl owner, String name)
   {
      return note(position, "property.declaration.first", owner.getName(), name);
   }

   private static Marker conflict(Position position, ClassDecl owner, String name, Decl existing, String newDesc)
   {
      final String existingDesc = existing.accept(DeclDescriber.INSTANCE, null);
      final Marker error = error(position, "property.redeclaration.conflict", owner.getName(), name, existingDesc,
                                 newDesc);
      final Marker note = firstDeclaration(existing.getPosition(), owner, name);

      return error.note(note);
   }

   private static String kindString(Decl decl)
   {
      final String simpleName = decl.getClass().getEnclosingClass().getSimpleName();
      final String stripped = simpleName.endsWith("Decl") ?
                                 simpleName.substring(0, simpleName.length() - 4) :
                                 simpleName;
      final String key = stripped.toLowerCase() + ".kind";
      return Marker.localize(key);
   }
}
