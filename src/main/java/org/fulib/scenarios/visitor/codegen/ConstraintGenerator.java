package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.pattern.AttributeEqualityConstraint;
import org.fulib.scenarios.ast.pattern.Constraint;
import org.fulib.scenarios.ast.pattern.LinkConstraint;

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
      final Name attributeName = attributeEqualityConstraint.getName();
      final String attributeNameValue;
      final String patteronObjectName;

      if (attributeName == null)
      {
         attributeNameValue = "*";
         patteronObjectName = this.generateUnknownAttributeName();
      }
      else
      {
         attributeNameValue = attributeName.getValue();
         patteronObjectName = this.receiverName + StrUtil.cap(attributeNameValue);
      }

      par.emitLine("final PatternObject " + patteronObjectName + " = builder.buildPatternObject(\"" + patteronObjectName
                   + "\");");

      par.emitIndent();
      par.emit("builder.buildEqualityConstraint(" + patteronObjectName + ", ");
      attributeEqualityConstraint.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.emit(");\n");

      par.emitLine(String.format("builder.buildPatternLink(%sPO, \"%s\", %s);", this.receiverName, attributeNameValue,
                                 patteronObjectName));

      return null;
   }

   private String generateUnknownAttributeName()
   {
      return this.receiverName + "Attr" + ++this.unknownAttributeNumber;
   }
}
