package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.scope.Scope;

public enum ConstraintResolver implements Constraint.Visitor<Scope, Constraint>
{
   INSTANCE;

   @Override
   public Constraint visit(LinkConstraint linkConstraint, Scope par)
   {
      // TODO error
      linkConstraint.setTarget(linkConstraint.getTarget().accept(NameResolver.INSTANCE, par));
      return linkConstraint;
   }

   @Override
   public Constraint visit(AttributeEqualityConstraint attributeEqualityConstraint, Scope par)
   {
      attributeEqualityConstraint.setExpr(attributeEqualityConstraint.getExpr().accept(ExprResolver.INSTANCE, par));
      return attributeEqualityConstraint;
   }

   @Override
   public Constraint visit(AttributePredicateConstraint attributePredicateConstraint, Scope par)
   {
      return attributePredicateConstraint;
   }

   @Override
   public Constraint visit(AttributeConditionalConstraint attributeConditionalConstraint, Scope par)
   {
      attributeConditionalConstraint.setRhs(attributeConditionalConstraint.getRhs().accept(ExprResolver.INSTANCE, par));
      return attributeConditionalConstraint;
   }
}
