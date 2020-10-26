package org.fulib.scenarios.visitor.codegen;

import org.apache.commons.text.StringEscapeUtils;
import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.PlaceholderExpr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.operator.BinaryOperator;
import org.fulib.scenarios.ast.pattern.Constraint;
import org.fulib.scenarios.ast.pattern.Pattern;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public enum SentenceGenerator implements Sentence.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   @Override
   public Object visit(Sentence sentence, CodeGenDTO par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, CodeGenDTO par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(this, par);
      }
      return null;
   }

   @Override
   public Object visit(SectionSentence sectionSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append(sectionSentence.getLevel().format(sectionSentence.getText().trim()));
      par.bodyBuilder.append('\n');
      return null;
   }

   @Override
   public Object visit(ExpectSentence expectSentence, CodeGenDTO par)
   {
      for (final Expr expr : expectSentence.getPredicates())
      {
         par.emitIndent();
         expr.accept(AssertionGenerator.INSTANCE, par);
         par.bodyBuilder.append(";\n");
      }
      return null;
   }

   @Override
   public Object visit(MatchSentence matchSentence, CodeGenDTO par)
   {
      par.addImport("org.fulib.FulibTables");
      par.addImport("org.fulib.patterns.PatternMatcher");
      par.addImport("org.fulib.patterns.PatternBuilder");
      par.addImport("org.fulib.patterns.model.PatternObject");
      par.addImport("org.fulib.yaml.ReflectorMap");

      // variable declarations
      final List<Pattern> patterns = matchSentence.getPatterns();
      for (final Pattern pattern : patterns)
      {
         final Decl decl = pattern.getName().getDecl();
         par.emitLine("final " + decl.getType().accept(TypeGenerator.INSTANCE, par) + " " + decl.getName() + ";");
      }

      par.emitLine("{");
      par.indentLevel++;
      par.emitLine("final PatternBuilder builder = FulibTables.patternBuilder();");

      for (final Pattern pattern : patterns)
      {
         generatePO(pattern, par);
      }

      // generate constraints after all POs to allow cross references
      for (final Pattern pattern : patterns)
      {
         final ConstraintGenerator gen = new ConstraintGenerator();
         for (final Constraint constraint : pattern.getConstraints())
         {
            constraint.accept(gen, par);
         }
      }

      final String commaSeparatedPOs = patterns
         .stream()
         .map(p -> p.getName().getValue() + "PO")
         .collect(Collectors.joining(", "));

      if (patterns.size() > 1)
      {
         par.emitLine("builder.buildDistinctConstraint(" + commaSeparatedPOs + ");");
      }

      par.emitLine("final PatternMatcher matcher = FulibTables.matcher(builder.getPattern());");

      par.emitLine("matcher.withRootPatternObjects(" + commaSeparatedPOs + ");");

      this.generateRootObjects(matchSentence, par);

      // par.emitLine("matcher.setDebugLogging(true);");
      par.emitLine("matcher.match();");
      // par.emitLine("matcher.getDebugEvents().forEach(System.out::println);");

      for (final Pattern pattern : patterns)
      {
         this.generateResultExtractor(pattern, par);
      }

      par.indentLevel--;
      par.emitLine("}");

      return null;
   }

   static void generatePO(Pattern pattern, CodeGenDTO par)
   {
      final String name = pattern.getName().getValue();
      Type type = pattern.getType();

      if (type instanceof ListType)
      {
         type = ((ListType) type).getElementType();
      }

      String typeParam;
      if (type != PrimitiveType.OBJECT)
      {
         type = PrimitiveType.primitiveToWrapper(type);
         typeParam = ", " + type.accept(TypeGenerator.INSTANCE, par) + ".class";
      }
      else
      {
         typeParam = "";
      }

      par.emitLine(
         String.format("final PatternObject %sPO = builder.buildPatternObject(\"%s\"%s);", name, name, typeParam));
   }

   private void generateRootObjects(MatchSentence matchSentence, CodeGenDTO par)
   {
      final Expr roots = matchSentence.getRoots();
      if (roots == null)
      {
         return;
      }

      final String packageName = par.modelManager.getClassModel().getPackageName();
      par.emitIndent();
      par.emit("matcher.withRootObjects(new ReflectorMap(\"" + packageName + "\").discoverObjects(");
      roots.accept(ExprGenerator.FLAT, par);
      par.emit("));\n");
   }

   private void generateResultExtractor(Pattern pattern, CodeGenDTO par)
   {
      final String name = pattern.getName().getValue();
      if (pattern.getType() instanceof ListType)
      {
         par.addImport("java.util.ArrayList");

         par.emitLine(name + " = new ArrayList<>(matcher.findAll(" + name + "PO));");
      }
      else
      {
         par.emitLine(name + " = matcher.findOne(" + name + "PO);");
      }
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, CodeGenDTO par)
   {
      final String sourceDir = par.group.getSourceDir();
      final String packageDir = par.group.getPackageDir();
      final String fileName = diagramSentence.getFileName();
      final String target = (sourceDir + "/" + packageDir + "/" + fileName).replace('\\', '/');

      final String diagramHandler = par.config.getDiagramHandlerFromFile(fileName);
      final String targetLiteral = '"' + StringEscapeUtils.escapeJava(target) + '"';

      final StringBuilder oldBuilder = par.bodyBuilder;
      final String objectExpr;
      par.bodyBuilder = new StringBuilder();
      try
      {
         diagramSentence.getObject().accept(ExprGenerator.FLAT, par);
         objectExpr = par.bodyBuilder.toString();
      }
      finally
      {
         par.bodyBuilder = oldBuilder;
      }

      if (diagramHandler != null)
      {
         par.emitIndent();
         par.bodyBuilder.append(String.format(diagramHandler, targetLiteral, objectExpr)).append(";\n");
      }

      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, CodeGenDTO par)
   {
      final Expr receiver = hasSentence.getObject();
      if (receiver instanceof PlaceholderExpr || hasSentence
         .getClauses()
         .stream()
         .allMatch(c -> c.getExpr() instanceof PlaceholderExpr))
      {
         return null;
      }

      par.emitIndent();
      receiver.accept(ExprGenerator.INSTANCE, par);

      final Type receiverType = receiver.getType();
      if (receiverType instanceof ListType)
      {
         par.bodyBuilder.append(".forEach(it -> it");
      }

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         if (!(attribute.getExpr() instanceof PlaceholderExpr))
         {
            ExprGenerator.generateSetterCall(par, attribute);
         }
      }

      if (receiverType instanceof ListType)
      {
         par.bodyBuilder.append(")");
      }

      par.bodyBuilder.append(";\n");

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, CodeGenDTO par)
   {
      isSentence.getDescriptor().accept(DeclGenerator.INSTANCE, par);
      return null;
   }

   @Override
   public Object visit(AnswerSentence answerSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("return ");
      answerSentence.getResult().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");

      return null;
   }

   @Override
   public Object visit(AddSentence addSentence, CodeGenDTO par)
   {
      par.emitIndent();

      final Expr target = addSentence.getTarget();
      final Expr source = addSentence.getSource();

      if (target instanceof AttributeAccess)
      {
         final AttributeAccess attributeAccess = (AttributeAccess) target;

         attributeAccess.getReceiver().accept(ExprGenerator.INSTANCE, par);
         par.bodyBuilder.append(".with").append(StrUtil.cap(attributeAccess.getName().getValue())).append('(');
         source.accept(ExprGenerator.WITHER, par);
         par.bodyBuilder.append(");\n");
         return null;
      }

      target.accept(ExprGenerator.INSTANCE, par);

      if (source.getType() instanceof ListType)
      {
         par.bodyBuilder.append(".addAll(");
      }
      else
      {
         par.bodyBuilder.append(".add(");
      }

      source.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(");\n");

      return null;
   }

   @Override
   public Object visit(RemoveSentence removeSentence, CodeGenDTO par)
   {
      par.emitIndent();

      final Expr target = removeSentence.getTarget();
      final Expr source = removeSentence.getSource();

      if (target instanceof AttributeAccess)
      {
         final AttributeAccess attributeAccess = (AttributeAccess) target;

         attributeAccess.getReceiver().accept(ExprGenerator.INSTANCE, par);
         par.bodyBuilder.append(".without").append(StrUtil.cap(attributeAccess.getName().getValue())).append('(');
         source.accept(ExprGenerator.WITHER, par);
         par.bodyBuilder.append(");\n");
         return null;
      }

      target.accept(ExprGenerator.INSTANCE, par);

      if (source.getType() instanceof ListType)
      {
         par.bodyBuilder.append(".removeAll(");
         source.accept(ExprGenerator.INSTANCE, par);
         par.bodyBuilder.append(");\n");
         return null;
      }

      par.addImport("java.util.Collections");
      par.bodyBuilder.append(".removeAll(Collections.singletonList(");
      source.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append("));\n");
      return null;
   }

   @Override
   public Object visit(TakeSentence takeSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("for (final ");

      final Type elementType = takeSentence.getVarName().getDecl().getType();
      final String varName = takeSentence.getVarName().getValue();

      par.bodyBuilder.append(elementType.accept(TypeGenerator.INSTANCE, par));
      par.bodyBuilder.append(' ');
      par.bodyBuilder.append(varName);
      par.bodyBuilder.append(" : ");

      takeSentence.getCollection().accept(ExprGenerator.INSTANCE, par);

      par.bodyBuilder.append(") {\n");

      par.indentLevel++;
      takeSentence.getBody().accept(this, par);
      par.indentLevel--;

      par.emitIndent();
      par.bodyBuilder.append("}\n");

      return null;
   }

   @Override
   public Object visit(ConditionalSentence conditionalSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("if (");
      conditionalSentence.getCondition().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(") {\n");

      par.indentLevel++;
      conditionalSentence.getBody().accept(this, par);
      par.indentLevel--;

      par.emitIndent();
      par.bodyBuilder.append("}\n");

      return null;
   }

   @Override
   public Object visit(AssignSentence assignSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append(assignSentence.getTarget().getName());
      par.bodyBuilder.append(' ');

      final BinaryOperator operator = assignSentence.getOperator();
      if (operator != null)
      {
         par.bodyBuilder.append(operator.getSymbol());
      }

      par.bodyBuilder.append("= ");
      assignSentence.getValue().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, CodeGenDTO par)
   {
      par.emitIndent();
      exprSentence.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }
}
