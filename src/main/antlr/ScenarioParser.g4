parser grammar ScenarioParser;

options { tokenVocab = ScenarioLexer; }

// =============== Scenarios ===============

file: scenario* EOF;

scenario: header sentence*;
header: H1 SCENARIO scenarioName FULL_STOP;
scenarioName: ~FULL_STOP+;

// --------------- Sentences ---------------

actor: WE | name;

sentence: thereSentence
        | isSentence
        | hasSentence
        | expectSentence
        | diagramSentence
        | createSentence
        | callSentence
        | answerSentence
        // | phrase FULL_STOP
        ;

// Definition

thereSentence: thereClause (sep thereClause)* FULL_STOP;
thereClause: THERE IS simpleDescriptor
           | THERE ARE multiDescriptor;

descriptor: simpleDescriptor | multiDescriptor;
simpleDescriptor: simpleTypeClause name? withClauses?;
multiDescriptor: multiTypeClause (name (sep name)+)? withClauses?;

isSentence: name IS simpleTypeClause withClauses? FULL_STOP;

simpleTypeClause: (A | AN | THE) (simpleName | name CARD);
multiTypeClause: name CARDS | name;

withClauses: withClause (sep withClause)*;
withClause: WITH namedExpr;

namedExpr: simpleName primaryExpr # NamedSimple
         | number name            # NamedNumber;

hasSentence: nameAccess hasClauses FULL_STOP;
hasClauses: hasClause (sep hasClause)*;
hasClause: HAS namedExpr;

createSentence: actor (CREATE | CREATES) simpleDescriptor FULL_STOP
              | actor (CREATE | CREATES) multiDescriptor FULL_STOP;

callSentence: actor (CALL | CALLS) name (ON expr)? withClauses? FULL_STOP;

answerSentence: actor (ANSWER | ANSWERS) WITH expr FULL_STOP;

// Testing

expectSentence: WE EXPECT thatClauses FULL_STOP;
thatClauses: thatClause (sep thatClause)*;
thatClause: THAT condExpr;

diagramSentence: IMG_START expr IMG_SEP fileName=FILE_NAME IMG_END;

// --------------- Phrases ---------------

/*
actor: name | IT;
phrase: createPhrase | callPhrase | writePhrase | answerPhrase;

// Creation

createPhrase: createSV descriptor (sep descriptor)*;
createSV: WE CREATE | actor CREATES;

// Calls

callPhrase: callSV name ON expr withClauses?;
callSV: WE CALL | actor CALLS;

// Write

writePhrase: writeSV named | writeSV source=expr INTO target=expr;
writeSV: name (READS | WRITES) | WE (READ | WRITE);

// Answer

answerPhrase: answerSV WITH expr;
answerSV: WE ANSWER | actor ANSWERS;
*/

// --------------- Expressions ---------------

expr: access | collection;

// Primary
primary: number | stringLiteral | it | nameAccess;
primaryExpr: primary | primaryCollection;

number: NUMBER;
stringLiteral: STRING_LITERAL;
it: IT;

simpleName: THE? WORD;
name: THE? WORD+;

nameAccess: name;

// string: (WORD | NUMBER)+;

// Access

access: primary | attributeAccess | exampleAccess;
namedAccess: nameAccess | attributeAccess;
named: namedAccess | exampleAccess;

attributeAccess: name OF access;
exampleAccess: primaryExpr FROM namedAccess;

// Collections

sep: COMMA | AND | COMMA AND;

collection: list /* | range */;
primaryCollection: primaryList /* | primaryRange */;

list: listElem (sep listElem)+;
listElem: access /* | range */;
primaryList: primaryListElem (sep primaryListElem)+;
primaryListElem: primary /* | primaryRange */;

/*
range: access TO access | access THROUGH access;
primaryRange: primary TO primary | primary THROUGH primary;
*/

// Conditional

condExpr: andCondExpr;

andCondExpr: orCondExpr (AND orCondExpr)*;
orCondExpr: primaryCondExpr (OR primaryCondExpr)*;

primaryCondExpr : attrCheck | condOpExpr;
attrCheck: access HAS namedExpr;

condOpExpr: access condOp access;
condOp: eqOp | cmpOp /* | collOp */;

eqOp: IS | IS NOT
    | IS THE SAME AS | IS NOT THE SAME AS;
cmpOp: IS LESS THAN | IS NOT LESS THAN | IS LESS EQUAL
     | IS GREATER THAN | IS GREATER EQUAL | IS NOT GREATER THAN;
// collOp: CONTAINS | DOES NOT CONTAIN | IS IN | IS NOT IN;
