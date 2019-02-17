
grammar FulibScenarios;

scenario: title section*
        | '#'+ 'Register' classDef*
          ;

title: '#'+ 'Scenario' any+;

section: '#'+ 'Setup' sentence*;

sentence: thereSentence | directSentence | isSentence | hasSentence | diagramSentence;

thereSentence: 'There' 'is' A? objectName=multiName? A className=multiName withClause* '.';

multiName: NAME+;

isSentence: objectName=multiName 'is' attrName='in' A? value=valueData '.';

directSentence: objectName=multiName 'is' A? className=multiName withClause* '.';

hasSentence: objectName=multiName 'has' attrName=NAME value=valueData '.';

diagramSentence: '!' '[' type=NAME ']' '(' fileName=fileNameClause ')';


withClause:   'with'?  attrName=NAME  attrValue=valueClause # UsualWithClause
            | 'with' value= NUMBER  attrName=multiName ','? 'and'? # NumberWithClause
            ;

valueClause: (A? valueData ','? 'and'?)+;

valueData: (NAME | NUMBER)+;

fileNameClause: (NAME | '.' | '/')+ ;

any: NUMBER | NAME | A | ',' | '.';


classDef:
    className=NAME 'e.g.' exampleValue*
    (attrDef | roleDef) *
;

exampleValue: (nameValue=NAME | numberValue=NUMBER) ','? ;

attrDef: '+' attrName=NAME 'e.g.' exampleValue*;

roleDef: '+' roleName=NAME card=cardDef className=NAME ('cf.' (otherClassName=NAME '.')? otherRoleName=NAME)?;

cardDef: 'one' | 'many' ;



A: 'a' | 'an' | 'the';

NAME: [a-zA-Z][a-zA-Z0-9]*;


NUMBER: '-'?[0-9]+ ('.' [0-9]+)?;


WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);

