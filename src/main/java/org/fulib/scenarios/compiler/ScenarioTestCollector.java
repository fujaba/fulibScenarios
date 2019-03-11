package org.fulib.scenarios.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.StrUtil;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.builder.ModelEventManager;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class ScenarioTestCollector extends FulibScenariosBaseListener
{
   public static final String VERB_PHRASE = "verbPhrase";
   private final ClassModelManager mm;
   public StringBuilder methodBody = new StringBuilder();
   public StringBuilder settings;
   public StringBuilder references;

   private LinkedHashMap<String, String> object2ClassMap;
   private String docDir;
   private LinkedHashMap<String, TreeSet<String>> attrValueExamplesMap;
   private LinkedHashMap<String, String> currentAttrValueMap;
   private LinkedHashMap<String, String> methodParams;

   private String objectName;
   private String className;
   private FMethod currentMethod;
   private final ModelEventManager em;
   private Clazz clazz;
   private final ClassModel classModel;
   private String attrName;
   private String attrValue;
   private ArrayList<String> valueDataTextList;
   private ArrayList<String> valueDataNameList;
   private Clazz currentRegisterClazz;
   private String attrType;
   private String sentenceType = null;


   public ScenarioTestCollector(LinkedHashMap<String, String> object2ClassMap, String docDir)
   {
      this.object2ClassMap = object2ClassMap;
      this.docDir = docDir;
      this.attrValueExamplesMap = new LinkedHashMap<>();

      em = new ModelEventManager();
      mm = new ClassModelManager(em);
      em.setModelManager(mm);

      classModel = mm.getClassModel();
   }


   public LinkedHashMap<String, String> getMethodParams()
   {
      return methodParams;
   }

   public LinkedHashMap<String, String> getObject2ClassMap()
   {
      return object2ClassMap;
   }


   public LinkedHashMap<String, TreeSet<String>> getAttrValueExamplesMap()
   {
      return attrValueExamplesMap;
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

      if (ctx.className == null)
      {
         className = StrUtil.cap(objectName);
      }
      else
      {
         className = StrUtil.cap(ctx.className.getText());
      }

      clazz = mm.haveClass(StrUtil.cap(className));
   }



   @Override
   public void enterHasSentence(FulibScenariosParser.HasSentenceContext ctx)
   {
      settings = new StringBuilder();
      objectName = StrUtil.downFirstChar(getMultiName(ctx.objectName));
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
      if (ctx.className == null)
      {
         className = StrUtil.cap(objectName);
      }
      else
      {
         className = StrUtil.cap(ctx.className.getText());
      }

      // objectCreate(className, objectName, settings)
      st.add("className", StrUtil.cap(className));
      st.add("objectName", objectName);
      st.add("settings", settings);
      String result = st.render();

      methodBody.append(result);
   }


   @Override
   public void exitNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx)
   {
      super.exitNumberWithClause(ctx);
   }

   @Override
   public void exitHasSentence(FulibScenariosParser.HasSentenceContext ctx)
   {
      className = object2ClassMap.get(objectName);

      // objectCreate(className, objectName, settings)
      if ( ! settings.toString().trim().equals(""))
      {
         String result = String.format("%s%s;\n\n", objectName, settings.toString().trim());
         methodBody.append(result);
      }
   }

   @Override
   public void enterDiagramSentence(FulibScenariosParser.DiagramSentenceContext ctx)
   {
      String fileName = StrUtil.downFirstChar(ctx.fileName.getText());

      String rootObj = StrUtil.downFirstChar(ctx.type.getText());

      String stmt = String.format("\nFulibTools.objectDiagrams().dumpPng(\"%s/%s\", %s);\n\n", docDir, fileName, rootObj);
      references.append(stmt);
   }

   @Override
   public void enterCallSentence(FulibScenariosParser.CallSentenceContext ctx)
   {
      // add references to method body
      this.methodBody.append(this.references);
      references.setLength(0);

      this.methodParams = new LinkedHashMap<>();
      this.objectName = StrUtil.downFirstChar(ctx.objectName.getText());

      this.sentenceType = "callSentence";
   }



   @Override
   public void exitCallSentence(FulibScenariosParser.CallSentenceContext ctx)
   {
      references.setLength(0);
      this.sentenceType = null;

      String methodName = ctx.methodName.getText();

      String actualParams = String.join(", ", this.methodParams.values());

      String invocation = String.format("%s.%s(%s);\n", objectName, methodName, actualParams);

      this.methodBody.append(invocation);

      String className = object2ClassMap.get(objectName);
      Clazz clazz = this.getModelManager().haveClass(className);
      FMethod method = this.getModelManager().haveMethod(clazz, methodName);
      method.setReturnType("void");

      for (Map.Entry<String, String> entry : this.methodParams.entrySet())
      {
         String paramName = entry.getKey();
         String paramValue = entry.getValue();

         String paramType = this.object2ClassMap.get(paramValue);

         if (paramType == null)
         {
            paramType = ClassModelBuilder.STRING;
         }
         method.getParams().put(paramName, paramType);

      }
   }

   @Override
   public void enterChainSentence(FulibScenariosParser.ChainSentenceContext ctx)
   {
      // method ....
      String methodName = StrUtil.downFirstChar(ctx.methodName.getText());

      this.currentMethod = this.getModelManager().getMethod(methodName);
   }


   @Override
   public void enterCreatePhrase(FulibScenariosParser.CreatePhraseContext ctx)
   {
      this.sentenceType = VERB_PHRASE;
      this.currentAttrValueMap = new LinkedHashMap<>();
   }

   @Override
   public void exitCreatePhrase(FulibScenariosParser.CreatePhraseContext ctx)
   {
      this.sentenceType = null;
      String className = StrUtil.cap(ctx.className.getText());
      String objectId = this.attrValue;

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("objectCreate");

      String newObjectName = this.currentAttrValueMap.values().iterator().next();
      newObjectName = StrUtil.downFirstChar(newObjectName);

      String inits = computeInitialization(newObjectName, className);

      this.object2ClassMap.put(newObjectName, className);

      // objectCreate(className, objectName, settings)
      st.add("className", className);
      st.add("objectName", newObjectName);
      st.add("settings", inits);
      String result = "      " + st.render();

      String currentMethodBody = this.currentMethod.getMethodBody();
      currentMethodBody = (currentMethodBody == null ? "" : currentMethodBody);
      currentMethodBody += result;
      this.currentMethod.setMethodBody(currentMethodBody);
   }



   @Override
   public void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      attrName = ctx.attrName.getText();

      ArrayList<String> valueList = getValueList(ctx.attrValue);
      attrValue = getMultiValue(ctx.attrValue);

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
   public void enterUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx)
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
      if (sentenceType == null)
      {
         computeSettings();
      }
      else if (sentenceType.equals(VERB_PHRASE))
      {
         String attrName = this.attrName;
         String value = this.valueDataNameList.get(0);
         if (this.object2ClassMap.get(value) == null) value = this.valueDataTextList.get(0);

         this.currentAttrValueMap.put(attrName, value);
      }
      else
      {
         // callSentence
         // just collect paramter names
         String attrName = this.attrName;
         String value = this.attrValue;
         String objName = StrUtil.downFirstChar(value);
         if (this.object2ClassMap.get(objName) != null)
         {
            methodParams.put(attrName, objName);
         }
         else
         {
            methodParams.put(attrName, value);
         }
      }
   }

   private void computeSettings()
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
         String srcClassName = object2ClassMap.get(objectName);

         if (srcClassName == null)
            throw new RuntimeException("Could not find class for " + objectName); //=============

         Clazz srcClass = mm.haveClass(srcClassName);
         String tgtClassName = object2ClassMap.get(valueDataNameList.get(0));
         Clazz tgtClass = mm.haveClass(tgtClassName);
         AssocRole role = mm.haveRole(srcClass, attrName, tgtClass, valueDataNameList.size());

         String stmt = objectName + ".";

         if (role.getCardinality() == 1)
         {
            stmt += "set";
         }
         else
         {
            stmt += "with";
         }

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
         mm.haveAttribute(clazz, attrName, ClassModelBuilder.STRING);

         st.add("attrName", StrUtil.cap(attrName));
         st.add("attrValue", "\"" + valueDataTextList.get(0) + "\"");
         String result = st.render();

         String key = className + "." + attrName;
         TreeSet<String> exampleSet = attrValueExamplesMap.get(key);
         if (exampleSet == null)
         {
            exampleSet = new TreeSet<>();
            attrValueExamplesMap.put(key, exampleSet);
         }
         exampleSet.add(valueDataTextList.get(0));

         settings.append(result);
      }
   }



   private String computeInitialization(String objectName, String className)
   {
      String result = "";

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      Clazz newClazz = this.getModelManager().haveClass(className);

      for (Map.Entry<String, String> entry : this.currentAttrValueMap.entrySet())
      {
         String key = entry.getKey();
         String value = entry.getValue();
         String valueId = StrUtil.downFirstChar(value);
         String paramValue = getParamValue(value);


         if ( ! valueId.equals(objectName) && this.object2ClassMap.get(valueId) != null)
         {
            // build role
            String tgtClassName = object2ClassMap.get(valueId);
            Clazz tgtClass = mm.haveClass(tgtClassName);
            AssocRole role = mm.haveRole(newClazz, attrName, tgtClass, valueDataNameList.size());

            String stmt = "\n         .";

            if (role.getCardinality() == 1)
            {
               stmt += "set";
            }
            else
            {
               stmt += "with";
            }

            stmt += StrUtil.cap(attrName);
            stmt += "(";

            for (String name : valueDataNameList)
            {
               String param = getParamValue(name);
               stmt += param + ", ";
            }

            stmt = stmt.substring(0, stmt.length()-2);

            stmt += ")";

            result += stmt;
         }
         else
         {
            mm.haveAttribute(newClazz, key, ClassModelBuilder.STRING);

            st.add("attrName", StrUtil.cap(key));
            st.add("attrValue", "\"" + value + "\"");
            String text = st.render();

            String newKey = className + "." + key;
            TreeSet<String> exampleSet = attrValueExamplesMap.get(newKey);
            if (exampleSet == null)
            {
               exampleSet = new TreeSet<>();
               attrValueExamplesMap.put(newKey, exampleSet);
            }
            exampleSet.add(value);

            result += text;
         }
      }

      return result;
   }



   private String getParamValue(String value)
   {
      String result = value;
      for (Map.Entry<String, String> entry : this.getMethodParams().entrySet())
      {
         String key = entry.getKey();
         String paramValue = entry.getValue();

         if (paramValue.equalsIgnoreCase(value))
         {
            return key;
         }
      }
      return result;
   }



   @Override
   public void exitUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx)
   {
      computeSettings();
   }



   @Override
   public void enterNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx)
   {
      // 'with' value= NUMBER  attrName=NAME+ ','? 'and'? # NumberWithClause

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());
      ST st = group.getInstanceOf("setting");

      // setting(attrName, value)
      String attrName = StrUtil.downFirstChar(getMultiName(ctx.attrName));
      String value = ctx.value.getText();

      mm.haveAttribute(clazz, attrName, ClassModelBuilder.DOUBLE);

      st.add("attrName", StrUtil.cap(attrName));
      st.add("attrValue", value);
      String result = st.render();

      String key = className + "." + attrName;
      TreeSet<String> exampleSet = attrValueExamplesMap.get(key);
      if (exampleSet == null)
      {
         exampleSet = new TreeSet<>();
         attrValueExamplesMap.put(key, exampleSet);
      }
      exampleSet.add(value);

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
         if ( ! (child instanceof TerminalNode))
         {
            result += StrUtil.cap(child.getText());
         }
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

   private void getValueClauseAsName(FulibScenariosParser.UsualHasClauseContext ctx)
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

   private ArrayList<String> getValueList(FulibScenariosParser.ValueClauseContext valueContext)
   {
      ArrayList<String> result = new ArrayList<>();
      String oneValue = "";

      for (ParseTree child : valueContext.children)
      {
         if ( ! (child instanceof TerminalNode))
         {
            oneValue += child.getText() + " ";
         }
         else
         {
            String multiName = oneValue.trim();
            if ( ! multiName.equals(""))
            {
               result.add(multiName);
            }
            oneValue = "";
         }
      }

      String multiName = oneValue.trim();
      if ( ! multiName.equals(""))
      {
         result.add(multiName);
      }
      oneValue = "";

      return result;
   }

   
   private String getMultiValue(FulibScenariosParser.ValueClauseContext valueContext)
   {
      String result = "";

      for (ParseTree child : valueContext.children)
      {
         if ( ! (child instanceof TerminalNode))
         {
            result += StrUtil.cap(child.getText()) + " ";
         }
      }

      return result.trim();
   }
}
