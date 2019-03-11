
grammar FulibScenarios;

scenario: '#'+ 'Scenario' any+ '.' sentence*
        | '#'+ 'Register' classDef*
          ;

sentence: thereSentence | directSentence | chainSentence | hasSentence | diagramSentence
        | callSentence;

callSentence: caller=NAME CALL methodName=NAME 'on' objectName=NAME withClause* '.';

thereSentence: 'There' 'is' A? objectName=multiName? (A className=multiName withClause*)? '.';

multiName: NAME+;

chainSentence: methodName=NAME predicateObjectPhrase ('and' 'it' predicateObjectPhrase)* '.'  ;

predicateObjectPhrase: createPhrase | verbPhrase | answerPhrase ;

createPhrase: 'creates' A? className=NAME withClause* ;

verbPhrase: ('adds'|'puts'|'reads') A? value=valueData ('to'|'into'|'from') A? attrName=NAME 'of' A? targetName=NAME ;

answerPhrase: 'answers' ('with'|':')? value=valueData;

directSentence: objectName=multiName 'is' A? className=multiName withClause*;

hasSentence: A? objectName=multiName hasClause+ '.';

hasClause:    'has' attrName=NAME attrValue=valueClause # UsualHasClause
            | 'has' value= NUMBER  attrName=multiName ','? 'and'? # NumberHasClause ;

diagramSentence: '!' '[' type=NAME ']' '(' fileName=fileNameClause ')';


withClause:   'with'?  attrName=NAME  attrValue=valueClause # UsualWithClause
            | 'with' value= NUMBER  attrName=multiName ','? 'and'? # NumberWithClause
            ;

valueClause: (A? valueData ','? 'and'?)+;

valueData: (NAME | NUMBER)+;

fileNameClause: (NAME | '.' | '/')+ ;

CALL: 'call' | 'calls';

any: NUMBER | NAME | A | CALL | 'with' | 'has' | 'There' | 'is' | ',';


classDef:
    className=NAME 'e.g.' exampleValue*
    (attrDef | roleDef) *
;

exampleValue: (nameValue=NAME | numberValue=NUMBER) ','? ;

attrDef: '+' attrName=NAME 'e.g.' exampleValue*;

roleDef: '+' roleName=NAME card=cardDef className=NAME ('cf.' (otherClassName=NAME '.')? otherRoleName=NAME)?;

cardDef: 'one' | 'many' ;



A: 'a' | 'A' | 'an' | 'An' | 'the' | 'The';

NAME: [0-9]*[a-zA-Z_-][a-zA-Z0-9_-]*;


NUMBER: '-'?[0-9]+ ('.' [0-9]+)?;


WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);

