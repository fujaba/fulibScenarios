package org.fulib.scenarios.library;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DirLibraryTest
{

   @Test
   public void hasClass()
   {
      final File classesDir = new File("build/classes/java/test");
      final DirLibrary library = new DirLibrary(classesDir);

      assertTrue(library.hasClass("org/fulib/scenarios/library/DirLibraryTest"));
      assertFalse(library.hasClass("org/fulib/scenarios/library/ClassThatDoesNotExist"));
      assertFalse(library.hasClass("org/fulib/scenarios/library/dirlibrarytest"));
      assertFalse(library.hasClass("Org/Fulib/Scenarios/Library/DirLibraryTest"));
   }
}
