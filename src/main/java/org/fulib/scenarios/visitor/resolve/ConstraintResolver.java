package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
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
   public Constraint visit(AttributeEqualityConstraint aec, Scope par)
   {
      final Expr expr = aec.getExpr().accept(ExprResolver.INSTANCE, par);
      aec.setExpr(expr);

      if (expr instanceof NameAccess)
      {
         final NameAccess access = (NameAccess) expr;
         final Name targetName = access.getName();
         final Decl decl = targetName.getDecl();
         if (decl instanceof VarDecl)
         {
            final VarDecl varDecl = (VarDecl) decl;
            if (varDecl.getPattern() != null)
            {
               return LinkConstraint.of(aec.getName(), targetName).accept(this, par);
            }
         }
      }

      return aec;
   }

   @Override
   public Constraint visit(AttributePredicateConstraint attributePredicateConstraint, Scope par)
   {
      // TODO
      return attributePredicateConstraint;
   }

   @Override
   public Constraint visit(AttributeConditionalConstraint attributeConditionalConstraint, Scope par)
   {
      if (attributeConditionalConstraint.getOperator() == ConditionalOperator.IS)
      {
         return AttributeEqualityConstraint
            .of(attributeConditionalConstraint.getName(), attributeConditionalConstraint.getRhs())
            .accept(this, par);
      }
      attributeConditionalConstraint.setRhs(attributeConditionalConstraint.getRhs().accept(ExprResolver.INSTANCE, par));
      // TODO
      return attributeConditionalConstraint;
   }
}
