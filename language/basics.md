# Basics

## Headlines

A scenario starts with a header line, like '\# Basics' at the top. The header line becomes the name of the test method in the Java code. You may have multiple scenarios in one file. Each header line starts a new test method, or 'scenario'. Keep in mind that you cannot access objects from another scenario.

{% tabs %}
{% tab title="Scenario" %}
```text
# My First Scenario

// ...

# The Second One

// ...
```
{% endtab %}

{% tab title="Java" %}
```java
@Test
void myFirstScenario() {
    // ...
}
@Test
void theSecondOne() {
    // ...
}
```
{% endtab %}
{% endtabs %}

## Sections

Lines starting with `##` are section headlines. In Java code, they are shown as special comments.

{% tabs %}
{% tab title="Scenario" %}
```text
## Examples
```
{% endtab %}

{% tab title="Java" %}
```java
// --- Examples ---
```
{% endtab %}
{% endtabs %}

## Comments

Everything after `//` up to the end of a line becomes a Java comment.

{% tabs %}
{% tab title="Scenario" %}
```
// comment
... // this does the thing
```
{% endtab %}

{% tab title="Java" %}
```java
// comment
... // this does the thing
```
{% endtab %}
{% endtabs %}

### Parenthesized Comments

Another form of comment uses parentheses. These comments will not be present in the resulting Java code.

{% tabs %}
{% tab title="Scenario" %}
```
... (this will not be part of the Java code) ...
```
{% endtab %}

{% tab title="Java" %}
```java
... ...
```
{% endtab %}
{% endtabs %}

