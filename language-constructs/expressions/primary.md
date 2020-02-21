# Primary

```markup
<primary> ::= <number> | <stringLiteral> | <it>
            | <nameAccess> | <string>
<primaryExpr> ::= <primary> | <primaryCollection>
```

## Numbers

```markup
<number> ::= /-?[0-9]+('.'[0-9]+)?/
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
0
42
1.5
-2
-4.5
```
{% endtab %}

{% tab title="Java" %}
```java
0
42
1.5
-2
-4.5
```
{% endtab %}
{% endtabs %}

## String Literals

```markup
<stringLiteral> ::= /"[^"]*"/
                  | /'[^']*'/
```

When you don't want to refer to an existing object or use special characters in a string, use a string literal with single or double quotes.

{% tabs %}
{% tab title="Scenarios" %}
```markup
'Hello World'
"Foo Bar 3"
'""'
```
{% endtab %}

{% tab title="Java" %}
```java
"Hello World"
"Foo Bar 3"
"\"\""
```
{% endtab %}
{% endtabs %}

## It

```markup
<it> ::= it
```

The keyword `it` refers to the primary closest to the left of it.

## Words

```markup
<word> ::= /[a-zA-Z][a-zA-Z0-9']*/
```

Whether a word is a name or a string depends on context.

## Names

```markup
<simpleName> ::= the? <word>
<multiName> ::= the? <word>+
<name> ::= <multiName>
```

The keyword `the` always indicates an object name.

{% tabs %}
{% tab title="Scenarios" %}
```markup
Carli
the position
index
the list
Peter
name
Peter Pan
my list
the Other list
MiXeD cAsE 
```
{% endtab %}

{% tab title="Java" %}
```java
carli
position
index
list
peter
name
peterPan
myList
otherList
mixedCase
```
{% endtab %}
{% endtabs %}

## Name Access

```markup
<nameAccess> ::= <multiName>
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
<name>
```
{% endtab %}

{% tab title="Java" %}
```java
<name>
```
{% endtab %}
{% endtabs %}

## Strings

Strings can consist of multiple words in certain contexts. See [Names](primary.md#names) for when a string refers to an object.

```markup
<string> ::= (<word> | <number>)+
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
Carli
math
integrals
math room
room 3
```
{% endtab %}

{% tab title="Java" %}
```java
"Carli"
"math"
"integrals"
"math room"
"room 3"
```
{% endtab %}
{% endtabs %}



