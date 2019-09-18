package org.fulib.scenarios.ast.expr.operator;

import org.fulib.scenarios.ast.type.Type;

public enum BinaryOperator
{
   PLUS("+", (t1, t2) -> t1),
   MINUS("-", (t1, t2) -> t1),
   ;

   // =============== Fields ===============

   private final String symbol;

   private final java.util.function.BinaryOperator<Type> typer;

   // =============== Constructors ===============

   BinaryOperator(String symbol, java.util.function.BinaryOperator<Type> typer)
   {
      this.symbol = symbol;
      this.typer = typer;
   }

   // =============== Properties ===============

   public String getSymbol()
   {
      return this.symbol;
   }

   // =============== Methods ===============

   public Type getType(Type lhs, Type rhs)
   {
      return this.typer.apply(lhs, rhs);
   }
}
