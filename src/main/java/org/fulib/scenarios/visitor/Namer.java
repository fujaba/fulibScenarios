package org.fulib.scenarios.visitor;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.type.*;

public enum Namer implements Type.Visitor<Object, String>, Expr.Visitor<Object, String>, Name.Visitor<Object, String>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public String visit(UnresolvedType unresolvedType, Object par)
   {
      return unresolvedType.getName();
   }

   @Override
   public String visit(ClassType classType, Object par)
   {
      return classType.getClassDecl().getName();
   }

   @Override
   public String visit(ListType listType, Object par)
   {
      return "List";
   }

   @Override
   public String visit(PrimitiveType primitiveType, Object par)
   {
      return primitiveType.getJavaName();
   }

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
   public String visit(ListAttributeAccess listAttributeAccess, Object par)
   {
      return listAttributeAccess.getName().accept(this, par);
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
      return StrUtil.downFirstChar(creationExpr.getType().accept(this, par));
   }

   @Override
   public String visit(CallExpr callExpr, Object par)
   {
      final Expr expr = callExpr.getBody().accept(ReturnExpr.INSTANCE, par);
      return expr != null ? expr.accept(Namer.INSTANCE, par) : null;
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName().accept(this, par);
   }

   @Override
   public String visit(ListExpr listExpr, Object par)
   {
      // TODO maybe "e1,e2,e3" should be named "es" (i.e. common prefix)?
      return null;
   }

   // --------------- Name.Visitor ---------------

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
