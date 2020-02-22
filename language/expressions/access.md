# Access

Access refers to accessing an attribute from some expression. This is usually done with getters.

```markup
<access> ::= <primary>
           | <attributeAccess>
           | <exampleAccess>

<namedAccess> ::= <nameAccess>
                | <attributeAccess>

<named> ::= <namedAccess> | <exampleAccess>
```

## Attribute Access

The simplest form of access is by attribute name. This invokes the getter on the receiving object.

```markup
<attributeAccess> ::= <name> of <access>
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
(the) <name> of <access>
```
{% endtab %}

{% tab title="Java" %}
```java
<access>.get<name>()
```
{% endtab %}
{% endtabs %}

### Vectorization

Access on lists with names ending with `s` are treated specially, by vectorizing the access across all list elements with a `map` operation.

Not yet available: If the name is `size` or `count`, it returns the size of the list.

{% tabs %}
{% tab title="Scenarios" %}
```markup
(the) <name>s of <someList>
(the) size of <someList>
(the) count of <someList>
```
{% endtab %}

{% tab title="Java" %}
```java
<someList>.stream().map(<elementType>::get<name>).collect(Collectors.toList())
<someList>.size()
<someList>.size()
```
{% endtab %}
{% endtabs %}

## Example Access

This expression serves as a way of documenting the expected value of an expression. This expectation is not checked; the expected value is not written to the Java code. As such, this type of expression has only commentary value.

```markup
<exampleAccess> ::= <primaryExpr> from <namedAccess>
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
<primaryExpr> from <namedAccess>
```
{% endtab %}

{% tab title="Java" %}
```java
<namedAccess>
```
{% endtab %}
{% endtabs %}

