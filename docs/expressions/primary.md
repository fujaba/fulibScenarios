# Primary

Primary expressions are those that do not involve any complex operations. Typically, they consist of one value or a few words.

```bnf
<primary> ::= <number> | <stringLiteral> | <it>
            | <nameAccess> | <string>
<primaryExpr> ::= <primary> | <primaryCollection>
```

## Numbers

Numbers can be written with or without a decimal part. Numbers without one will become `int` in Java, and those with a decimal part will become `double`s. Usually, the translation is as-is.

```bnf
<number> ::= /-?[0-9]+('.'[0-9]+)?/
```

```scenario
0
42
1.5
-2
-4.5
```

```java
0
42
1.5
-2
-4.5
```

## String Literals

When you don't want to refer to an existing object or use special characters in a string, use a string literal with single or double quotes.

```bnf
<stringLiteral> ::= /"[^"]*"/
                  | /'[^']*'/
```

When using double quotes, the translation is mostly as-is. Single quotes in a Scenario become double quotes in Java.

```scenario
'Hello World'
"Foo Bar 3"
'""'
```

```java
"Hello World"
"Foo Bar 3"
"\"\""
```

## It \(not yet available\)

The keyword `it` refers to the primary expression closest to the left of it.

```bnf
<it> ::= it
```

## Words

Words make up names and simple strings. Which one depends on context. When you are sure you want a string, you can use a [string literal](primary.md#string-literals) instead. When you want to refer to a name, use the keyword [`the`](primary.md#names).

```bnf
<word> ::= /[a-zA-Z][a-zA-Z0-9'_-]*/
```

## Names

Names may refer to objects, but can also be used for strings.
In any case, they consist of at least one word followed by zero or more words and numbers.
The keyword `the` always indicates an object name.
It depends on context whether a name declares an object or refers to one.

```bnf
<simpleName> ::= the? <word>
<multiName> ::= the? <word> (<word> | <number>)*
<name> ::= <multiName>
<nameAccess> ::= <multiName>
```

```scenario
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

## Strings

Strings can consist of multiple words in certain contexts. See [Names](primary.md#names) for when a string refers to an object.

```bnf
<string> ::= (<word> | <number>)+
```

```scenario
Carli
math
integrals
math room
room 3
```

```java
"Carli"
"math"
"integrals"
"math room"
"room 3"
```
