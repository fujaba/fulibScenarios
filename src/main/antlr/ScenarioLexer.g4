lexer grammar ScenarioLexer;

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
IN:       'in';
INTO:     'into';
IS:       'is';
IT:       'it';
LESS:     'less';
MANY:     'many';
NOT:      'not';
OF:       'of';
ON:       'on';
ONE:      'one';
READ:     'read';
READS:    'reads';
REGISTER: 'register';
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
PLUS:      '+';

// --------------- Literals ---------------

NUMBER:         '-'? [0-9]+ ('.' [0-9]+)?;
STRING_LITERAL: ['] [^']* [']
              | '"' [^"]* '"';

// --------------- Words ---------------

WORD: [a-zA-Z][a-zA-Z0-9']*;

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
