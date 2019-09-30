package org.fulib.scenarios.visitor.resolve;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.EmptyScope;
import org.fulib.scenarios.ast.scope.GroupScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.parser.Identifiers;
import org.fulib.scenarios.visitor.ExtractClassDecl;

import java.util.*;

public enum NameResolver implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<Scope, Object>,
                                       ScenarioFile.Visitor<Scope, Object>, Scenario.Visitor<Scope, Object>,
                                       Name.Visitor<Scope, Name>
{
   INSTANCE;

   // =============== Constants ===============

   protected static final String PREDICATE_RECEIVER = "<predicate-receiver>";
   protected static final String ANSWER_VAR         = "<answer-var>";

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
      final Scope scope = new GroupScope(par, scenarioGroup);

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

            for (final ParameterDecl parameter : methodDecl.getParameters())
            {
               parameter.setType(parameter.getType().accept(TypeResolver.INSTANCE, scope));
            }
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
            return className.equals(name) || DeclResolver.ENCLOSING_CLASS.equals(name) ?
                      classDecl :
                      super.resolve(name);
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
      methodDecl.setPosition(scenario.getPosition());

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
}
