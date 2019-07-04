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
CALL:     'call';
CALLS:    'calls';
CARD:     'card';
CARDS:    'cards';
CF:       'cf.';
CONTAIN:  'contain';
CONTAINS: 'contains';
CREATE:   'create';
CREATES:  'creates';
DOES:     'does';
EG:       'e.g.';
EQUAL:    'equal';
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
MANY:     'many';
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
THAN:     'than';
THAT:     'that';
THE:      'The' | 'the';
THEN:     'then';
THERE:    'There' | 'there';
THROUGH:  'through';
TO:       'to';
WE:       'We' | 'we';
WITH:     'with';
WHICH:    'which';
WRITE:    'write';
WRITES:   'writes';

// --------------- Key Symbols ---------------

H1:        '#' -> mode(HEADLINE);
H2:        '##' -> mode(HEADLINE);
COMMA:     [,];
FULL_STOP: [.];
PLUS:      [+];

// --------------- Literals ---------------

NUMBER:         [-]? [0-9]+ ([.] [0-9]+)?;
STRING_LITERAL: ['] (~['\\\r\n] | EscapeSequence)* [']
              | ["] (~["\\\r\n] | EscapeSequence)* ["];

fragment EscapeSequence: [\\] [btnfr"'\\];

// --------------- Words ---------------

WORD: [a-zA-Z_][a-zA-Z0-9'_-]*;

// --------------- Whitespace ---------------

WS:           [ \t\r\n\u000C]+ -> skip;
COMMENT:      '<!--' .*? '-->' -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\r\n]*    -> channel(HIDDEN);

// --------------- Images ---------------

IMG_START: '![';
IMG_SEP: '](' -> mode(FILE_NAME_MODE);

mode FILE_NAME_MODE;
IMG_END: ')' -> mode(DEFAULT_MODE);
FILE_NAME: ~')'+;

mode HEADLINE;
HEADLINE_TEXT: ~'\n'+;
HEADLINE_END: '\n' -> mode(DEFAULT_MODE);
