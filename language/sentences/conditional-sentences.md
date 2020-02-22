# Conditional Sentences

## If/As/When and Otherwise Sentences

```markup
<ifSentence> ::= (if | as | when) <condExpr> , <statements> . <otherwiseSentence>?
<otherwiseSentence> ::= Otherwise , <statements> .
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
If/As/When <condExpr>, <statements>.


Otherwise, <statements>.


```
{% endtab %}

{% tab title="Java" %}
```java
if (<condExpr>) {
    <statements>
}
else {
    <statements>
}
```
{% endtab %}
{% endtabs %}

## 

