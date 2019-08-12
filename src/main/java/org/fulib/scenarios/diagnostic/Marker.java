package org.fulib.scenarios.diagnostic;

import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.*;

public class Marker implements Diagnostic<String>, Comparable<Marker>
{
   // =============== Constants ===============

   public static final String BUNDLE_NAME = "org.fulib.scenarios.diagnostic.messages";

   // =============== Fields ===============

   private final Kind     kind;
   private final Position position;
   private final String   code;
   private final Object[] args;

   private List<Marker> notes;

   // =============== Constructors ===============

   public Marker(Kind kind, Position position, String code, Object... args)
   {
      this.kind = kind;
      this.position = position;
      this.code = code;
      this.args = args;
   }

   // =============== Static Methods ===============

   private static String localize(String key)
   {
      return localize(Locale.getDefault(), key);
   }

   private static String localize(Locale locale, String key)
   {
      return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
   }

   public static String localize(String key, Object... args)
   {
      return localize(Locale.getDefault(), key, args);
   }

   public static String localize(Locale locale, String key, Object... args)
   {
      return String.format(localize(locale, key), args);
   }

   public static Marker error(Position position, String code, Object... args)
   {
      return new Marker(Kind.ERROR, position, code, args);
   }

   public static Marker warning(Position position, String code, Object... args)
   {
      return new Marker(Kind.WARNING, position, code, args);
   }

   public static Marker note(Position position, String code, Object... args)
   {
      return new Marker(Kind.NOTE, position, code, args);
   }

   // =============== Properties ===============

   @Override
   public Kind getKind()
   {
      return this.kind;
   }

   public Position getPositionObject()
   {
      return this.position;
   }

   @Override
   public String getSource()
   {
      return this.position.getSourceName();
   }

   @Override
   public long getPosition()
   {
      return this.position.getStartOffset();
   }

   @Override
   public long getStartPosition()
   {
      return this.position.getStartOffset();
   }

   @Override
   public long getEndPosition()
   {
      return this.position.getEndOffset();
   }

   @Override
   public long getLineNumber()
   {
      return this.position.getLineNumber();
   }

   @Override
   public long getColumnNumber()
   {
      return this.position.getColumnNumber();
   }

   @Override
   public String getCode()
   {
      return this.code;
   }

   public String getLocalizedMessage()
   {
      return this.getMessage(Locale.getDefault());
   }

   // =============== Methods ===============

   public List<Marker> getNotes()
   {
      return this.notes != null ? this.notes : (this.notes = new ArrayList<>());
   }

   public Marker note(Marker marker)
   {
      this.getNotes().add(marker);
      return this;
   }

   @Override
   public String getMessage(Locale locale)
   {
      return localize(locale, this.code, this.args);
   }

   public void print(PrintWriter out)
   {
      // src/dir/package/name/file_name.md:10:20: error: info... [code]
      // (more info...)

      out.print(this.getSource());
      out.print(':');
      out.print(this.getLineNumber());
      out.print(':');
      out.print(this.getColumnNumber());
      out.print(": ");
      out.print(this.getKind().name().toLowerCase());
      out.print(": ");

      // insert the code after the first line of the message

      final String message = this.getLocalizedMessage();
      final int newlineIndex = message.indexOf('\n');
      if (newlineIndex >= 0)
      {
         out.print(message.substring(0, newlineIndex));
      }
      else
      {
         out.print(message);
      }

      out.print(" [");
      out.print(this.code);
      out.println(']');

      if (newlineIndex >= 0)
      {
         out.println(message.substring(newlineIndex + 1));
      }

      if (this.notes != null)
      {
         for (final Marker note : this.notes)
         {
            note.print(out);
         }
      }
   }

   @Override
   public int compareTo(Marker o)
   {
      return Long.compare(this.getPosition(), o.getPosition());
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (!(o instanceof Marker))
      {
         return false;
      }

      final Marker that = (Marker) o;

      return this.kind == that.kind && this.position.equals(that.position) && this.code.equals(that.code) //
             && Arrays.equals(this.args, that.args) && Objects.equals(this.notes, that.notes);
   }

   @Override
   public int hashCode()
   {
      int result = this.kind.hashCode();
      result = 31 * result + this.position.hashCode();
      result = 31 * result + this.code.hashCode();
      result = 31 * result + Arrays.hashCode(this.args);
      result = 31 * result + Objects.hashCode(this.notes);
      return result;
   }
}
