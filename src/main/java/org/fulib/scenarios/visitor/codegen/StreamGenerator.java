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
import org.fulib.scenarios.visitor.Typer;

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

      par.bodyBuilder.append("Stream.of(");
      ExprGenerator.INSTANCE.emitList(par, listExpr.getElements());
      par.bodyBuilder.append(')');
      return null;
   }

   @Override
   public Void visit(RangeExpr rangeExpr, CodeGenDTO par)
   {
      final PrimitiveType type = (PrimitiveType) rangeExpr.getStart().accept(Typer.INSTANCE, null);
      switch (type)
      {
      case BYTE:
      case BYTE_WRAPPER:
      case SHORT:
      case SHORT_WRAPPER:
      case CHAR:
      case CHAR_WRAPPER:
         this.emitRangeStream(rangeExpr, par, "IntStream");
         par.bodyBuilder.append(".mapToObj(i -> (");
         par.bodyBuilder.append(type.getJavaName());
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
         throw new IllegalStateException("invalid range element type " + type.getJavaName());
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
      final Type listType = listAttributeAccess.getReceiver().accept(Typer.INSTANCE, null);
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
         par.bodyBuilder.append(".flatMap(List::stream)");
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
