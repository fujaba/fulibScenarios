package org.fulib.scenarios;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fulib.Fulib;
import org.fulib.FulibTools;
import org.fulib.classmodel.ClassModel;
import org.fulib.scenarios.compiler.FulibScenariosLexer;
import org.fulib.scenarios.compiler.FulibScenariosParser;
import org.fulib.scenarios.compiler.ScenarioObjectCollector;
import org.fulib.scenarios.compiler.ScenarioTestCollector;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScenarioTestGenerator
{
   public static void main(String[] args)
   {
      // parse register
      FulibScenariosParser.ScenarioContext registerContext = parse("doc/studyRight/Register.md");

      ScenarioTestCollector myScenarioListener = new ScenarioTestCollector(new LinkedHashMap<>());
      ParseTreeWalker.DEFAULT.walk(myScenarioListener, registerContext);

      FulibTools.classDiagrams().dumpPng(myScenarioListener.getClassModel(), "doc/studyRight/RegisterDiag.png");

      // parse scenario
      FulibScenariosParser.ScenarioContext mainContext = parse("doc/studyRight/StudyRightScenario.md");

      ScenarioObjectCollector objectCollector = new ScenarioObjectCollector();
      ParseTreeWalker.DEFAULT.walk(objectCollector, mainContext);

      myScenarioListener.setObject2ClassMap(objectCollector.object2ClassMap);
      ParseTreeWalker.DEFAULT.walk(myScenarioListener, mainContext);

      // generate class model implementation
      ClassModel classModel = myScenarioListener.getClassModel()
            .setMainJavaDir("src/test/java")
            .setPackageName("uniks.scenarios.studyright");
      Fulib.generator().generate(classModel);
      FulibTools.classDiagrams().dumpPng(classModel, "doc/studyRight/ScenarioResultClassDiagram.png");

      // generate scenario test
      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("junitTest");

      // packageName, testClassName, testMethodName, methodBody
      st.add("packageName", "uniks.scenarios.studyright");
      st.add("testClassName", "TestDoAssignments");
      st.add("testMethodName", "testScenario1");
      st.add("methodBody", myScenarioListener.methodBody);
      String result = st.render();
      System.out.println(result);

      try
      {
         Path path = Paths.get("src/test/java/uniks/scenarios/studyright/TestDoAssignments.java");
         // Files.createDirectories(path);
         Files.write(path,
               result.getBytes());
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private static FulibScenariosParser.ScenarioContext parse(String fileName)
   {
      FulibScenariosLexer lexer = null;
      try
      {
         lexer = new FulibScenariosLexer(
               CharStreams.fromFileName(fileName));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      // lexer.removeErrorListeners();

      FulibScenariosParser parser = new FulibScenariosParser(new CommonTokenStream(lexer));

      return parser.scenario();
   }
}
