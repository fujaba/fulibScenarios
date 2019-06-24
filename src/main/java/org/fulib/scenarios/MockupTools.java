package org.fulib.scenarios;

import org.fulib.yaml.Reflector;
import org.fulib.yaml.ReflectorMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class MockupTools
{

   private static LinkedHashMap<Object, ArrayList<String>> mapForStepLists = new LinkedHashMap<>();


   public static final String DESCRIPTION = "description";
   public static final String ID = "id";
   public static final String CONTENT = "content";
   private ReflectorMap reflectorMap;

   public static MockupTools htmlTool() {
      return new MockupTools();
   }


   public int dump(String fileName, Object root) {

      String packageName = root.getClass().getPackage().getName();

      reflectorMap = new ReflectorMap(packageName);

      String bootstrap = "<!-- Bootstrap CSS -->\n" +
            "    <link rel=\"stylesheet\"\n" +
            "          href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
            "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\n" +
            "          crossorigin=\"anonymous\">\n\n";

      if (fileName.endsWith(".mockup.html")) {
         generateMockup(fileName, root, bootstrap);
      }
      else
      {
         generateSingleScreen(fileName, root, bootstrap);
      }

      return 0;
   }

   private void generateMockup(String fileName, Object root, String bootstrap)
   {
      StringBuilder body = new StringBuilder();
      body.append(bootstrap);

      String rootDiv = "" +
            "<div id='thePanel'>\n" +
            "</div>\n\n";

      body.append(rootDiv);

      String scriptStart = "" +
            "<script>\n" +
            "   const stepList = [];\n\n";

      body.append(scriptStart);

      ArrayList<String> stepList = mapForStepLists.get(root);

      int i = 0;
      if (stepList != null) {
         for (String stepText : stepList)
         {
            String[] lines = stepText.split("\n");
            String firstLine = "   stepList.push(\"\" +\n";
            body.append(firstLine);

            for (String line : lines)
            {
               body.append("      \"").append(line).append("\\n\" +\n");
            }

            String lastLine = String.format("         \"\");\n\n", i++);
            body.append(lastLine);
         }
      }

      String scriptEnd = "" +
            "   var stepCount = 0;\n" +
            "\n" +
            "   stepList.push(\"<h2 class='row justify-content-center' style='margin: 1rem'>The End</h2>\");\n" +
            "\n" +
            "   document.getElementById('thePanel').innerHTML = stepList[stepCount];\n" +
            "\n" +
            "   const nextStep = function (event) {\n" +
            "        if (event.ctrlKey) {\n" +
            "           stepCount--\n" +
            "        } else {\n" +
            "           stepCount++;\n" +
            "        }\n" +
            "        if (stepList.length > stepCount) {\n" +
            "            document.getElementById('thePanel').innerHTML = stepList[stepCount];\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    document.getElementById('thePanel').onclick = nextStep;\n" +
            "\n" +
            "</script>\n";

      body.append(scriptEnd);

      try
      {
         Files.write(Paths.get(fileName), body.toString().getBytes(StandardCharsets.UTF_8));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }



   private void generateSingleScreen(String fileName, Object root, String bootstrap)
   {
      String body = generateElement(root, "");

      putToStepList(root, body);

      try
      {
         Files.write(Paths.get(fileName), (bootstrap + body).getBytes(StandardCharsets.UTF_8));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private void putToStepList(Object root, String body)
   {
      ArrayList<String> stepList = mapForStepLists.get(root);
      if (stepList == null) {
         stepList = new ArrayList<>();
         mapForStepLists.put(root, stepList);
      }
      stepList.add(body);
   }

   private String generateElement(Object root, String indent)
   {
      String containerClass = "";
      if ("".equals(indent)) {
         containerClass = "class='container'";
      }

      Reflector reflector = reflectorMap.getReflector(root);
      Collection content = null;
      Object bareContent = reflector.getValue(root, CONTENT);
      if (bareContent != null) {
         if (bareContent instanceof Collection) {
            content = (Collection) bareContent;
         }
         else {
            content = new ArrayList();
            ((ArrayList) content).add(bareContent);
         }
      }

      StringBuilder contentBuf = new StringBuilder();
      if (content != null)
      {
         for (Object element : content)
         {
            String elementHtml = generateElement(element, indent + "   ");
            contentBuf.append(elementHtml);
         }
      }

      String rootId = reflector.getValue(root, ID).toString();


      String rootDescription = reflector.getValue(root, DESCRIPTION).toString();

      String cellList = "";

      String[] split = rootDescription.split("\\|");

      for (String elem : split)
      {
         String cell = generateOneCell(root, indent, reflector, elem.trim(), split.length);
         cellList += cell;
      }


      String body = String.format("" +
            indent + "<div id='%s' %s>\n" +
            indent + "   <div class='row justify-content-center'>\n" +
            indent + "      %s\n" +
            indent + "   </div>\n" +
            "%s" +
            indent + "</div>\n", rootId, containerClass, cellList, contentBuf.toString());

      return body;
   }

   private String generateOneCell(Object root, String indent, Reflector reflector, String rootDescription, int numberOfElemsPerLine)
   {
      String cols = "class='col col-lg-2 text-center'";

      String elem = String.format("<div %s style='margin: 1rem'>" + rootDescription + "</div>\n", cols);

      if (rootDescription.startsWith("input"))
      {
         //        <div class="row justify-content-center" style="margin: 1rem">
         //            <input id="partyNameInput" placeholder="Name?" style="margin: 1rem"></input>
         //        </div>

         String prompt = "?";
         int pos = rootDescription.indexOf("prompt");
         if (pos >= 0) {
            prompt = rootDescription.substring(pos + "prompt ".length());
         }
         else {
            prompt = rootDescription.substring("input ".length());
         }

         String value = (String) reflector.getValue(root, "value");
         if (value == null) {
            value = "";
         }
         else {
            value = String.format("value='%s'", value);
         }

         elem = String.format("" +
               indent + "   <input %s placeholder='%s' %s style='margin: 1rem'></input>\n",
               cols, prompt, value);

      }
      else if (rootDescription.startsWith("button")) {
         //        <div class="row justify-content-center" style="margin: 1rem">
         //            <button style="margin: 1rem">next</button>
         //        </div>
         String text = rootDescription.substring("button ".length());
         elem = String.format(indent + "   <div %s><button style='margin: 1rem'>%s</button></div>\n", cols, text);
      }

      return elem;
   }
}
