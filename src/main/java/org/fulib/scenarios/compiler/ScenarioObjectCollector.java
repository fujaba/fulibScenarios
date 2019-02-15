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

public class ScenarioObjectCollector extends FulibScenariosBaseListener
{
   public LinkedHashMap<String,String> object2ClassMap = new LinkedHashMap<>();

   private String objectName;
   private String className;

   @Override
   public void enterTitle(FulibScenariosParser.TitleContext ctx)
   {
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
   }

   @Override
   public void exitThereSentence(FulibScenariosParser.ThereSentenceContext ctx)
   {
      object2ClassMap.put(StrUtil.downFirstChar(objectName), className);
   }


   @Override
   public void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx)
   {
      if (objectName == null)
      {
         String result = "";

         for (ParseTree child : ctx.attrValue.children)
         {
            result += StrUtil.cap(child.getText());
         }

         if (result.endsWith(","))
         {
         }

         objectName = result;
      }
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
