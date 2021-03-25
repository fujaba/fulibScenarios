# Conditionals \[WIP\]

```bnf
<condExpr> ::= <attrCheck> | <condOpExpr>
```

## Attribute Check Expression

```bnf
<attrCheck> ::= <access> has <namedExpr>
```

```scenario
<access> has <namedExpr>
```

```java
<access>.get<namedExpr.name>().equals(<namedExpr.expr>)
```

## Conditional Operator Expression

```bnf
<condOpExpr> ::= <access> <condOp> <access>
<condOp> ::= <eqOp>
           | <cmpOp>
           | <collOp> 
```

### Equality Operators

```bnf
<eqOp> ::= is | is not | is the same as | is not the same as
```

```scenario
is
is not
is the same as
is not the same as
```

```java
equals
!equals
==
!=
```

### Comparison Operators

```bnf
<cmpOp> ::= is less than | is not less than | is less equal
          | is greater than | is greater equal | is not greater than
```

```scenario
is less than
is not less than
is less equal
is greater than
is not greater than
is greater equal
```

```java
<
>=
<=
>
<=
>=
```

### Collection Operators

```bnf
<collOp> ::= contains | does not contain | is in | is not in
```

```scenario
contains
does not contain
is in
is not in
```

```java
contains
!contains
contains'
!contains'
```
