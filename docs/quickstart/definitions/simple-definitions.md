# Simple Definitions

You create objects using the keyword `is`:

```scenario
Kassel is a City.
```

```java
City kassel = new City();
```

Set attributes using the keyword `has`:

```scenario
Kassel has postcode 34117.
```

```java
kassel.setPostcode(34117);
```

For numeric attributes, you may also write the value first.

```scenario
Kassel has 200000 inhabitants.
```

```java
kassel.setInhabitants(200000);
```

Numeric attributes with a decimal point translate to the Java type `double`.

```scenario
Kassel has area 106.78 (sq km).
```

```java
kassel.setArea(106.78);
```
