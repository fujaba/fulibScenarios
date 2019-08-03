package org.fulib.scenarios.diagnostic;

import org.fulib.scenarios.ast.ScenarioFile;

import javax.tools.Diagnostic;
import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;

public class Marker implements Diagnostic<ScenarioFile>
{
   // =============== Constants ===============

   public static final String BUNDLE_NAME = "org.fulib.scenarios.diagnostic.messages";

   // =============== Fields ===============

   private final Kind kind;
   private final Position position;
   private final ScenarioFile source;
   private final String code;
   private final Object[] args;

   // =============== Constructors ===============

   public Marker(Kind kind, Position position, ScenarioFile source, String code, Object... args)
   {
      this.kind = kind;
      this.position = position;
      this.source = source;
      this.code = code;
      this.args = args;
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

   @Override
   public String getMessage(Locale locale)
   {
      final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
      final String localizedCode = bundle.getString(this.code);
      return String.format(localizedCode, this.args);
   }

   public void print(PrintStream out)
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
}
