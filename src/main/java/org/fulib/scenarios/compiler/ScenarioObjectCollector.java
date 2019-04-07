package org.fulib.scenarios.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.Fulib;
import org.fulib.StrUtil;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScenarioObjectCollector extends FulibScenariosBaseListener
{
   public LinkedHashMap<String,String> object2ClassMap = new LinkedHashMap<>();

   private String objectName;
   private String className;
   private ArrayList<String> objectNamesList;

   @Override
   public void enterDirectSentence(FulibScenariosParser.DirectSentenceContext ctx)
   {
      objectName = StrUtil.downFirstChar(getMultiName(ctx.objectName));
      className = StrUtil.cap(getMultiName(ctx.className));
      object2ClassMap.put(objectName, className);
   }

   @Override
   public void enterThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
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
   }

   @Override
   public void enterHasSentence(FulibScenariosParser.HasSentenceContext ctx)
   {
      objectName = null;
      if (ctx.objectName != null)
      {
         objectName = StrUtil.downFirstChar(getMultiName(ctx.objectName));
      }
   }

   @Override
   public void exitThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      object2ClassMap.put(objectName, className);
   }


   @Override
   public void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      objectNamesList = new ArrayList<String>();
   }


   @Override
   public void enterUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx)
   {
      objectNamesList = new ArrayList<String>();
   }

   @Override
   public void exitUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      if (objectName == null)
      {
         objectName = StrUtil.downFirstChar(objectNamesList.get(0));
      }
   }

   @Override
   public void exitUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx)
   {
      if (objectName == null)
      {
         objectName = StrUtil.downFirstChar(objectNamesList.get(0));
      }
   }

   @Override
   public void enterValueClause(FulibScenariosParser.ValueClauseContext ctx)
   {
      // String multiValueName = getMultiValue(ctx);
      // objectNamesList.add(multiValueName);
   }

   @Override
   public void enterValueData(FulibScenariosParser.ValueDataContext ctx)
   {
      String result = "";

      for (ParseTree child : ctx.children)
      {
         result += StrUtil.cap(child.getText());
      }

      result = StrUtil.downFirstChar(result);
      objectNamesList.add(result);
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
