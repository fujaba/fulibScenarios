parser grammar ScenarioParser;

options { tokenVocab = ScenarioLexer; }

// =============== Scenarios ===============

scenario: header sentence* EOF;
header: H1 SCENARIO scenarioName FULL_STOP;
scenarioName: ~FULL_STOP+;

// --------------- Expressions ---------------

expr: access /* | collection */;

// Primary
primary: number | stringLiteral | it | nameAccess;
primaryExpr: primary /* | primaryCollection */;

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

/*
collection: range | list;
primaryCollection: primaryRange | primaryList;

range: access TO access | access THROUGH access;
primaryRange: primary TO primary | primary THROUGH primary;

list: listElem (sep listElem)+;
listElem: access | range;
primaryList: primaryListElem (sep primaryListElem)+;
primaryListElem: primary | primaryRange;
*/

// Conditional

condExpr: attrCheck /* | condOpExpr */;
attrCheck: access HAS simpleName primary
         | access HAS number name;
/*

condOpExpr: access condOp access;
condOp: eqOp | cmpOp | collOp;

eqOp: IS | IS NOT
    | IS THE SAME AS | IS NOT THE SAME AS;
cmpOp: IS LESS THAN | IS NOT LESS THAN | IS LESS EQUAL
     | IS GREATER THAN | IS GREATER EQUAL | IS NOT GREATER THAN;
collOp: CONTAINS | DOES NOT CONTAIN | IS IN | IS NOT IN;
*/

// --------------- Sentences ---------------

sentence: thereSentence
        | expectSentence
        // | phrase FULL_STOP
        // | isSentence
        // | diagramSentence
        ;

thereSentence: THERE IS descriptor FULL_STOP
| THERE ARE descriptor (sep descriptor)+ FULL_STOP;

expectSentence: WE EXPECT thatClauses FULL_STOP;
thatClauses: thatClause (sep thatClause)*;
thatClause: THAT condExpr;

/*
isSentence: name IS constructor;

diagramSentence: IMG_START name IMG_SEP FILE_NAME IMG_END;
*/

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

// --------------- Clauses ---------------

descriptor: (name COMMA?)? constructor;
constructor: typeClause withClauses?;
typeClause: (A | AN) name CARD?
          | name CARDS;

withClauses: withClause (sep withClause)*;
withClause: WITH simpleName primaryExpr # SimpleWithClause
          | WITH number name # NumberWithClause;
