package org.fulib.scenarios.visitor.codegen;

import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.CollectionType;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractClassDecl;

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

      if (classDecl.getSuperType() != null)
      {
         final ClassDecl superClassDecl = classDecl.getSuperType().accept(ExtractClassDecl.INSTANCE, null);
         if (superClassDecl != null)
         {
            final Clazz superClazz = par.modelManager.haveClass(superClassDecl.getName());
            par.modelManager.haveSuper(par.clazz, superClazz);
         }
      }

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
         final Type wrappedType = PrimitiveType.primitiveToWrapper(elementType);
         par.modelManager
            .haveAttribute(clazz, attributeDecl.getName(), wrappedType.accept(TypeGenerator.INSTANCE, par))
            .setCollectionType(CollectionType.ArrayList);
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

         par.modelManager.associate(clazz, associationDecl.getName(), associationDecl.getCardinality(), otherClazz,
                                    other.getName(), other.getCardinality());
      }
      else if (associationDecl.getCardinality() == 1) // unidirectional one
      {
         par.modelManager.haveAttribute(clazz, associationDecl.getName(), targetType);
      }
      else // unidirectional many
      {
         par.modelManager
            .haveAttribute(clazz, associationDecl.getName(), targetType)
            .setCollectionType(par.modelManager.getClassModel().getDefaultCollectionType());
      }

      return null;
   }

   @Override
   public Object visit(MethodDecl methodDecl, CodeGenDTO par)
   {
      final Clazz clazz = par.modelManager.haveClass(methodDecl.getOwner().getName());

      final FMethod method = new FMethod();
      method.setClazz(clazz);
      method.setName(methodDecl.getName());
      method.setReturnType(methodDecl.getType().accept(TypeGenerator.INSTANCE, par));

      for (final ParameterDecl parameter : methodDecl.getParameters())
      {
         final String name = parameter.getName();
         if (!"this".equals(name))
         {
            method.getParams().put(name, parameter.getType().accept(TypeGenerator.INSTANCE, par));
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
      throw new AssertionError("handled by visit(MethodDecl, ...)");
   }

   @Override
   public Object visit(VarDecl varDecl, CodeGenDTO par)
   {
      par.emitIndent();

      par.bodyBuilder
         .append(varDecl.getType().accept(TypeGenerator.INSTANCE, par))
         .append(' ')
         .append(varDecl.getName());

      final Expr expr = varDecl.getExpr();
      if (expr != null)
      {
         par.bodyBuilder.append(" = ");
         expr.accept(ExprGenerator.INSTANCE, par);
      }

      par.bodyBuilder.append(";\n");
      return null;
   }
}
