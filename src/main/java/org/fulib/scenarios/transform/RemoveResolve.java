package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.TemplateSentence;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;

import java.util.Arrays;

public enum RemoveResolve implements Expr.Visitor<Expr, Sentence>
{
   INSTANCE;

   @Override
   public Sentence visit(Expr expr, Expr par)
   {
      // TODO diagnostic
      throw new IllegalStateException("cannot remove from " + expr.getClass().getEnclosingClass().getSimpleName());
   }

   @Override
   public Sentence visit(NameAccess nameAccess, Expr par)
   {
      final Type type = par.accept(Typer.INSTANCE, null);
      // TODO avoid new ArrayList in removeAll
      final String template = type instanceof ListType ? "<%>.removeAll(<%>);" : "<%>.remove(<%>);";

      return TemplateSentence.of(template, Arrays.asList(nameAccess, par));
   }

   @Override
   public Sentence visit(AttributeAccess attributeAccess, Expr par)
   {
      final String attributeName = attributeAccess.getName().accept(Namer.INSTANCE, null);
      final String template = "<%>.without" + StrUtil.cap(attributeName) + "(<*>);";
      final Expr receiver = attributeAccess.getReceiver();

      return TemplateSentence.of(template, Arrays.asList(receiver, par));
   }
}
