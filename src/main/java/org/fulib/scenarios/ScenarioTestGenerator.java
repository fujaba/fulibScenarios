package org.fulib.scenarios;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fulib.Fulib;
import org.fulib.FulibTools;
import org.fulib.StrUtil;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.Attribute;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

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

      generateClassModelCode(myScenarioListener);

      generateJUnitTest(myScenarioListener);

      generateRegister(myScenarioListener);
   }



   private static void generateRegister(ScenarioTestCollector myScenarioListener)
   {
      // generate scenario test
      STGroupFile group = new STGroupFile("templates/register.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st;
      String result;

      // collect classes
      ClassModel classModel = myScenarioListener.getClassModel();
      TreeMap<String, Clazz> clazzTreeMap = new TreeMap<>();

      for (Clazz clazz : classModel.getClasses())
      {
         clazzTreeMap.put(clazz.getName(), clazz);
      }

      StringBuilder body = new StringBuilder();
      for (Map.Entry<String, Clazz> entry : clazzTreeMap.entrySet())
      {
         Clazz clazz = entry.getValue();

         StringBuilder objectExamples = new StringBuilder();
         for (Map.Entry<String, String> objectEntry : myScenarioListener.getObject2ClassMap().entrySet())
         {
            String objectName = objectEntry.getKey();
            String clazzName = objectEntry.getValue();

            if (StrUtil.stringEquals(clazzName, clazz.getName()))
            {
               objectExamples.append(objectName).append(", ");
            }
         }

         StringBuilder attributeList = new StringBuilder();
         for (Attribute attribute : clazz.getAttributes())
         {
            String key = clazz.getName() + "." + attribute.getName();
            String exampleString = myScenarioListener.getAttrValueExamplesMap().get(key);

            st = group.getInstanceOf("attribute");
            st.add("name", attribute.getName());
            st.add("examples", exampleString);
            result = st.render();
            attributeList.append(result);
         }

         LinkedHashSet<String> usedRoles = new LinkedHashSet<>();
         for (AssocRole role : clazz.getRoles())
         {
            if (usedRoles.contains(role.getName())) continue; //=====================
            usedRoles.add(role.getName());

            st = group.getInstanceOf("role");
            st.add("name", role.getName());
            st.add("card", (role.getCardinality()==1 ? "one" : "many"));
            st.add("tgtClass", role.getOther().getClazz().getName());
            st.add("reverseName", role.getOther().getName());
            result = st.render();
            attributeList.append(result);
         }


         st = group.getInstanceOf("classDef");
         st.add("name", clazz.getName());
         st.add("examples", objectExamples.toString());
         st.add("attributes", attributeList.toString());
         result = st.render();
         body.append(result);
      }


      st = group.getInstanceOf("register");
      st.add("body", body.toString());
      result = st.render();

      System.out.println(result);

      try
      {
         Path path = Paths.get("doc/studyright/Register.md");
         // Files.createDirectories(path);
         Files.write(path,
               result.getBytes());
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }


   private static void generateJUnitTest(ScenarioTestCollector myScenarioListener)
   {
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

   private static void generateClassModelCode(ScenarioTestCollector myScenarioListener)
   {
      // generate class model implementation
      ClassModel classModel = myScenarioListener.getClassModel()
            .setMainJavaDir("src/test/java")
            .setPackageName("uniks.scenarios.studyright");
      Fulib.generator().generate(classModel);
      FulibTools.classDiagrams().dumpPng(classModel, "doc/studyRight/ScenarioResultClassDiagram.png");
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
