package org.fulib.scenarios.library;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class Library implements Closeable
{
   // =============== Fields ===============

   private final File source;

   // =============== Constructors ===============

   public Library(File source)
   {
      this.source = source;
   }

   // =============== Properties ===============

   public File getSource()
   {
      return this.source;
   }

   // =============== Methods ===============

   public abstract boolean hasClass(String name);

   public abstract InputStream loadClass(String name) throws IOException;

   @Override
   public void close() throws IOException
   {
   }
}
