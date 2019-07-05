package org.fulib.scenarios.tool;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.AttributeDecl;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.MethodDecl;
import org.fulib.scenarios.ast.decl.ParameterDecl;
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
import java.util.stream.Collectors;

public class ClassModelVisitor extends ClassVisitor
{
   // =============== Fields ===============

   private final ClassDecl classDecl;

   private final Set<String> properties = new LinkedHashSet<>();

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
      if ("<init>".equals(name) || "<clinit>".equals(name))
      {
         // don't care about constructors or class initializer
         return null;
      }
      if (name.startsWith("get") || name.startsWith("set") || name.startsWith("with") || name.startsWith("without"))
      {
         // check for an attribute with the UpperCamelCased name too for compatibility with older versions
         final String attributeName = name.substring(3);
         if (this.properties.contains(attributeName))
         {
            if (name.charAt(0) == 'g') // get
            {
               this.tryCreateAttribute(attributeName, descriptor, signature);
            }
            return null;
         }

         final String lowerAttributeName = StrUtil.downFirstChar(attributeName);
         if (this.properties.contains(lowerAttributeName))
         {
            if (name.charAt(0) == 'g') // get
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

   private void tryCreateAttribute(String name, String descriptor, String signature)
   {
      try
      {
         final int returnTypeIndex = descriptor.indexOf(')') + 1;

         if (descriptor.endsWith(")Ljava/util/List;") || descriptor.endsWith(")Ljava/util/ArrayList;"))
         {
            // multi-values attribute (or association - but that doesn't matter for decompiled classes)

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
      catch (UnsupportedOperationException ignored)
      {
      }
   }

   private void tryCreateMethod(String name, String descriptor)
   {
      try
      {
         final MethodDecl methodDecl = MethodDecl.of(this.classDecl, name, new ArrayList<>(), null, null);
         parseType(descriptor, descriptor.lastIndexOf(')') + 1, methodDecl::setType);

         final ParameterDecl thisParam = ParameterDecl.of(methodDecl, "this", this.classDecl.getType());
         methodDecl.getParameters().add(thisParam);

         int index = 1;
         while (descriptor.charAt(index) != ')')
         {
            final ParameterDecl param = ParameterDecl.of(methodDecl, null, null);
            index = parseType(descriptor, index, param::setType);
            methodDecl.getParameters().add(param);
         }

         this.classDecl.getMethods().add(methodDecl);
      }
      catch (UnsupportedOperationException ignored)
      {
      }
   }

   @Override
   public void visitEnd()
   {
      System.out.print(this.classDecl.getName());
      System.out.print(' ');
      System.out.print(this.properties);
      System.out.print(' ');
      System.out.println(this.classDecl.getMethods().stream().map(MethodDecl::getName).collect(Collectors.joining(", ")));
   }

   // =============== Static Methods ===============

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
         final int end = descriptor.indexOf(';', index + 1);
         final int slash = descriptor.lastIndexOf('/', end);

         if (descriptor.charAt(end - 1) == '>')
         {
            throw new UnsupportedOperationException("generic type arguments");
         }

         final String packageDir = slash < index ? "" : descriptor.substring(index + 1, slash);
         final String className = descriptor.substring(slash < index ? index + 1 : slash + 1, end);

         consumer.accept(resolveType(packageDir, className));
         return end + 1;
      default:
         consumer.accept(parsePrimitiveType(c));
         return index + 1;
      }
   }

   private static Type resolveType(String packageDir, String className)
   {
      if ("java/lang".equals(packageDir))
      {
         // Object, String and wrapper classes
         final PrimitiveType primitive = PrimitiveType.javaNameMap.get(className);
         if (primitive != null)
         {
            return primitive;
         }
      }

      final UnresolvedType type = UnresolvedType.of(className);
      type.setPackageDir(packageDir);
      return type;
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
