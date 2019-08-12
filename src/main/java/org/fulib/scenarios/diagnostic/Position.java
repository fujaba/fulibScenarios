package org.fulib.scenarios.diagnostic;

public class Position
{
   // =============== Fields ===============

   private final String sourceName;
   private final long   startOffset;
   private final long   endOffset;
   private final long   lineNumber;
   private final long   columnNumber;

   // =============== Constructors ===============

   public Position(String sourceName, long startOffset, long endOffset, long lineNumber, long columnNumber)
   {
      this.sourceName = sourceName;
      this.startOffset = startOffset;
      this.endOffset = endOffset;
      this.lineNumber = lineNumber;
      this.columnNumber = columnNumber;
   }

   // =============== Properties ===============

   public String getSourceName()
   {
      return this.sourceName;
   }

   public long getStartOffset()
   {
      return this.startOffset;
   }

   public long getEndOffset()
   {
      return this.endOffset;
   }

   public long getLineNumber()
   {
      return this.lineNumber;
   }

   public long getColumnNumber()
   {
      return this.columnNumber;
   }
}
