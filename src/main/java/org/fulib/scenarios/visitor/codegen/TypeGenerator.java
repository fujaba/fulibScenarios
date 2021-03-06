package org.fulib.scenarios.visitor.codegen;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

public enum TypeGenerator implements Type.Visitor<CodeGenDTO, String>, ClassDecl.Visitor<CodeGenDTO, String>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public String visit(UnresolvedType unresolvedType, CodeGenDTO par)
   {
      throw new IllegalStateException("unresolved type " + unresolvedType.getName());
   }

   @Override
   public String visit(ClassType classType, CodeGenDTO par)
   {
      return classType.getClassDecl().accept(this, par);
   }

   @Override
   public String visit(ListType listType, CodeGenDTO par)
   {
      par.addImport("java.util.List");
      final Type elementType = listType.getElementType();
      final Type wrappedType = PrimitiveType.primitiveToWrapper(elementType);
      return "List<" + wrappedType.accept(this, par) + ">";
   }

   @Override
   public String visit(PrimitiveType primitiveType, CodeGenDTO par)
   {
      return primitiveType.getJavaName();
   }

   // --------------- ClassDecl.Visitor ---------------

   @Override
   public String visit(ClassDecl classDecl, CodeGenDTO par)
   {
      final ScenarioGroup group = classDecl.getGroup();
      if (group != par.group)
      {
         par.addImport(group.getPackageDir().replace('/', '.') + '.' + classDecl.getName());
      }
      return classDecl.getName();
   }
}
