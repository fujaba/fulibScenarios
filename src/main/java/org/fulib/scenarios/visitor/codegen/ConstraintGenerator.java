package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.pattern.AttributeEqualityConstraint;
import org.fulib.scenarios.ast.pattern.Constraint;

public class ConstraintGenerator implements Constraint.Visitor<CodeGenDTO, Void>
{
   private final String receiverName;
   private int unknownAttributeNumber;

   public ConstraintGenerator(String receiverName)
   {
      this.receiverName = receiverName;
   }

   @Override
   public Void visit(AttributeEqualityConstraint attributeEqualityConstraint, CodeGenDTO par)
   {
      final Name attribute = attributeEqualityConstraint.getName();
      final String attributeValue = attribute.getValue();
      final String name = "*".equals(attributeValue) ?
         this.generateUnknownAttributeName() :
         this.receiverName + StrUtil.cap(attributeValue);

      par.emitLine("final PatternObject " + name + " = builder.buildPatternObject(\"" + name + "\");");

      par.emitIndent();
      par.emit("builder.buildEqualityConstraint(" + name + ", ");
      attributeEqualityConstraint.getExpr().accept(ExprGenerator.INSTANCE, par);
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
