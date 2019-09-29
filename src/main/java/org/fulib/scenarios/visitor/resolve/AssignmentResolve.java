package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.diagnostic.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssignmentResolve implements Expr.Visitor<Expr, Sentence>
{
   // =============== Fields ===============

   private final Scope scope;

   // =============== Constructors ===============

   public AssignmentResolve(Scope scope)
   {
      this.scope = scope;
   }

   // =============== Methods ===============

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
      final HasSentence hasSentence = HasSentence.of(receiver, Collections.singletonList(namedExpr));
      hasSentence.setPosition(attributeAccess.getPosition());
      return hasSentence;
   }

   @Override
   public Sentence visit(ExampleAccess exampleAccess, Expr par)
   {
      return exampleAccess.getExpr().accept(this, par);
   }

   @Override
   public Sentence visit(ListExpr listExpr, Expr par)
   {
      final List<Expr> targets = listExpr.getElements();
      final int numElements = targets.size();
      final List<Sentence> result = new ArrayList<>(numElements);

      final List<Expr> sources;
      if (par instanceof ListExpr && (sources = ((ListExpr) par).getElements()).size() == numElements)
      {
         // we write 1,2,3 into x,y,z.
         // =>
         // x = 1; y = 2; z = 3;

         for (int i = 0; i < numElements; i++)
         {
            final Expr target = targets.get(i);
            final Expr source = sources.get(i);
            final Sentence part = target.accept(this, source);
            result.add(part);
         }
      }
      else
      {
         // we write 1,2,3 into xs,ys.
         // =>
         // var temp = List(1,2,3)
         // xs = temp
         // ys = temp

         final Position position = par.getPosition();
         final VarDecl temp = VarDecl.of("temp++", null, par);
         temp.setPosition(position);
         final IsSentence isSentence = IsSentence.of(temp);
         isSentence.setPosition(position);
         result.add(isSentence);

         for (final Expr target : targets)
         {
            final ResolvedName name = ResolvedName.of(temp);
            name.setPosition(position);
            final NameAccess source = NameAccess.of(name);
            source.setPosition(position);
            final Sentence part = target.accept(this, source);
            result.add(part);
         }
      }

      return new FlattenSentenceList(result);
   }

   @Override
   public Sentence visit(NameAccess nameAccess, Expr par)
   {
      final String name = nameAccess.getName().getValue();
      final Decl existing = this.scope.resolve(name);
      if (existing instanceof VarDecl)
      {
         final AssignSentence assignSentence = AssignSentence.of((VarDecl) existing, null, par);
         assignSentence.setPosition(nameAccess.getPosition());
         return assignSentence;
      }

      final VarDecl varDecl = VarDecl.of(name, null, par);
      varDecl.setPosition(nameAccess.getPosition());
      final IsSentence isSentence = IsSentence.of(varDecl);
      isSentence.setPosition(varDecl.getPosition());
      return isSentence;
   }
}
