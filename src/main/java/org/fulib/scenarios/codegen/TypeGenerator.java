package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

public enum TypeGenerator implements Type.Visitor<CodeGenerator, String>
{
   INSTANCE;

   @Override
   public String visit(UnresolvedType unresolvedType, CodeGenerator par)
   {
      throw new UnsupportedOperationException("unresolved type " + unresolvedType.getName());
   }

   @Override
   public String visit(ClassType classType, CodeGenerator par)
   {
      final ClassDecl classDecl = classType.getClassDecl();
      final ScenarioGroup group = classDecl.getGroup();
      if (group != par.group)
      {
         par.addImport(group.getPackageDir().replace('/', '.') + '.' + classDecl.getName());
      }
      return classDecl.getName();
   }

   @Override
   public String visit(ListType listType, CodeGenerator par)
   {
      par.addImport("java.util.List");
      return "List<" + listType.getElementType().accept(this, par) + ">";
   }

   @Override
   public String visit(PrimitiveType primitiveType, CodeGenerator par)
   {
      return primitiveType.getJavaName();
   }
}
