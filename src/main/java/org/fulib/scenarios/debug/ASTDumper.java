package org.fulib.scenarios.debug;

import java.beans.Introspector;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

// TODO move to gentreesrc runtime library
public class ASTDumper
{
   private final Class<?>[] rootClasses;

   public ASTDumper(Class<?>... rootClasses)
   {
      this.rootClasses = rootClasses;
   }

   public ASTDumper(Collection<? extends Class<?>> rootClasses)
   {
      this.rootClasses = rootClasses.toArray(new Class[0]);
   }

   private boolean isNode(Object object)
   {
      for (Class<?> rootClass : this.rootClasses)
      {
         if (rootClass.isInstance(object))
         {
            return true;
         }
      }
      return false;
   }

   public void dump(Object object, PrintWriter out)
   {
      this.dump(object, "", out, Collections.newSetFromMap(new IdentityHashMap<>()));
   }

   private void dump(Object object, String indent, PrintWriter out, Set<Object> dejaVu)
   {
      if (object == null)
      {
         out.println("null");
         return;
      }

      if (!this.isNode(object))
      {
         out.println(object);
         return;
      }

      this.dumpNode(object, indent, out, dejaVu);
   }

   private void dumpNode(Object object, String indent, PrintWriter out, Set<Object> dejaVu)
   {
      final Class<?> type = object.getClass();
      out.print(getID(object));

      if (!dejaVu.add(object))
      {
         // already seen.
         out.println(" (s.a.)");
         return;
      }

      final Map<String, Object> attributes = new TreeMap<>();
      final Map<String, Object> children = new TreeMap<>();

      this.collectProperties(object, type, attributes, children);

      if (!attributes.isEmpty())
      {
         this.printAttributes(out, attributes);
      }

      out.println();

      if (!children.isEmpty())
      {
         this.printChildren(indent, out, dejaVu, children);
      }
   }

   private void collectProperties(Object object, Class<?> type, Map<String, Object> attributes,
      Map<String, Object> children)
   {
      for (Method method : type.getDeclaredMethods())
      {
         if ((method.getModifiers() & Modifier.PUBLIC) == 0 || !method.getName().startsWith("get")
             || method.getParameterCount() != 0)
         {
            continue;
         }

         this.collectProperty(object, method, attributes, children);
      }
   }

   private void collectProperty(Object object, Method method, Map<String, Object> attributes,
      Map<String, Object> children)
   {
      final String key = Introspector.decapitalize(method.getName().substring(3));
      Object value;

      try
      {
         value = method.invoke(object);
      }
      catch (Exception ignored)
      {
         value = "<error>";
      }

      this.collectProperty(key, value, attributes, children);
   }

   private void collectProperty(String key, Object value, Map<String, Object> attributes, Map<String, Object> children)
   {
      if (value instanceof Collection)
      {
         this.collectProperties(key, (Collection) value, attributes, children);
      }
      else if (value instanceof Map)
      {
         this.collectProperties(key, (Map) value, attributes, children);
      }
      else
      {
         (this.isNode(value) ? children : attributes).put(key, value);
      }
   }

   private void collectProperties(String key, Map<?, ?> value, Map<String, Object> attributes, Map<String, Object> children)
   {
      if (value.isEmpty())
      {
         attributes.put(key, "{}");
         return;
      }

      final int maxKeyLength = value.keySet().stream().map(Object::toString).mapToInt(String::length).max()
                                    .getAsInt();
      final String format = key + "[%" + maxKeyLength + "s]";
      final Map<String, Object> rest = new TreeMap<>();

      for (Map.Entry<?, ?> entry : value.entrySet())
      {
         final String entryKey = entry.getKey().toString();
         final Object entryValue = entry.getValue();
         if (this.isNode(entryValue))
         {
            children.put(String.format(format, entryKey), entryValue);
            rest.put(entryKey, getID(entryValue));
         }
         else
         {
            rest.put(entryKey, entryValue);
         }
      }

      attributes.put(key, rest.toString());
   }

   private void collectProperties(String key, Collection<?> value, Map<String, Object> attributes,
      Map<String, Object> children)
   {
      if (value.isEmpty())
      {
         attributes.put(key, "[]");
         return;
      }

      final int size = value.size();
      final String format = key + "[%" + ((int) Math.log10(size) + 1) + "d]";
      final Object[] rest = new Object[size];

      int index = 0;
      for (Object item : value)
      {
         if (this.isNode(item))
         {
            children.put(String.format(format, index), item);
            rest[index] = getID(item);
         }
         else
         {
            rest[index] = item;
         }

         index++;
      }

      attributes.put(key, Arrays.toString(rest));
   }

   private static String getID(Object item)
   {
      final Class<?> itemClass = item.getClass();
      final Class<?> enclosingClass = itemClass.getEnclosingClass();

      final String simpleName = itemClass.getSimpleName();
      final String idNum = Integer.toHexString(System.identityHashCode(item));

      return ("Impl".equals(simpleName) ? enclosingClass.getSimpleName() : simpleName) + "@" + idNum;
   }

   private void printAttributes(PrintWriter out, Map<String, Object> attributes)
   {
      out.print('(');

      final Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
      Map.Entry<String, Object> entry = iterator.next();

      out.print(entry.getKey());
      out.print(": ");
      out.print(entry.getValue());
      while (iterator.hasNext())
      {
         entry = iterator.next();
         out.print(", ");
         out.print(entry.getKey());
         out.print(": ");
         out.print(entry.getValue());
      }

      out.print(')');
   }

   private void printChildren(String indent, PrintWriter out, Set<Object> dejaVu, Map<String, Object> children)
   {
      for (Iterator<Map.Entry<String, Object>> iterator = children.entrySet().iterator(); iterator.hasNext(); )
      {
         final Map.Entry<String, Object> entry = iterator.next();
         final String key = entry.getKey();

         out.print(indent + "+- ");
         out.print(key);
         out.print(": ");

         final StringBuilder newIndent = new StringBuilder(indent.length() + key.length() + 2 + 3);
         newIndent.append(indent);
         newIndent.append(iterator.hasNext() ? "|  " : "   ");
         for (int i = 0; i < key.length(); i++)
         {
            newIndent.append(' ');
         }
         newIndent.append("  ");

         this.dumpNode(entry.getValue(), newIndent.toString(), out, dejaVu);
      }
   }
}
