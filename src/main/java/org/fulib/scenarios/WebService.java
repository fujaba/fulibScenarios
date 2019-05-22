package org.fulib.scenarios;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static spark.Spark.*;

public class WebService
{
   public static void main(String[] args)
   {
      staticFiles.externalLocation("webapp");

      get("/github", (req, res) -> {
         res.redirect("https://github.com/fujaba/fulib");
         return res;
      });

      post("/runcodegen", WebService::runCodeGen);

      // http://localhost:4567
   }

   private static String runCodeGen(Request req, Response res) throws IOException
   {
      final Path codegendir = Files.createTempDirectory("codegendir");
      final Path srcDir = codegendir.resolve("src");
      final Path mainPackageDir = srcDir.resolve("example");
      final Path modelSrcDir = codegendir.resolve("modelsrc");
      final Path testSrcDir = codegendir.resolve("testsrc");
      final Path scenarioFile = mainPackageDir.resolve("scenario.md");
      final Path exampleTestDir = testSrcDir.resolve("example");
      final Path exampleModelDir = modelSrcDir.resolve("example");

      try
      {
         final String body = req.body();
         final JSONObject jsonObject = new JSONObject(body);
         final String bodyText = jsonObject.getString("scenarioText");

         // create source directory and write source scenario file
         Files.createDirectories(srcDir);
         Files.createDirectories(mainPackageDir);
         Files.write(scenarioFile, bodyText.getBytes(StandardCharsets.UTF_8));

         // create output directories
         Files.createDirectories(modelSrcDir);
         Files.createDirectories(testSrcDir);

         // invoke compiler
         Main.main(new String[] { "-m", modelSrcDir.toString(), "-t", testSrcDir.toString(), srcDir.toString(),
            "--class-diagram-svg" });

         final JSONObject result = new JSONObject();

         // collect test methods
         final JSONArray methodArray = new JSONArray();

         Files.list(exampleTestDir).filter(file -> file.toString().endsWith(".java")).forEach(file -> {
            try
            {
               byte[] bytes = Files.readAllBytes(file);

               String firstTestBody = new String(bytes, StandardCharsets.UTF_8);
               JSONObject firstTestMethod = new JSONObject();
               firstTestMethod.put("name", file.getFileName().toString());
               firstTestMethod.put("body", firstTestBody);

               methodArray.put(firstTestMethod);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         });

         result.put("testMethods", methodArray);

         // read class diagram
         final byte[] bytes = Files.readAllBytes(exampleModelDir.resolve("classDiagram.svg"));
         final String svgText = new String(bytes, StandardCharsets.UTF_8);
         result.put("classDiagram", svgText);

         return result.toString(3);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return "{}";
      }
      finally
      {
         Files.walk(codegendir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
      }
   }
}
