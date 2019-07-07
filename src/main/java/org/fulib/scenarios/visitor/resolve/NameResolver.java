package org.fulib.scenarios.visitor.resolve;

import org.fulib.StrUtil;
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
import org.fulib.scenarios.parser.Identifiers;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;

import java.util.*;

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
         for (final AttributeDecl attributeDecl : classDecl.getAttributes().values())
         {
            attributeDecl.setType(attributeDecl.getType().accept(TypeResolver.INSTANCE, scope));
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

   static ClassDecl resolveClass(Scope scope, Expr expr)
   {
      final Type type = expr.accept(Typer.INSTANCE, null);
      return resolveClass(scope, type);
   }

   static ClassDecl resolveClass(Scope scope, Type type)
   {
      if (type instanceof ListType)
      {
         return resolveClass(scope, ((ListType) type).getElementType());
      }

      final String typeName = type.accept(Namer.INSTANCE, null);
      return resolveClass(scope, typeName);
   }

   static ClassDecl resolveClass(Scope scope, String name)
   {
      final Decl resolved = scope.resolve(name);
      if (resolved instanceof ClassDecl)
      {
         return (ClassDecl) resolved;
      }
      if (resolved != null)
      {
         throw new IllegalStateException("class name " + name + " resolved to " + resolved);
      }

      final ClassDecl decl = ClassDecl.of(null, name, null, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                          new ArrayList<>());
      decl.setExternal(getEnclosingClass(scope).getExternal());
      decl.setType(ClassType.of(decl));
      scope.add(decl);
      return decl;
   }

   static MethodDecl resolveMethod(ClassDecl classDecl, String name)
   {
      for (final MethodDecl decl : classDecl.getMethods())
      {
         if (name.equals(decl.getName()))
         {
            return decl;
         }
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException("unresolved external method " + classDecl.getName() + "." + name);
      }

      final SentenceList body = SentenceList.of(new ArrayList<>());
      final MethodDecl decl = MethodDecl.of(classDecl, name, new ArrayList<>(), null, body);
      classDecl.getMethods().add(decl);
      return decl;
   }

   static Name resolveAttributeOrAssociation(ClassDecl classDecl, Name name, Expr rhs)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         // already resolved
         return name;
      }

      return ResolvedName.of(resolveAttributeOrAssociation(classDecl, name.accept(Namer.INSTANCE, null), rhs));
   }

   static Decl resolveAttributeOrAssociation(ClassDecl classDecl, String attributeName, Expr rhs)
   {
      final AttributeDecl existingAttribute = classDecl.getAttributes().get(attributeName);
      if (existingAttribute != null)
      {
         return existingAttribute;
      }

      final AssociationDecl existingAssociation = classDecl.getAssociations().get(attributeName);
      if (existingAssociation != null)
      {
         return existingAssociation;
      }

      final Type attributeType = rhs.accept(Typer.INSTANCE, null);

      if (attributeType instanceof ListType)
      {
         // new value is multi-valued

         final Type otherType = ((ListType) attributeType).getElementType();
         if (otherType instanceof ClassType)
         {
            return resolveAssociation(classDecl, attributeName, 2, ((ClassType) otherType).getClassDecl());
         }
         else
         {
            // was element type that we have no control over, e.g. List<String>
            return resolveAttribute(classDecl, attributeName, attributeType);
         }
      }
      else if (attributeType instanceof ClassType)
      {
         return resolveAssociation(classDecl, attributeName, 1, ((ClassType) attributeType).getClassDecl());
      }
      else
      {
         return resolveAttribute(classDecl, attributeName, attributeType);
      }
   }

   static AttributeDecl resolveAttribute(ClassDecl classDecl, String name, Type type)
   {
      final AttributeDecl existing = classDecl.getAttributes().get(name);
      if (existing != null)
      {
         final Type existingType = existing.getType();
         if (!type.equals(existingType)) // TODO type equality
         {
            throw new IllegalStateException(
               "mismatched attribute type " + classDecl.getName() + "." + name + ": " + existingType + " vs "
               + type);
         }

         return existing;
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException("unresolved external attribute " + classDecl.getName() + "." + name);
      }

      final AttributeDecl attribute = AttributeDecl.of(classDecl, name, type);
      classDecl.getAttributes().put(name, attribute);
      return attribute;
   }

   static AssociationDecl resolveAssociation(ClassDecl classDecl, String name, int cardinality, ClassDecl otherClass)
   {
      if (otherClass.getFrozen())
      {
         return resolveAssociation(classDecl, name, cardinality, otherClass, null, 0);
      }
      else
      {
         final String otherName = StrUtil.downFirstChar(classDecl.getName());
         return resolveAssociation(classDecl, name, cardinality, otherClass, otherName, 1);
      }
   }

   static AssociationDecl resolveAssociation(ClassDecl classDecl, String name, int cardinality, ClassDecl otherClass,
      String otherName, int otherCardinality)
   {
      final AssociationDecl existing = classDecl.getAssociations().get(name);
      if (existing != null)
      {
         if (existing.getTarget() != otherClass || existing.getCardinality() != cardinality)
         {
            final String olda = associationString(classDecl, name, existing.getCardinality(), existing.getTarget());
            final String newa = associationString(classDecl, name, cardinality, otherClass);
            throw new IllegalStateException("conflicting new association\nold: " + olda + "\nnew: " + newa);
         }

         final AssociationDecl other = existing.getOther();
         if (!otherName.equals(other.getName()) || otherCardinality != other.getCardinality())
         {
            final String olda = associationString(other.getOwner(), other.getName(), other.getCardinality(),
                                                  classDecl);
            final String newa = associationString(other.getOwner(), otherName, otherCardinality, classDecl);
            throw new IllegalStateException("conflicting new reverse association\nold: " + olda + "\nnew: " + newa);
         }

         return existing;
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException(
            "unresolved association " + associationString(classDecl, name, cardinality, otherClass));
      }

      final AssociationDecl association = createAssociation(classDecl, name, cardinality, otherClass);

      if (otherClass == classDecl && name.equals(otherName))
      {
         if (cardinality != otherCardinality)
         {
            throw new UnsupportedOperationException(
               "mismatching cardinality of self-association\norigin:  " + cardinalityString(cardinality)
               + "\nreverse: " + cardinalityString(otherCardinality));
         }

         // self-association
         association.setOther(association);
      }
      else if (otherName != null)
      {
         if (otherClass.getFrozen())
         {
            throw new IllegalStateException(
               "unresolved reverse association " + associationString(otherClass, otherName, otherCardinality,
                                                                     classDecl));
         }

         final AssociationDecl other = createAssociation(otherClass, otherName, otherCardinality, classDecl);

         association.setOther(other);
         other.setOther(association);
      }

      return association;
   }

   private static AssociationDecl createAssociation(ClassDecl owner, String name, int cardinality, ClassDecl target)
   {
      final Type type = cardinality != 1 ? ListType.of(target.getType()) : target.getType();
      final AssociationDecl association = AssociationDecl.of(owner, name, cardinality, target, type, null);

      owner.getAssociations().put(association.getName(), association);
      return association;
   }

   private static String associationString(ClassDecl owner, String name, int cardinality, ClassDecl other)
   {
      return owner.getName() + "." + name + ": " + cardinalityString(cardinality) + " " + other.getName();
   }

   private static String cardinalityString(int cardinality)
   {
      return cardinality == 1 ? "one" : "many";
   }

   static Name getAttributeOrAssociation(Scope scope, Expr receiver, Name name)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         return name;
      }

      return getAttributeOrAssociation(resolveClass(scope, receiver), name.accept(Namer.INSTANCE, null));
   }

   static Name getAttributeOrAssociation(ClassDecl receiverClass, String name)
   {
      final AttributeDecl attribute = receiverClass.getAttributes().get(name);
      if (attribute != null)
      {
         return ResolvedName.of(attribute);
      }

      final AssociationDecl association = receiverClass.getAssociations().get(name);
      if (association != null)
      {
         return ResolvedName.of(association);
      }

      throw new IllegalStateException("unresolved attribute or association " + receiverClass.getName() + "." + name);
   }
}
