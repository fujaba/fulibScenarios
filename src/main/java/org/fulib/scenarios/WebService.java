package org.fulib.scenarios;

import spark.Request;
import spark.Response;

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

      // http://localhost:4567
   }

   private static String runCodeGen(Request req, Response res)
   {
      return "greetings from code gen";
   }
}
