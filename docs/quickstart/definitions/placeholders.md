# Placeholders

In some situations, you may want to define your class model without coming up with concrete objects.
This is where Placeholders become useful.

## Inheritance

Using 'Every', you can specify that one class inherits from another:

```scenario
Every student is a person.
```

```java
class Student extends Person
{
   // ...
}
```

## 'Every' and Types

`Every` is also useful for defining attributes and associations.
In the examples below, `Every X` acts as a placeholder for a concrete object.
Meanwhile, `a Y of type Z` is a placeholder for a concrete attribute value.

```scenario
Every person has a name of type string.
Every person has an age of type int.
Every student has a motivation of type double.
Every student has credits of type int.
```

```java
class Person
{
   String name;
   int age;
}

class Student
{
   double motivation;
   int credits;
}
```

For associations, this works similarly.
These sentences express the same thing and can be used interchangably:

```scenario
Every university has students and is uni of many Students.
Every student has uni and is one of the students of a University.
```

```java
class University
{
   @Link("uni")
   List<Student> students;
}

class Student
{
   @Link("students")
   University uni;
}
```

## Concrete Objects as Subjects

The placeholders can be combined with concrete objects.
Let's define some using familiar syntax.

```scenario
There is the person Alice.
There are the students Bob and Charlie.
There is the university StudyRight.
```

The next sentences define the same attributes and associations as above, but their subject is a concrete object.

```scenario
Alice has a name of type string.
Alice has an age of type int.
Bob has a motivation of type double.
Charlie has credits of type int.

StudyRight has students and is uni of many students.
Bob has uni and is one of the students of a university.
```

## Examples with 'like'

You can optionally supply examples with 'like'.
Again we define the same attributes and associations.

```scenario
Every person like Alice has a name of type string.
Every person like Alice has an age of type int.
Every student like Bob has a motivation of type double.
Every student like Charlie has credits of type int.

StudyRight has students and is uni of many Students like Bob and Charlie.
Bob has uni and is one of the students of a University like StudyRight.
```

For attributes, you can also keep the example and remove the type.

```scenario
Every person has a name like "Alice".
Every person has an age like 20.
Every student has a motivation like 12.3.
Every student has credits like 10.
```
