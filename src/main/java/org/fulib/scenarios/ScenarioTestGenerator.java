package org.fulib.scenarios;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fulib.scenarios.compiler.FulibScenariosLexer;
import org.fulib.scenarios.compiler.FulibScenariosParser;
import org.fulib.scenarios.compiler.ScenarioObjectCollector;
import org.fulib.scenarios.compiler.ScenarioTestCollector;

import java.io.IOException;
import java.util.Map;

public class ScenarioTestGenerator
{
   public static void main(String[] args)
   {
      // parse scenario
      FulibScenariosLexer lexer = null;
      try
      {
         lexer = new FulibScenariosLexer(
               CharStreams.fromFileName("doc/studyRight/StudyRightScenario.md"));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      // lexer.removeErrorListeners();

      FulibScenariosParser parser = new FulibScenariosParser(new CommonTokenStream(lexer));

      FulibScenariosParser.ScenarioContext mainContext = parser.scenario();

      ScenarioObjectCollector objectCollector = new ScenarioObjectCollector();
      ParseTreeWalker.DEFAULT.walk(objectCollector, mainContext);

      ScenarioTestCollector myScenarioListener = new ScenarioTestCollector(objectCollector.object2ClassMap);
      ParseTreeWalker.DEFAULT.walk(myScenarioListener, mainContext);

      // generate class model implementation
//      Fulib.generator().generate(myScenarioListener.mb.getClassModel());
//      FulibTools.classDiagrams().dumpSVG(myScenarioListener.mb.getClassModel(), "src/main/resources/ScenarioResultClassDiagram.svg");
//
//      // generate scenario test
//      STGroupFile group = new STGroupFile("templates/junitTest.stg");
//      group.registerRenderer(String.class, new StringRenderer());
//      ST st = group.getInstanceOf("junitTest");
//
//      // packageName, testClassName, testMethodName, methodBody
//      st.add("packageName", "uniks.scenarios.studyright");
//      st.add("testClassName", "TestDoAssignments");
//      st.add("testMethodName", "testScenario1");
//      st.add("methodBody", myScenarioListener.methodBody);
//      String result = st.render();
//      System.out.println(result);
//
//      try
//      {
//         Path path = Paths.get("src/test/java/uniks/scenarios/studyright/TestDoAssignments.java");
//         // Files.createDirectories(path);
//         Files.write(path,
//               result.getBytes());
//      }
//      catch (IOException e)
//      {
//         e.printStackTrace();
//      }
   }
}
