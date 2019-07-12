package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
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
   public Object visit(AttributeAccess attributeAccess, CodeGenDTO par)
   {
      attributeAccess.getReceiver().accept(this, par);
      par.bodyBuilder.append(".get").append(StrUtil.cap(attributeAccess.getName().accept(Namer.INSTANCE, null)))
                     .append("()");
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
      final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
      final Decl decl = attribute.getName().accept(ExtractDecl.INSTANCE, null);
      final boolean wither = decl.getType() instanceof ListType;

      par.bodyBuilder.append(wither ? ".with" : ".set").append(StrUtil.cap(attributeName)).append("(");
      attribute.getExpr().accept(wither ? NO_LIST : INSTANCE, par);
      par.bodyBuilder.append(")");
   }

   @Override
   public Object visit(CallExpr callExpr, CodeGenDTO par)
   {
      callExpr.getReceiver().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append('.');
      par.bodyBuilder.append(callExpr.getName().accept(Namer.INSTANCE, null));
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
      par.bodyBuilder.append(nameAccess.getName().accept(Namer.INSTANCE, null));
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
