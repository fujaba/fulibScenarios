# Write Sentences \[WIP\]

```bnf
<writeSentence> ::= <writeSV> <named>
                  | <writeSV> <expr1> into <expr2>
<writeSV> ::= <actor> (reads | writes)
            | we (read | write)
```

```scenario
<actor> reads/writes <named>
<actor> reads/writes <expr1> into <expr2>
we read score of player A
we read 5 from size of elements
we write 5 from points of Carli, 6, and 7 into list
we write 1 to 5 into numbers of interest
```

## Write Translation

The following algorithm describes how writes of the form `write <rhs> into <lhs>` are translated. Note that "collection" refers to a list or range with a size that can be statically determined, while multi-valued can also be a vectorized attribute access.

1. If `<lhs>` and `<rhs>` are both collections of size `n`, then translate the sequence of writes formed by `write <rhs[0]> into <lhs[0]>; ...; write <rhs[n-1]> into <lhs[n-1]>`.
2. If `<rhs>` is multi-valued and `<lhs>` is not a collection, then
   1. If `<lhs>` is a name, emit `<lhs> = <rhs>` and translate `<rhs>` as an expression.
   2. If `<lhs>` is an attribute access `<attributes> of <receiver>` referring to an association with cardinality "many", emit `<receiver>.with<attributes>(<rhs>.toArray())`.
   3. If `<lhs>` is a vectorized attribute access `<attributes> of <receivers>` referring to an attribute or an association with cardinality "one", emit `Runtime.zipSet(<receivers>, <receiver.type>::set<attribute>, <rhs>)`, where `<rhs>` is translated as an expression.
   4. Otherwise, the statement is malformed.
3. If `<lhs>` is a collection of size `n` and `<rhs>` is not multi-valued, then translate the sequence of writes formed by `write <rhs> into <lhs[0]>; ...; write <rhs> into <lhs[n-1]>`.
4. If neither `<lhs>` nor `<rhs>` are multi-valued:
   1. If `<lhs>` is a name, emit `<lhs> = <rhs>`.
   2. If `<lhs>` is an attribute access `<attribute> of <receiver>` referring to an attribute or an association with cardinality "one", emit `<receiver>.set<attribute>(<rhs>)`.
   3. If `<lhs>` is an attribute access `<attributes> of <receiver>` referring to an association with cardinality "many", emit `<receiver>.with<attributes>(<rhs>)`.

| Case | Example | Code |
| :--- | :--- | :--- |
| 1 | write 1,2,3 into a, b, c | a = 1; b = 2; c = 3 |
| 2.1 | write 1,2,3 into a | a = Runtime.list\(1,2,3\) |
| 2.2 | write 1,2,3 into as of x | x.withAs\(Runtime.list\(1,2,3\).toArray\(\)\) |
| 2.3 | write 1,2,3 into as of xs | Runtime.zipSet\(xs, X::setA, Runtime.list\(1,2,3\)\) |
| 2.4 | write 1,2,3 into a of x | invalid |
| 3 | write 1 into x, y, z | x = 1; y = 1; z = 1 |
| 4.1 | write 1 into x | x = 1; |
| 4.2 | write 1 into a of x | x.setA\(1\) |
| 4.3 | write 1 into as of x | x.withAs\(1\) |

