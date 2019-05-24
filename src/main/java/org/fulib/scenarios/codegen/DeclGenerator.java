package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.transform.Typer;

public enum DeclGenerator implements Decl.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   @Override
   public Object visit(Decl decl, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(VarDecl varDecl, CodeGenerator par)
   {
      final String type = varDecl.accept(new Typer(par.modelManager.getClassModel()), null);
      par.emitIndent();

      if (type.startsWith("List<"))
      {
         par.addImport("java.util.List");
      }

      par.bodyBuilder.append(type).append(' ').append(varDecl.getName()).append(" = ");
      varDecl.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }
}
