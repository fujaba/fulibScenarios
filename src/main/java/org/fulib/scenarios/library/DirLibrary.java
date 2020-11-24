package org.fulib.scenarios.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DirLibrary extends Library
{
   // =============== Constructors ===============

   public DirLibrary(File source)
   {
      super(source);
   }

   // =============== Methods ===============

   private File getValidFileOrNull(String className)
   {
      final String classFileName = className.replace('/', File.separatorChar) + ".class";
      final File file = new File(this.getSource(), classFileName);
      if (!file.exists())
      {
         return null;
      }

      final String canonicalFile;
      try
      {
         canonicalFile = file.getCanonicalPath();
      }
      catch (IOException e)
      {
         return null;
      }

      // case sensitive check
      if (!canonicalFile.endsWith(classFileName))
      {
         return null;
      }
      return file;
   }

   @Override
   public boolean hasClass(String name)
   {
      return this.getValidFileOrNull(name) != null;
   }

   @Override
   public InputStream loadClass(String name) throws IOException
   {
      final File file = this.getValidFileOrNull(name);
      return file != null ? new FileInputStream(file) : null;
   }
}
