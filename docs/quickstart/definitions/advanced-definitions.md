# Advanced Definitions

Instead of the `is` syntax shown in [Simple Definitions](simple-definitions.md), you can also define your objects with `There`:

```scenario
There is a University.
```

```java
University university = new University();
```

Note that since the university was not given a name, you refer to it as `the university`.

```scenario
The university has 16 departments.
```

```java
university.setDepartments(16);
```

You may provide a name:

```scenario
There is the University Uni Kassel.
Uni Kassel has 400 lecturers.
```

```java
University uniKassel = new University();
uniKassel.setLecturers(400);
```

Using the keyword 'with', you can add attributes.
The first attribute will serve as object name.

```scenario
There is a Student with name Karli.
Karli has student-id m4242.
```

```java
Student karli = new Student();
karli.setName("Karli");
karli.setStudentId("m4242");
```

You can also define multiple attributes at once.

```scenario
There is a Student with name Alice and with 20 credits.
```

```java
Student alice = new Student();
alice.setName("Alice");
alice.setCredits(20);
```

`There` Sentences can define multiple objects at once:

```scenario
There are the Students Bob, Carli, and Dora.
```

```java
Student bob = new Student();
Student carli = new Student();
Student dora = new Student();
```

This also works with attributes:

```scenario
There are Students with name Eyshe, Felice, and Gloria, and with credits 15, 30, 35.
```

```java
Student eyshe = new Student();
Student felice = new Student();
Student gloria = new Student();
eyshe.setName("Eyshe");
felice.setName("Felice");
gloria.setName("Gloria");
eyshe.setCredits(15);
felice.setCredits(30);
gloria.setCredits(35);
```
