# Conditional Sentences \[WIP\]

## If/As/When and Otherwise Sentences

```bnf
<ifSentence> ::= (if | as | when) <condExpr> , <statements> . <otherwiseSentence>?
<otherwiseSentence> ::= Otherwise , <statements> .
```

```scenario
If/As/When <condExpr>, <statements>.
Otherwise, <statements>.
```

```java
if (<condExpr>) {
    <statements>
}
else {
    <statements>
}
```
