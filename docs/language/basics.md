# Basics

## Headlines

A scenario starts with a header line, like '\# Basics' at the top. The header line becomes the name of the test method in the Java code. You may have multiple scenarios in one file. Each header line starts a new test method, or 'scenario'. Keep in mind that you cannot access objects from another scenario.

```markdown
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

Lines starting with `##` are section headlines. In Java code, they are shown as special comments.

```markdown
## Examples
```

```java
// --- Examples ---
```

## Comments

Everything after `//` up to the end of a line becomes a Java comment.

```markdown
// comment
... // this does the thing
```

```java
// comment
... // this does the thing
```

### Parenthesized Comments

Another form of comment uses parentheses. These comments will not be present in the resulting Java code.

```markdown
... (this will not be part of the Java code) ...
```

```java
... ...
```
