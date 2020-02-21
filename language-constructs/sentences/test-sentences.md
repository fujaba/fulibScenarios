# Test Sentences

## Expect Sentences

```markup
<expectSentence> ::= we expect <thatClauses> .
<thatClauses> ::= <thatClause> (<sep> <thatClause>)*
<thatClause> ::= that <condExpr>
```

## Diagram Sentences

```markup
<diagramSentence> ::= ![ <expr> ]( <?>* )
```

