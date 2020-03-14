package org.fulib.scenarios.visitor.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.operator.BinaryOperator;
import org.fulib.scenarios.ast.pattern.Constraint;
import org.fulib.scenarios.ast.pattern.Pattern;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.resolve.SymbolCollector;

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

      // POs and constraints
      for (final Pattern pattern : patterns)
      {
         generatePO(pattern, par);

         final ConstraintGenerator gen = new ConstraintGenerator();
         for (final Constraint constraint : pattern.getConstraints())
         {
            constraint.accept(gen, par);
         }
      }

      this.generateDistinctConstraint(patterns, par);

      par.emitLine("final PatternMatcher matcher = FulibTables.matcher(builder.getPattern());");

      // root POs
      for (final Pattern pattern : patterns)
      {
         par.emitLine("matcher.withRootPatternObjects(" + pattern.getName().getValue() + "PO);");
      }

      this.generateRootObjects(matchSentence, par);

      par.emitLine("matcher.match();");

      for (final Pattern pattern : patterns)
      {
         this.generateResultExtractor(pattern, par);
      }

      par.indentLevel--;
      par.emitLine("}");

      return null;
   }

   static String generatePO(Pattern pattern, CodeGenDTO par)
   {
      final String name = pattern.getName().getValue();
      par.emitLine("final PatternObject " + name + "PO = builder.buildPatternObject(\"" + name + "\");");

      generateInstanceOfConstraint(pattern, par);

      return name;
   }

   private static void generateInstanceOfConstraint(Pattern pattern, CodeGenDTO par)
   {
      final String name = pattern.getName().getValue();
      Type type = pattern.getType();

      if (type instanceof ListType)
      {
         type = ((ListType) type).getElementType();
      }

      if (type != PrimitiveType.OBJECT)
      {
         par.emitLine("builder.buildInstanceOfConstraint(" + name + "PO, " + type.accept(TypeGenerator.INSTANCE, par)
                      + ".class);");
      }
   }

   private void generateDistinctConstraint(List<Pattern> patterns, CodeGenDTO par)
   {
      if (patterns.size() > 1)
      {
         final String commaSeparatedPOs = patterns
            .stream()
            .map(p -> p.getName().getValue() + "PO")
            .collect(Collectors.joining(", "));
         par.emitLine("builder.buildInequalityConstraint(" + commaSeparatedPOs + ");");
      }
   }

   private void generateRootObjects(MatchSentence matchSentence, CodeGenDTO par)
   {
      final String packageName = par.modelManager.getClassModel().getPackageName();
      final ListExpr roots = SymbolCollector.getRoots(matchSentence.getScopeDecls(), par.group.getClasses());
      if (roots != null)
      {
         par.emitIndent();
         par.emit("matcher.withRootObjects(new ReflectorMap(\"" + packageName + "\").discoverObjects(");
         roots.accept(ExprGenerator.NO_LIST, par);
         par.emit("));\n");
      }
      // TODO if roots == null, i.e. there are no root objects, the match will always fail.
   }

   private void generateResultExtractor(Pattern pattern, CodeGenDTO par)
   {
      final String name = pattern.getName().getValue();
      if (pattern.getType() instanceof ListType)
      {
         par.emitLine(name + " = matcher.findAll(" + name + "PO);");
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
      String target = (sourceDir + "/" + packageDir + "/" + fileName).replace('\\', '/');

      final int dotIndex = fileName.lastIndexOf('.');
      if (dotIndex < 0)
      {
         throw new IllegalStateException("invalid file name '" + fileName + "' - missing extension");
      }

      final String extension = fileName.substring(dotIndex).toLowerCase();
      final String toolClass;
      final String toolMethod;
      switch (extension)
      {
      case ".svg":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpSVG";
         break;
      case ".png":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpPng";
         break;
      case ".yaml":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpYaml";
         break;
      case ".html":
         toolClass = "org.fulib.scenarios.MockupTools";
         toolMethod = "MockupTools.htmlTool().dump";
         break;
      case ".txt":
         toolClass = "org.fulib.scenarios.MockupTools";
         toolMethod = "MockupTools.htmlTool().dumpToString";

         break;
      default:
         throw new IllegalStateException("invalid file name '" + fileName + "' - unsupported extension");
      }

      par.addImport(toolClass);

      par.emitIndent();

      // method call
      par.bodyBuilder.append(toolMethod).append('(');
      par.emitStringLiteral(target);
      par.bodyBuilder.append(", ");
      diagramSentence.getObject().accept(ExprGenerator.NO_LIST, par);
      par.bodyBuilder.append(");\n");

      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, CodeGenDTO par)
   {
      par.emitIndent();

      final Expr receiver = hasSentence.getObject();
      final Type receiverType = receiver.getType();

      receiver.accept(ExprGenerator.INSTANCE, par);

      if (receiverType instanceof ListType)
      {
         par.bodyBuilder.append(".forEach(it -> it");
      }

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         ExprGenerator.generateSetterCall(par, attribute);
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
         source.accept(ExprGenerator.NO_LIST, par);
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
         source.accept(ExprGenerator.NO_LIST, par);
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
