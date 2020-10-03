parser grammar ScenarioParser;

options { tokenVocab = ScenarioLexer; }

// =============== Scenarios ===============

file: scenario* EOF;

scenario: header sentence*;
header: H1 HEADLINE_TEXT HEADLINE_END;

// --------------- Sentences ---------------

actor: WE | THE? name;

sentence: simpleSentences FULL_STOP
        | compoundSentence FULL_STOP
        | diagramSentence
        | sectionSentence
        | commentSentence
        ;

simpleSentence: thereSentence
              | isSentence
              | areSentence
              | hasSentence
              | expectSentence
              | matchSentence
              | createSentence
              | callSentence
              | answerSentence
              | writeSentence
              | addSentence
              | removeSentence
              ;

simpleSentences: simpleSentence (sep simpleSentence)* (sep compoundSentence)?;

compoundSentence: conditionalSentence
                | takeSentence
                ;

sectionSentence: H2 HEADLINE_TEXT HEADLINE_END;
commentSentence: LINE_COMMENT HEADLINE_TEXT HEADLINE_END;

// Definition

thereSentence: THERE IS simpleDescriptor
             | THERE ARE multiDescriptor;

simpleDescriptor: (A | AN) typeName name? withClauses? // indefinite form
                | THE typeName name withClauses? // definite form
                ;

multiDescriptor: typesName (name (sep name)+)? withClauses? // indefinite form
               | THE typesName name (sep name)+ withClauses? // definite form
               ;

typeName: simpleName | name CARD;
typesName: simpleName | name CARDS;

isSentence: EVERY typeName IS (A | AN) typeName # InheritanceIsSentence
          | THE? name IS (A | AN) typeName withClauses? # SimpleIsSentence
          ;
areSentence: name (sep name)+ ARE typesName withClauses?;

withClauses: withClause (sep withClause)*;
withClause: WITH namedExpr;

namedExpr: THE? simpleName expr # NamedSimple
         | number name          # NamedNumber
         ;
bidiNamedExpr: firstName=simpleName AND (IS | ARE) (ONE OF)? THE? otherName=simpleName OF (expr | aPlaceholder | manyPlaceholder);

placeholderNamedExpr: (A | AN) name (likePlaceholder | ofTypePlaceholder);

aPlaceholder: (A | AN) typeName (LIKE expr)?;
manyPlaceholder: MANY typesName (LIKE expr)?;
likePlaceholder: LIKE expr;
ofTypePlaceholder: OF TYPE typeName (LIKE expr)?;
everyPlaceholder: EVERY typeName (LIKE nameAccess)?;

hasSentence: (everyPlaceholder | nameAccess) hasClauses;
hasClauses: hasClause (sep hasClause)*;
hasClause: verb=(HAS | HAVE) (namedExpr | bidiNamedExpr | placeholderNamedExpr);

createSentence: actor verb=(CREATE | CREATES) (simpleDescriptor | multiDescriptor);

callSentence: actor verb=(CALL | CALLS) name (ON expr)? withClauses?;

answerSentence: actor verb=(ANSWER | ANSWERS) WITH expr (INTO THE? name)?;

writeSentence: actor verb=(WRITE | WRITES) expr INTO expr;
addSentence: actor verb=(ADD | ADDS) expr TO expr;
removeSentence: actor verb=(REMOVE | REMOVES) expr FROM expr;

// Compound sentences, i.e. those that end in other sentences.
// "Dangling Else" is solved by allowing exactly one "nested" compound sentence at the end,
// or one or more non-compound (simple) sentences.
// So "compound, compound, simple, simple" becomes "compound { compound { simple; simple } },
// not "compound { compound { simple }; simple }

conditionalSentence: AS condExpr COMMA compoundSentenceBody;

takeSentence: actor verb=(TAKE | TAKES) ((A | AN) name (LIKE example=expr)? | (THE? simpleVarName=simpleName)? example=expr)
              FROM source=expr COMMA? AND compoundSentenceBody;

compoundSentenceBody: compoundSentence | simpleSentences;

// Testing

expectSentence: WE EXPECT thatClauses;
thatClauses: thatClause (sep thatClause)*;
thatClause: THAT condExpr;

diagramSentence: IMG_START expr IMG_SEP fileName=FILE_NAME IMG_END;

// Matching

matchSentence: actor verb=(MATCH | MATCHES) (ON expr)? patternObjects;
patternObjects: patternObject (sep patternObject)*
              | COLON ((BULLET | NUMBERED) patternObject)+;

// --------------- Patterns & Constraints ---------------

patternObject: (SOME typeName | ALL typesName) name constraints?;
constraints: constraint (sep constraint)*;
constraint: linkConstraint
             | attributeEqualityConstraint
             | attributeConditionalConstraint
             // | attributePredicateConstraint
             | matchConstraint
             ;

linkConstraint: WITH SOME LINK TO name;
attributeEqualityConstraint: WITH (name expr | number name);
attributeConditionalConstraint: (WHERE SOME ATTRIBUTE | WHOSE name) condOp rhs=expr;
attributePredicateConstraint: (WHERE SOME ATTRIBUTE | WHOSE name) predOp;
matchConstraint: WHERE condExpr;

// --------------- Expressions ---------------

expr: access | collection;

// Primary
primary: number | stringLiteral | it | answer | nameAccess;
primaryExpr: primary;

number: DECIMAL | INTEGER;
stringLiteral: STRING_LITERAL;
it: IT;
answer: THE? ANSWER;

simpleName: identifier;
name: identifier+;

identifier: WORD
          // new keywords for pattern matching since v1.1
          | ATTRIBUTE
          | LINK
          | MATCH
          | MATCHES
          | SOME
          | WHERE
          | WHOSE
          // new keywords for placeholders since v1.4
          | EVERY
          | TYPE
          ;

nameAccess: THE? name;

// string: (WORD | NUMBER)+;

// Access

access: primary | attributeAccess | exampleAccess | filterExpr;
namedAccess: nameAccess | attributeAccess;
named: namedAccess | exampleAccess;

attributeAccess: THE? name OF access;
exampleAccess: primaryExpr FROM namedAccess;

filterExpr: ALL expr WHICH condExpr;

// Collections

sep: COMMA | AND | COMMA AND;

collection: list | range;

list: listElem (sep listElem)+;
listElem: access | range;

range: access TO access;

// Conditional

condExpr: andCondExpr;

andCondExpr: orCondExpr (AND orCondExpr)*;
orCondExpr: primaryCondExpr (OR primaryCondExpr)*;

primaryCondExpr : attrCheck | condOpExpr | predOpExpr;
attrCheck: access? (HAS | HAVE) namedExpr;

condOpExpr: lhs=access? condOp rhs=access;
condOp: eqOp | cmpOp | collOp | strOp;

eqOp: IS | ARE | (IS | ARE) NOT
    | (IS | ARE) THE SAME AS | (IS | ARE) NOT THE SAME AS;
cmpOp: (IS | ARE) LESS THAN | (IS | ARE) NOT LESS THAN | (IS | ARE) LESS EQUAL
     | (IS | ARE) GREATER THAN | (IS | ARE) GREATER EQUAL | (IS | ARE) NOT GREATER THAN;
collOp: CONTAIN | CONTAINS | (DO | DOES) NOT CONTAIN /* | (IS | ARE) IN | (IS | ARE) NOT IN */;
strOp: MATCH | MATCHES;

predOpExpr: lhs=access? predOp;

predOp: (IS | ARE) EMPTY | (IS | ARE) NOT EMPTY;
