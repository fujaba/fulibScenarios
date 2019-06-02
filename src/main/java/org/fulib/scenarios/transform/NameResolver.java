package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum NameResolver implements ScenarioGroup.Visitor<Object, Object>, ScenarioFile.Visitor<Scope, Object>,
                                       Scenario.Visitor<Scope, Object>, Sentence.Visitor<Scope, Object>,
                                       Expr.Visitor<Scope, Expr>, Name.Visitor<Scope, Name>
{
   INSTANCE;

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      final Scope scope = new GroupScope(scenarioGroup);
      for (final ScenarioFile file : scenarioGroup.getFiles())
      {
         file.accept(this, scope);
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Scope par)
   {
      for (final Scenario scenario : scenarioFile.getScenarios())
      {
         scenario.accept(this, par);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Scope par)
   {
      scenario.getBody().accept(this, par);
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Scope par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Scope par)
   {
      BasicScope scope = new BasicScope(par);

      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(SymbolCollector.INSTANCE, scope.decls);
         item.accept(this, scope);
      }
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(this, par));
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Scope par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(this, par));
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Scope par)
   {
      hasSentence.setObject(hasSentence.getObject().accept(this, par));

      final String name = hasSentence.getObject().accept(Namer.INSTANCE, null);
      final Scope scope = name != null ? new HidingScope(name, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, scope));
      }

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      varDecl.setExpr(varDecl.getExpr().accept(this, par));
      return null;
   }

   @Override
   public Object visit(CreateSentence createSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AnswerSentence answerSentence, Scope par)
   {
      if (answerSentence.getActor() != null)
      {
         answerSentence.setActor(answerSentence.getActor().accept(this, par));
      }

      answerSentence.setResult(answerSentence.getResult().accept(this, par));
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(this, par));
      return null;
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(Expr expr, Scope par)
   {
      return expr;
   }

   @Override
   public Expr visit(AttributeAccess attributeAccess, Scope par)
   {
      attributeAccess.setReceiver(attributeAccess.getReceiver().accept(this, par));
      attributeAccess.setName(getAttributeOrAssociation(par, attributeAccess.getReceiver(), attributeAccess.getName()));
      return attributeAccess;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Scope par)
   {
      exampleAccess.setExpr(exampleAccess.getExpr().accept(this, par));
      return exampleAccess;
   }

   @Override
   public Expr visit(PrimaryExpr primaryExpr, Scope par)
   {
      return primaryExpr;
   }

   @Override
   public Expr visit(NameAccess nameAccess, Scope par)
   {
      if (nameAccess.getName() instanceof UnresolvedName)
      {
         final UnresolvedName unresolvedName = (UnresolvedName) nameAccess.getName();
         final String unresolvedValue = unresolvedName.getValue();

         final Decl target = par.resolve(unresolvedValue);
         if (target == null)
         {
            return StringLiteral.of(unresolvedName.getText());
         }
         else
         {
            nameAccess.setName(ResolvedName.of(target));
         }
      }

      return nameAccess;
   }

   @Override
   public Expr visit(NumberLiteral numberLiteral, Scope par)
   {
      return numberLiteral;
   }

   @Override
   public Expr visit(StringLiteral stringLiteral, Scope par)
   {
      return stringLiteral;
   }

   @Override
   public Expr visit(CreationExpr creationExpr, Scope par)
   {
      creationExpr.setClassName(creationExpr.getClassName().accept(this, par));
      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
      }
      return creationExpr;
   }

   @Override
   public Expr visit(CallExpr callExpr, Scope par)
   {
      final Expr receiver = callExpr.getReceiver();
      if (receiver != null)
      {
         callExpr.setReceiver(receiver.accept(this, par));
      }
      for (final NamedExpr argument : callExpr.getArguments())
      {
         argument.setExpr(argument.getExpr().accept(this, par));
      }
      callExpr.getBody().accept(this, par);

      // generate method

      final ClassDecl receiverClass = resolveClass(par, callExpr.getReceiver());

      final String methodName = callExpr.getName().accept(Namer.INSTANCE, null);
      final MethodDecl method = resolveMethod(receiverClass, methodName);

      if (method.getType() == null) // newly created method
      {
         final String returnType = callExpr.accept(Typer.INSTANCE, null);
         method.setType(returnType);

         for (final NamedExpr argument : callExpr.getArguments())
         {
            final String name = argument.getName().accept(Namer.INSTANCE, null);
            final Expr expr = argument.getExpr();
            final String type = expr.accept(Typer.INSTANCE, null);
            final ParameterDecl param = ParameterDecl.of(method, name, type);

            argument.setName(ResolvedName.of(param));
            method.getParameters().add(param);
         }
      }
      else
      {
         // TODO match parameters and arguments
      }

      method.getBody().getItems().addAll(callExpr.getBody().getItems()); // TODO replace arguments with parameter names

      return callExpr;
   }

   @Override
   public Expr visit(ConditionalExpr conditionalExpr, Scope par)
   {
      return conditionalExpr;
   }

   @Override
   public Expr visit(AttributeCheckExpr attributeCheckExpr, Scope par)
   {
      attributeCheckExpr.setReceiver(attributeCheckExpr.getReceiver().accept(this, par));
      attributeCheckExpr.setValue(attributeCheckExpr.getValue().accept(this, par));
      attributeCheckExpr
         .setAttribute(getAttributeOrAssociation(par, attributeCheckExpr.getReceiver(), attributeCheckExpr.getAttribute()));
      return attributeCheckExpr;
   }

   @Override
   public Expr visit(CollectionExpr collectionExpr, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Expr visit(ListExpr listExpr, Scope par)
   {
      listExpr.getElements().replaceAll(it -> it.accept(this, par));
      return listExpr;
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

   static ScenarioGroup getGroup(Scope scope)
   {
      Scope outer;
      while ((outer = scope.getOuter()) != null)
      {
         scope = outer;
      }
      return ((GroupScope) scope).getGroup();
   }

   static ClassDecl resolveClass(Scope scope, Expr expr)
   {
      return resolveClass(scope, expr.accept(Typer.INSTANCE, null));
   }

   static ClassDecl resolveClass(Scope scope, String name)
   {
      return resolveClass(getGroup(scope), name);
   }

   static ClassDecl resolveClass(ScenarioGroup group, String name)
   {
      ClassDecl decl = group.getClasses().get(name);

      if (decl != null)
      {
         return decl;
      }

      decl = ClassDecl.of(group, name, name, new LinkedHashMap<>(), new LinkedHashMap<>(), new ArrayList<>());
      group.getClasses().put(name, decl);
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

      final SentenceList body = SentenceList.of(new ArrayList<>());
      final MethodDecl decl = MethodDecl.of(classDecl, name, new ArrayList<>(), null, body);
      classDecl.getMethods().add(decl);
      return decl;
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

      throw new IllegalStateException("unresolved attribute " + receiverClass.getName() + "." + name);
   }
}

interface Scope
{
   Scope getOuter();

   Decl resolve(String name);

   void add(Decl decl);
}

class GroupScope implements Scope
{
   final ScenarioGroup group;

   GroupScope(ScenarioGroup group)
   {
      this.group = group;
   }

   @Override
   public Scope getOuter()
   {
      return null;
   }

   public ScenarioGroup getGroup()
   {
      return this.group;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.group.getClasses().get(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.group.getClasses().put(decl.getName(), (ClassDecl) decl);
   }
}

class BasicScope implements Scope
{
   final Map<String, Decl> decls;

   final Scope outer;

   public BasicScope(Scope outer)
   {
      this.outer = outer;
      this.decls = new HashMap<>();
   }

   @Override
   public Scope getOuter()
   {
      return this.outer;
   }

   @Override
   public Decl resolve(String name)
   {
      Decl inner = this.decls.get(name);
      return inner != null ? inner : this.outer.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.decls.put(decl.getName(), decl);
   }
}

class HidingScope implements Scope
{
   final String name;
   final Scope  outer;

   HidingScope(String name, Scope outer)
   {
      this.name = name;
      this.outer = outer;
   }

   @Override
   public Scope getOuter()
   {
      return this.outer;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.name.equals(name) ? null : this.outer.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.outer.add(decl);
   }
}
