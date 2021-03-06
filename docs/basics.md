# Basics

## Headlines

A scenario starts with a header line, like '\# My First Scenario' in the following example.
The header line becomes the name of the test method in the Java code.
You may have multiple scenarios in one file.
Each header line starts a new scenario, which corresponds to one test method.

```scenario
# My First Scenario

// ...

# The Second One

// ...
```

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

## Sections

Lines starting with `##` are section headlines.
In Java code, they are shown as special comments.

```scenario
## Examples
```

```java
// --- Examples ---
```

## Comments

Comments allow you to put additional information in a scenario without affecting its semantics.
There are multiple different comment syntaxes available.

### Blockquote Comments

Quoted content, i.e. everything after `>` up to the end of a line, becomes a Java comment.

```scenario
> comment
```

```java
// comment
```

### Slashy Comments

An alternative syntax uses `//` in the scenario text:

```scenario
// comment
There is a Student. // another comment
```

```java
// comment
Student student = new Student();
// another comment
```

However, this is discouraged because it does not render nicely as Markdown.

### Parenthesized Comments

Another form of comment uses parentheses.
These comments will not be present in the resulting Java code.

```scenario
There is a Student (without a name for now).
```

```java
Student student = new Student();
```

### HTML Comments

This syntax is similar to parenthesized comments in that they will not end up in the Java code.
However, they will not be visible in rendered Markdown.

```scenario
There is a Student <!-- without a name for now -->.
```

```java
Student student = new Student();
```
