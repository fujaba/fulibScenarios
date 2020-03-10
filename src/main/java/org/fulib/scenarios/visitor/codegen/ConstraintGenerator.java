package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.*;

import java.util.function.Consumer;

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
         par.emitIndent();
         par.emit("builder.buildAttributeConstraint(" + patternObjectName + ", it -> ");

         final Expr it = makeItExpr();
         final ConditionalOperatorExpr condOpExpr = ConditionalOperatorExpr.of(it, acc.getOperator(), acc.getRhs());
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

         final Expr it = makeItExpr();
         final PredicateOperatorExpr predOpExpr = PredicateOperatorExpr.of(it, apc.getPredicate());
         predOpExpr.accept(ExprGenerator.INSTANCE, par);

         par.emit(");\n");
      });

      return null;
   }

   private static Expr makeItExpr()
   {
      return NameAccess.of(ResolvedName.of(VarDecl.of("it", null, null)));
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
}
