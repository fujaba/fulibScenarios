package org.fulib.scenarios;

import static spark.Spark.*;

public class WebService
{
   public static void main(String[] args)
   {
      staticFiles.externalLocation("webapp");

      redirect.get("/github", "https://github.com/fujaba/fulib");

      get("/scenarios/run", (req, res) -> "StudyRight studyRight = new StudyRight();");

      // http://localhost:4567
   }
}
