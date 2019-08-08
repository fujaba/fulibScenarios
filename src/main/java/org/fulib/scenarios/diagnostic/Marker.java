package org.fulib.scenarios.diagnostic;

import org.fulib.scenarios.ast.ScenarioFile;

import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Marker implements Diagnostic<ScenarioFile>, Comparable<Marker>
{
   // =============== Constants ===============

   public static final String BUNDLE_NAME = "org.fulib.scenarios.diagnostic.messages";

   // =============== Fields ===============

   private final Kind         kind;
   private final Position     position;
   private       ScenarioFile source;
   private final String       code;
   private final Object[]     args;

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

   @Override
   public ScenarioFile getSource()
   {
      return this.source;
   }

   public void setSource(ScenarioFile source)
   {
      this.source = source;
   }

   public Position getPositionObject()
   {
      return this.position;
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

   @Override
   public String getMessage(Locale locale)
   {
      return localize(locale, this.code, this.args);
   }

   public void print(PrintWriter out)
   {
      // src/dir/package/name/file_name.md:10:20: error: info...
      out.print(this.source.getGroup().getSourceDir());
      out.print('/');
      out.print(this.source.getGroup().getPackageDir());
      out.print('/');
      out.print(this.source.getName());
      out.print(".md:");
      out.print(this.getLineNumber());
      out.print(':');
      out.print(this.getColumnNumber());
      out.print(": ");
      out.print(this.getKind().name().toLowerCase());
      out.print(": ");
      out.println(this.getLocalizedMessage());
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

      return this.kind == that.kind && this.position.equals(that.position) && Objects.equals(this.source, that.source)
             && this.code.equals(that.code) && Arrays.equals(this.args, that.args);
   }

   @Override
   public int hashCode()
   {
      int result = this.kind.hashCode();
      result = 31 * result + this.position.hashCode();
      result = 31 * result + Objects.hashCode(this.source);
      result = 31 * result + this.code.hashCode();
      result = 31 * result + Arrays.hashCode(this.args);
      return result;
   }
}
