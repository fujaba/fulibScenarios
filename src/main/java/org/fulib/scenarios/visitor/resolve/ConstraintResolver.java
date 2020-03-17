package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.scope.PatternReferenceCollectingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;

import java.util.*;

public class ConstraintResolver implements Constraint.Visitor<Scope, Constraint>
{
   private int uniqueIndex;

   @Override
   public Constraint visit(AndConstraint andConstraint, Scope par)
   {
      andConstraint.getConstraints().replaceAll(c -> {
         c.setOwner(andConstraint.getOwner());
         return c.accept(this, par);
      });

      if (andConstraint.getConstraints().size() == 1)
      {
         return andConstraint.getConstraints().get(0);
      }
      return andConstraint;
   }

   @Override
   public Constraint visit(LinkConstraint linkConstraint, Scope par)
   {
      final Name target = linkConstraint.getTarget();
      final Name resolvedTarget = target.accept(NameResolver.INSTANCE, par);
      linkConstraint.setTarget(resolvedTarget);

      final Decl decl = resolvedTarget.getDecl();
      if (decl == null)
      {
         par.report(Marker.error(target.getPosition(), "link-constraint.target.unresolved", target.getValue()));
      }
      else if (!(decl instanceof VarDecl) || ((VarDecl) decl).getPattern() == null)
      {
         par.report(Marker.error(target.getPosition(), "link-constraint.target.not.pattern-object", target.getValue()));
      }

      return linkConstraint;
   }

   @Override
   public Constraint visit(AttributeConstraint attributeConstraint, Scope par)
   {
      if (attributeConstraint.getPattern() == null)
      {
         this.makePattern(attributeConstraint, PrimitiveType.OBJECT);
      }
      return attributeConstraint;
   }

   private void makePattern(AttributeConstraint ac, Type type)
   {
      final Pattern owner = ac.getOwner();
      final Name attribute = ac.getAttribute();
      final String attributeName = attribute == null ? "Attr" + ++this.uniqueIndex : attribute.getValue();
      final VarDecl varDecl = VarDecl.of(owner.getName().getValue() + attributeName, type, null);
      final Pattern pattern = Pattern.of(type, ResolvedName.of(varDecl), Collections.emptyList());
      ac.setPattern(pattern);
   }

   static Pattern getPatternIfRef(Expr expr)
   {
      if (!(expr instanceof NameAccess))
      {
         return null;
      }

      final NameAccess access = (NameAccess) expr;
      final Name targetName = access.getName();
      final Decl decl = targetName.getDecl();

      if (!(decl instanceof VarDecl))
      {
         return null;
      }

      final VarDecl varDecl = (VarDecl) decl;
      return varDecl.getPattern();
   }

   @Override
   public Constraint visit(AttributeEqualityConstraint aec, Scope par)
   {
      final Expr expr = aec.getExpr().accept(ExprResolver.INSTANCE, par);

      // We match some object foo and some object bar
      // where attr of foo is bar.

      final Pattern target = getPatternIfRef(expr);
      if (target != null)
      {
         final LinkConstraint lc = LinkConstraint.of(aec.getAttribute(), target.getName());
         lc.setOwner(aec.getOwner());
         lc.setPosition(aec.getPosition());
         return lc.accept(this, par);
      }

      aec.setExpr(expr);

      this.makePattern(aec, expr.getType());

      return aec;
   }

   @Override
   public Constraint visit(AttributePredicateConstraint attributePredicateConstraint, Scope par)
   {
      // TODO disabled in parser, because the only predicates that exist are for lists or null,
      //      both of which never appear in tables
      return attributePredicateConstraint;
   }

   @Override
   public Constraint visit(AttributeConditionalConstraint acc, Scope par)
   {
      final Pattern owner = acc.getOwner();
      final Position position = acc.getPosition();
      final Name attribute = acc.getAttribute();
      final Expr rhs = acc.getRhs();
      final ConditionalOperator operator = acc.getOperator();

      switch (operator)
      {
      case CONTAINS:
      case IS:
         final AttributeEqualityConstraint aec = AttributeEqualityConstraint.of(attribute, rhs);
         aec.setOwner(owner);
         aec.setPosition(position);
         return aec.accept(this, par);
      }

      final Set<Pattern> patterns = new HashSet<>();
      final Scope scope = new PatternReferenceCollectingScope(par, patterns);
      final Expr resolvedRhs = rhs.accept(ExprResolver.INSTANCE, scope);
      final Type nullableLhsType = operator.getLhsType();
      final Type lhsType = nullableLhsType != null ? nullableLhsType : PrimitiveType.OBJECT;

      if (patterns.isEmpty())
      {
         acc.setRhs(resolvedRhs);

         this.makePattern(acc, lhsType);
         return acc;
      }

      // the rhs references a pattern object
      // -> convert to a generic attribute constraint followed by a match constraint

      final AttributeConstraint ac = AttributeConstraint.of(attribute);
      ac.setOwner(owner);
      ac.setPosition(position);
      this.makePattern(ac, lhsType);

      final Expr lhs = NameAccess.of(ac.getPattern().getName());
      lhs.setPosition(position);

      final Expr condExpr = ConditionalOperatorExpr.of(lhs, operator, resolvedRhs).accept(ExprResolver.INSTANCE, par);
      condExpr.setPosition(position);

      final MatchConstraint matchConstraint = MatchConstraint.of(condExpr, prepend(ac.getPattern(), patterns));
      matchConstraint.setOwner(owner);
      matchConstraint.setPosition(position);

      final AndConstraint andConstraint = AndConstraint.of(Arrays.asList(ac, matchConstraint));
      andConstraint.setOwner(owner);
      andConstraint.setPosition(position);
      return andConstraint;
   }

   private static List<Pattern> prepend(Pattern pattern, Collection<Pattern> patterns)
   {
      final List<Pattern> result = new ArrayList<>(1 + patterns.size());
      result.add(pattern);
      result.addAll(patterns);
      return result;
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
