parser grammar RegisterParser;

options { tokenVocab = ScenarioLexer; }

// =============== Register ===============

register: H1 REGISTER classDef*;

// --------------- Classes ---------------

classDef: WORD exampleClause (attrDef | roleDef)*;

// --------------- Examples ---------------

exampleClause: EG exampleValue (COMMA exampleValue)*;
exampleValue: WORD+ | NUMBER ;

// --------------- Attributes ---------------

attrDef: PLUS WORD exampleClause;

// --------------- Associations ---------------

roleDef: PLUS WORD COMMA? cardinality WORD cfClause? exampleClause?;

cfClause: CF WORD;
cardinality: ONE | MANY;
