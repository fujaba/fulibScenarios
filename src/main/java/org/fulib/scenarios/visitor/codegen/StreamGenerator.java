package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.MapAccessExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;

import java.util.ArrayList;
import java.util.List;

public enum StreamGenerator implements Expr.Visitor<CodeGenDTO, Void>
{
   INSTANCE;

   @Override
   public Void visit(Expr expr, CodeGenDTO par)
   {
      expr.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(".stream()");
      return null;
   }

   @Override
   public Void visit(ListExpr listExpr, CodeGenDTO par)
   {
      par.addImport("java.util.stream.Stream");

      final List<Expr> elements = listExpr.getElements();
      if (elements.isEmpty())
      {
         par.bodyBuilder.append("Stream.empty()");
         return null;
      }

      if (elements.stream().noneMatch(it -> it.getType() instanceof ListType))
      {
         // no flattening required

         par.bodyBuilder.append("Stream.of(");
         ExprGenerator.INSTANCE.emitList(par, elements);
         par.bodyBuilder.append(')');
         return null;
      }

      final List<Expr> grouped = group(elements);
      if (grouped.size() == 1)
      {
         // grouping returned only one element, no (explicit) flattening required
         grouped.get(0).accept(this, par);
         return null;
      }
      if (grouped.size() == 2)
      {
         // for two streams, concat is ok
         par.bodyBuilder.append("Stream.concat(");
         grouped.get(0).accept(this, par);
         par.bodyBuilder.append(", ");
         grouped.get(1).accept(this, par);
         par.bodyBuilder.append(')');
         return null;
      }

      // for more than two stream, we use a stream of streams and then flatten it.

      par.bodyBuilder.append("Stream.of(");

      grouped.get(0).accept(this, par);
      for (int i = 1; i < grouped.size(); i++)
      {
         par.bodyBuilder.append(", ");
         grouped.get(i).accept(this, par);
      }

      par.addImport("java.util.function.Function");
      par.bodyBuilder.append(").flatMap(Function.identity())");
      return null;
   }

   private static List<Expr> group(List<Expr> elements)
   {
      final int size = elements.size();
      final List<Expr> result = new ArrayList<>(size);

      int start = 0;
      while (start < size)
      {
         int end = start;
         while (end < size && !(elements.get(end).getType() instanceof ListType))
         {
            end++;
         }
         if (end > start)
         {
            // can use sublist views here because we know they won't be modified
            // (by visit(ListExpr) anyway, the only caller)
            result.add(ListExpr.of(elements.subList(start, end)));
            start = end;
         }
         else
         {
            result.add(elements.get(start));
            start++;
         }
      }

      return result;
   }

   @Override
   public Void visit(RangeExpr rangeExpr, CodeGenDTO par)
   {
      final Type type = rangeExpr.getStart().getType();
      if (!(type instanceof PrimitiveType))
      {
         par.bodyBuilder.append("error");
         return null;
      }

      final PrimitiveType primitiveType = (PrimitiveType) type;
      switch (primitiveType)
      {
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
         this.emitRangeStream(rangeExpr, par, "IntStream");
         par.bodyBuilder.append(".mapToObj(i -> (");
         par.bodyBuilder.append(primitiveType.getJavaName());
         par.bodyBuilder.append(") i)");
         return null;
      case INT:
      case INT_WRAPPER:
         this.emitRangeStream(rangeExpr, par, "IntStream");
         par.bodyBuilder.append(".boxed()");
         return null;
      case LONG:
      case LONG_WRAPPER:
         this.emitRangeStream(rangeExpr, par, "LongStream");
         par.bodyBuilder.append(".boxed()");
         return null;
      default:
         par.bodyBuilder.append("error");
         return null;
      }
   }

   private void emitRangeStream(RangeExpr rangeExpr, CodeGenDTO par, String streamClass)
   {
      par.addImport("java.util.stream." + streamClass);
      par.addImport("java.util.stream.Collectors");

      par.bodyBuilder.append(streamClass);
      par.bodyBuilder.append(".rangeClosed(");
      rangeExpr.getStart().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(", ");
      rangeExpr.getEnd().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(")");
   }

   @Override
   public Void visit(MapAccessExpr listAttributeAccess, CodeGenDTO par)
   {
      final Type listType = listAttributeAccess.getReceiver().getType();
      final Type elementType = ((ListType) listType).getElementType();
      final String elementTypeName = elementType.accept(Namer.INSTANCE, elementType);
      final Decl attribute = listAttributeAccess.getName().accept(ExtractDecl.INSTANCE, null);

      listAttributeAccess.getReceiver().accept(this, par);
      par.bodyBuilder.append(".map(");
      par.bodyBuilder.append(elementTypeName);
      par.bodyBuilder.append("::get");
      par.bodyBuilder.append(StrUtil.cap(attribute.getName()));
      par.bodyBuilder.append(')');

      if (attribute.getType() instanceof ListType)
      {
         par.bodyBuilder.append(".flatMap(x -> x.stream())");
      }
      return null;
   }

   @Override
   public Void visit(FilterExpr filterExpr, CodeGenDTO par)
   {
      filterExpr.getSource().accept(this, par);
      par.bodyBuilder.append(".filter(it -> ");
      filterExpr.getPredicate().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(')');
      return null;
   }
}
