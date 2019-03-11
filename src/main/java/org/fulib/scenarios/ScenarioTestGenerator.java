package org.fulib.scenarios;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fulib.Fulib;
import org.fulib.FulibTools;
import org.fulib.StrUtil;
import org.fulib.classmodel.*;
import org.fulib.scenarios.compiler.FulibScenariosLexer;
import org.fulib.scenarios.compiler.FulibScenariosParser;
import org.fulib.scenarios.compiler.ScenarioObjectCollector;
import org.fulib.scenarios.compiler.ScenarioTestCollector;
import org.fulib.util.Generator4FMethod;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ScenarioTestGenerator
{
   public static void main(String[] args)
   {
      // parse register
      generateScenarios("doc/studentAffairs/seGroup");
   }

   private static void generateScenarios(String docDir)
   {
      FulibScenariosParser.ScenarioContext registerContext = parse(docDir + "/Register.md");

      ScenarioTestCollector myScenarioListener = new ScenarioTestCollector(new LinkedHashMap<>(), docDir);
      ParseTreeWalker.DEFAULT.walk(myScenarioListener, registerContext);

      // parse scenarios
      try
      {
         Files.list(Paths.get(docDir)).forEach(
               path -> {
                  System.out.println(path.toString());

                  if ( path.toString().endsWith(".md"))
                  {
                     if (path.toString().endsWith("Register.md")) return;

                     String fileName = path.toString();
                     FulibScenariosParser.ScenarioContext mainContext = parse(fileName);

                     ScenarioObjectCollector objectCollector = new ScenarioObjectCollector();
                     ParseTreeWalker.DEFAULT.walk(objectCollector, mainContext);

                     myScenarioListener.setObject2ClassMap(objectCollector.object2ClassMap);
                     myScenarioListener.methodBody.setLength(0);
                     ParseTreeWalker.DEFAULT.walk(myScenarioListener, mainContext);

                     generateJUnitTest(myScenarioListener, docDir, path.getFileName().toString());
                  }
               }
         );
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      generateClassModelCode(myScenarioListener, docDir);
      generateFMethods(myScenarioListener, docDir);
      generateRegister(myScenarioListener, docDir);

      FulibTools.classDiagrams().dumpPng(myScenarioListener.getClassModel(), docDir + "/RegisterDiag.png");
   }

   private static void generateFMethods(ScenarioTestCollector myScenarioListener, String docDir)
   {
      // generate class model implementation
      String packageName = docDir.substring("doc/".length());
      packageName = packageName.replaceAll("/", ".");
      String javaSrcDir = "src/test/java";

      Generator4FMethod generator = new Generator4FMethod();
      for (Map.Entry<String, FMethod> entry : myScenarioListener.getModelManager().getMethodMap().entrySet())
      {
         String methodName = entry.getKey();
         FMethod method = entry.getValue();

         method.setPackageName(packageName);
         method.setJavaSrcDir(javaSrcDir);

         generator.generate(method);
      }
   }


   private static void generateRegister(ScenarioTestCollector myScenarioListener, String docDir)
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
            TreeSet<String> exampleSet = myScenarioListener.getAttrValueExamplesMap().get(key);
            String exampleString = "  ";
            for (String s : exampleSet)
            {
               exampleString += s + ", ";
            }
            exampleString = exampleString.substring(0, exampleString.length()-", ".length()).trim();
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

      // System.out.println(result);

      try
      {
         Path path = Paths.get(docDir + "/Register.md");
         // Files.createDirectories(path);
         Files.write(path,
               result.getBytes());
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }


   private static void generateJUnitTest(ScenarioTestCollector myScenarioListener, String docDir, String fileName)
   {
      fileName = fileName.substring(0, fileName.length()-".md".length());
      // generate scenario test
      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("junitTest");

      // packageName, testClassName, testMethodName, methodBody
      String packagePath = docDir.substring("doc/".length());
      String packageName = packagePath;
      packageName = packageName.replaceAll("/", ".");
      st.add("packageName", packageName);
      st.add("testClassName", "Test" + fileName);
      st.add("testMethodName", "test" + fileName);
      st.add("methodBody", myScenarioListener.methodBody);
      String result = st.render();

      try
      {
         String dirName = "src/test/java/" + packagePath;
         Path path = Paths.get(dirName);
         Files.createDirectories(path);
         path = Paths.get(dirName + "/Test" + fileName + ".java");
         Files.write(path,
               result.getBytes());
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private static void generateClassModelCode(ScenarioTestCollector myScenarioListener, String docDir)
   {
      // generate class model implementation
      String packageName = docDir.substring("doc/".length());
      packageName = packageName.replaceAll("/", ".");
      ClassModel classModel = myScenarioListener.getClassModel()
            .setMainJavaDir("src/test/java")
            .setPackageName(packageName);
      Fulib.generator().generate(classModel);
      // FulibTools.classDiagrams().dumpPng(classModel, docDir + "/ScenarioResultClassDiagram.png");
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
