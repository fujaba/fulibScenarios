package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.MapAccessExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.conditional.*;
import org.fulib.scenarios.ast.expr.primary.AnswerLiteral;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.visitor.*;
import org.fulib.scenarios.visitor.describe.TypeDescriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.visitor.resolve.DeclResolver.firstDeclaration;
import static org.fulib.scenarios.visitor.resolve.NameResolver.ANSWER_VAR;
import static org.fulib.scenarios.visitor.resolve.NameResolver.PREDICATE_RECEIVER;

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
         final Type elementType = ((ListType) receiverType).getElementType();
         final Name resolvedName = DeclResolver
                                      .getAttributeOrAssociation(par, elementType, attributeAccess.getName());
         return MapAccessExpr.of(resolvedName, receiver);
      }

      attributeAccess.setName(DeclResolver.getAttributeOrAssociation(par, receiver, attributeAccess.getName()));
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
   public Expr visit(AnswerLiteral answerLiteral, Scope par)
   {
      final Decl answerVar = par.resolve(ANSWER_VAR);
      if (answerVar == null)
      {
         par.report(error(answerLiteral.getPosition(), "answer.unresolved"));
         return ErrorExpr.of(PrimitiveType.ERROR);
      }
      return NameAccess.of(ResolvedName.of(answerVar));
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
            final StringLiteral stringLiteral = StringLiteral.of(unresolvedName.getText());
            stringLiteral.setPosition(unresolvedName.getPosition());
            return stringLiteral;
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
      final ClassDecl classDecl = creationExpr.getType().accept(ExtractClassDecl.INSTANCE, null);

      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         SentenceResolver.resolveHasNamedExpr(namedExpr, classDecl, par);
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

      final Type receiverType = callExpr.getReceiver().accept(Typer.INSTANCE, null);
      final ClassDecl receiverClass = receiverType.accept(ExtractClassDecl.INSTANCE, null);

      final String methodName = callExpr.getName().accept(Namer.INSTANCE, null);
      final MethodDecl method = DeclResolver
                                   .resolveMethod(par, callExpr.getName().getPosition(), receiverClass, methodName);
      final List<ParameterDecl> parameters = method.getParameters();
      final ParameterDecl thisParameter;
      final Map<String, ParameterDecl> decls = new HashMap<>();

      callExpr.setName(ResolvedName.of(method));

      if (method.getParameters().isEmpty()) // method is new
      {
         // create this parameter
         thisParameter = ParameterDecl.of(method, "this", receiverClass.getType());
         parameters.add(thisParameter);

         // create parameters based on arguments
         for (final NamedExpr argument : arguments)
         {
            final String name = argument.getName().accept(Namer.INSTANCE, null);
            final Expr expr = argument.getExpr();
            final Type type = expr.accept(Typer.INSTANCE, null);
            final ParameterDecl param = ParameterDecl.of(method, name, type);

            parameters.add(param);
            argument.setName(ResolvedName.of(param));
            decls.put(name, param);
         }
      }
      else
      {
         thisParameter = parameters.get(0);

         // put all but the first (this) parameter into decls
         for (int i = 1; i < parameters.size(); i++)
         {
            final ParameterDecl parameter = parameters.get(i);
            decls.put(parameter.getName(), parameter);
         }

         // check if arguments and parameters match (by label)
         final String params = parameters.stream().skip(1).map(ParameterDecl::getName)
                                         .collect(Collectors.joining(" "));
         final String args = arguments.stream().map(NamedExpr::getName).map(n -> n.accept(Namer.INSTANCE, null))
                                      .collect(Collectors.joining(" "));

         if (!params.equals(args))
         {
            final Marker error = error(callExpr.getPosition(), "call.mismatch.params.args", receiverClass.getName(),
                                       methodName, params, args);
            final Marker note = firstDeclaration(method.getPosition(), method.getOwner(), method.getName());
            par.report(error.note(note));
         }

         // match arguments and check types
         for (final NamedExpr argument : arguments)
         {
            final Expr expr = argument.getExpr();
            final Type type = expr.accept(Typer.INSTANCE, null);
            final String name = argument.getName().accept(Namer.INSTANCE, null);
            final ParameterDecl param = decls.get(name);

            if (param == null)
            {
               // mismatch, was already reported.
               continue;
            }

            argument.setName(ResolvedName.of(param));

            if (type == PrimitiveType.ERROR)
            {
               continue;
            }

            final Type paramType = param.getType();
            if (paramType == PrimitiveType.ERROR)
            {
               continue;
            }

            final Expr converted = TypeConversion.convert(expr, paramType);
            if (converted != null)
            {
               argument.setExpr(converted);
               continue;
            }

            par.report(
               error(expr.getPosition(), "call.mismatch.type", paramType.accept(TypeDescriber.INSTANCE, null),
                     type.accept(TypeDescriber.INSTANCE, null)));
         }
      }

      // add the "this" parameter to decls. done here to avoid having arguments matched with it.
      decls.put("this", thisParameter);

      // references to the receiver name are replaced with 'this'
      final String receiverName = receiver.accept(Namer.INSTANCE, null);
      if (receiverName != null)
      {
         decls.put(receiverName, thisParameter);
      }

      // make expression names resolve to the parameter
      for (final NamedExpr argument : arguments)
      {
         final ParameterDecl param = (ParameterDecl) argument.getName().accept(ExtractDecl.INSTANCE, null);
         if (param == null)
         {
            continue;
         }

         final Expr expr = argument.getExpr();
         final String exprName = expr.accept(Namer.INSTANCE, null);
         if (exprName == null)
         {
            continue;
         }

         decls.put(exprName, param);
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
      Expr receiver = attributeCheckExpr.getReceiver();
      if (receiver == null)
      {
         final Decl predicateReceiver = par.resolve(PREDICATE_RECEIVER);
         if (predicateReceiver == null)
         {
            par.report(error(attributeCheckExpr.getPosition(), "attribute-check.receiver.missing"));
            receiver = ErrorExpr.of(PrimitiveType.ERROR);
         }
         else
         {
            receiver = NameAccess.of(ResolvedName.of(predicateReceiver));
         }
      }

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
      final Expr rhs = conditionalOperatorExpr.getRhs().accept(this, par);
      conditionalOperatorExpr.setRhs(rhs);

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
            par.report(error(conditionalOperatorExpr.getPosition(), "conditional.lhs.missing"));
            conditionalOperatorExpr.setLhs(ErrorExpr.of(rhs.accept(Typer.INSTANCE, null)));
         }
         else
         {
            conditionalOperatorExpr.setLhs(NameAccess.of(ResolvedName.of(predicateReceiver)));
         }
      }

      return conditionalOperatorExpr;
   }

   @Override
   public Expr visit(PredicateOperatorExpr predicateOperatorExpr, Scope par)
   {
      final Expr lhs = predicateOperatorExpr.getLhs();
      if (lhs != null)
      {
         predicateOperatorExpr.setLhs(lhs.accept(this, par));
      }
      else
      {
         final Decl predicateReceiver = par.resolve(PREDICATE_RECEIVER);
         if (predicateReceiver == null)
         {
            par.report(error(predicateOperatorExpr.getPosition(), "predicate.lhs.missing"));
            predicateOperatorExpr.setLhs(ErrorExpr.of(null));
         }
         else
         {
            predicateOperatorExpr.setLhs(NameAccess.of(ResolvedName.of(predicateReceiver)));
         }
      }

      return predicateOperatorExpr;
   }

   @Override
   public Expr visit(ListExpr listExpr, Scope par)
   {
      listExpr.getElements().replaceAll(it -> it.accept(this, par));
      return listExpr;
   }

   @Override
   public Expr visit(RangeExpr rangeExpr, Scope par)
   {
      final Expr start = rangeExpr.getStart().accept(this, par);
      final Expr end = rangeExpr.getEnd().accept(this, par);

      rangeExpr.setStart(start);
      rangeExpr.setEnd(end);

      final Type startType = start.accept(Typer.INSTANCE, null);
      final Type endType = end.accept(Typer.INSTANCE, null);

      if (!TypeComparer.equals(startType, endType))
      {
         par.report(error(rangeExpr.getPosition(), "range.element.type.mismatch",
                          startType.accept(TypeDescriber.INSTANCE, null),
                          endType.accept(TypeDescriber.INSTANCE, null)));
      }
      if (!isValidRangeType(startType))
      {
         par.report(error(start.getPosition(), "range.element.type.unsupported",
                          startType.accept(TypeDescriber.INSTANCE, null)));
      }
      if (!isValidRangeType(endType))
      {
         par.report(error(end.getPosition(), "range.element.type.unsupported",
                          endType.accept(TypeDescriber.INSTANCE, null)));
      }

      return rangeExpr;
   }

   private static boolean isValidRangeType(Type type)
   {
      if (!(type instanceof PrimitiveType))
      {
         return false;
      }

      switch ((PrimitiveType) type)
      {
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
      case INT:
      case INT_WRAPPER:
      case LONG:
      case LONG_WRAPPER:
         return true;
      default:
         return false;
      }
   }
}
