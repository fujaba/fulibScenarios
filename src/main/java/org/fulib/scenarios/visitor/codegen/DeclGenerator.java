package org.fulib.scenarios.visitor.codegen;

import org.fulib.MultiAttributes;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;

public enum DeclGenerator implements Decl.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   @Override
   public Object visit(ClassDecl classDecl, CodeGenDTO par)
   {
      if (classDecl.getExternal())
      {
         return null;
      }

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
   public Object visit(AttributeDecl attributeDecl, CodeGenDTO par)
   {
      final Clazz clazz = par.modelManager.haveClass(attributeDecl.getOwner().getName());

      final Type type = attributeDecl.getType();
      if (type instanceof ListType)
      {
         final Type elementType = ((ListType) type).getElementType();
         MultiAttributes
            .buildMultiAttribute(clazz, attributeDecl.getName(), elementType.accept(TypeGenerator.INSTANCE, par));
      }
      else
      {
         par.modelManager.haveAttribute(clazz, attributeDecl.getName(), type.accept(TypeGenerator.INSTANCE, par));
      }

      return null;
   }

   @Override
   public Object visit(AssociationDecl associationDecl, CodeGenDTO par)
   {
      final Clazz clazz = par.modelManager.haveClass(associationDecl.getOwner().getName());

      final AssociationDecl other = associationDecl.getOther();
      final String targetType = associationDecl.getTarget().accept(TypeGenerator.INSTANCE, par);

      if (other != null) // bidirectional
      {
         final Clazz otherClazz = par.modelManager.haveClass(targetType);

         par.modelManager.haveRole(clazz, associationDecl.getName(), otherClazz, associationDecl.getCardinality(),
                                   other.getName(), other.getCardinality());
      }
      else if (associationDecl.getCardinality() == 1) // unidirectional one
      {
         par.modelManager.haveAttribute(clazz, associationDecl.getName(), targetType);
      }
      else // unidirectional many
      {
         MultiAttributes.buildMultiAttribute(clazz, associationDecl.getName(), targetType);
      }

      return null;
   }

   @Override
   public Object visit(MethodDecl methodDecl, CodeGenDTO par)
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
   public Object visit(ParameterDecl parameterDecl, CodeGenDTO par)
   {
      throw new UnsupportedOperationException("handled by visit(MethodDecl, ...)");
   }

   @Override
   public Object visit(VarDecl varDecl, CodeGenDTO par)
   {
      par.emitIndent();

      par.bodyBuilder.append(varDecl.getType().accept(TypeGenerator.INSTANCE, par)).append(' ')
                     .append(varDecl.getName()).append(" = ");
      varDecl.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }
}
