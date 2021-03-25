# Collections \[WIP\]

```bnf
<collection> ::= <range> | <list>
<primaryCollection> ::= <primaryRange> | <primaryList>
```

## Ranges

```bnf
<range> ::= <access> to <access>
          | <access> through <access>
          
<primaryRange> ::= <primary> to <primary>
                 | <primary> through <primary> 
```

```scenario
<access> to <access>
<access> through <access>
```

```java
Runtime.to(<access>, <access>)
Runtime.through(<access>, <access>)
```

## Lists

```bnf
<sep> ::= ',' | 'and' | ',' 'and'

<list> ::= <listElem> (<sep> <listElem>)+
<listElem> ::= <access> | <range>

<primaryList> ::= <primaryListElem> (<sep> <primaryListElem>)+
<primaryListElem> ::= <primary> | <primaryRange>
```

```scenario
<listElem> (<sep> <listElem>)+
1, 2, 3
1, 2, and 3
1 and 2 and 3
1 and 2 to 4
1 to 3 and 6
1 to 3 and 6 to 10
1 to 3 and 5 and 6 to 8 and 10
```

```java
Runtime.list(<listElem>, ..., <listElem>)
Runtime.list(1, 2, 3)
Runtime.list(1, 2, 3)
Runtime.list(1, 2, 3)
Runtime.list(1, Runtime.to(2, 4))
Runtime.list(Runtime.to(1, 3), 6)
Runtime.list(Runtime.to(1, 3), Runtime.to(6, 10))
Runtime.list(Runtime.to(1, 3), 5, Runtime.to(6, 8), 10)
```

> #### ⓘ Hint
>
> The `Runtime.list(Object...)` method automatically flattens the resulting list if one or more arguments are lists.
