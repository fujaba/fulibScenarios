package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.scope.*;
import org.fulib.scenarios.ast.sentence.SentenceList;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.parser.Identifiers;
import org.fulib.util.Validator;

import java.util.*;
import java.util.function.BiConsumer;

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
      final Scope globalScope = new GlobalScope(compilationContext);

      // make a copy of the groups that actually need processing.
      // new ones might be added to compilationContext.groups while resolving those,
      // but they do not need resolving as they are external and do not contain any scenario files we care about.
      final List<ScenarioGroup> groups = new ArrayList<>(compilationContext.getGroups().values());
      groups.parallelStream().forEach(it -> it.accept(this, globalScope));
      return null;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Scope par)
   {
      final Scope scope = new GroupScope(par, scenarioGroup);

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
         public void list(BiConsumer<? super String, ? super Decl> consumer)
         {
            consumer.accept(className, classDecl);
            consumer.accept(DeclResolver.ENCLOSING_CLASS, classDecl);
            super.list(consumer);
         }

         @Override
         public void report(Marker marker)
         {
            scenarioFile.getMarkers().add(marker);
         }
      };
      for (final Scenario scenario : scenarioFile.getScenarios())
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
      String methodName = Identifiers.toLowerCamelCase(scenario.getName());

      if (!Validator.isSimpleName(methodName))
      {
         methodName = "_" + methodName;
      }

      final Position position = scenario.getPosition();
      final MethodDecl methodDecl = DeclResolver.resolveMethod(par, position, classDecl, methodName);
      final ParameterDecl thisParam;

      if (methodDecl.getParameters().isEmpty())
      {
         thisParam = ParameterDecl.of(methodDecl, "this", classDecl.getType());
         thisParam.setPosition(position);
         methodDecl.getParameters().add(thisParam);

         methodDecl.setType(PrimitiveType.VOID);
      }
      else
      {
         thisParam = methodDecl.getParameters().get(0);
      }

      scenario.setMethodDecl(methodDecl);

      final Scope scope = new ExtendingScope(new Decl[] { thisParam, methodDecl }, par);

      scenario.getBody().accept(SentenceResolver.INSTANCE, scope);

      methodDecl.getBody().getItems().addAll(scenario.getBody().getItems());

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
