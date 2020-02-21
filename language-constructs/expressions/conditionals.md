# Conditionals

```markup
<condExpr> ::= <attrCheck> | <condOpExpr>
```

## Attribute Check Expression

```markup
<attrCheck> ::= <access> has <namedExpr>
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
<access> has <namedExpr>
```
{% endtab %}

{% tab title="Java" %}
```java
<access>.get<namedExpr.name>().equals(<namedExpr.expr>)
```
{% endtab %}
{% endtabs %}

## Conditional Operator Expression

```markup
<condOpExpr> ::= <access> <condOp> <access>
<condOp> ::= <eqOp>
           | <cmpOp>
           | <collOp> 
```

### Equality Operators

```markup
<eqOp> ::= is | is not | is the same as | is not the same as
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
is
is not
is the same as
is not the same as
```
{% endtab %}

{% tab title="Java" %}
```java
equals
!equals
==
!=
```
{% endtab %}
{% endtabs %}

### Comparison Operators

```markup
<cmpOp> ::= is less than | is not less than | is less equal
          | is greater than | is greater equal | is not greater than
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
is less than
is not less than
is less equal
is greater than
is not greater than
is greater equal
```
{% endtab %}

{% tab title="Java" %}
```java
<
>=
<=
>
<=
>=
```
{% endtab %}
{% endtabs %}

### Collection Operators

```markup
<collOp> ::= contains | does not contain | is in | is not in
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
contains
does not contain
is in
is not in
```
{% endtab %}

{% tab title="Java" %}
```java
contains
!contains
contains'
!contains'
```
{% endtab %}
{% endtabs %}



