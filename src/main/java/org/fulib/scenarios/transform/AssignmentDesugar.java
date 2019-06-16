package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.HasSentence;
import org.fulib.scenarios.ast.sentence.IsSentence;
import org.fulib.scenarios.ast.sentence.Sentence;

import java.util.Collections;

public enum AssignmentDesugar implements Expr.Visitor<Expr, Sentence>
{
   INSTANCE;

   @Override
   public Sentence visit(Expr expr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(AttributeAccess attributeAccess, Expr par)
   {
      final Expr receiver = attributeAccess.getReceiver();
      final Name name = attributeAccess.getName();
      final NamedExpr namedExpr = NamedExpr.of(name, par);
      return HasSentence.of(receiver, Collections.singletonList(namedExpr));
   }

   @Override
   public Sentence visit(ExampleAccess exampleAccess, Expr par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public Sentence visit(ListAttributeAccess listAttributeAccess, Expr par)
   {
      throw new AssertionError("appears after NameResolver");
   }

   @Override
   public Sentence visit(CallExpr callExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(CreationExpr creationExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(CollectionExpr collectionExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(ListExpr listExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(ConditionalExpr conditionalExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(AttributeCheckExpr attributeCheckExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(ConditionalOperatorExpr conditionalOperatorExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(PrimaryExpr primaryExpr, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(NameAccess nameAccess, Expr par)
   {
      final String name = nameAccess.getName().accept(Namer.INSTANCE, null);
      final VarDecl varDecl = VarDecl.of(name, null, par);
      return IsSentence.of(varDecl);
   }

   @Override
   public Sentence visit(NumberLiteral numberLiteral, Expr par)
   {
      return null;
   }

   @Override
   public Sentence visit(StringLiteral stringLiteral, Expr par)
   {
      return null;
   }
}
