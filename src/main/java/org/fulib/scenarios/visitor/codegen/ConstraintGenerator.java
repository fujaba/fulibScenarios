package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.pattern.AttributeEqualityConstraint;
import org.fulib.scenarios.ast.pattern.Constraint;
import org.fulib.scenarios.ast.pattern.LinkConstraint;

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
