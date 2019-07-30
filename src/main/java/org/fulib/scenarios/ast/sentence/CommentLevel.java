package org.fulib.scenarios.ast.sentence;

public enum CommentLevel
{
   GENERATED("/////////////// %s ///////////////"),
   MEMBERS("// =============== %s ==============="),
   CATEGORIES("// --------------- %s ---------------"),
   CODE_SECTION("// --- %s ---"),
   REGULAR("// %s"),
   ;

   // =============== Fields ===============

   private final String format;

   // =============== Constructors ===============

   CommentLevel(String format)
   {
      this.format = format;
   }

   // =============== Properties ===============

   public String getFormat()
   {
      return this.format;
   }

   public String format(String text)
   {
      return String.format(this.format, text);
   }
}
