package org.fulib.scenarios.visitor.resolve;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.EmptyScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.parser.Identifiers;
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;
import org.fulib.scenarios.visitor.describe.DeclDescriber;
import org.fulib.scenarios.visitor.describe.TypeDescriber;

import java.util.*;

import static org.fulib.scenarios.diagnostic.Marker.*;

public enum NameResolver implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<Scope, Object>,
                                       ScenarioFile.Visitor<Scope, Object>, Scenario.Visitor<Scope, Object>,
                                       Name.Visitor<Scope, Name>
{
   INSTANCE;

   // =============== Constants ===============

   protected static final String ENCLOSING_CLASS    = "<enclosing:class>";
   protected static final String PREDICATE_RECEIVER = "<predicate-receiver>";

   // =============== Methods ===============

   // --------------- CompilationContext.Visitor ---------------

   @Override
   public Object visit(CompilationContext compilationContext, Object par)
   {
      final Map<String, ScenarioGroup> groups = compilationContext.getGroups();
      final Set<ScenarioGroup> importedGroups = new HashSet<>();
      final Map<String, ClassDecl> importedClasses = new HashMap<>();

      // first, process all groups that are imported.
      for (final String packageName : compilationContext.getConfig().getImports())
      {
         final String packageDir = packageName.replace('.', '/');
         final ScenarioGroup group = groups.get(packageDir);
         if (group != null)
         {
            group.accept(this, EmptyScope.INSTANCE);
            importedGroups.add(group);
            importedClasses.putAll(group.getClasses());
         }
      }

      final Scope importedScope = new Scope()
      {
         @Override
         public Decl resolve(String name)
         {
            return importedClasses.get(name);
         }

         @Override
         public void add(Decl decl)
         {
         }

         @Override
         public void report(Marker marker)
         {
         }
      };

      // then, process remaining groups.
      // since there are no more inter-group references, we can parallelize.
      groups.values().parallelStream().filter(o -> !importedGroups.contains(o))
            .forEach(it -> it.accept(this, importedScope));
      return null;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Scope par)
   {
      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            final ClassDecl classDecl = scenarioGroup.getClasses().get(name);
            return classDecl != null ? classDecl : super.resolve(name);
         }

         @Override
         public void add(Decl decl)
         {
            final ClassDecl classDecl = (ClassDecl) decl;
            classDecl.setGroup(scenarioGroup);
            scenarioGroup.getClasses().put(decl.getName(), classDecl);
         }
      };

      // first, process and freeze external classes.
      for (final ClassDecl classDecl : scenarioGroup.getClasses().values())
      {
         for (Iterator<AttributeDecl> iterator = classDecl.getAttributes().values().iterator(); iterator.hasNext(); )
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
            final AssociationDecl assoc = AssociationDecl.of(classDecl, name, cardinality, otherClass, type, null);

            iterator.remove();
            classDecl.getAssociations().put(name, assoc);
         }

         for (final MethodDecl methodDecl : classDecl.getMethods())
         {
            methodDecl.setType(methodDecl.getType().accept(TypeResolver.INSTANCE, scope));
         }

         classDecl.setFrozen(true);
      }

      // now process non-external files.
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         if (!file.getExternal())
         {
            file.accept(this, scope);
         }
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Scope par)
   {
      final ScenarioGroup group = scenarioFile.getGroup();
      final String className = Identifiers.toUpperCamelCase(scenarioFile.getName()) + "Test";
      final ClassDecl classDecl = ClassDecl.of(group, className, null, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                               new ArrayList<>());
      classDecl.setExternal(scenarioFile.getExternal());
      classDecl.setType(ClassType.of(classDecl));

      // group.getClasses().put(className, classDecl);
      scenarioFile.setClassDecl(classDecl);

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            return className.equals(name) || ENCLOSING_CLASS.equals(name) ? classDecl : super.resolve(name);
         }

         @Override
         public void report(Marker marker)
         {
            scenarioFile.getMarkers().add(marker);
         }
      };
      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         scenario.accept(this, scope);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Scope par)
   {
      final ClassDecl classDecl = scenario.getFile().getClassDecl();
      final String methodName = Identifiers.toLowerCamelCase(scenario.getName());
      final SentenceList body = scenario.getBody();
      final MethodDecl methodDecl = MethodDecl.of(classDecl, methodName, null, PrimitiveType.VOID, body);

      final ParameterDecl thisParam = ParameterDecl.of(methodDecl, "this", classDecl.getType());
      methodDecl.setParameters(Collections.singletonList(thisParam));

      classDecl.getMethods().add(methodDecl);
      scenario.setMethodDecl(methodDecl);

      final DelegatingScope scope = new DelegatingScope(par)
      {

         @Override
         public Decl resolve(String name)
         {
            if ("this".equals(name))
            {
               return thisParam;
            }
            if (methodName.equals(name))
            {
               return methodDecl;
            }
            return super.resolve(name);
         }
      };

      body.accept(SentenceResolver.INSTANCE, scope);
      return null;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Name visit(Name name, Scope par)
   {
      return name;
   }

   @Override
   public Name visit(ResolvedName resolvedName, Scope par)
   {
      return resolvedName;
   }

   @Override
   public Name visit(UnresolvedName unresolvedName, Scope par)
   {
      final Decl decl = par.resolve(unresolvedName.getValue());
      return decl == null ? unresolvedName : ResolvedName.of(decl);
   }

   // =============== Static Methods ===============

   static ClassDecl getEnclosingClass(Scope scope)
   {
      return (ClassDecl) scope.resolve(ENCLOSING_CLASS);
   }

   // --------------- Decl Resolution/Creation ---------------

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

   static MethodDecl resolveMethod(Scope scope, ClassDecl owner, String name, Position position)
   {
      for (final MethodDecl decl : owner.getMethods())
      {
         if (name.equals(decl.getName()))
         {
            return decl;
         }
      }

      if (owner.getExternal())
      {
         scope.report(error(position, "method.unresolved.external", name, owner.getName()));
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "method.unresolved.frozen", name, owner.getName()));
      }

      final SentenceList body = SentenceList.of(new ArrayList<>());
      final MethodDecl decl = MethodDecl.of(owner, name, new ArrayList<>(), null, body);
      decl.setPosition(position);
      owner.getMethods().add(decl);
      return decl;
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

   static Decl resolveAttribute(Scope scope, ClassDecl owner, String name, Type type, Position position)
   {
      final AttributeDecl existingAttribute = owner.getAttributes().get(name);
      if (existingAttribute != null)
      {
         final Type existingType = existingAttribute.getType();
         if (!type.equals(existingType)) // TODO type equality
         {
            // TODO optimize
            final String newDesc = AttributeDecl.of(owner, name, type).accept(DeclDescriber.INSTANCE, null);
            scope.report(conflict(position, owner, name, existingAttribute, newDesc));
         }

         return existingAttribute;
      }
      final AssociationDecl existingAssociation = owner.getAssociations().get(name);
      if (existingAssociation != null)
      {
         // TODO optimize
         final String newDesc = AttributeDecl.of(owner, name, type).accept(DeclDescriber.INSTANCE, null);
         scope.report(conflict(position, owner, name, existingAssociation, newDesc));

         return existingAssociation;
      }

      if (owner.getExternal())
      {
         scope.report(error(position, "attribute.unresolved.external", name, owner.getName()));
      }
      else if (owner.getFrozen())
      {
         scope.report(error(position, "attribute.unresolved.frozen", name, owner.getName()));
      }

      final AttributeDecl attribute = AttributeDecl.of(owner, name, type);
      attribute.setPosition(position);
      owner.getAttributes().put(name, attribute);
      return attribute;
   }

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
         // TODO optimize
         final String newDesc = AssociationDecl
                                   .of(owner, name, cardinality, otherClass, createType(cardinality, otherClass),
                                       null).accept(DeclDescriber.INSTANCE, null);
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
            // TODO optimize
            final String newDesc = AssociationDecl.of(owner, name, cardinality, otherClass,
                                                      createType(cardinality, otherClass), null)
                                                  .accept(DeclDescriber.INSTANCE, null);
            scope.report(conflict(position, owner, name, existing, newDesc));
         }
         else if (otherName != null)
         {
            final AssociationDecl other = existing.getOther();
            if (other == null)
            {
               scope.report(error(otherPosition, "association.reverse.late", otherName, owner.getName(), name));
            }
            else if (!otherName.equals(other.getName()) || otherCardinality != other.getCardinality())
            {
               final String existingDesc = other.accept(DeclDescriber.INSTANCE, null);
               // TODO optimize
               final String newDesc = AssociationDecl.of(otherClass, otherName, otherCardinality, owner,
                                                         createType(otherCardinality, owner), null)
                                                     .accept(DeclDescriber.INSTANCE, null);
               scope.report(error(otherPosition, "association.reverse.conflict", owner.getName(), name,
                                  otherClass.getName(), other.getName(), existingDesc, otherClass.getName(),
                                  otherName, newDesc));
            }
         }

         return existing;
      }

      if (otherClass.getExternal())
      {
         scope.report(error(otherPosition, "association.unresolved.external", name, owner.getName()));
      }
      else if (otherClass.getFrozen())
      {
         scope.report(error(otherPosition, "association.unresolved.frozen", name, owner.getName()));
      }

      final AssociationDecl association = createAssociation(owner, name, cardinality, otherClass);
      association.setPosition(position);

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
         if (otherClass.getExternal())
         {
            scope.report(error(otherPosition, "association.unresolved.external", otherName, otherClass.getName()));
         }
         else if (otherClass.getFrozen())
         {
            scope.report(error(otherPosition, "association.unresolved.frozen", otherName, otherClass.getName()));
         }

         final AssociationDecl other = createAssociation(otherClass, otherName, otherCardinality, owner);
         other.setPosition(otherPosition);

         association.setOther(other);
         other.setOther(association);
      }

      return association;
   }

   private static AssociationDecl createAssociation(ClassDecl owner, String name, int cardinality, ClassDecl target)
   {
      final Type type = createType(cardinality, target);
      final AssociationDecl association = AssociationDecl.of(owner, name, cardinality, target, type, null);

      owner.getAssociations().put(association.getName(), association);
      return association;
   }

   private static Type createType(int cardinality, ClassDecl target)
   {
      return cardinality != 1 ? ListType.of(target.getType()) : target.getType();
   }

   // --------------- Decl Resolution (without Creation) ---------------

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

   // --------------- Helper Methods ---------------

   private static Marker conflict(Position position, ClassDecl owner, String name, Decl existing, String newDesc)
   {
      final String existingDesc = existing.accept(DeclDescriber.INSTANCE, null);
      final Marker error = error(position, "property.redeclaration.conflict", owner.getName(), name, existingDesc,
                                 newDesc);
      final Marker note = note(existing.getPosition(), "property.declaration.first", owner.getName(), name);

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
