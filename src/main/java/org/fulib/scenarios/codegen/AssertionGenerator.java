package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

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
      final Expr receiver = attributeCheckExpr.getReceiver();
      final Name attributeName = attributeCheckExpr.getAttribute();

      codeGen.addImport("static org.junit.Assert.assertEquals");

      codeGen.emit("assertEquals(");

      attributeCheckExpr.getValue().accept(ExprGenerator.INSTANCE, codeGen);
      codeGen.emit(", ");

      receiver.accept(ExprGenerator.INSTANCE, codeGen);
      codeGen.emit(".get");
      codeGen.emit(StrUtil.cap(attributeName.accept(Namer.INSTANCE, null)));
      codeGen.emit("()");

      final Type type = AttributeAccess.of(attributeName, receiver).accept(Typer.INSTANCE, null);
      if (type == PrimitiveType.FLOAT || type == PrimitiveType.DOUBLE)
      {
         codeGen.emit(", 0");
      }

      codeGen.emit(")");
      return null;
   }
}
