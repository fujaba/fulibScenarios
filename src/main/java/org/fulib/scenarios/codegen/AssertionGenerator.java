package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.transform.Namer;

public enum AssertionGenerator implements ConditionalExpr.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   @Override
   public Object visit(ConditionalExpr conditionalExpr, CodeGenerator codeGen)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AttributeCheckExpr attributeCheckExpr, CodeGenerator codeGen)
   {
      codeGen.emit("assertEquals(");
      attributeCheckExpr.getValue().accept(codeGen, null);
      codeGen.emit(", ");
      attributeCheckExpr.getReceiver().accept(codeGen, null);
      codeGen.emit(".get");
      final String attributeName = attributeCheckExpr.getAttribute().accept(Namer.INSTANCE, null);
      codeGen.emit(attributeName);
      codeGen.emit("()");
      return null;
   }
}
