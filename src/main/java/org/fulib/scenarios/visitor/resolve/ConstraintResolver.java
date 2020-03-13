package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.scope.PatternReferenceCollectingScope;
import org.fulib.scenarios.ast.scope.Scope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
               final LinkConstraint lc = LinkConstraint.of(aec.getName(), targetName);
               lc.setOwner(aec.getOwner());
               lc.setPosition(aec.getPosition());
               return lc.accept(this, par);
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
   public Constraint visit(AttributeConditionalConstraint acc, Scope par)
   {
      if (acc.getOperator() == ConditionalOperator.IS)
      {
         final AttributeEqualityConstraint aec = AttributeEqualityConstraint.of(acc.getName(), acc.getRhs());
         aec.setOwner(acc.getOwner());
         aec.setPosition(acc.getPosition());
         return aec.accept(this, par);
      }

      final Set<Pattern> patterns = new HashSet<>();
      final Scope scope = new PatternReferenceCollectingScope(par, patterns);
      final Expr resolvedRhs = acc.getRhs().accept(ExprResolver.INSTANCE, scope);

      if (patterns.isEmpty())
      {
         acc.setRhs(resolvedRhs);
         return acc;
      }

      // the rhs references a pattern object
      // -> convert to match constraint
      final Pattern owner = acc.getOwner();
      final Expr lhs = AttributeAccess.of(acc.getName(), NameAccess.of(owner.getName()));
      final Expr condExpr = ConditionalOperatorExpr.of(lhs, acc.getOperator(), resolvedRhs);
      final Expr resolvedExpr = condExpr.accept(ExprResolver.INSTANCE, par);

      final List<Pattern> patternList = new ArrayList<>();
      patternList.add(owner);
      patternList.addAll(patterns);

      final MatchConstraint matchConstraint = MatchConstraint.of(resolvedExpr, patternList);
      matchConstraint.setOwner(owner);
      matchConstraint.setPosition(acc.getPosition());
      return matchConstraint;
   }

   @Override
   public Constraint visit(MatchConstraint matchConstraint, Scope par)
   {
      final Set<Pattern> patterns = new HashSet<>();
      final Scope scope = new PatternReferenceCollectingScope(par, patterns);

      matchConstraint.setExpr(matchConstraint.getExpr().accept(ExprResolver.INSTANCE, scope));
      matchConstraint.getPatterns().addAll(patterns);
      return matchConstraint;
   }
}
