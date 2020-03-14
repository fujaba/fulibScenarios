package org.fulib.scenarios.visitor.codegen;

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
   @Override
   public Void visit(AndConstraint andConstraint, CodeGenDTO par)
   {
      for (final Constraint constraint : andConstraint.getConstraints())
      {
         constraint.accept(this, par);
      }
      return null;
   }

   @Override
   public Void visit(LinkConstraint linkConstraint, CodeGenDTO par)
   {
      final String ownerName = linkConstraint.getOwner().getName().getValue();
      final String linkName = linkConstraint.getName() != null ? linkConstraint.getName().getValue() : "*";
      final String targetName = linkConstraint.getTarget().getValue();

      par.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %sPO);", ownerName, linkName, targetName));
      return null;
   }

   @Override
   public Void visit(AttributeConstraint ac, CodeGenDTO par)
   {
      this.generateAttributeConstraint(ac, par, s -> {});
      return null;
   }

   @Override
   public Void visit(AttributeEqualityConstraint aec, CodeGenDTO par)
   {
      this.generateAttributeConstraint(aec, par, patternObjectName -> {
         par.emitIndent();
         par.emit("builder.buildEqualityConstraint(" + patternObjectName + ", ");
         aec.getExpr().accept(ExprGenerator.INSTANCE, par);
         par.emit(");\n");
      });

      return null;
   }

   @Override
   public Void visit(AttributeConditionalConstraint acc, CodeGenDTO par)
   {
      this.generateAttributeConstraint(acc, par, patternObjectName -> {
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
      this.generateAttributeConstraint(apc, par, patternObjectName -> {
         par.emitIndent();
         par.emit("builder.buildAttributeConstraint(" + patternObjectName + ", it -> ");

         final Expr it = NameAccess.of(ResolvedName.of(VarDecl.of("it", null, null)));
         final PredicateOperatorExpr predOpExpr = PredicateOperatorExpr.of(it, apc.getPredicate());
         predOpExpr.accept(ExprGenerator.INSTANCE, par);

         par.emit(");\n");
      });

      return null;
   }

   private void generateAttributeConstraint(AttributeConstraint ac, CodeGenDTO gen, Consumer<? super String> poName)
   {
      final String ownerName = ac.getOwner().getName().getValue();
      final Name attribute = ac.getAttribute();
      final String attributeNameValue = attribute == null ? "*" : attribute.getValue();

      final String patternObjectName = ac.getPattern().getName().getValue();

      gen.emitLine(
         "final PatternObject " + patternObjectName + "PO = builder.buildPatternObject(\"" + patternObjectName + "\");");

      poName.accept(patternObjectName + "PO");

      gen.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %sPO);", ownerName, attributeNameValue,
                                 patternObjectName));
   }

   @Override
   public Void visit(MatchConstraint matchConstraint, CodeGenDTO par)
   {
      par.emitLine("builder.buildMatchConstraint(row -> {");

      par.indentLevel++;
      for (final Pattern pattern : matchConstraint.getPatterns())
      {
         // <type> _<name> = (<type>) row.get("<name>");
         final Decl decl = pattern.getName().getDecl();
         final String poName = decl.getName();
         final String varName = "_" + poName;
         decl.setName(varName);

         final String type = decl.getType().accept(TypeGenerator.INSTANCE, par);
         par.emitLine(String.format("final %s %s = (%s) row.get(\"%s\");", type, varName, type, poName));
      }

      // return <expr>;
      par.emitIndent();
      par.emit("return ");
      matchConstraint.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.emit(";\n");

      // remove leading _
      for (final Pattern pattern : matchConstraint.getPatterns())
      {
         final Decl decl = pattern.getName().getDecl();
         decl.setName(decl.getName().substring(1));
      }

      par.indentLevel--;

      final String commaSeparatedPONames = matchConstraint
         .getPatterns()
         .stream()
         .map(p -> p.getName().getValue() + "PO")
         .collect(Collectors.joining(", "));
      par.emitLine("}, " + commaSeparatedPONames + ");");
      return null;
   }
}
