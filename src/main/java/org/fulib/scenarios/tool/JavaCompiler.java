package org.fulib.scenarios.tool;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class JavaCompiler
{
   public static int javac(String classpath, Path outFolder, Path... sourceFolders) throws IOException
   {
      Files.createDirectories(outFolder);

      ArrayList<String> args = new ArrayList<>();

      Arrays.stream(sourceFolders).flatMap(JavaCompiler::collectJavaFiles).map(Path::toString).forEach(args::add);

      args.add("-d");
      args.add(outFolder.toString());
      if (classpath != null)
      {
         args.add("-classpath");
         args.add(classpath);
      }

      return ToolProvider.getSystemJavaCompiler().run(null, System.out, System.err, args.toArray(new String[0]));
   }

   public static boolean isJava(Path file)
   {
      return file.toString().endsWith(".java");
   }

   public static Stream<Path> collectJavaFiles(Path sourceFolder)
   {
      try
      {
         return Files.walk(sourceFolder).filter(JavaCompiler::isJava);
      }
      catch (IOException e)
      {
         return Stream.empty();
      }
   }

   public static void deleteRecursively(Path dir)
   {
      try
      {
         Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(file -> {
            try
            {
               Files.deleteIfExists(file);
            }
            catch (IOException ignored)
            {
            }
         });
      }
      catch (IOException ignored)
      {
      }
   }
}
