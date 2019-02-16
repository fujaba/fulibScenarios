
grammar FulibScenarios;

scenario: title section*
          ;

title: '#' any+;

section: '##' 'Setup' sentence*;

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

A: 'a' | 'an' | 'the';

NAME: [a-zA-Z][a-zA-Z0-9]*;


NUMBER: '-'?[0-9]+ ('.' [0-9]+)?;


WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);

