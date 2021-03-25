# Conditionals \[WIP\]

```markup
<condExpr> ::= <attrCheck> | <condOpExpr>
```

## Attribute Check Expression

```markup
<attrCheck> ::= <access> has <namedExpr>
```

```markup
<access> has <namedExpr>
```

```java
<access>.get<namedExpr.name>().equals(<namedExpr.expr>)
```

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

```markup
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

```markup
<cmpOp> ::= is less than | is not less than | is less equal
          | is greater than | is greater equal | is not greater than
```

```markup
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

```markup
<collOp> ::= contains | does not contain | is in | is not in
```

```markup
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
