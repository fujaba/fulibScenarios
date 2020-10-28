# Conditional Sentences \[WIP\]

## If/As/When and Otherwise Sentences

```markup
<ifSentence> ::= (if | as | when) <condExpr> , <statements> . <otherwiseSentence>?
<otherwiseSentence> ::= Otherwise , <statements> .
```

```markup
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
