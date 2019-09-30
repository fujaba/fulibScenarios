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

   private File getFile(String className)
   {
      return new File(this.getSource().getPath() + File.separatorChar + className + ".class");
   }

   @Override
   public boolean hasClass(String name)
   {
      return this.getFile(name).exists();
   }

   @Override
   public InputStream loadClass(String name) throws IOException
   {
      return new FileInputStream(this.getFile(name));
   }
}
