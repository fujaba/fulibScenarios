package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.HasSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.TemplateSentence;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;

import java.util.Arrays;
import java.util.Collections;

public enum AddResolve implements Expr.Visitor<Expr, Sentence>
{
   INSTANCE;

   @Override
   public Sentence visit(NameAccess nameAccess, Expr par)
   {
      final Type type = par.accept(Typer.INSTANCE, null);
      // TODO avoid new ArrayList in addAll
      final String template = type instanceof ListType ? "<%>.addAll(<%>);" : "<%>.add(<%>);";

      return TemplateSentence.of(template, Arrays.asList(nameAccess, par));
   }

   @Override
   public Sentence visit(AttributeAccess attributeAccess, Expr par)
   {
      final Type type = par.accept(Typer.INSTANCE, null);
      final Expr receiver = attributeAccess.getReceiver();
      final Name name = attributeAccess.getName();
      //noinspection ArraysAsListWithZeroOrOneArgument - singletonList is not modifiable
      final Expr source = type instanceof ListType ? par : ListExpr.of(Arrays.asList(par));

      return HasSentence.of(receiver, Collections.singletonList(NamedExpr.of(name, source)));
   }
}
