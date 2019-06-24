package org.fulib.scenarios.ast.sentence;

import java.util.List;

public class FlattenSentenceList extends SentenceList.Impl
{
   public FlattenSentenceList(List<Sentence> items)
   {
      super(items);
   }

   public static void add(List<Sentence> newItems, Sentence result)
   {
      if (result instanceof FlattenSentenceList)
      {
         newItems.addAll(((FlattenSentenceList) result).getItems());
      }
      else
      {
         newItems.add(result);
      }
   }
}
