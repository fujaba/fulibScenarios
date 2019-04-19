grammar Scenario;

@header {
package org.fulib.scenarios.compiler;
}

scenario: header sentence*;
header: H1 SCENARIO ~FULL_STOP+ FULL_STOP;

// =============== Parser ===============

// --------------- Expressions ---------------

expr: access | collection;

// Primary
primary: NUMBER | STRING_LITERAL | IT | nameAccess;
primaryExpr: primary | primaryCollection;

simpleName: THE? WORD;
multiName: THE? WORD+;
name: multiName;

nameAccess: multiName;

string: (WORD | NUMBER)+;

// Access

access: primary | attributeAccess | exampleAccess;
namedAccess: nameAccess | attributeAccess;
named: namedAccess | exampleAccess;

attributeAccess: name OF access;
exampleAccess: primaryExpr FROM namedAccess;

// Collections

collection: range | list;
primaryCollection: primaryRange | primaryList;

range: access TO access | access THROUGH access;
primaryRange: primary TO primary | primary THROUGH primary;

sep: COMMA | AND | COMMA AND;
list: listElem (sep listElem)+;
listElem: access | range;
primaryList: primaryListElem (sep primaryListElem)+;
primaryListElem: primary | primaryRange;

// Conditional

condExpr: attrCheck | condOpExpr;
attrCheck: access HAS simpleName primary
         | access HAS NUMBER multiName;

condOpExpr: access condOp access;
condOp: eqOp | cmpOp | collOp;

eqOp: IS | IS NOT
    | IS THE SAME AS | IS NOT THE SAME AS;
cmpOp: IS LESS THAN | IS NOT LESS THAN | IS LESS EQUAL
     | IS GREATER THAN | IS GREATER EQUAL | IS NOT GREATER THAN;
collOp: CONTAINS | DOES NOT CONTAIN | IS IN | IS NOT IN;

// --------------- Sentences ---------------

sentence: phrase FULL_STOP | thereSentence | isSentence | expectSentence | diagramSentence;

thereSentence: THERE IS descriptor FULL_STOP
| THERE ARE descriptor (sep descriptor)+ FULL_STOP;

isSentence: multiName IS constructor;

expectSentence: WE EXPECT thatClauses;
thatClauses: thatClause (sep thatClause)*;
thatClause: THAT condExpr;

diagramSentence: '![' multiName '](' ~')'+ ')';

// --------------- Phrases ---------------

actor: name | IT;
phrase: createPhrase | callPhrase | writePhrase | answerPhrase;

// Creation

createPhrase: createSV descriptor (sep descriptor)*;
createSV: WE CREATE | actor CREATES;

descriptor: (multiName COMMA?)? constructor;
constructor: typeClause withClauses?;
typeClause: (A | AN) multiName CARD?
          | multiName CARDS;

withClauses: withClause (sep withClause)*;
withClause: WITH simpleName primaryExpr
          | WITH NUMBER multiName;

// Calls

callPhrase: callSV multiName ON expr withClauses?;
callSV: WE CALL | actor CALLS;

// Write

writePhrase: writeSV named | writeSV source=expr INTO target=expr;
writeSV: name (READS | WRITES) | WE (READ | WRITE);

// Answer

answerPhrase: answerSV WITH expr;
answerSV: WE ANSWER | actor ANSWERS;

// =============== Lexer ===============

// --------------- Keywords ---------------

A:        'a';
AN:       'an';
AND:      'and';
ANSWER:   'answer';
ANSWERS:  'answers';
ARE:      'are';
AS:       'as';
CALL:     'call';
CALLS:    'calls';
CARD:     'card';
CARDS:    'cards';
CONTAIN:  'contain';
CONTAINS: 'contains';
CREATE:   'create';
CREATES:  'creates';
DOES:     'does';
EQUAL:    'equal';
EXPECT:   'expect';
FROM:     'from';
GREATER:  'greater';
HAS:      'has';
IN:       'in';
INTO:     'into';
IS:       'is';
IT:       'it';
LESS:     'less';
NOT:      'not';
OF:       'of';
ON:       'on';
READ:     'read';
READS:    'reads';
SAME:     'same';
SCENARIO: 'Scenario';
THAN:     'than';
THAT:     'that';
THE:      'the';
THEN:     'then';
THERE:    'there';
THROUGH:  'through';
TO:       'to';
WE:       'We' | 'we';
WITH:     'with';
WRITE:    'write';
WRITES:   'writes';

// --------------- Key Symbols ---------------

H1:        '#';
H2:        '##';
COMMA:     ',';
FULL_STOP: '.';

// --------------- Literals ---------------

NUMBER:         '-'? [0-9]+ ('.' [0-9]+)?;
STRING_LITERAL: ['] [^']* [']
              | '"' [^"]* '"';

// --------------- Words ---------------

WORD: [a-zA-Z][a-zA-Z0-9']*;

// --------------- Whitespace ---------------

WS:           [ \t\r\n\u000C]+ -> skip;
COMMENT:      '<!--' .*? '-->'    -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\r\n]*    -> channel(HIDDEN);
