package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;

public enum Namer implements Expr.Visitor<Object, String>, Name.Visitor<Object, String>
{
   INSTANCE;

   // --------------- Expr.Visitor ---------------

   @Override
   public String visit(Expr expr, Object par)
   {
      return null;
   }

   @Override
   public String visit(AttributeAccess attributeAccess, Object par)
   {
      return attributeAccess.getName().accept(this, par);
   }

   @Override
   public String visit(ExampleAccess exampleAccess, Object par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public String visit(CreationExpr creationExpr, Object par)
   {
      // the name of the first named expression, or else the class name
      for (NamedExpr attribute : creationExpr.getAttributes())
      {
         final String exprName = attribute.getExpr().accept(this, par);
         if (exprName != null)
         {
            return exprName;
         }
      }
      return creationExpr.getClassName().accept(this, par);
   }

   @Override
   public String visit(PrimaryExpr primaryExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public String visit(NumberLiteral numberLiteral, Object par)
   {
      return null;
   }

   @Override
   public String visit(StringLiteral stringLiteral, Object par)
   {
      return null;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public String visit(Name name, Object par)
   {
      return null;
   }

   @Override
   public String visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName.getDecl().getName();
   }

   @Override
   public String visit(UnresolvedName unresolvedName, Object par)
   {
      return unresolvedName.getValue();
   }
}
