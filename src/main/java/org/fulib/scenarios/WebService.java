package org.fulib.scenarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class WebService
{
   public static void main(String[] args)
   {
      staticFiles.externalLocation("webapp");

      get("/github", (req, res) -> {
         res.redirect("https://github.com/fujaba/fulib");
         return res;}
      );

      post("/runcodegen", (req, res) -> runCodeGen(req, res));

      Logger.getGlobal().info("scenario server started ... ");

      // http://localhost:4567
   }

   private static String runCodeGen(Request req, Response res) throws IOException, JSONException
   {
      Logger.getGlobal().info("run code gen ...");
      try
      {
         String body = req.body();

         JSONObject jsonObject = new JSONObject(body);

         String bodyText = jsonObject.getString("scenarioText");

         Path codegendir = Files.createTempDirectory("codegendir");
         Path srcDir = codegendir.resolve("src");
         Files.createDirectories(srcDir);
         Path mainPackageDir = srcDir.resolve("example");
         Files.createDirectories(mainPackageDir);

         Path modelSrcDir = codegendir.resolve("modelsrc");
         Files.createDirectories(modelSrcDir);
         Path testSrcDir = codegendir.resolve("testsrc");
         Files.createDirectories(testSrcDir);

         Path scenarioFile = mainPackageDir.resolve("scenario.md");
         Files.write(scenarioFile, bodyText.getBytes(StandardCharsets.UTF_8));

         try
         {
            Main.main(new String[]{"-m", modelSrcDir.toString(), "-t", testSrcDir.toString(), srcDir.toString(),
                  "--class-diagram-svg"});
         }
         catch (Exception e)
         {
            Logger.getGlobal().log(Level.SEVERE, "Generator failed: ", e);
         }

         Path exampleTestDir = testSrcDir.resolve("example");

         JSONArray methodArray = new JSONArray();

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


         JSONObject result = new JSONObject();
         result.put("testMethods", methodArray);

         Path exampleModelDir = modelSrcDir.resolve("example");
         byte[] bytes = Files.readAllBytes(exampleModelDir.resolve("classDiagram.svg"));
         String svgText = new String(bytes, StandardCharsets.UTF_8);

         result.put("classDiagram", svgText);

         return result.toString(3);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return "ups";
   }
}
