package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;

public class PatternPredicateGenerator implements Expr.Visitor<CodeGenDTO, Void>
{
   private final String receiverName;
   private int unknownAttributeNumber;

   public PatternPredicateGenerator(String receiverName)
   {
      this.receiverName = receiverName;
   }

   @Override
   public Void visit(AttributeCheckExpr attributeCheckExpr, CodeGenDTO par)
   {
      final Name attribute = attributeCheckExpr.getAttribute();
      final String attributeValue = attribute.getValue();
      final String name = "*".equals(attributeValue) ?
         this.generateUnknownAttributeName() :
         this.receiverName + StrUtil.cap(attributeValue);

      par.emitLine("final PatternObject " + name + " = builder.buildPatternObject(\"" + name + "\");");

      par.emitIndent();
      par.emit("builder.buildEqualityConstraint(" + name + ", ");
      attributeCheckExpr.getValue().accept(ExprGenerator.INSTANCE, par);
      par.emit(");\n");

      par.emitLine(
         String.format("builder.buildPatternLink(%sPO, \"%s\", %s);", this.receiverName, attributeValue, name));

      return null;
   }

   private String generateUnknownAttributeName()
   {
      return this.receiverName + "Attr" + ++this.unknownAttributeNumber;
   }
}