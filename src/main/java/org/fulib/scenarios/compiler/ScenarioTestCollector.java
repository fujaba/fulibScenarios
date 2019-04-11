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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScenarioTestCollector extends FulibScenariosBaseListener
{
   public static final String VERB_PHRASE = "verbPhrase";
   public static final String TO_OBJ_NAME = "toObjName";
   public static final String FROM_ATTR_NAME = "fromAttrName";
   public static final String FROM_CLASS_NAME = "fromClassName";
   public static final String TO_ATTR_NAME = "toAttrName";
   public static final String FROM_OBJ_NAME = "fromObjName";
   public static final String VALUE_CLASS_NAME = "valueClassName";
   public static final String VALUE_CLASS_IS_ATTR_TYPE = "valueClassIsAttrType";
   public static final String NEW_VAR_NAME = "newVarName";
   public static final String VALUE_NAME = "valueName";
   private final ClassModelManager mm;
   public StringBuilder methodBody = new StringBuilder();
   public StringBuilder settings;
   public StringBuilder references;

   private LinkedHashMap<String, String> object2ClassMap;
   private String docDir;
   private LinkedHashMap<String, TreeSet<String>> attrValueExamplesMap;
   private LinkedHashMap<String, ArrayList<String>> currentAttrValueMap;
   private LinkedHashMap<String, String> methodParams;
   private LinkedHashMap<String, String> methodLocalVars;

   private String objectName;
   private String className;
   private FMethod currentMethod;
   private final ModelEventManager em;
   private Clazz clazz;
   private final ClassModel classModel;
   private String attrName;
   private String attrValue;
   private ArrayList<String> valueDataTextList = new ArrayList<>();
   private ArrayList<String> valueDataNameList = new ArrayList<>();
   private ArrayList<String> previousValueDataTextList = new ArrayList<>();
   private ArrayList<String> previousValueDataNameList = new ArrayList<>();
   private Clazz currentRegisterClazz;
   private String attrType;
   private String sentenceType = null;
   private String lastInvocation;
   private int oldMethodBodyLength;
   private FulibScenariosParser.LoopClauseContext loopIntro;
   private String indent = "";
   private String statementClosing = "";


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


   public void initMethodParams() {
      this.methodParams = new LinkedHashMap<>();
      this.methodLocalVars = new LinkedHashMap<>();
   }

   public LinkedHashMap<String, String> getMethodLocalVars()
   {
      return methodLocalVars;
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

      mm.haveClass(className);
      this.object2ClassMap.put(objectName, className);

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
      String attrName = ctx.attrName.getText();
      ArrayList<String> valueList = new ArrayList<>();
      String value = ctx.value.getText();
      valueList.add(value);
      this.currentAttrValueMap.put(attrName, valueList);
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
      this.methodLocalVars = new LinkedHashMap<>();
      this.objectName = StrUtil.downFirstChar(ctx.objectName.getText());
      this.methodParams.put("this", this.objectName);
      this.methodLocalVars.put("this", this.objectName);

      this.sentenceType = "callSentence";
   }



   @Override
   public void exitCallSentence(FulibScenariosParser.CallSentenceContext ctx)
   {
      references.setLength(0);
      this.sentenceType = null;

      String methodName = ctx.methodName.getText();


      String actualParams = String.join(", ", this.methodParams.values());
      int pos = actualParams.indexOf(',');
      if (pos < 0) {
         actualParams = "";
      }
      else {
         actualParams = actualParams.substring(pos+1).trim();
      }

      // actualParams =

      lastInvocation = String.format("%s.%s(%s);\n", objectName, methodName, actualParams);

      this.methodBody.append(lastInvocation);

      String className = object2ClassMap.get(objectName);
      if (className != null)
      {
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
               paramType = getAttrType(paramValue);
            }
            method.getParams().put(paramName, paramType);
         }
      }
   }



   @Override
   public void enterChainSentence(FulibScenariosParser.ChainSentenceContext ctx)
   {
      // method ....
      String methodName = StrUtil.downFirstChar(ctx.methodName.getText());

      this.currentMethod = this.getModelManager().getMethod(methodName);
      this.loopIntro = null;

      if (ctx.loopIntro != null) {
         if (ctx.loopIntro.children.get(0) instanceof FulibScenariosParser.LoopClauseContext)
         {
            this.loopIntro = (FulibScenariosParser.LoopClauseContext) ctx.loopIntro.children.get(0);
         }
      }
   }

   @Override
   public void exitStopPhrase(FulibScenariosParser.StopPhraseContext ctx)
   {
      appendToCurrentMethodBody("      }\n");
      this.indent = "";
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
      Clazz newClazz = this.getModelManager().haveClass(className);

      String objectId = this.attrValue;

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());


      String attrName = this.currentAttrValueMap.keySet().iterator().next();
      String value = this.currentAttrValueMap.get(attrName).get(0);

      String newObjectName = StrUtil.downFirstChar(value);
      this.object2ClassMap.put(newObjectName, className);

      // create attrValueLists for attrs with lists of values
      boolean thereAreLists = false;
      String firstAttrName = null;
      String indent = "";
      ArrayList<String> firstValueList = null;
      for (String attr : this.currentAttrValueMap.keySet())
      {
         ArrayList<String> valueList = this.currentAttrValueMap.get(attr);

         if (firstAttrName == null)
         {
            firstAttrName = attr;
            firstValueList = valueList;
         }

         if (valueList.size() > 1)
         {
            String attrType = StrUtil.cap(getAttrType(valueList.get(0)));
            String listInit = String.format("      java.util.ArrayList<%s> %sList = new java.util.ArrayList<%s>();\n",
                  attrType, attr, attrType);
            for (String v : valueList)
            {
               String actualParam = String.format("\"%s\"", v);
               if (! attrType.equals(ClassModelBuilder.STRING)) {
                  actualParam = "(double) " + v;
               }
               String oneAdd = String.format("      %sList.add(%s);\n",
                     attr, actualParam);
               listInit += oneAdd;
            }
            listInit += "\n";
            appendToCurrentMethodBody(listInit);
            thereAreLists = true;

         }
      }

      // if there are lists, create object list
      if (thereAreLists)
      {
         String objList = String.format("      java.util.ArrayList<%s> %sList = new java.util.ArrayList<>();\n",
               className, StrUtil.downFirstChar(className));
         appendToCurrentMethodBody((objList));

         // loop through list of object names
         String forStmt = String.format("      for (int i = 0; i < %sList.size(); i++ ) {\n",
               firstAttrName);
         appendToCurrentMethodBody(forStmt);

         newObjectName = StrUtil.downFirstChar(className) + "Tmp";
         indent = "   ";
      }

      // create objects and
      ST st = group.getInstanceOf("objectCreate");
      st.add("className", className);
      st.add("objectName", newObjectName);
      String result = indent + "      " + st.render();

      // set attributes and
      for (String attr : this.currentAttrValueMap.keySet())
      {
         ArrayList<String> valueList = this.currentAttrValueMap.get(attr);
         String oneValue = valueList.get(0);
         String attrType = getAttrType(oneValue);
         if (valueList.size() > 1) {
            oneValue = String.format("%sList.get(i)", attr);
         }

         String text = computeFullSetterCall(className, newClazz, newObjectName, attr, attrType, oneValue);

         result += indent + text;
      }

      result += "\n";

      // add to object list
      if (thereAreLists) {
         String stmt = String.format("         %sList.add(%sTmp);\n" +
                     "      }\n\n",
               StrUtil.downFirstChar(className), StrUtil.downFirstChar(className));
         result += stmt;
      }
      appendToCurrentMethodBody(result);
   }

   private void appendToCurrentMethodBody(String result)
   {
      String currentMethodBody = this.currentMethod.getMethodBody();
      currentMethodBody = (currentMethodBody == null ? "" : currentMethodBody);
      currentMethodBody += result;
      this.currentMethod.setMethodBody(currentMethodBody);
   }


   @Override
   public void exitAsPhrase(FulibScenariosParser.AsPhraseContext ctx)
   {
      LinkedHashMap<String,String> map1 = new LinkedHashMap<>();

      String fromAttrName1 = null;
      if (ctx.fromAttrName1 != null) {
         fromAttrName1 = ctx.fromAttrName1.getText();
         map1.put(FROM_ATTR_NAME, fromAttrName1);
      }

      String fromObjName1 = null;
      if (ctx.fromObjName1 != null) {
         fromObjName1 = StrUtil.downFirstChar(ctx.fromObjName1.getText());
         map1.put(FROM_OBJ_NAME, fromObjName1);
      }

      String firstValue = generateRightHandValue(map1, previousValueDataNameList, previousValueDataTextList);
      firstValue = firstValue.substring(0, firstValue.length()-1);

      LinkedHashMap<String,String> map2 = new LinkedHashMap<>();
      String fromAttrName2 = null;
      if (ctx.fromAttrName2 != null) {
         fromAttrName2 = ctx.fromAttrName2.getText();
         map2.put(FROM_ATTR_NAME, fromAttrName2);
      }

      String cmpOp = "==";
      String text = ctx.cmp.getText();
      if (text.equals("greaterequal")) {
         cmpOp = ">=";
      }

      String fromObjName2 = null;
      if (ctx.fromObjName2 != null) {
         fromObjName2 = StrUtil.downFirstChar(ctx.fromObjName2.getText());
         map2.put(FROM_OBJ_NAME, fromObjName2);
      }

      String secondValue = generateRightHandValue(map2, valueDataNameList, valueDataTextList);
      secondValue = secondValue.substring(0, secondValue.length()-1);

      String result = String.format("      if (%s <= %s) {\n",
            firstValue, secondValue);

      this.indent = "   ";
      this.statementClosing = "      }\n";

      appendToCurrentMethodBody(result);
   }

   @Override
   public void exitVerbPhrase(FulibScenariosParser.VerbPhraseContext ctx)
   {
      LinkedHashMap<String,String> map = new LinkedHashMap<>();

      String fromAttrName = null;
      if (ctx.fromAttrName != null) {
         fromAttrName = ctx.fromAttrName.getText();
         map.put(FROM_ATTR_NAME, fromAttrName);
      }

      String fromObjName = null;
      if (ctx.fromObjName != null) {
         fromObjName = StrUtil.downFirstChar(ctx.fromObjName.getText());
         map.put(FROM_OBJ_NAME, fromObjName);
      }

      String toAttrName = null;
      if (ctx.toAttrName != null) {
         toAttrName = ctx.toAttrName.getText();
         map.put(TO_ATTR_NAME, toAttrName);
      }

      String toObjName = null;
      if (ctx.toObjName != null) {
         toObjName = ctx.toObjName.getText();
         map.put(TO_OBJ_NAME, toObjName);
      }

      // do all the reading.
      String rightHandValue = generateRightHandValue(map, this.valueDataNameList, this.valueDataTextList);

      String fromClassName = map.get(FROM_CLASS_NAME);
      boolean valueClassIsAttrType = map.get(VALUE_CLASS_IS_ATTR_TYPE) != null;
      String valueClassName = map.get(VALUE_CLASS_NAME);
      String entryClassName = null;
      String newVarName = map.get(NEW_VAR_NAME);
      String valueName = map.get(VALUE_NAME);
      String leftHandValue = "?";

      if (toObjName == null)
      {
         // declare local variable
         if (fromClassName != null) {
            Clazz fromClass = mm.haveClass(fromClassName);
            AssocRole fromRole = fromClass.getRole(fromAttrName);

            if (fromRole != null) {
               valueClassName = fromRole.getOther().getClazz().getName();
               if (fromRole.getCardinality() > 1) {
                  entryClassName = valueClassName;
                  valueClassName = String.format("java.util.ArrayList<%s>", valueClassName);
               }
               valueClassIsAttrType = false;
            }
         }

         leftHandValue = String.format("%s %s = ",
               valueClassName, newVarName);
         if (this.getMethodLocalVars().get(newVarName) != null) {
            leftHandValue = String.format("%s = ", newVarName);
         }

         if (ctx.verb.getText().equals("adds")) {
            leftHandValue += newVarName + " + " ;
         }

         if (! valueClassIsAttrType) {
            this.object2ClassMap.put(newVarName, valueClassName);

            if (entryClassName == null) {
               this.object2ClassMap.put(valueName, valueClassName);
            } else {
               this.object2ClassMap.put(newVarName + "_i", entryClassName);

               for (String v : this.valueDataNameList) {
                  this.object2ClassMap.put(v, entryClassName);
               }
            }
         }

         this.getMethodLocalVars().put(newVarName, valueName);
      }

      String result = String.format(this.indent + "      %s%s\n",
            leftHandValue, rightHandValue);

      if (this.loopIntro != null) {
         result += String.format("      int %s_i = 1;\n", newVarName);
         result += String.format("      for ( ; %s_i <= %s.size(); %s_i++) {\n",
               newVarName, newVarName, newVarName);
         this.indent = "   ";
      }

      result += this.statementClosing;
      if ( ! this.statementClosing.equals("")) {
         this.indent = "";
      }
      this.statementClosing = "";

      appendToCurrentMethodBody(result);
      System.out.println();
   }


   private String generateRightHandValue(LinkedHashMap<String, String> map,
                                         ArrayList<String> myValueDataNameList,
                                         ArrayList<String> myValueDataTextList) {
      // value target = value read
      String leftHandSetter = "?";
      String valueName = myValueDataNameList.get(0);
      String rangeStart = null;
      String rangeEnd = null;
      String fromListName = null;
      String index = null;
      int pos = valueName.indexOf(':');

      if (pos > 0) {
         rangeStart = valueName.substring(0, pos);
         rangeEnd = valueName.substring(pos+1);
         valueName = rangeStart;
      }
      else {
         index = indexPart(valueName);

         if (index != null) {
            valueName = valueName.substring(0, valueName.length()-index.length());
            fromListName = valueName;
         }
      }

      map.put(VALUE_NAME, valueName);

      String newVarName = valueName;
      String paramValue = this.methodLocalVars.get(newVarName);
      if (paramValue != null) {
         valueName = paramValue;
      }

      boolean valueClassIsAttrType = false;
      boolean valueClassIsDouble = false;
      String valueClassName = this.object2ClassMap.get(valueName);

      if (valueClassName == null) {
         valueClassName = getAttrType(valueName);
         valueClassIsAttrType = true;
         map.put(VALUE_CLASS_IS_ATTR_TYPE, "true");
         valueClassIsDouble = valueClassName.equals(ClassModelBuilder.DOUBLE);
      }

      if (isList(myValueDataNameList)) {
         valueClassName = String.format("java.util.ArrayList<%s>", StrUtil.cap(valueClassName));
      }

      map.put(VALUE_CLASS_NAME, valueClassName);

      String fromAttrName = map.get(FROM_ATTR_NAME);
      String fromClassName = null;
      if (fromAttrName != null) {
         index = indexPart(fromAttrName);
         fromClassName = this.object2ClassMap.get(fromAttrName);
         newVarName = fromAttrName;

         if (index != null) {
            fromAttrName = fromAttrName.substring(0, fromAttrName.length() - index.length());
            fromListName = fromAttrName;
            fromClassName = this.object2ClassMap.get(fromAttrName + "_i");
         }

      }

      String toAttrName = map.get(TO_ATTR_NAME);
      if (toAttrName != null) {
         newVarName = toAttrName;
      }

      map.put(NEW_VAR_NAME, newVarName);

      String rightHandValue = "\"" + myValueDataTextList.get(0) + "\"" + ";";
      if (valueClassIsDouble) {
         rightHandValue = valueName + ";";
      }

      if (fromAttrName != null) {
         rightHandValue = fromAttrName + ";";
      }

      if (isList(myValueDataNameList)) {
         // assign new ArrayList and than all elements.
         rightHandValue = "new java.util.ArrayList<>();\n";

         String cast = (valueClassIsDouble ? "(double) " : "");
         for (String v : myValueDataTextList) {
            String nextAdd = String.format("      %s.add(%s%s);\n",
                  newVarName, cast, v);

            if (v.indexOf(':') > 0) {
               // iterate
               String[] split = v.split("\\:");

               nextAdd = String.format("      for (double d = %s; d <= %s; d++) {\n" +
                           "         %s.add(d);\n" +
                           "      }\n",
                     split[0], split[1], newVarName);
            }

            rightHandValue += nextAdd;
         }
      }

      String fromObjName = map.get(FROM_OBJ_NAME);
      String fromObjVarName = null;

      if (fromObjName != null) {
         String fromIndex = this.indexPart(fromObjName);
         if (fromIndex == null) {
            fromClassName = this.object2ClassMap.get(fromObjName);
            fromObjVarName = getParamVarName4Value(fromObjName);
            rightHandValue = String.format("%s.get%s();", fromObjVarName, StrUtil.cap(fromAttrName));
         } else {
            String listName = fromObjName.substring(0, fromObjName.length()-fromIndex.length());
            fromObjVarName = listName + "_i";
            fromClassName = this.object2ClassMap.get(fromObjVarName);
            rightHandValue = String.format("%s.get(%s).get%s();",
                  listName, fromObjVarName, StrUtil.cap(fromAttrName));
         }
      }

      if (index != null) {
         rightHandValue = String.format("%s.get(%s-1);", fromListName, index);
      }

      map.put(FROM_CLASS_NAME, fromClassName);

      return rightHandValue;
   }


   private String indexPart(String valueName)
   {
      // Pattern regEx = Pattern.compile(".*(([0-9]+)|(_[a-zA-Z]*))$");
      Pattern regEx = Pattern.compile("(([0-9]+)|(_[a-zA-Z]*))$");
      Matcher matcher = regEx.matcher(valueName);
      boolean found = matcher.find();
      if (found) {
         String group = matcher.group();
         if ( ! group.equals(valueName)) {
            return group;
         }
      }

      return null;
   }

   private boolean isList(ArrayList<String> nameList)
   {
      if (nameList.size() > 1) return true;

      if (nameList.get(0).indexOf(':') > 0) return true;

      return false;
   }


   private String setMethodName(String obj, String attrName, String value)
   {
      String objectType = this.object2ClassMap.get(obj);
      Clazz objectClass = mm.haveClass(objectType);
      String valueType = this.object2ClassMap.get(value);
      if (valueType == null) {
         AssocRole role = objectClass.getRole(attrName);
         valueType = role.getOther().getClazz().getName();
      }

      Clazz valueClass = mm.haveClass(valueType);

      AssocRole role = mm.haveRole(objectClass, attrName, valueClass, 1);

      String prefix = "with";
      if (role.getCardinality() == 1)
      {
         prefix = "set";
      }
      return prefix + StrUtil.cap(attrName);
   }


   private String realValue(String value)
   {
      String ownerId = StrUtil.downFirstChar(value);
      if (this.object2ClassMap.get(ownerId) != null)
      {
         value = ownerId;
      }
      return value;
   }


   @Override
   public void exitAnswerPhrase(FulibScenariosParser.AnswerPhraseContext ctx)
   {
      String oneReturnValue = this.valueDataTextList.get(0);
      String[] returnValueList = oneReturnValue.split(" ");
      String returnValue = ctx.value.getText();
      String returnValueId = StrUtil.downFirstChar(returnValue);

      if (returnValueList.length == 2) {
         returnValue = returnValueList[1];
         returnValueId = StrUtil.downFirstChar(returnValue);
         mm.haveClass(returnValueList[0]);
         this.object2ClassMap.put(returnValueId, returnValueList[0]);
      }

      String resultType = this.object2ClassMap.get(returnValueId);
      if (resultType != null)
      {
         returnValue = returnValueId;
      }

      if (this.currentMethod.getMethodBody() != null) {
         String result = String.format("      return %s;\n", returnValue);
         this.currentMethod.setReturnType(resultType);
         appendToCurrentMethodBody(result);
      }

      String newCallStatement = String.format("%s %s = %s", resultType, returnValue, this.lastInvocation);

      int pos = methodBody.lastIndexOf(this.lastInvocation);
      methodBody.replace(pos, pos + this.lastInvocation.length(), newCallStatement);
   }


   @Override
   public void enterThatPhrase(FulibScenariosParser.ThatPhraseContext ctx)
   {
      this.sentenceType = "thatPhrase";
   }

   @Override
   public void enterNumberHasClause(FulibScenariosParser.NumberHasClauseContext ctx)
   {
      super.enterNumberHasClause(ctx);
   }

   @Override
   public void exitThatPhrase(FulibScenariosParser.ThatPhraseContext ctx)
   {
      this.sentenceType = null;
      String objectName = StrUtil.downFirstChar(ctx.objectName.getText());

      if (ctx.hasPart instanceof FulibScenariosParser.UsualHasClauseContext)
      {
         FulibScenariosParser.UsualHasClauseContext hasCtx = (FulibScenariosParser.UsualHasClauseContext) ctx.hasPart;

         String attrName = hasCtx.attrName.getText();

         String objectType = this.object2ClassMap.get(objectName);
         Clazz objectClass = mm.haveClass(objectType);
         AssocRole role = objectClass.getRole(attrName);
         String checkOp = "hasItem";
         if (role == null || role.getCardinality() == 1)
         {
            checkOp = "equalTo";
         }

         String value = this.valueDataTextList.get(0);
         value = realValue(value);
         if (this.object2ClassMap.get(value) == null)
         {
            if (getAttrType(value).equals(ClassModelBuilder.STRING))
            {
               value = "\"" + value + "\"";
            } else {
               value = "(double) " + value;
            }
         }

         String result = String.format("assertThat(%s.get%s(), %s(%s));\n",
               objectName, StrUtil.cap(attrName), checkOp, value);

         methodBody.append(result);
         references.setLength(0);

         System.out.println();
      }
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
         ArrayList<String> valueList = new ArrayList<>();
         for (int i = 0; i < this.valueDataNameList.size(); i++)
         {
            String value = this.valueDataNameList.get(i);

            if (this.object2ClassMap.get(value) == null)
            {
               valueList.add(this.valueDataTextList.get(i));
            }
            else
            {
               valueList.add(value);
            }
         }
         this.currentAttrValueMap.put(attrName, valueList);

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
            methodLocalVars.put(attrName, objName);
         }
         else
         {
            methodParams.put(attrName, value);
            methodLocalVars.put(attrName, value);
         }
      }
   }



   private String computeFullSetterCall(String className, Clazz newClazz, String newObjectName, String attr, String attrType, String newValue)
   {
      String result = "";
      String newParam = getParamVarName4Value(newValue);

      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());

      if ( ! newObjectName.equals(newValue) && this.object2ClassMap.get(newValue) != null)
      {
         // build role
         String tgtClassName = object2ClassMap.get(newValue);
         Clazz tgtClass = mm.haveClass(tgtClassName);
         AssocRole role = mm.haveRole(newClazz, attr, tgtClass, 1);
         addAttrValueExample(className, attr, newValue);

         String stmt = "      " + newObjectName + ".";

         if (role.getCardinality() == 1)
         {
            stmt += "set";
         }
         else
         {
            stmt += "with";
         }

         stmt += StrUtil.cap(attr);
         stmt += "(" + newParam + ");\n";

         result += stmt;
      }
      else
      {
         mm.haveAttribute(newClazz, attr, attrType);

         String actualParam = newParam;
         if (newParam.equals(newValue)
               && attrType.equals(ClassModelBuilder.STRING)
               && newValue.indexOf(".") < 0)
         {
            actualParam = "\"" + newValue + "\"";
         }
         ST st = group.getInstanceOf("fullSetting");
         st.add("objectName", newObjectName);
         st.add("attrName", StrUtil.cap(attr));
         st.add("attrValue", actualParam);
         String text = st.render();

         if (newValue.indexOf(".") < 0) {
            addAttrValueExample(className, attr, newValue);
         }

         result += text;
      }

      return result;
   }

   private String getAttrType(String newValue)
   {
      String attrType = ClassModelBuilder.STRING;
      try  {
         NumberFormat.getInstance().parse(newValue);
         attrType = ClassModelBuilder.DOUBLE;
      } catch (ParseException e) { }
      return attrType;
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
            addAttrValueExample(srcClassName, attrName, name);
         }

         stmt = stmt.substring(0, stmt.length()-2);

         stmt += ");\n";

         references.append(stmt);
      }
      else
      {
         mm.haveAttribute(clazz, attrName, ClassModelBuilder.STRING);

         st.add("attrName", StrUtil.cap(attrName));
         String value = valueDataTextList.get(0);
         st.add("attrValue", "\"" + value + "\"");
         String result = st.render();

         addAttrValueExample(className, attrName, value);

         settings.append(result);
      }
   }

   private void addAttrValueExample(String className, String attrName, String value)
   {
      String key = className + "." + attrName;
      TreeSet<String> exampleSet = attrValueExamplesMap.get(key);
      if (exampleSet == null)
      {
         exampleSet = new TreeSet<>();
         attrValueExamplesMap.put(key, exampleSet);
      }
      exampleSet.add(value);
   }


   private ArrayList<String> computeInitialization(String objectName, String className)
   {
      STGroupFile group = new STGroupFile("templates/junitTest.stg");
      group.registerRenderer(String.class, new StringRenderer());


      Clazz newClazz = this.getModelManager().haveClass(className);

      ArrayList<String> resultList = new ArrayList<>();

      for (Map.Entry<String, ArrayList<String>> entry : this.currentAttrValueMap.entrySet())
      {
         String key = entry.getKey();
         ArrayList<String> valueList = entry.getValue();
         ArrayList<String> valueIdList = new ArrayList<>();
         for (String v : valueList) {
            valueIdList.add(StrUtil.downFirstChar(v));
         }
         ArrayList<String> paramValueList = new ArrayList<>();
         for (String v : valueList) {
            paramValueList.add(getParamVarName4Value(v));
         }

         for (int i = 0; i < valueList.size(); i++)
         {
            String result = "";
            String valueId = valueIdList.get(i);
            String value = valueList.get(i);
            if ( ! valueId.equals(objectName) && this.object2ClassMap.get(valueId) != null)
            {
               // build role
               String tgtClassName = object2ClassMap.get(valueId);
               Clazz tgtClass = mm.haveClass(tgtClassName);
               AssocRole role = mm.haveRole(newClazz, key, tgtClass, valueDataNameList.size());

               String stmt = "\n         .";

               if (role.getCardinality() == 1)
               {
                  stmt += "set";
               }
               else
               {
                  stmt += "with";
               }

               stmt += StrUtil.cap(key);
               stmt += "(";

               String param = getParamVarName4Value(valueId);
               addAttrValueExample(className, key, valueId);

               stmt += param;
               stmt += ")";

               result += stmt;
            }
            else
            {
               mm.haveAttribute(newClazz, key, ClassModelBuilder.STRING);

               ST st = group.getInstanceOf("setting");
               st.add("attrName", StrUtil.cap(key));
               st.add("attrValue", "\"" + value + "\"");
               String text = st.render();

               addAttrValueExample(className, key, value);

               result += text;
            }
            resultList.add(result);
         }
      }

      return resultList;
   }



   private String getParamVarName4Value(String value)
   {
      String result = value;
      for (Map.Entry<String, String> entry : this.getMethodLocalVars().entrySet())
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
      if (sentenceType == null)
      {
         computeSettings();
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
      String attrName = StrUtil.downFirstChar(getMultiName(ctx.attrName));
      String value = ctx.value.getText();

      mm.haveAttribute(clazz, attrName, ClassModelBuilder.DOUBLE);

      st.add("attrName", StrUtil.cap(attrName));
      st.add("attrValue", value);
      String result = st.render();

      addAttrValueExample(className, attrName, value);

      settings.append(result);
   }



   @Override
   public void enterValueClause(FulibScenariosParser.ValueClauseContext ctx)
   {
      previousValueDataTextList = valueDataTextList;
      previousValueDataNameList = valueDataNameList;
      valueDataTextList = new ArrayList<String>();
      valueDataNameList = new ArrayList<String>();
   }

   @Override
   public void exitRangeClause(FulibScenariosParser.RangeClauseContext ctx)
   {
      // is this a range?
      if (ctx.secondData != null) {
         // combine start and end into one value
         int pos = valueDataNameList.size()-2;
         String first = valueDataNameList.get(pos);
         String second = valueDataNameList.get(pos+1);
         valueDataNameList.set(pos, first + ":" + second);
         valueDataNameList.remove(pos+1);

         first = valueDataTextList.get(pos);
         second = valueDataTextList.get(pos + 1);
         valueDataTextList.set(pos, first + ":" + second);
         valueDataTextList.remove(pos+1);
      }
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
