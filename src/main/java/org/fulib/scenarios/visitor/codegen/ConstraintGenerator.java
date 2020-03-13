package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConstraintGenerator implements Constraint.Visitor<CodeGenDTO, Void>
{
   private final String receiverName;
   private int unknownAttributeNumber;

   public ConstraintGenerator(String receiverName)
   {
      this.receiverName = receiverName;
   }

   @Override
   public Void visit(LinkConstraint linkConstraint, CodeGenDTO par)
   {
      final Name name = linkConstraint.getName();
      final String nameValue = name != null ? name.getValue() : "*";
      par.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %sPO);", this.receiverName, nameValue,
                                 linkConstraint.getTarget().getValue()));
      return null;
   }

   @Override
   public Void visit(AttributeEqualityConstraint attributeEqualityConstraint, CodeGenDTO par)
   {
      this.generateAttributeConstraint(attributeEqualityConstraint.getName(), par, patternObjectName -> {
         par.emitIndent();
         par.emit("builder.buildEqualityConstraint(" + patternObjectName + ", ");
         attributeEqualityConstraint.getExpr().accept(ExprGenerator.INSTANCE, par);
         par.emit(");\n");
      });

      return null;
   }

   @Override
   public Void visit(AttributeConditionalConstraint acc, CodeGenDTO par)
   {
      this.generateAttributeConstraint(acc.getName(), par, patternObjectName -> {
         final ConditionalOperator operator = acc.getOperator();
         final Type lhsType = operator.getLhsType();
         final Type wrappedLhsType = PrimitiveType.primitiveToWrapper(lhsType);

         par.emitIndent();
         par.emit("builder.buildAttributeConstraint(");
         par.emit(patternObjectName);
         par.emit(", ");
         par.emit(wrappedLhsType.accept(TypeGenerator.INSTANCE, par));
         par.emit(".class, it -> ");

         final Expr it = NameAccess.of(ResolvedName.of(VarDecl.of("it", lhsType, null)));
         final ConditionalOperatorExpr condOpExpr = ConditionalOperatorExpr.of(it, operator, acc.getRhs());
         condOpExpr.accept(ExprGenerator.INSTANCE, par);

         par.emit(");\n");
      });

      return null;
   }

   @Override
   public Void visit(AttributePredicateConstraint apc, CodeGenDTO par)
   {
      this.generateAttributeConstraint(apc.getName(), par, patternObjectName -> {
         par.emitIndent();
         par.emit("builder.buildAttributeConstraint(" + patternObjectName + ", it -> ");

         final Expr it = NameAccess.of(ResolvedName.of(VarDecl.of("it", null, null)));
         final PredicateOperatorExpr predOpExpr = PredicateOperatorExpr.of(it, apc.getPredicate());
         predOpExpr.accept(ExprGenerator.INSTANCE, par);

         par.emit(");\n");
      });

      return null;
   }

   private void generateAttributeConstraint(Name attributeName, CodeGenDTO gen, Consumer<? super String> poName)
   {
      final String attributeNameValue;
      final String patternObjectName;

      if (attributeName == null)
      {
         attributeNameValue = "*";
         patternObjectName = this.generateUnknownAttributeName();
      }
      else
      {
         attributeNameValue = attributeName.getValue();
         patternObjectName = this.receiverName + StrUtil.cap(attributeNameValue);
      }

      gen.emitLine(
         "final PatternObject " + patternObjectName + " = builder.buildPatternObject(\"" + patternObjectName + "\");");

      poName.accept(patternObjectName);

      gen.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %s);", this.receiverName, attributeNameValue,
                                 patternObjectName));
   }

   private String generateUnknownAttributeName()
   {
      return this.receiverName + "Attr" + ++this.unknownAttributeNumber;
   }

   @Override
   public Void visit(MatchConstraint matchConstraint, CodeGenDTO par)
   {
      par.emit("builder.buildMatchConstraint(row -> {");

      par.indentLevel++;
      for (final Pattern pattern : matchConstraint.getPatterns())
      {
         // <type> <name> = (<type>) row.get("<name>");
         final Name name = pattern.getName();
         final Decl decl = name.getDecl();
         final String type = decl.getType().accept(TypeGenerator.INSTANCE, par);
         par.emit(String.format("%s %s = (%s) row.get(\"%s\");", type, decl.getName(), type, name.getValue()));
      }

      // return <expr>;
      par.emitIndent();
      par.emit("return ");
      matchConstraint.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.emit(";\n");

      par.indentLevel--;

      final String commaSeparatedPONames = matchConstraint
         .getPatterns()
         .stream()
         .map(p -> p.getName().getValue() + "PO")
         .collect(Collectors.joining(", "));
      par.emit("}, " + commaSeparatedPONames + ");");
      return null;
   }
}
