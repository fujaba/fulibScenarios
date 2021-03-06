package org.fulib.scenarios.library;

import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.ast.type.UnresolvedType;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ClassModelVisitor extends ClassVisitor
{
   // =============== Constants ===============

   private static final String[] SETTER_PREFIXES = new String[] { "set", "with", "without" };

   // =============== Fields ===============

   private final ExternalClassDecl classDecl;

   // =============== Constructors ===============

   public ClassModelVisitor(ExternalClassDecl classDecl)
   {
      super(Opcodes.ASM7);
      this.classDecl = classDecl;
   }

   // =============== Methods ===============

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
   {
      this.classDecl.setName(getSimpleName(name));

      if (superName != null)
      {
         this.classDecl.setSuperType(UnresolvedType.of(getSimpleName(superName), superName, false));
      }
   }

   private static String getSimpleName(String name)
   {
      final int slashIndex = name.lastIndexOf('/');
      return slashIndex < 0 ? name : name.substring(slashIndex + 1);
   }

   // all fields will be processed before the first method

   @Override
   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions)
   {
      if (name.isEmpty() || "<init>".equals(name) || "<clinit>".equals(name))
      {
         // don't care about constructors or class initializer
         return null;
      }

      final int length = name.length();
      if (tryCreateAttribute(name, descriptor, signature, "get"))
      {
         return null;
      }
      if (tryCreateAttribute(name, descriptor, signature, "is"))
      {
         return null;
      }
      for (String prefix : SETTER_PREFIXES)
      {
         final int prefixLength = prefix.length();
         if (length > prefixLength && name.startsWith(prefix) && !Character.isLowerCase(name.charAt(prefixLength)))
         {
            // ignore setters and withers
            return null;
         }
      }

      // don't care about non-public or static methods
      if ((access & Modifier.PUBLIC) != 0 && (access & Modifier.STATIC) == 0)
      {
         this.tryCreateMethod(name, descriptor);
      }

      return null;
   }

   private boolean tryCreateAttribute(String name, String descriptor, String signature, String prefix)
   {
      final int prefixLength = prefix.length();
      if (name.startsWith(prefix) && name.length() > prefixLength && !Character.isLowerCase(name.charAt(prefixLength)))
      {
         // convert getters to attributes
         final String attributeName =
            Character.toLowerCase(name.charAt(prefixLength)) + name.substring(prefixLength + 1);
         this.tryCreateAttribute(attributeName, descriptor, signature);
         return true;
      }
      return false;
   }

   @Override
   public void visitEnd()
   {
      this.classDecl.markUnresolved();
   }

   private void tryCreateAttribute(String name, String descriptor, String signature)
   {
      try
      {
         this.createAttribute(name, descriptor, signature);
      }
      catch (UnsupportedOperationException ignored)
      {
      }
   }

   private void createAttribute(String name, String descriptor, String signature)
   {
      final int returnTypeIndex = descriptor.indexOf(')') + 1;

      // We only create attributes at this point because types are unresolved.
      // After external type resolution in NameResolver.visit(ScenarioGroup), we convert them
      // to associations as necessary.

      if (descriptor.endsWith(")Ljava/util/List;") || descriptor.endsWith(")Ljava/util/ArrayList;"))
      {
         final ListType listType = ListType.of(null);
         final int elementTypeIndex = signature.indexOf('<', returnTypeIndex) + 1;
         parseType(signature, elementTypeIndex, listType::setElementType);

         final AttributeDecl attribute = AttributeDecl.of(this.classDecl, name, listType);
         this.classDecl.getAttributes().put(name, attribute);
         return;
      }

      final AttributeDecl attribute = AttributeDecl.of(this.classDecl, name, null);
      parseType(descriptor, returnTypeIndex, attribute::setType);
      this.classDecl.getAttributes().put(name, attribute);
   }

   private void tryCreateMethod(String name, String descriptor)
   {
      try
      {
         final MethodDecl methodDecl = new ExternalMethodDecl(this.classDecl, name, new ArrayList<>(), null, null);
         parseType(descriptor, descriptor.lastIndexOf(')') + 1, methodDecl::setType);

         final ParameterDecl thisParam = new ExternalParameterDecl(methodDecl, "this", this.classDecl.getType());
         methodDecl.getParameters().add(thisParam);

         int index = 1;
         while (descriptor.charAt(index) != ')')
         {
            final ParameterDecl param = new ExternalParameterDecl(methodDecl, null, null);
            index = parseType(descriptor, index, param::setType);
            methodDecl.getParameters().add(param);
         }

         this.classDecl.getMethods().add(methodDecl);
      }
      catch (UnsupportedOperationException ignored)
      {
      }
   }

   // =============== Static Methods ===============

   public static Type parseType(String descriptor, int index)
   {
      final Type[] holder = new Type[1];
      parseType(descriptor, index, t -> holder[0] = t);
      return holder[0];
   }

   public static int parseType(String descriptor, int index, Consumer<? super Type> consumer)
   {
      final char c = descriptor.charAt(index);
      switch (c)
      {
      case '[':
         throw new UnsupportedOperationException("array types");
      case 'T':
         throw new UnsupportedOperationException("generic type variables");
      case 'L':
         final int genericStart = descriptor.indexOf('<', index + 1);
         if (genericStart > 0)
         {
            throw new UnsupportedOperationException("generic type arguments");
         }

         final int semi = descriptor.indexOf(';', index + 1);
         final int slash = descriptor.lastIndexOf('/', semi - 1);
         final String text = descriptor.substring(index + 1, semi);
         final String name = descriptor.substring(Math.max(slash, index) + 1, semi);

         consumer.accept(UnresolvedType.of(name, text, false));
         return semi + 1;
      default:
         consumer.accept(parsePrimitiveType(c));
         return index + 1;
      }
   }

   private static PrimitiveType parsePrimitiveType(char c)
   {
      switch (c)
      {
      // @formatter:off
      case 'V': return PrimitiveType.VOID;
      case 'Z': return PrimitiveType.BOOLEAN;
      case 'B': return PrimitiveType.BYTE;
      case 'S': return PrimitiveType.SHORT;
      case 'C': return PrimitiveType.CHAR;
      case 'I': return PrimitiveType.INT;
      case 'J': return PrimitiveType.LONG;
      case 'F': return PrimitiveType.FLOAT;
      case 'D': return PrimitiveType.DOUBLE;
      // @formatter:on
      }
      throw new UnsupportedOperationException("unknown type char " + c);
   }
}
