package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.PlaceholderExpr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.MapAccessExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.operator.BinaryExpr;
import org.fulib.scenarios.ast.expr.primary.AnswerLiteral;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.scope.ExtendingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.visitor.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.diagnostic.Marker.note;
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
   public Expr visit(PlaceholderExpr placeholderExpr, Scope par)
   {
      final Type unresolvedType = placeholderExpr.getType();
      if (unresolvedType != null)
      {
         placeholderExpr.setType(unresolvedType.accept(TypeResolver.INSTANCE, par));
      }

      final Expr unresolvedExample = placeholderExpr.getExample();
      if (unresolvedExample != null)
      {
         final Expr resolvedExample = unresolvedExample.accept(this, par);
         placeholderExpr.setExample(resolvedExample);

         final Type exampleType = resolvedExample.getType();
         if (unresolvedType == null)
         {
            placeholderExpr.setType(exampleType);
         }
         else
         {
            final Type resolvedType = placeholderExpr.getType();
            if (resolvedType != PrimitiveType.ERROR && exampleType != PrimitiveType.ERROR && !TypeComparer.isSuperType(
               resolvedType, exampleType))
            {
               par.report(error(resolvedExample.getPosition(), "placeholder.example.type.mismatch",
                                exampleType.getDescription(), resolvedType.getDescription()));
            }
         }
      }

      return placeholderExpr;
   }

   // --------------- PrimaryExpr.Visitor ---------------

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

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(AttributeAccess attributeAccess, Scope par)
   {
      final Expr receiver = attributeAccess.getReceiver().accept(this, par);
      attributeAccess.setReceiver(receiver);

      final Type receiverType = receiver.getType();
      if (receiverType instanceof ListType)
      {
         final Type elementType = ((ListType) receiverType).getElementType();
         final Name resolvedName = DeclResolver.getAttributeOrAssociation(par, receiver, elementType,
                                                                          attributeAccess.getName());
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
   public Expr visit(CreationExpr creationExpr, Scope par)
   {
      final Type receiverType = creationExpr.getType().accept(TypeResolver.INSTANCE, par);
      creationExpr.setType(receiverType);

      if (receiverType == PrimitiveType.ERROR)
      {
         // recover from previous error
         return creationExpr;
      }

      final ClassDecl receiverClass = receiverType.accept(ExtractClassDecl.INSTANCE, null);
      if (receiverClass == null)
      {
         if (!creationExpr.getAttributes().isEmpty())
         {
            par.report(
               error(creationExpr.getPosition(), "create.subject.primitive.attributes", receiverType.getDescription()));
         }

         return creationExpr;
      }

      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         SentenceResolver.resolveHasNamedExpr(namedExpr, receiverClass, par);
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

      final String methodName = callExpr.getName().getValue();
      final Type receiverType = callExpr.getReceiver().getType();

      if (receiverType == PrimitiveType.ERROR)
      {
         // recover from previous error
         return callExpr;
      }

      final ClassDecl receiverClass = receiverType.accept(ExtractClassDecl.INSTANCE, null);

      if (receiverClass == null)
      {
         final Marker error = error(receiver.getPosition(), "call.receiver.primitive", methodName,
                                    receiverType.getDescription());
         SentenceResolver.addStringLiteralTypoNotes(par, receiver, error);
         par.report(error);
         return callExpr;
      }

      final Position position = callExpr.getName().getPosition();
      final MethodDecl method = DeclResolver.resolveMethod(par, position, receiverClass, methodName);

      if (method == null)
      {
         // cannot create method (external or frozen class)
         return callExpr;
      }

      final List<ParameterDecl> parameters = method.getParameters();
      final ParameterDecl thisParameter;
      final Map<String, ParameterDecl> decls = new HashMap<>();

      final ResolvedName resolvedName = ResolvedName.of(method);
      resolvedName.setPosition(position);
      callExpr.setName(resolvedName);

      if (method.getParameters().isEmpty()) // method is new
      {
         // create this parameter
         thisParameter = ParameterDecl.of(method, "this", receiverClass.getType());
         thisParameter.setPosition(position);
         parameters.add(thisParameter);

         // create parameters based on arguments
         for (final NamedExpr argument : arguments)
         {
            final String name = argument.getName().getValue();
            final Expr expr = argument.getExpr();
            final Type type = expr.getType();
            final ParameterDecl param = ParameterDecl.of(method, name, type);
            param.setPosition(expr.getPosition());

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
                                         .collect(Collectors.joining(", "));
         final String args = arguments.stream().map(NamedExpr::getName).map(Name::getValue)
                                      .collect(Collectors.joining(", "));

         if (!params.equals(args))
         {
            final Marker error = error(position, "call.mismatch.params.args", receiverClass.getName(),
                                       methodName);
            error.note(note(position, "call.parameters", params));
            error.note(note(position, "call.arguments", args));
            error.note(firstDeclaration(method.getPosition(), method.getOwner(), method.getName()));
            par.report(error);
         }

         // match arguments and check types
         for (final NamedExpr argument : arguments)
         {
            final Expr expr = argument.getExpr();
            final Type type = expr.getType();
            final String name = argument.getName().getValue();
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

            par.report(error(expr.getPosition(), "call.mismatch.type", type.getDescription(), name,
                             paramType.getDescription()));
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
         final ParameterDecl param = (ParameterDecl) argument.getName().getDecl();
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

      final Scope scope = new ExtendingScope(decls, par);
      callExpr.getBody().accept(SentenceResolver.INSTANCE, scope);

      // set return type if necessary. has to happen after body resolution!
      inferMethodType(method, callExpr, par);

      method.getBody().getItems().addAll(callExpr.getBody().getItems());

      return callExpr;
   }

   private static void inferMethodType(MethodDecl method, CallExpr callExpr, Scope par)
   {
      final AnswerSentence answerSentence = callExpr.getBody().accept(GetAnswerSentence.INSTANCE, null);
      final Type methodType = method.getType();
      if (methodType == null)
      {
         // method is new
         final Type returnType = answerSentence != null ? answerSentence.getResult().getType() : PrimitiveType.VOID;
         method.setType(returnType);

         return;
      }

      if (answerSentence == null)
      {
         // no additional error
         return;
      }

      final Expr result = answerSentence.getResult();
      final Expr converted = TypeConversion.convert(result, methodType);
      if (converted != null)
      {
         answerSentence.setResult(converted);
         return;
      }

      final Type resultType = result.getType();
      par.report(error(result.getPosition(), "call.return.type", resultType.getDescription(), method.getName(),
                       methodType.getDescription()));
   }

   @Override
   public Expr visit(BinaryExpr binaryExpr, Scope par)
   {
      binaryExpr.setLhs(binaryExpr.getLhs().accept(this, par));
      binaryExpr.setRhs(binaryExpr.getRhs().accept(this, par));
      return binaryExpr;
   }

   // --------------- ConditionalExpr.Visitor ---------------

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
            conditionalOperatorExpr.setLhs(ErrorExpr.of(rhs.getType()));
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
            predicateOperatorExpr.setLhs(ErrorExpr.of(PrimitiveType.ERROR));
         }
         else
         {
            predicateOperatorExpr.setLhs(NameAccess.of(ResolvedName.of(predicateReceiver)));
         }
      }

      return predicateOperatorExpr;
   }

   // --------------- CollectionExpr.Visitor ---------------

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

      final Type startType = start.getType();
      final Type endType = end.getType();

      if (!TypeComparer.equals(startType, endType))
      {
         par.report(error(rangeExpr.getPosition(), "range.element.type.mismatch")
                       .note(note(start.getPosition(), "range.element.type.lower", startType.getDescription()))
                       .note(note(end.getPosition(), "range.element.type.upper", endType.getDescription())));
      }
      if (!PrimitiveType.isIntegral(startType))
      {
         par.report(error(start.getPosition(), "range.element.type.unsupported", startType.getDescription()));
      }
      if (!PrimitiveType.isIntegral(endType))
      {
         par.report(error(end.getPosition(), "range.element.type.unsupported", endType.getDescription()));
      }

      return rangeExpr;
   }

   @Override
   public Expr visit(FilterExpr filterExpr, Scope par)
   {
      final Expr source = filterExpr.getSource().accept(this, par);
      filterExpr.setSource(source);

      final Type sourceType = source.getType();
      final Type elementType = ((ListType) sourceType).getElementType();
      final VarDecl it = VarDecl.of("it", elementType, null);

      final Scope scope = new ExtendingScope(PREDICATE_RECEIVER, it, par);

      final Expr predicate = filterExpr.getPredicate().accept(this, scope);
      filterExpr.setPredicate(checkConditional(predicate, par));
      return filterExpr;
   }

   static Expr checkConditional(Expr predicate, Scope par)
   {
      final Expr converted = TypeConversion.convert(predicate, PrimitiveType.BOOLEAN);
      if (converted != null)
      {
         return converted;
      }

      par.report(error(predicate.getPosition(), "conditional.type", predicate.getType().getDescription()));
      return predicate;
   }
}
