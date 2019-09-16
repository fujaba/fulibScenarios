package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.ErrorExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Typer;

import java.util.List;
import java.util.stream.Collectors;

public enum ExprGenerator implements Expr.Visitor<CodeGenDTO, Object>
{
   INSTANCE, NO_LIST;

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

      final Decl decl = name.accept(ExtractDecl.INSTANCE, null);
      final boolean wither = (decl != null ? decl.getType() : expr.accept(Typer.INSTANCE, null)) instanceof ListType;

      par.bodyBuilder.append(wither ? ".with" : ".set").append(StrUtil.cap(name.getValue())).append("(");
      expr.accept(wither ? NO_LIST : INSTANCE, par);
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
   public Object visit(StringLiteral stringLiteral, CodeGenDTO par)
   {
      par.emitStringLiteral(stringLiteral.getValue());
      return null;
   }

   @Override
   public Object visit(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenDTO par)
   {
      final boolean numeric = AssertionGenerator.isNumeric(conditionalOperatorExpr);
      final ConditionalOperator operator = conditionalOperatorExpr.getOperator();
      final String format = numeric ? operator.getNumberOperator() : operator.getObjectOperator();
      AssertionGenerator.generateCondOp(conditionalOperatorExpr, par, format);
      return null;
   }

   @Override
   public Object visit(PredicateOperatorExpr predicateOperatorExpr, CodeGenDTO par)
   {
      final Expr lhs = predicateOperatorExpr.getLhs();
      switch (predicateOperatorExpr.getOperator())
      {
      case IS_NOT_EMPTY:
         if (lhs.accept(Typer.INSTANCE, null) instanceof ListType)
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
         if (lhs.accept(Typer.INSTANCE, null) instanceof ListType)
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
      if (this == NO_LIST)
      {
         this.emitList(par, listExpr.getElements());
      }
      else if (listExpr.getElements().stream().anyMatch(it -> it.accept(Typer.INSTANCE, null) instanceof ListType))
      {
         // let stream generator handle any necessary flattening

         par.addImport("java.util.stream.Collectors");

         listExpr.accept(StreamGenerator.INSTANCE, par);
         par.bodyBuilder.append(".collect(Collectors.toList())");
      }
      else
      {
         par.addImport("java.util.ArrayList");
         par.addImport("java.util.Arrays");

         par.bodyBuilder.append("new ArrayList<>(Arrays.asList(");
         this.emitList(par, listExpr.getElements());
         par.bodyBuilder.append("))");
      }

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
