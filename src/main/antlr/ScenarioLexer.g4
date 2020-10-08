lexer grammar ScenarioLexer;

// --------------- Keywords ---------------

A:        'a';
ADD:      'add';
ADDS:     'adds';
ALL:      'all';
AN:       'an';
AND:      'and';
ANSWER:   'answer';
ANSWERS:  'answers';
ARE:      'are';
AS:       'As' | 'as';
ATTRIBUTE:'attribute';
CALL:     'call';
CALLS:    'calls';
CARD:     'card';
CARDS:    'cards';
CF:       'cf.';
CONTAIN:  'contain';
CONTAINS: 'contains';
CREATE:   'create';
CREATES:  'creates';
DO:       'do';
DOES:     'does';
EG:       'e.g.';
EMPTY:    'empty';
EQUAL:    'equal';
EVERY:    'Every';
EXPECT:   'expect';
FROM:     'from';
GREATER:  'greater';
HAS:      'has';
HAVE:     'have';
IN:       'in';
INTO:     'into';
IS:       'is';
IT:       'it';
LESS:     'less';
LIKE:     'like';
LINK:     'link';
MANY:     'many';
MATCH:    'match';
MATCHES:  'matches';
NOT:      'not';
OF:       'of';
OR:       'or';
ON:       'on';
ONE:      'one';
READ:     'read';
READS:    'reads';
REGISTER: 'register';
REMOVE:   'remove';
REMOVES:  'removes';
SAME:     'same';
SOME:     'some';
TAKE:     'take';
TAKES:    'takes';
THAN:     'than';
THAT:     'that';
THE:      'The' | 'the';
THEN:     'then';
THERE:    'There' | 'there';
THROUGH:  'through';
TYPE:     'type';
TO:       'to';
WE:       'We' | 'we';
WITH:     'with';
WHERE:    'where';
WHICH:    'which';
WHOSE:    'whose';
WRITE:    'write';
WRITES:   'writes';

// --------------- Key Symbols ---------------

H1:           '#' -> mode(HEADLINE);
H2:           '##' -> mode(HEADLINE);
LINE_COMMENT: '//' -> mode(HEADLINE);

CODE_BLOCK: LEADING_WHITESPACE '```' SEMPRED_SOL -> mode(CODE_BLOCK_HEADER);

BULLET: LEADING_WHITESPACE [+*-] SEMPRED_SOL;
NUMBERED: LEADING_WHITESPACE [0-9]+ [.] SEMPRED_SOL;

fragment SEMPRED_SOL: {getCharPositionInLine() == getText().length()}?;

COMMA:     [,];
FULL_STOP: [.];
PLUS:      [+];
COLON:     [:];

// --------------- Literals ---------------

INTEGER:        [-]? [0-9]+;
DECIMAL:        [-]? [0-9]+ [.] [0-9]+;
STRING_LITERAL: ['] (~['\\\r\n] | EscapeSequence)* [']
              | ["] (~["\\\r\n] | EscapeSequence)* ["];

fragment EscapeSequence: [\\] [btnfr"'\\];

// --------------- Words ---------------

WORD: [a-zA-Z_][a-zA-Z0-9'_-]*;

// --------------- Whitespace ---------------

fragment LEADING_WHITESPACE: [ \t\u000C]*;
WHITESPACE: [ \t\u000C]+ -> skip;
LINEBREAK: [\r\n]+ -> skip;

// --------------- Comments ---------------

COMMENT:      ('(' .*? ')' | '<!--' .*? '-->') -> channel(HIDDEN);

// --------------- Images ---------------

IMG_START: '![';
IMG_SEP: '](' -> mode(FILE_NAME_MODE);

mode FILE_NAME_MODE;
IMG_END: ')' -> mode(DEFAULT_MODE);
FILE_NAME: ~')'+;

mode HEADLINE;
HEADLINE_END: ([\n\r] | '\r\n') -> mode(DEFAULT_MODE);
HEADLINE_TEXT: ~[\n\r]+;

mode CODE_BLOCK_HEADER;
CODE_BLOCK_HEADER_END: ([\n\r] | '\r\n') -> skip, mode(CODE_BLOCK_BODY);
CODE_BLOCK_LANGUAGE: ~[\n\r]+;

mode CODE_BLOCK_BODY;
CODE_BLOCK_END: [ \t\u000C]* '```' ([\n\r] | '\r\n') -> mode(DEFAULT_MODE);
CODE_BLOCK_LINE: ~[\n\r]* ([\n\r] | '\r\n');
