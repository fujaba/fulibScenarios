package org.fulib.scenarios;

import org.fulib.scenarios.tool.Tools;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class WebService
{
   private static final String TEMP_DIR_PREFIX    = "fulibScenarios";
   private static final String PACKAGE_NAME       = "webapp";
   private static final String SCENARIO_FILE_NAME = "scenario.md";

   public static void main(String[] args)
   {
      staticFiles.externalLocation("webapp");

      get("/github", (req, res) -> {
         res.redirect("https://github.com/fujaba/fulib");
         return res;
      });

      post("/runcodegen", WebService::runCodeGen);

      Logger.getGlobal().info("scenario server started ... ");

      // http://localhost:4567
   }

   private static String runCodeGen(Request req, Response res) throws Exception
   {
      final Path codegendir = Files.createTempDirectory(TEMP_DIR_PREFIX);
      final Path srcDir = codegendir.resolve("src");
      final Path mainPackageDir = srcDir.resolve(PACKAGE_NAME);
      final Path modelSrcDir = codegendir.resolve("model_src");
      final Path testSrcDir = codegendir.resolve("test_src");
      final Path scenarioFile = mainPackageDir.resolve(SCENARIO_FILE_NAME);
      final Path modelClassesDir = codegendir.resolve("model_classes");
      final Path testClassesDir = codegendir.resolve("test_classes");

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

         final ByteArrayOutputStream out = new ByteArrayOutputStream();

         // invoke scenario compiler
         final int exitCode = Tools.genCompileRun(out, out, srcDir, modelSrcDir, testSrcDir, modelClassesDir,
                                                  testClassesDir, "--class-diagram-svg"
                                                  // "--object-diagram-svg"
         );

         final JSONObject result = new JSONObject();

         result.put("exitCode", exitCode);

         final String output = new String(out.toByteArray(), StandardCharsets.UTF_8);
         result.put("output", output.replace(codegendir.toString(), "."));

         if (exitCode < 0) // exception occurred
         {
            Logger.getGlobal().severe(output);
         }
         if (exitCode == 0 || (exitCode & 4) != 0) // scenarioc did not fail
         {
            // collect test methods
            final JSONArray methodArray = new JSONArray();

            Files.walk(testSrcDir).filter(Tools::isJava).forEach(file -> {
               try
               {
                  String firstTestBody = Files.lines(file).filter(it -> it.startsWith("      "))
                                              .map(it -> it.substring(6)).collect(Collectors.joining("\n"));
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
            final Path classDiagramFile = modelSrcDir.resolve(PACKAGE_NAME).resolve("classDiagram.svg");
            if (Files.exists(classDiagramFile))
            {
               final byte[] bytes = Files.readAllBytes(classDiagramFile);
               final String svgText = new String(bytes, StandardCharsets.UTF_8);
               result.put("classDiagram", svgText);
            }
         }
         if (exitCode == 0) // everything succeeded
         {
            // TODO read object diagram
         }

         res.type("application/json");
         return result.toString(3);
      }
      finally
      {
         Tools.deleteRecursively(codegendir);
      }
   }
}
