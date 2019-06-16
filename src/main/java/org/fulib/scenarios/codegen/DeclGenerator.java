package org.fulib.scenarios.codegen;

import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.decl.*;

public enum DeclGenerator implements Decl.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   @Override
   public Object visit(Decl decl, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ClassDecl classDecl, CodeGenerator par)
   {
      par.clazz = par.modelManager.haveClass(classDecl.getName());

      for (final AttributeDecl attributeDecl : classDecl.getAttributes().values())
      {
         attributeDecl.accept(this, par);
      }

      for (final AssociationDecl associationDecl : classDecl.getAssociations().values())
      {
         associationDecl.accept(this, par);
      }

      for (final MethodDecl method : classDecl.getMethods())
      {
         method.accept(this, par);
      }

      return null;
   }

   @Override
   public Object visit(AttributeDecl attributeDecl, CodeGenerator par)
   {
      final Clazz clazz = par.modelManager.haveClass(attributeDecl.getOwner().getName());

      par.modelManager
         .haveAttribute(clazz, attributeDecl.getName(), attributeDecl.getType().accept(TypeGenerator.INSTANCE, par));

      return null;
   }

   @Override
   public Object visit(AssociationDecl associationDecl, CodeGenerator par)
   {
      final Clazz clazz = par.modelManager.haveClass(associationDecl.getOwner().getName());

      final Clazz otherClazz = par.modelManager.haveClass(associationDecl.getTarget().getName());

      final AssociationDecl other = associationDecl.getOther();
      if (other != null)
      {
         par.modelManager.haveRole(clazz, associationDecl.getName(), otherClazz, associationDecl.getCardinality(),
                                   other.getName(), other.getCardinality());
      }
      else
      {
         par.modelManager.haveRole(clazz, associationDecl.getName(), otherClazz, associationDecl.getCardinality());
      }

      return null;
   }

   @Override
   public Object visit(MethodDecl methodDecl, CodeGenerator par)
   {
      final Clazz clazz = par.modelManager.haveClass(methodDecl.getOwner().getName());

      final FMethod method = new FMethod();
      method.setClazz(clazz);
      method.writeName(methodDecl.getName());
      method.writeReturnType(methodDecl.getType().accept(TypeGenerator.INSTANCE, par));

      for (final ParameterDecl parameter : methodDecl.getParameters())
      {
         final String name = parameter.getName();
         if (!"this".equals(name))
         {
            method.readParams().put(name, parameter.getType().accept(TypeGenerator.INSTANCE, par));
         }
      }

      par.bodyBuilder = new StringBuilder();
      methodDecl.getBody().accept(SentenceGenerator.INSTANCE, par);
      method.setMethodBody(par.bodyBuilder.toString());
      par.bodyBuilder = null;

      return null;
   }

   @Override
   public Object visit(ParameterDecl parameterDecl, CodeGenerator par)
   {
      throw new UnsupportedOperationException("handled by visit(MethodDecl, ...)");
   }

   @Override
   public Object visit(VarDecl varDecl, CodeGenerator par)
   {
      par.emitIndent();

      par.bodyBuilder.append(varDecl.getType().accept(TypeGenerator.INSTANCE, par)).append(' ')
                     .append(varDecl.getName()).append(" = ");
      varDecl.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }
}
