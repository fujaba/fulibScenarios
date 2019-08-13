parser grammar ScenarioParser;

options { tokenVocab = ScenarioLexer; }

// =============== Scenarios ===============

file: scenario* EOF;

scenario: header sentence*;
header: H1 HEADLINE_TEXT HEADLINE_END;

// --------------- Sentences ---------------

actor: WE | THE? name;

sentence: simpleSentence FULL_STOP
        | diagramSentence
        | sectionSentence
        | commentSentence
        ;

simpleSentence: thereSentence
              | isSentence
              | hasSentence
              | expectSentence
              | createSentence
              | callSentence
              | answerSentence
              | writeSentence
              | addSentence
              | removeSentence
              | conditionalSentence
              | takeSentence
              ;

sectionSentence: H2 HEADLINE_TEXT HEADLINE_END;
commentSentence: LINE_COMMENT HEADLINE_TEXT HEADLINE_END;

// Definition

thereSentence: thereClause (sep thereClause)*;
thereClause: THERE IS simpleDescriptor
           | THERE ARE multiDescriptor;

simpleDescriptor: aTypeClause withClauses? // indefinite form
                | theTypeClause name withClauses? // definite form
                ;

multiDescriptor: aTypesClause withClauses? // indefinite form
               | theTypesClause name (sep name)+ withClauses? // definite form
               ;

aTypeClause: (A | AN) name CARD?;
aTypesClause: name CARDS?;
theTypeClause: THE (simpleName | name CARD);
theTypesClause: THE (simpleName | name CARDS);

isSentence: THE? name IS aTypeClause withClauses?;

withClauses: withClause (sep withClause)*;
withClause: WITH namedExpr;

namedExpr: THE? simpleName expr # NamedSimple
         | number name          # NamedNumber;
bidiNamedExpr: firstName=simpleName AND (IS | ARE) (ONE OF)? THE? otherName=simpleName OF expr;

hasSentence: nameAccess hasClauses;
hasClauses: hasClause (sep hasClause)*;
hasClause: (HAS | HAVE) (namedExpr | bidiNamedExpr);

createSentence: actor (CREATE | CREATES) (simpleDescriptor | multiDescriptor);

callSentence: actor (CALL | CALLS) name (ON expr)? withClauses?;

answerSentence: actor (ANSWER | ANSWERS) WITH expr (INTO THE? name)?;

writeSentence: actor (WRITE | WRITES) expr INTO expr;
addSentence: actor (ADD | ADDS) expr TO expr;
removeSentence: actor (REMOVE | REMOVES) expr FROM expr;

// Control Flow

conditionalSentence: AS condExpr COMMA simpleSentence (sep simpleSentence)*;

takeSentence: actor (TAKE | TAKES) ((A | AN) name (LIKE example=expr)? | (THE? simpleVarName=simpleName)? example=expr)
              FROM source=expr AND simpleSentence (sep simpleSentence)*;

// Testing

expectSentence: WE EXPECT thatClauses;
thatClauses: thatClause (sep thatClause)*;
thatClause: THAT condExpr;

diagramSentence: IMG_START expr IMG_SEP fileName=FILE_NAME IMG_END;

// --------------- Expressions ---------------

expr: access | collection;

// Primary
primary: number | stringLiteral | it | nameAccess;
primaryExpr: primary;

number: DECIMAL | INTEGER;
stringLiteral: STRING_LITERAL;
it: IT;

simpleName: WORD;
name: WORD+;

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
condOp: eqOp | cmpOp | collOp;

eqOp: IS | ARE | (IS | ARE) NOT
    | (IS | ARE) THE SAME AS | (IS | ARE) NOT THE SAME AS;
cmpOp: (IS | ARE) LESS THAN | (IS | ARE) NOT LESS THAN | (IS | ARE) LESS EQUAL
     | (IS | ARE) GREATER THAN | (IS | ARE) GREATER EQUAL | (IS | ARE) NOT GREATER THAN;
collOp: CONTAIN | CONTAINS | (DO | DOES) NOT CONTAIN /* | (IS | ARE) IN | (IS | ARE) NOT IN */;

predOpExpr: lhs=access? predOp;

predOp: (IS | ARE) EMPTY | (IS | ARE) NOT EMPTY;
