package org.fulib.scenarios;

import org.fulib.yaml.Reflector;
import org.fulib.yaml.ReflectorMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class MockupTools
{

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

      String body = generateElement(root, "");


      try
      {
         Files.write(Paths.get(fileName), (bootstrap + body).getBytes(StandardCharsets.UTF_8));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      System.out.println("going to generate html mockup for " + root + " into " + fileName + "\n" +
            body);

      return 0;
   }

   private String generateElement(Object root, String indent)
   {
      Reflector reflector = reflectorMap.getReflector(root);
      Collection content = (Collection) reflector.getValue(root, CONTENT);
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

      String body = String.format("" +
            indent + "<div id='%s' >\n" +
            indent + "   <div class='row justify-content-center' style='margin: 1rem'>%s</div>\n" +
            "%s" +
            indent + "</div>\n", rootId, rootDescription, contentBuf.toString());

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

         body = String.format("" +
               indent + "<div id='%s' class='row justify-content-center' style='margin: 1rem'>\n" +
               indent + "   <input placeholder='%s'></input>\n" +
               "%s" +
               indent + "</div>\n", rootId, prompt, contentBuf.toString());

      }
      else if (rootDescription.startsWith("button")) {
         //        <div class="row justify-content-center" style="margin: 1rem">
         //            <button style="margin: 1rem">next</button>
         //        </div>
         String text = rootDescription.substring("button ".length());
         body = String.format("" +
               indent + "<div id='%s' class='row justify-content-center' style='margin: 1rem'>\n" +
               indent + "   <button>%s</button>\n" +
               indent + "</div>\n", rootId, text);
      }

      return body;
   }
}
