package org.fulib.scenarios.library;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.ast.type.UnresolvedType;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ClassModelVisitor extends ClassVisitor
{
   // =============== Fields ===============

   private final ExternalClassDecl classDecl;

   private final Set<String> properties = new LinkedHashSet<>();

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
      final int slashIndex = name.lastIndexOf('/');
      final String simpleName = slashIndex < 0 ? name : name.substring(slashIndex + 1);
      this.classDecl.setName(simpleName);

      if (superName != null)
      {
         this.classDecl.setSuperType(UnresolvedType.of(superName));
      }
   }

   @Override
   public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value)
   {
      if (name.startsWith("PROPERTY_"))
      {
         final String attributeName = name.substring("PROPERTY_".length());
         if (attributeName.equals(value))
         {
            this.properties.add(attributeName);
         }
      }

      return null;
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

      final int prefixLength;
      if (name.startsWith("get") || name.startsWith("set"))
      {
         prefixLength = 3;
      }
      else if (name.startsWith("without"))
      {
         prefixLength = 7;
      }
      else if (name.startsWith("with"))
      {
         prefixLength = 4;
      }
      else
      {
         prefixLength = 0;
      }

      if (prefixLength > 0 && name.length() > prefixLength)
      {
         final String attributeName = name.substring(prefixLength);
         final String lowerAttributeName = StrUtil.downFirstChar(attributeName);

         if (this.properties.contains(lowerAttributeName))
         {
            if (name.charAt(0) == 'g') // must be getter
            {
               this.tryCreateAttribute(lowerAttributeName, descriptor, signature);
            }
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

         final int end = descriptor.indexOf(';', index + 1);

         consumer.accept(UnresolvedType.of(descriptor.substring(index + 1, end)));
         return end + 1;
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
