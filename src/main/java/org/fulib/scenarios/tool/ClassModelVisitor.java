package org.fulib.scenarios.tool;

import org.fulib.scenarios.ast.decl.ClassDecl;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassModelVisitor extends ClassVisitor
{
   // =============== Fields ===============

   private final ClassDecl classDecl;

   // =============== Constructors ===============

   public ClassModelVisitor(ClassDecl classDecl)
   {
      super(Opcodes.ASM7);
      this.classDecl = classDecl;
   }

   // =============== Methods ===============

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
   {
      final int slashIndex = name.lastIndexOf('/');
      final String simpleName = slashIndex < 0 ? name : name.substring(slashIndex + 1);
      this.classDecl.setName(simpleName);
   }
}
