# Definition Sentences

## There Sentences

```markup
<thereSentence> ::= <thereClause> (<sep> <thereClause>)* .

<thereClauses> ::= there is <simpleDescriptor> | there are <multiDescriptor>
<descriptor> ::= <simpleDescriptor> | <multiDescriptor>
<simpleDescriptor> ::= (<multiName> ,?)? <simpleConstructor>
<multiDescriptor> ::= (<multiName> (<sep> <multiName>)+); <multiConstructor>
```

{% tabs %}
{% tab title="Scenarios" %}
```text
There is the SEGroup.
There is a student.
There is a student Karli.
There is a student with name Karli.
There is a student Karli with age 12.

There are students with names Karli, Anna.
There are students Karli and Anna.
```
{% endtab %}

{% tab title="Java" %}
```text

```
{% endtab %}
{% endtabs %}

## Is Sentences

```markup
<isSentence> ::= <name> is <simpleConstructor> .

<simpleConstructor> ::= <simpleTypeClause> <withClauses>?
<multiConstructor> ::= <multiTypeClause> <withClauses>?

<simpleTypeClause> ::= (a | an | the) (<simpleName> | <name> card)
<multiTypeClause> ::= <name> cards | <simpleName>s

<withClauses> ::= <withClause> (<sep> <withClause>)*
<withClause> ::= with <namedExpr>
```

## Has Sentences

```markup
<hasSentence> ::= <nameAccess> has <hasClauses> .

<hasClauses> ::= <hasClause> (<sep> <hasClause>)*
<hasClause> ::= has <namedExpr>
```

## Create Sentences

```markup
<createSentence> ::= <actor> create(s) <descriptor> .
```

