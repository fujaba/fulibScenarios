package org.fulib.scenarios.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.StrUtil;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.builder.ModelEventManager;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScenarioTestCollector extends FulibScenariosBaseListener
{
   private final ClassModelManager mm;
   public StringBuilder methodBody = new StringBuilder();
   public StringBuilder settings;
   public StringBuilder references;

   private LinkedHashMap<String, String> object2ClassMap;

   private String objectName;
   private String className;
   private final ModelEventManager em;
   private Clazz clazz;
   private final ClassModel classModel;
   private String attrName;
   private String attrValue;
   private ArrayList<String> valueDataTextList;
   private ArrayList<String> valueDataNameList;
   private Clazz currentRegisterClazz;
   private String attrType;


   public ScenarioTestCollector(LinkedHashMap<String, String> object2ClassMap)
   {
      this.object2ClassMap = object2ClassMap;

      em = new ModelEventManager();
      mm = new ClassModelManager(em);
      em.setModelManager(mm);

      classModel = mm.getClassModel();
   }



   public void setObject2ClassMap(LinkedHashMap<String, String> object2ClassMap)
   {
      this.object2ClassMap = object2ClassMap;
   }



   public ClassModelManager getModelManager()
   {
      return mm;
   }



   public ClassModel getClassModel()
   {
      return classModel;
   }



   @Override
   public void enterScenario(FulibScenariosParser.ScenarioContext ctx)
   {
      references = new StringBuilder();
   }



   @Override
   public void exitScenario(FulibScenariosParser.ScenarioContext ctx)
   {
      methodBody.append(references.toString());
   }



   @Override
   public void enterDirectSentence(FulibScenariosParser.DirectSentenceContext ctx)
   {
      settings = new StringBuilder();

      objectName = StrUtil.downFirstChar(getMultiName(ctx.objectName));
      className = StrUtil.cap(ctx.className.getText());

      clazz = mm.haveClass(className);
   }



   @Override
   public void exitDirectSentence(FulibScenariosParser.DirectSentenceContext ctx)
   {
      objectName = getMultiName(ctx.objectName);
      objectName = StrUtil.downFirstChar(objectName);
      className = StrUtil.cap(ctx.className.getText());

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("objectCreate");

      // objectCreate(className, objectName, settings)
      st.add("className", StrUtil.cap(className));
      st.add("objectName", objectName);
      st.add("settings", settings);
      String result = st.render();

      methodBody.append(result);
   }



   @Override
   public void enterThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      settings = new StringBuilder();

      objectName = null;

      if (ctx.objectName != null)
      {
         objectName = StrUtil.downFirstChar(getMultiName(ctx.objectName));
      }

      className = StrUtil.cap(ctx.className.getText());
      clazz = mm.haveClass(StrUtil.cap(className));
   }



   @Override
   public void exitThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      // There is Karli a student with name Karli
      // Student karli = new Student().setName("Karli");

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("objectCreate");

      objectName = StrUtil.downFirstChar(objectName);
      className = StrUtil.cap(ctx.className.getText());

      // objectCreate(className, objectName, settings)
      st.add("className", StrUtil.cap(className));
      st.add("objectName", objectName);
      st.add("settings", settings);
      String result = st.render();

      methodBody.append(result);
   }



   @Override
   public void enterDiagramSentence(FulibScenariosParser.DiagramSentenceContext ctx)
   {
      String fileName = ctx.fileName.getText();

      String stmt = "\nFulibTools.objectDiagrams().dumpPng(\"doc/studyRight/" + fileName + "\", studyRight);\n";
      references.append(stmt);
   }



   @Override
   public void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      attrName = ctx.attrName.getText();
      attrValue = ctx.attrValue.getText();

      if (ctx.children.get(ctx.children.size()-1) instanceof TerminalNode)
      {
         attrValue = attrValue.substring(0, attrValue.length()-3);
      }

      attrValue = attrValue.trim();

      if (attrValue.endsWith(","))
      {
         attrValue = attrValue.substring(0, attrValue.length()-1);
      }
   }

   @Override
   public void exitUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      boolean buildAttr = false;

      if (objectName == null)
      {
         objectName = valueDataNameList.get(0);
         buildAttr = true;
      }


      if ( ! buildAttr && object2ClassMap.get(valueDataNameList.get(0)) != null)
      {
         // build role
         String stmt = objectName + ".";

         if (valueDataNameList.size() == 1)
         {
            stmt += "set";
         }
         else
         {
            stmt += "with";
         }

         String srcClassName = object2ClassMap.get(objectName);

         if (srcClassName == null)
            throw new RuntimeException("Could not find class for " + objectName); //=============

         Clazz srcClass = mm.haveClass(srcClassName);
         String tgtClassName = object2ClassMap.get(valueDataNameList.get(0));
         Clazz tgtClass = mm.haveClass(tgtClassName);
         mm.haveRole(srcClass, attrName, tgtClass, valueDataNameList.size());

         stmt += StrUtil.cap(attrName);
         stmt += "(";

         for (String name : valueDataNameList)
         {
            stmt += name + ", ";
         }

         stmt = stmt.substring(0, stmt.length()-2);

         stmt += ");\n";

         references.append(stmt);
      }
      else
      {
         getValueClauseAsName(ctx);

         mm.haveAttribute(clazz, attrName, ClassModelBuilder.STRING);

         st.add("attrName", StrUtil.cap(attrName));
         st.add("attrValue", "\"" + valueDataTextList.get(0) + "\"");
         String result = st.render();

         settings.append(result);
      }
   }

   

   @Override
   public void enterNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx)
   {
      // 'with' value= NUMBER  attrName=NAME+ ','? 'and'? # NumberWithClause

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      // setting(attrName, value)
      String attrName = ctx.attrName.getText();
      String value = ctx.value.getText();

      mm.haveAttribute(clazz, attrName, ClassModelBuilder.DOUBLE);

      st.add("attrName", StrUtil.cap(attrName));
      st.add("attrValue", value);
      String result = st.render();

      settings.append(result);
   }


   @Override
   public void enterValueClause(FulibScenariosParser.ValueClauseContext ctx)
   {
      valueDataTextList = new ArrayList<String>();
      valueDataNameList = new ArrayList<String>();
   }



   @Override
   public void enterValueData(FulibScenariosParser.ValueDataContext ctx)
   {
      String valueText = "";
      String valueName = "";

      for (ParseTree child : ctx.children)
      {
         valueText += child.getText() + " ";
         valueName += StrUtil.cap(child.getText());
      }

      valueText = valueText.substring(0, valueText.length()-1);
      valueName = StrUtil.downFirstChar(valueName);

      valueDataTextList.add(valueText);
      valueDataNameList.add(valueName);
   }


   @Override
   public void enterClassDef(FulibScenariosParser.ClassDefContext ctx)
   {
      String className = ctx.className.getText();
      currentRegisterClazz = mm.haveClass(className);
   }



   @Override
   public void enterAttrDef(FulibScenariosParser.AttrDefContext ctx)
   {
      String myAttrName = ctx.attrName.getText();
      mm.haveAttribute(currentRegisterClazz, myAttrName, attrType);
   }


   @Override
   public void enterRoleDef(FulibScenariosParser.RoleDefContext ctx)
   {
      //      Room e.g. math room, arts room
      //         + doors many rooms cf. room.doors
      String srcRole = ctx.roleName.getText();
      String tgtClassName = StrUtil.cap(ctx.className.getText());
      Clazz tgtClass = mm.haveClass(tgtClassName);
      int srcSize = (ctx.card.getText().equals("one") ? ClassModelBuilder.ONE : ClassModelBuilder.MANY);

      if (ctx.otherRoleName != null)
      {
         String tgtRole = ctx.otherRoleName.getText();
         mm.haveRole(currentRegisterClazz, srcRole, tgtClass, srcSize, tgtRole, ClassModelBuilder.ONE);
      }
      else
      {
         mm.haveRole(currentRegisterClazz, srcRole, tgtClass, srcSize);
      }
   }



   @Override
   public void enterExampleValue(FulibScenariosParser.ExampleValueContext ctx)
   {
      if (ctx.nameValue != null) attrType = ClassModelBuilder.STRING;

      if (ctx.numberValue != null) attrType = ClassModelBuilder.DOUBLE;

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



   private void getValueClauseAsName(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      String result = "";

      for (ParseTree child : ctx.attrValue.children)
      {
         result += StrUtil.cap(child.getText());
      }

      if (result.endsWith(","))
      {
         result = result.substring(0, result.length()-1);
      }

      if (objectName == null)
      {
         objectName = StrUtil.downFirstChar(result);
      }
   }
   
   
   
   private String getMultiValue(FulibScenariosParser.ValueClauseContext valueContext)
   {
      String result = "";

      for (ParseTree child : valueContext.children)
      {
         if ( ! (child instanceof TerminalNode))
         {
            result += StrUtil.cap(child.getText());
         }
      }

      result = StrUtil.downFirstChar(result);

      return result;
   }
}
