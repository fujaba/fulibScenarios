package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

public enum TypeGenerator implements Type.Visitor<CodeGenDTO, String>
{
   INSTANCE;

   @Override
   public String visit(UnresolvedType unresolvedType, CodeGenDTO par)
   {
      throw new UnsupportedOperationException("unresolved type " + unresolvedType.getName());
   }

   @Override
   public String visit(ClassType classType, CodeGenDTO par)
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
   public String visit(ListType listType, CodeGenDTO par)
   {
      par.addImport("java.util.List");
      return "List<" + listType.getElementType().accept(this, par) + ">";
   }

   @Override
   public String visit(PrimitiveType primitiveType, CodeGenDTO par)
   {
      return primitiveType.getJavaName();
   }
}
