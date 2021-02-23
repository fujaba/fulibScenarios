package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.PlaceholderExpr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.operator.BinaryExpr;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public enum ExprGenerator implements Expr.Visitor<CodeGenDTO, Object>
{
   INSTANCE, WITHER, FLAT;

   @Override
   public Object visit(Expr expr, CodeGenDTO par)
   {
      return null;
   }

   @Override
   public Object visit(ErrorExpr errorExpr, CodeGenDTO par)
   {
      final Type type = errorExpr.getType();
      if (type != null && type != PrimitiveType.ERROR)
      {
         // ((Type) null)
         par.bodyBuilder.append("((");
         par.bodyBuilder.append(type.accept(TypeGenerator.INSTANCE, par));
         par.bodyBuilder.append(") error)");
         return null;
      }

      par.bodyBuilder.append("error");
      return null;
   }

   @Override
   public Object visit(PlaceholderExpr placeholderExpr, CodeGenDTO par)
   {
      throw new IllegalStateException("Placeholder expressions should not appear in generated code!");
   }

   @Override
   public Object visit(AttributeAccess attributeAccess, CodeGenDTO par)
   {
      attributeAccess.getReceiver().accept(this, par);
      par.bodyBuilder.append(".get").append(StrUtil.cap(attributeAccess.getName().getValue())).append("()");
      return null;
   }

   @Override
   public Object visit(ExampleAccess exampleAccess, CodeGenDTO par)
   {
      exampleAccess.getExpr().accept(this, par);
      return null;
   }

   @Override
   public Object visit(CreationExpr creationExpr, CodeGenDTO par)
   {
      final String className = creationExpr.getType().accept(TypeGenerator.INSTANCE, par);

      par.bodyBuilder.append("new ").append(className).append("()");
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         generateSetterCall(par, attribute);
      }
      return null;
   }

   static void generateSetterCall(CodeGenDTO par, NamedExpr attribute)
   {
      final Name name = attribute.getName();
      final Expr expr = attribute.getExpr();

      final Decl decl = name.getDecl();
      final boolean wither = (decl != null ? decl.getType() : expr.getType()) instanceof ListType;

      par.bodyBuilder.append(wither ? ".with" : ".set").append(StrUtil.cap(name.getValue())).append("(");
      expr.accept(wither ? WITHER : INSTANCE, par);
      par.bodyBuilder.append(")");
   }

   @Override
   public Object visit(CallExpr callExpr, CodeGenDTO par)
   {
      callExpr.getReceiver().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append('.');
      par.bodyBuilder.append(callExpr.getName().getValue());
      par.bodyBuilder.append('(');

      final List<NamedExpr> arguments = callExpr.getArguments();
      if (!arguments.isEmpty())
      {
         this.emitList(par, arguments.stream().map(NamedExpr::getExpr).collect(Collectors.toList()));
      }
      par.bodyBuilder.append(')');

      return null;
   }

   @Override
   public Object visit(BinaryExpr binaryExpr, CodeGenDTO par)
   {
      // TODO parenthesize if necessary (precedence)
      binaryExpr.getLhs().accept(this, par);
      par.bodyBuilder.append(' ');
      par.bodyBuilder.append(binaryExpr.getOperator().getSymbol());
      par.bodyBuilder.append(' ');
      binaryExpr.getRhs().accept(this, par);
      return null;
   }

   @Override
   public Object visit(PrimaryExpr primaryExpr, CodeGenDTO par)
   {
      return null;
   }

   @Override
   public Object visit(NameAccess nameAccess, CodeGenDTO par)
   {
      par.bodyBuilder.append(nameAccess.getName().getValue());
      return null;
   }

   @Override
   public Object visit(IntLiteral intLiteral, CodeGenDTO par)
   {
      par.bodyBuilder.append(intLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(DoubleLiteral doubleLiteral, CodeGenDTO par)
   {
      par.bodyBuilder.append(doubleLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(BooleanLiteral booleanLiteral, CodeGenDTO par)
   {
      par.bodyBuilder.append(booleanLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(StringLiteral stringLiteral, CodeGenDTO par)
   {
      par.emitStringLiteral(stringLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(ConditionalOperatorExpr coe, CodeGenDTO par)
   {
      generateOperator(coe.getLhs(), coe.getOperator(), coe.getRhs(), par);
      return null;
   }

   private static void generateOperator(Expr lhs, ConditionalOperator op, Expr rhs, CodeGenDTO par)
   {
      switch (op)
      {
      // @formatter:off
      case OR: generateOperator(lhs, " || ", rhs, par); break;
      case AND: generateOperator(lhs, " && ", rhs, par); break;

      case IS:
         if (PrimitiveType.isJavaPrimitive(lhs.getType())) { generateOperator(lhs, " == ", rhs, par); }
         else { generateOperator("", lhs, ".equals(", rhs, ")", par); }
         break;
      case IS_NOT:
         if (PrimitiveType.isJavaPrimitive(lhs.getType())) { generateOperator(lhs, " != ", rhs, par); }
         else { generateOperator("!", lhs, ".equals(", rhs, ")", par); }
         break;

      case IS_SAME: generateOperator(lhs, " == ", rhs, par); break;
      case IS_NOT_SAME: generateOperator(lhs, " != ", rhs, par); break;

      case LT: generateComparisonOperator(lhs, " < ", rhs, par); break;
      case NOT_GT:
      case LE: generateComparisonOperator(lhs, " <= ", rhs, par); break;
      case GT: generateComparisonOperator(lhs, " > ", rhs, par); break;
      case NOT_LT:
      case GE: generateComparisonOperator(lhs, " >= ", rhs, par); break;

      case CONTAINS: generateOperator("", lhs, ".contains(", rhs, ")", par); break;
      case NOT_CONTAINS: generateOperator("!", lhs, ".contains(", rhs, ")", par); break;

      case MATCHES:
         if (lhs.getType() != PrimitiveType.STRING) { generateOperator("String.valueOf(", lhs, ").matches(", rhs, ")", par); }
         else { generateOperator("", lhs, ".matches(", rhs, ")", par); }
         break;
      // @formatter:on
      }
   }

   private static void generateComparisonOperator(Expr lhs, String operator, Expr rhs, CodeGenDTO par)
   {
      if (lhs.getType() == PrimitiveType.NUMBER)
      {
         operator = ".doubleValue()" + operator;
      }
      generateOperator(lhs, operator, rhs, par);
   }

   static void generateOperator(Expr lhs, String operator, Expr rhs, CodeGenDTO par)
   {
      generateOperator("", lhs, operator, rhs, "", par);
   }

   static void generateOperator(String prefix, Expr lhs, String operator, Expr rhs, String postfix, CodeGenDTO par)
   {
      par.bodyBuilder.append(prefix);
      lhs.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(operator);
      rhs.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(postfix);
   }

   @Override
   public Object visit(PredicateOperatorExpr predicateOperatorExpr, CodeGenDTO par)
   {
      final Expr lhs = predicateOperatorExpr.getLhs();
      switch (predicateOperatorExpr.getOperator())
      {
      case IS_NOT_EMPTY:
         if (lhs.getType() instanceof ListType)
         {
            par.bodyBuilder.append('!');
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(".isEmpty()");
         }
         else
         {
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(" != null");
         }
         return null;
      case IS_EMPTY:
         if (lhs.getType() instanceof ListType)
         {
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(".isEmpty()");
         }
         else
         {
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(" == null");
         }
         return null;
      }
      return null;
   }

   // --------------- CollectionExpr.Visitor ---------------

   // Range, Map and Filter handled by visit(CollectionExpr)

   @Override
   public Object visit(CollectionExpr collectionExpr, CodeGenDTO par)
   {
      par.addImport("java.util.stream.Collectors");

      collectionExpr.accept(StreamGenerator.INSTANCE, par);
      par.bodyBuilder.append(".collect(Collectors.toList())");
      return null;
   }

   @Override
   public Object visit(ListExpr listExpr, CodeGenDTO par)
   {
      // in a flat (Object...) context, anything goes
      if (this == FLAT)
      {
         this.emitList(par, listExpr.getElements());
         return null;
      }

      // a nested list means we have to go through Streams. No wither shortcuts possible.
      if (listExpr.getElements().stream().anyMatch(it -> it.getType() instanceof ListType))
      {
         // let stream generator handle any necessary flattening

         par.addImport("java.util.stream.Collectors");

         listExpr.accept(StreamGenerator.INSTANCE, par);
         par.bodyBuilder.append(".collect(Collectors.toList())");

         return null;
      }

      // a wither context allows varargs, so no need to wrap the elements in an ArrayList
      if (this == WITHER)
      {
         this.emitList(par, listExpr.getElements());
         return null;
      }

      par.addImport("java.util.ArrayList");
      par.addImport("java.util.Arrays");

      par.bodyBuilder.append("new ArrayList<>(Arrays.asList(");
      this.emitList(par, listExpr.getElements());
      par.bodyBuilder.append("))");

      return null;
   }

   void emitList(CodeGenDTO par, List<Expr> elements)
   {
      elements.get(0).accept(this, par);
      for (int i = 1; i < elements.size(); i++)
      {
         par.bodyBuilder.append(", ");
         elements.get(i).accept(this, par);
      }
   }
}
