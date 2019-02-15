package org.fulib.scenarios.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.fulib.Fulib;
import org.fulib.StrUtil;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.util.LinkedHashMap;

public class ScenarioTestCollector extends FulibScenariosBaseListener
{
   public StringBuilder methodBody = new StringBuilder();
   public StringBuilder settings;

   public ClassModelBuilder mb = Fulib.classModelBuilder("uniks.scenarios.studyright",
         "src/test/java");

   private ClassBuilder classBuilder;

   private LinkedHashMap<String, String> object2ClassMap;

   private String objectName;
   private String className;



   public ScenarioTestCollector(LinkedHashMap<String, String> object2ClassMap)
   {
      this.object2ClassMap = object2ClassMap;
   }



   @Override
   public void enterTitle(FulibScenariosParser.TitleContext ctx)
   {
   }



   @Override
   public void enterDirectSentence(FulibScenariosParser.DirectSentenceContext ctx)
   {
      objectName = getMultiName(ctx.objectName);
      className = StrUtil.cap(ctx.className.getText());


      object2ClassMap.put(StrUtil.downFirstChar(objectName), className);
   }



   @Override
   public void enterThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      objectName = null;

      if (ctx.objectName != null)
      {
         objectName = getMultiName(ctx.objectName);
      }

      className = StrUtil.cap(ctx.className.getText());
      classBuilder = mb.buildClass(StrUtil.cap(className));
   }

   @Override
   public void exitThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      // There is Karli a student with name Karli
      // Student karli = new Student().setName("Karli");

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("objectCreate");

      String objectNameText = ctx.objectName.getText();
      String classNameText = ctx.className.getText();

      // objectCreate(className, objectName, settings)
      st.add("className", StrUtil.cap(classNameText));
      st.add("objectName", objectNameText);
      st.add("settings", settings);
      String result = st.render();

      methodBody.append(result);
   }


   @Override
   public void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      // setting(attrName, value)
      String attrNameText = ctx.attrName.getText();
      String value = "ctx.value.getText()";

      classBuilder.buildAttribute(attrNameText, mb.STRING);

      st.add("attrName", StrUtil.cap(attrNameText));
      st.add("attrValue", "\"" + value + "\"");
      String result = st.render();

      settings.append(result);
   }

   @Override
   public void enterNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx)
   {
      // 'with' value= NUMBER  attrName=NAME+ ','? 'and'? # NumberWithClause

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      // setting(attrName, value)
      String attrNameText = ctx.attrName.getText();
      String value = ctx.value.getText();

      System.out.println("Adding attribute" + attrNameText);
      classBuilder.buildAttribute(attrNameText, mb.DOUBLE);

      st.add("attrName", StrUtil.cap(attrNameText));
      st.add("attrValue", value);
      String result = st.render();

      settings.append(result);
   }



   private String getMultiName(FulibScenariosParser.MultiNameContext objectName)
   {
      String result = "";

      for (ParseTree child : objectName.children)
      {
         result += StrUtil.cap(child.getText());
      }

      return result;
   }
}
