# Access

```markup
<access> ::= <primary>
           | <attributeAccess>
           | <exampleAccess>

<namedAccess> ::= <nameAccess>
                | <attributeAccess>

<named> ::= <namedAccess> | <exampleAccess>
```

## Attribute Access

```markup
<attributeAccess> ::= <anyName> of <access>
```

{% tabs %}
{% tab title="Scenarios" %}
```markup
<anyName> of <access>
```
{% endtab %}

{% tab title="Java" %}
```java
<access>.get<anyName>()
```
{% endtab %}
{% endtabs %}

### Vectorization

Access on lists with names ending with `s` are treated specially, by vectorizing the access across all list elements with a `map` operation. If the name is `size` or `count`, it returns the size of the list.

{% tabs %}
{% tab title="Scenarios" %}
```markup
(the) size of <someList>
(the) count of <someList>
(the) <name>s of <someList>
```
{% endtab %}

{% tab title="Java" %}
```java
<someList>.size()
<someList>.size()
<someList>.stream().map(it::get<name>).collect(Collectors.toList())
```
{% endtab %}
{% endtabs %}

## Example Access

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

