package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.ScenarioGroup;
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
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.TypeConversion;
import org.fulib.scenarios.visitor.describe.DeclDescriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.diagnostic.Marker.note;

public class DeclResolver
{
   // =============== Constants ===============

   protected static final String ENCLOSING_CLASS = "<enclosing:class>";
   private static final int UNRESOLVED_HINT_DISTANCE_THRESHOLD = 3;

   // =============== Static Methods ===============

   public static ScenarioGroup resolveGroup(CompilationContext context, String packageDir)
   {
      return context.getGroups().computeIfAbsent(packageDir, p -> {
         return ScenarioGroup.of(context, null, p, new HashMap<>(), new ConcurrentHashMap<>());
      });
   }

   // --------------- Classes ---------------

   static ClassDecl getEnclosingClass(Scope scope)
   {
      return (ClassDecl) scope.resolve(ENCLOSING_CLASS);
   }

   static ClassDecl resolveClass(Scope scope, String name, Position position)
   {
      /* TODO find an example that causes this warning.
              it "should" not appear because class names are normalized to UpperCamelCase while all other
              declarations are lowerCamelCase.
      scope.report(warning(position, "class.name.shadow.other.decl", name, kindString(resolved)));
       */
      return scope.resolve(name, ClassDecl.class, n -> {
         final ClassDecl decl = ClassDecl
            .of(null, name, null, PrimitiveType.OBJECT, new LinkedHashMap<>(), new LinkedHashMap<>(),
                new ArrayList<>());
         decl.setPosition(position);
         decl.setType(ClassType.of(decl));
         return decl;
      });
   }

   // --------------- Methods ---------------

   private static MethodDecl getMethod(ClassDecl owner, String name)
   {
      while (true)
      {
         final MethodDecl decl = getOwnMethod(owner, name);
         if (decl != null)
         {
            return decl;
         }

         final Type superType = owner.getSuperType();
         if (superType == null || (owner = superType.accept(ExtractClassDecl.INSTANCE, null)) == null)
         {
            return null;
         }
      }
   }

   private static MethodDecl getOwnMethod(ClassDecl owner, String name)
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
         return null;
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "method.unresolved.frozen", name, owner.getName()));
         return null;
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
      return getAttributeOrAssociation(scope, receiver, receiver.getType(), name);
   }

   static Name getAttributeOrAssociation(Scope scope, Type owner, Name name)
   {
      return getAttributeOrAssociation(scope, null, owner, name);
   }

   static Name getAttributeOrAssociation(Scope scope, Expr receiver, Type owner, Name name)
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

      final Marker error = error(name.getPosition(), "property.unresolved.primitive", name.getValue(),
                                 owner.getDescription());
      if (receiver != null)
      {
         SentenceResolver.addStringLiteralTypoNotes(scope, receiver, error);
      }
      scope.report(error);
      return name;
   }

   static Name getAttributeOrAssociation(Scope scope, ClassDecl owner, Name name)
   {
      if (name.getDecl() != null)
      {
         return name;
      }

      final String nameValue = name.getValue();
      final Decl decl = getAttributeOrAssociation(owner, nameValue);

      if (decl != null)
      {
         return ResolvedName.of(decl);
      }

      final Position position = name.getPosition();
      final Marker error = error(position, "property.unresolved", owner.getName(), nameValue);
      Stream
         .concat(owner.getAttributes().keySet().stream(), owner.getAssociations().keySet().stream())
         .filter(SentenceResolver.caseInsensitiveLevenshteinDistance(nameValue, UNRESOLVED_HINT_DISTANCE_THRESHOLD))
         .forEach(suggestion -> error.note(note(position, "property.typo", suggestion, nameValue)));
      scope.report(error);
      return name; // unresolved
   }

   static Decl getAttributeOrAssociation(ClassDecl owner, String name)
   {
      final AttributeDecl attribute = getAttribute(owner, name);
      return attribute != null ? attribute : getAssociation(owner, name);
   }

   static Decl resolveAttributeOrAssociation(Scope scope, ClassDecl classDecl, String attributeName, Expr rhs,
      Position position)
   {
      final Type attributeType = rhs.getType();

      if (attributeType instanceof ListType)
      {
         // new value is multi-valued

         final Type otherType = ((ListType) attributeType).getElementType();
         if (otherType instanceof ClassType)
         {
            return resolveAssociation(scope, classDecl, attributeName, org.fulib.builder.Type.MANY,
                                      ((ClassType) otherType).getClassDecl(), position, rhs);
         }
         else
         {
            // was element type that we have no control over, e.g. List<String>
            return resolveAttribute(scope, classDecl, attributeName, attributeType, position, rhs);
         }
      }
      else if (attributeType instanceof ClassType)
      {
         return resolveAssociation(scope, classDecl, attributeName, 1, ((ClassType) attributeType).getClassDecl(),
                                   position, rhs);
      }
      else
      {
         return resolveAttribute(scope, classDecl, attributeName, attributeType, position, rhs);
      }
   }

   // --------------- Attributes ---------------

   static AttributeDecl getAttribute(ClassDecl owner, String name)
   {
      while (true)
      {
         final AttributeDecl decl = getOwnAttribute(owner, name);
         if (decl != null)
         {
            return decl;
         }

         final Type superType = owner.getSuperType();
         if (superType == null || (owner = superType.accept(ExtractClassDecl.INSTANCE, null)) == null)
         {
            return null;
         }
      }
   }

   static AttributeDecl getOwnAttribute(ClassDecl owner, String name)
   {
      return owner.getAttributes().get(name);
   }

   static Decl resolveAttribute(Scope scope, ClassDecl owner, String name, Type type, Position position, Expr rhs)
   {
      final AttributeDecl existingAttribute = getAttribute(owner, name);
      if (existingAttribute != null)
      {
         final Type existingType = existingAttribute.getType();
         if (type != PrimitiveType.ERROR && existingType != PrimitiveType.ERROR //
             && !TypeConversion.isConvertible(type, existingType))
         {
            final String newDesc = DeclDescriber.describeAttribute(type);
            final Marker conflict = conflict(position, owner, name, existingAttribute, newDesc);
            SentenceResolver.addStringLiteralTypoNotes(scope, rhs, conflict);
            scope.report(conflict);
         }

         return existingAttribute;
      }
      final AssociationDecl existingAssociation = getAssociation(owner, name);
      if (existingAssociation != null)
      {
         final String newDesc = DeclDescriber.describeAttribute(type);
         final Marker conflict = conflict(position, owner, name, existingAssociation, newDesc);
         SentenceResolver.addStringLiteralTypoNotes(scope, rhs, conflict);
         scope.report(conflict);

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
         return null;
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "attribute.unresolved.frozen", name, owner.getName()));
         return null;
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

   static AssociationDecl getAssociation(ClassDecl owner, String name)
   {
      return owner.getAssociations().get(name);
   }

   static AssociationDecl resolveAssociation(Scope scope, ClassDecl owner, String name, int cardinality,
      ClassDecl otherClass, Position position, Expr rhs)
   {
      return resolveAssociation(scope, owner, name, cardinality, otherClass, null, 0, position, null, rhs);
   }

   static AssociationDecl resolveAssociation(Scope scope, ClassDecl owner, String name, int cardinality,
      ClassDecl otherClass, String otherName, int otherCardinality, Position position, Position otherPosition, Expr rhs)
   {
      final AttributeDecl existingAttribute = getAttribute(owner, name);
      if (existingAttribute != null)
      {
         final String newDesc = DeclDescriber.describeAssociation(cardinality, otherClass);
         final Marker conflict = conflict(position, owner, name, existingAttribute, newDesc);
         SentenceResolver.addStringLiteralTypoNotes(scope, rhs, conflict);
         scope.report(conflict);

         return null;
      }

      final AssociationDecl existing = getAssociation(owner, name);
      if (existing != null)
      {
         // uses < because redeclaration as to-one when it was to-many is ok.
         // TODO investigate this claim

         if (existing.getTarget() != otherClass || existing.getCardinality() < cardinality)
         {
            final String newDesc = DeclDescriber.describeAssociation(cardinality, otherClass);
            final Marker conflict = conflict(position, owner, name, existing, newDesc);
            SentenceResolver.addStringLiteralTypoNotes(scope, rhs, conflict);
            scope.report(conflict);
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
               final Marker error = error(otherPosition, "association.reverse.conflict", owner.getName(), name);
               error.note(note(otherPosition, "conflict.old",
                               otherClass.getName() + "." + other.getName() + ", " + existingDesc));
               error.note(note(otherPosition, "conflict.new", otherClass.getName() + "." + otherName + ", " + newDesc));
               error.note(firstDeclaration(other.getPosition(), other.getOwner(), other.getName()));
               scope.report(error);
            }
         }

         return existing;
      }

      final AssociationDecl association = createAssociation(scope, position, owner, name, cardinality, otherClass);
      if (association == null)
      {
         // class was external or frozen, error already reported
         return null;
      }

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
         if (other != null)
         {
            association.setOther(other);
            other.setOther(association);
         }
      }

      return association;
   }

   private static AssociationDecl createAssociation(Scope scope, Position position, ClassDecl owner, String name,
      int cardinality, ClassDecl target)
   {
      if (owner.getExternal())
      {
         scope.report(error(position, "association.unresolved.external", name, owner.getName()));
         return null;
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "association.unresolved.frozen", name, owner.getName()));
         return null;
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
      final Marker error = error(position, "property.redeclaration.conflict", owner.getName(), name);
      error.note(note(position, "conflict.old", existingDesc));
      error.note(note(position, "conflict.new", newDesc));

      final Position existingPosition = existing.getPosition();
      if (existingPosition != null)
      {
         error.note(firstDeclaration(existingPosition, owner, name));
      }

      return error;
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
