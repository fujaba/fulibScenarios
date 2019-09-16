package org.fulib.scenarios.ast.expr;

import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.Typer;

public class ExprDelegate
{
   public static Type getType(Expr expr)
   {
      return expr.accept(Typer.INSTANCE, null);
   }
}
