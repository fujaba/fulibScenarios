package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.Attribute;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;

public class Typer implements Decl.Visitor<Object, String>, Expr.Visitor<Object, String>
{
   private final ClassModel classModel;

   public Typer(ClassModel classModel)
   {
      this.classModel = classModel;
   }

   // --------------- VarDecl.Visitor ---------------

   @Override
   public String visit(Decl decl, Object par)
   {
      return decl.getType();
   }

   @Override
   public String visit(VarDecl varDecl, Object par)
   {
      if (varDecl.getType() == null)
      {
         varDecl.setType(varDecl.getExpr().accept(this, par));
      }
      return varDecl.getType();
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
      final String attributeName = attributeAccess.getName().accept(Namer.INSTANCE, null);
      final String receiverType = attributeAccess.getReceiver().accept(this, par);
      final Clazz receiverClazz = this.classModel.getClazz(receiverType);

      final Attribute attribute = receiverClazz.getAttribute(attributeName);
      if (attribute != null)
      {
         return attribute.getType();
      }

      final AssocRole role = receiverClazz.getRole(attributeName);
      if (role != null)
      {
         return role.getOther().getClazz().getName();
      }

      return null;
   }

   @Override
   public String visit(ExampleAccess exampleAccess, Object par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public String visit(CreationExpr creationExpr, Object par)
   {
      return StrUtil.cap(creationExpr.getClassName().accept(Namer.INSTANCE, null));
   }

   @Override
   public String visit(PrimaryExpr primaryExpr, Object par)
   {
      return null;
   }

   @Override
   public String visit(NameAccess nameAccess, Object par)
   {
      return nameAccess.getName() instanceof ResolvedName ?
                ((ResolvedName) nameAccess.getName()).getDecl().getType() :
                null;
   }

   @Override
   public String visit(NumberLiteral numberLiteral, Object par)
   {
      return "double";
   }

   @Override
   public String visit(StringLiteral stringLiteral, Object par)
   {
      return "String";
   }

   @Override
   public String visit(ConditionalExpr conditionalExpr, Object par)
   {
      return "boolean";
   }

   @Override
   public String visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      return "boolean";
   }
}
