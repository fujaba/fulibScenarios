package org.fulib.scenarios.library;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JarLibrary extends Library
{
   private final JarFile               jarFile;
   private final Map<String, JarEntry> classEntries;

   public JarLibrary(File source) throws IOException
   {
      super(source);
      this.jarFile = new JarFile(source);
      this.classEntries = this.jarFile.stream().filter(e -> {
         final String name = e.getName();
         return name.endsWith(".class") && name.indexOf('$') < 0;
      }).collect(Collectors.toMap(e -> {
         final String name = e.getName();
         return name.substring(0, name.length() - 6); // 6 = ".class".length()
      }, Function.identity()));
   }

   // =============== Methods ===============

   @Override
   public boolean hasClass(String name)
   {
      return this.classEntries.containsKey(name);
   }

   @Override
   public InputStream loadClass(String name) throws IOException
   {
      final JarEntry entry = this.classEntries.get(name);
      return entry != null ? this.jarFile.getInputStream(entry) : null;
   }

   @Override
   public void close() throws IOException
   {
      if (this.jarFile != null)
      {
         this.jarFile.close();
      }
   }
}
