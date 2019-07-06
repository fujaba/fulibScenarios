package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.FilterExpr;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fulib.scenarios.visitor.resolve.NameResolver.*;

public enum ExprResolver implements Expr.Visitor<Scope, Expr>
{
   INSTANCE;

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(Expr expr, Scope par)
   {
      return expr;
   }

   @Override
   public Expr visit(AttributeAccess attributeAccess, Scope par)
   {
      final Expr receiver = attributeAccess.getReceiver().accept(this, par);
      attributeAccess.setReceiver(receiver);

      final Type receiverType = receiver.accept(Typer.INSTANCE, null);
      if (receiverType instanceof ListType)
      {
         final String attributeName = attributeAccess.getName().accept(Namer.INSTANCE, null);
         final Type elementType = ((ListType) receiverType).getElementType();
         final Name resolvedName = getAttributeOrAssociation(resolveClass(par, elementType), attributeName);
         return ListAttributeAccess.of(resolvedName, receiver);
      }

      attributeAccess.setName(getAttributeOrAssociation(par, receiver, attributeAccess.getName()));
      return attributeAccess;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Scope par)
   {
      exampleAccess.setExpr(exampleAccess.getExpr().accept(this, par));
      return exampleAccess;
   }

   @Override
   public Expr visit(FilterExpr filterExpr, Scope par)
   {
      final Expr source = filterExpr.getSource().accept(this, par);
      filterExpr.setSource(source);

      final Type sourceType = source.accept(Typer.INSTANCE, null);
      final Type elementType = ((ListType) sourceType).getElementType();
      final VarDecl it = VarDecl.of("it", elementType, null);

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            return PREDICATE_RECEIVER.equals(name) ? it : super.resolve(name);
         }
      };
      filterExpr.setPredicate((ConditionalExpr) filterExpr.getPredicate().accept(this, scope));
      return filterExpr;
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
   public Expr visit(CreationExpr creationExpr, Scope par)
   {
      creationExpr.setType(creationExpr.getType().accept(TypeResolver.INSTANCE, par));
      final ClassDecl classDecl = resolveClass(par, creationExpr.getType());

      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
         namedExpr.setName(resolveAttributeOrAssociation(classDecl, namedExpr.getName(), namedExpr.getExpr()));
      }
      return creationExpr;
   }

   @Override
   public Expr visit(CallExpr callExpr, Scope par)
   {
      final List<NamedExpr> arguments = callExpr.getArguments();
      Expr receiver = callExpr.getReceiver();
      if (receiver != null)
      {
         receiver = receiver.accept(this, par);
      }
      else
      {
         final Decl thisDecl = par.resolve("this");
         receiver = NameAccess.of(ResolvedName.of(thisDecl));
      }
      callExpr.setReceiver(receiver);

      for (final NamedExpr argument : arguments)
      {
         argument.setExpr(argument.getExpr().accept(this, par));
      }

      // generate method

      final ClassDecl receiverClass = resolveClass(par, callExpr.getReceiver());

      final String methodName = callExpr.getName().accept(Namer.INSTANCE, null);
      final MethodDecl method = resolveMethod(receiverClass, methodName);
      final List<ParameterDecl> parameters = method.getParameters();
      final boolean isNew = method.getParameters().isEmpty();
      final Map<String, Decl> decls = new HashMap<>();

      if (isNew)
      {
         // this parameter
         final ParameterDecl thisParam = ParameterDecl.of(method, "this", receiverClass.getType());
         parameters.add(thisParam);
         decls.put("this", thisParam);
      }
      else
      {
         decls.put("this", parameters.get(0));

         // check if arguments and parameters match (by label)
         final String params = parameters.stream().skip(1).map(ParameterDecl::getName)
                                         .collect(Collectors.joining(" "));
         final String args = arguments.stream().map(NamedExpr::getName).map(n -> n.accept(Namer.INSTANCE, null))
                                      .collect(Collectors.joining(" "));

         if (!params.equals(args))
         {
            throw new IllegalStateException(
               "mismatching parameters and arguments:\nparameters: " + params + "\narguments : " + args);
         }
      }

      // references to the receiver name are replaced with 'this'
      final String receiverName = receiver.accept(Namer.INSTANCE, null);
      if (receiverName != null)
      {
         decls.put(receiverName, parameters.get(0));
      }

      // match arguments and parameters
      for (int i = 0; i < arguments.size(); i++)
      {
         final NamedExpr argument = arguments.get(i);
         final String name = argument.getName().accept(Namer.INSTANCE, null);
         final Expr expr = argument.getExpr();
         final ParameterDecl param;

         if (isNew)
         {
            final Type type = expr.accept(Typer.INSTANCE, null);
            param = ParameterDecl.of(method, name, type);
            parameters.add(param);
         }
         else
         {
            param = parameters.get(i + 1);
         }

         argument.setName(ResolvedName.of(param));
         decls.put(name, param);

         // references to the expression name refer to the parameter
         final String exprName = expr.accept(Namer.INSTANCE, null);
         if (exprName != null)
         {
            decls.put(exprName, param);
         }
      }

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            final Decl decl = decls.get(name);
            return decl != null ? decl : super.resolve(name);
         }
      };
      callExpr.getBody().accept(SentenceResolver.INSTANCE, scope);

      // set return type if necessary. has to happen after body resolution!
      if (method.getType() == null)
      {
         final Type returnType = callExpr.accept(Typer.INSTANCE, null);
         method.setType(returnType);
      }

      method.getBody().getItems().addAll(callExpr.getBody().getItems());

      return callExpr;
   }

   @Override
   public Expr visit(AttributeCheckExpr attributeCheckExpr, Scope par)
   {
      // extract
      final Expr receiver = attributeCheckExpr.getReceiver();
      final Expr expected = attributeCheckExpr.getValue();
      final Name attribute = attributeCheckExpr.getAttribute();

      // transform
      final AttributeAccess access = AttributeAccess.of(attribute, receiver);
      final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(access, ConditionalOperator.IS, expected);

      // resolve
      return condOp.accept(this, par);
   }

   @Override
   public Expr visit(ConditionalOperatorExpr conditionalOperatorExpr, Scope par)
   {
      final Expr lhs = conditionalOperatorExpr.getLhs();
      if (lhs != null)
      {
         conditionalOperatorExpr.setLhs(lhs.accept(this, par));
      }
      else
      {
         final Decl predicateReceiver = par.resolve(PREDICATE_RECEIVER);
         if (predicateReceiver == null)
         {
            throw new IllegalStateException("invalid conditional operator - missing left-hand expression");
         }
         conditionalOperatorExpr.setLhs(NameAccess.of(ResolvedName.of(predicateReceiver)));
      }

      conditionalOperatorExpr.setRhs(conditionalOperatorExpr.getRhs().accept(this, par));
      return conditionalOperatorExpr;
   }

   @Override
   public Expr visit(ListExpr listExpr, Scope par)
   {
      listExpr.getElements().replaceAll(it -> it.accept(this, par));
      return listExpr;
   }
}