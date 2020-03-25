package org.fulib.scenarios.visitor.codegen;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;

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
      this.generateAttributeConstraint(ac, par);
      return null;
   }

   @Override
   public Void visit(AttributeEqualityConstraint aec, CodeGenDTO par)
   {
      final String patternObjectName = this.generateAttributeConstraint(aec, par);

      par.emitIndent();
      par.emit("builder.buildEqualityConstraint(" + patternObjectName + ", ");
      aec.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.emit(");\n");

      return null;
   }

   @Override
   public Void visit(AttributeConditionalConstraint acc, CodeGenDTO par)
   {
      final Expr it = NameAccess.of(acc.getPattern().getName());
      final ConditionalOperatorExpr condOpExpr = ConditionalOperatorExpr.of(it, acc.getOperator(), acc.getRhs());

      this.generateAttributeConstraint(acc, condOpExpr, par);

      return null;
   }

   @Override
   public Void visit(AttributePredicateConstraint apc, CodeGenDTO par)
   {
      final Expr it = NameAccess.of(apc.getPattern().getName());
      final PredicateOperatorExpr predOpExpr = PredicateOperatorExpr.of(it, apc.getPredicate());

      this.generateAttributeConstraint(apc, predOpExpr, par);

      return null;
   }

   private void generateAttributeConstraint(AttributeConstraint ac, Expr expr, CodeGenDTO gen)
   {
      final String patternObjectName = this.generateAttributeConstraint(ac, gen);
      final Pattern pattern = ac.getPattern();
      final String name = pattern.getName().getValue();
      final String typeStr = pattern.getType().accept(TypeGenerator.INSTANCE, gen);

      gen.emitIndent();
      gen.emit("builder.buildAttributeConstraint(" + patternObjectName + ", (" + typeStr + " " + name + ") -> ");
      expr.accept(ExprGenerator.INSTANCE, gen);
      gen.emit(");\n");
   }

   private String generateAttributeConstraint(AttributeConstraint ac, CodeGenDTO gen)
   {
      final String ownerName = ac.getOwner().getName().getValue();
      final Name attribute = ac.getAttribute();
      final String attributeNameValue = attribute == null ? "*" : attribute.getValue();
      final Pattern pattern = ac.getPattern();
      final String patternObjectName = pattern.getName().getValue();

      SentenceGenerator.generatePO(pattern, gen);

      gen.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %sPO);", ownerName, attributeNameValue,
                                 patternObjectName));

      return patternObjectName + "PO";
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

         Type type = decl.getType();
         if (type instanceof ListType)
         {
            type = ((ListType) type).getElementType();
         }

         final String typeStr = type.accept(TypeGenerator.INSTANCE, par);
         par.emitLine(String.format("final %s %s = (%s) row.get(\"%s\");", typeStr, varName, typeStr, poName));
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
