# Definition Sentences

## Is Sentences

Is Sentences are the simplest way to define objects. They define a type, a name and various attributes. Is Sentences can be used in tests as well as calls.

```markup
Kassel is a City.

Frankfurt is a City with 750000 inhabitants.
```

```java
City kassel = new City();

City frankfurt = new City().setInhabitants(750000);
```

```markup
<isSentence> ::= <name> is <simpleConstructor> .

<simpleConstructor> ::= <simpleTypeClause> <withClauses>?
<multiConstructor> ::= <multiTypeClause> <withClauses>?

<simpleTypeClause> ::= (a | an | the) (<simpleName> | <name> card)
<multiTypeClause> ::= <name> cards | <simpleName>s

<withClauses> ::= <withClause> (<sep> <withClause>)*
<withClause> ::= with <namedExpr>
```

## Has Sentences

Has Sentences can only be used to set attributes on objects, but not to declare them. Like Is Sentences, they can be used in tests and calls.

```markup
Kassel has postcode 34117.
Kassel has 200000 inhabitants.
Kassel has area 106.78 (sq km).
```

```java
kassel.setPostcode(34117);
kassel.setInhabitants(200000);
kassel.setArea(106.78);
```

```markup
<hasSentence> ::= <nameAccess> <hasClauses> .

<hasClauses> ::= <hasClause> (<sep> <hasClause>)*
<hasClause> ::= has <namedExpr>
```

## There Sentences

There Sentences are similar to Is Sentences. In addition to being only usable in tests, There Sentences have the following extra features on top of the functionaly provided by Is Sentences:

* You can omit the name of the object. In this case, it will be inferred from the first string attribute, or the class name.

```scenario
There is the university.
There is a student.
There is a student with name Karli.
There is a student with age 21 and with name Anna.
```

```java
University university = new University();
Student student = new Student();
Student karli = new Student().setName("Karli");
Student anna = new Student().setAge(21).setName("Anna");
```

The equivalent sentences with an explicit name are:

```markup
There is the university university.
There is the student student.
There is the student karli with name Karli.
There is the student anna with age 21 and with name Anna.
```

> #### ⓘ Hint
>
> Note the use of `the` instead of `a` in these examples. In definitions, you use `the` when you give an object an explicit name, and `a` when you use an inferred name.

* You can declare multiple objects at once. This saves you from typing the class name and the attribute names multiple times when you want to create multiple objects with of the same type and with the same attribute. If you use the same number of values for every attribute, they are assigned to the objects in order \(second example\). If you have only one value of an attribute, this value is assigned to the attribute of each object \(third example\). Note that the number of generated lines grows quickly with the number of attributes and objects. You may want to split a There Sentence with many objects into multiple sentences for readability.

```scenario
There are students with names Karli and Anna.

There are students with names Bob, Charlie and David and with credits 10, 20, 30.

There are students with names Emil and Fred and with 40 credits.
```

```java
Student karli = new Student();
Student anna = new Student();
karli.setNames("Karli");
anna.setNames("Anna");

Student bob = new Student();
Student charlie = new Student();
Student david = new Student();
bob.setNames("Bob");
charlie.setNames("Charlie");
david.setNames("David");
bob.setCredits(10);
charlie.setCredits(20);
david.setCredits(30);

Student emil = new Student();
Student fred = new Student();
emil.setNames("Emil");
fred.setNames("Fred");
int temp1 = 40;
emil.setCredits(temp1);
fred.setCredits(temp1);
```

Again, you can give your objects explicit names instead of relying on the inferred names.

```markup
There are the students Karli and Anna with names Karli and Anna.
There are the students Bob, Charlie and David with names Bob, Charlie and David and with credits 10, 20, 30.
There are the students Emil and Fred with names Emil and Fred and with 40 credits.
```

```markup
<thereSentence> ::= <thereClause> (<sep> <thereClause>)* .

<thereClauses> ::= there is <simpleDescriptor> | there are <multiDescriptor>
<descriptor> ::= <simpleDescriptor> | <multiDescriptor>
<simpleDescriptor> ::= (<multiName> ,?)? <simpleConstructor>
<multiDescriptor> ::= (<multiName> (<sep> <multiName>)+); <multiConstructor>
```

## Create Sentences

Create sentences are used in place of There Sentences within calls. The examples from above are written with Create Sentences as follows \(the translation stays the same so is not listed again here\):

```markup
We create the university.
We create a student.
We create a student with name Karli.
We create a student with age 21 and with name Anna.

We create the university university.
We create the student student.
We create the student karli with name Karli.
We create the student anna with age 21 and with name Anna.

We create students with names Karli and Anna.
We create students with names Bob, Charlie and David and with credits 10, 20, 30.
We create students with names Emil and Fred and with 40 credits.

We create the students Karli and Anna with names Karli and Anna.
We create the students Bob, Charlie and David with names Bob, Charlie and David and with credits 10, 20, 30.
We create the students Emil and Fred with names Emil and Fred and with 40 credits.
```

> #### ⓘ Hint
>
> You get the equivalent Create Sentence from a There Sentence by replacing `There is` / `There are` with `We create` and vice-versa.

```markup
<createSentence> ::= <actor> create(s) <descriptor> .
```

## Associations

With and Has Clauses can also create associations instead of attributes. This usually depends on the value. If it refers to an object, you get an association. If the value is a string or number, it becomes an attribute. Associations are by default uni-directional.

```markup
There is a University with name Uni Kassel.
There is a Student with name Bob.
```

```markup
There is a Student with name Alice and with university Uni Kassel.
Bob has university Uni Kassel.
```

```java
Student alice = new Student().setName("Alice").setUniversity(uniKassel); 
bob.setUniversity(uniKassel);
```

If you specify multiple values, the association becomes to-many.

```markup
The university has students Alice and Bob.
```

```java
university.withStudents(alice, bob);
```

> #### ⚠ Warning
>
> A common mistake when omitting the explicit object name is having an association right after the class:
>
> ```scenario
> There is a Student with university Uni Kassel.
> ```
>
> This will cause the student object to be named `uniKassel`, which produces a duplicate variable name in the Java code.
>
> ```java
> University uniKassel = ...;
> Student uniKassel = new Student().setUniversity(uniKassel);
> ```
>
> The Scenario compiler will produce an error:
>
> ```scenario
> error: invalid redeclaration of 'uniKassel' [variable.redeclaration]
> perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
> ```
>
> Following the compiler's advice, you can either give the object an explicit name, or introduce another attribute, if appropriate.
>
> ```scenario
> There is the Student Charlie with university Uni Kassel.
> There is a Student with name Charlie and with university Uni Kassel.
> ```

### Bidirectional Associations

To make an association bidirectional, you have to specify the reverse name. This is only necessary once; you can omit the reverse name as long as you specify it in any one place. The important bit is the `and is university of` between the association name and the values.

```markup
Uni Kassel has students and is university of Alice and Bob.
```

```java
uniKassel.withStudents(alice, bob);
```

Note how the reverse association name disappears from the Java code. If you view the implementation of `withStudents`, you can see that the association is indeed bidirectional and linked to the `university` property of the `Student` class.

You may also specify this association between students and universities from the student side. To indicate that the reverse association is to-many, you use `and is` **`one of`** `the students of`.

By including the association for Bob, the two sentences become identical in effect to the previous example. Note how you can omit the reverse association name here because it was already specified in the sentence about Alice.

```markup
Alice has university and is one of the students of the Uni Kassel.
Bob has university Uni Kassel.
```

```java
alice.setUniversity(uniKassel);
bob.setUniversity(uniKassel);
```

### Special Associations

You can specify an association where the source and target class are the same \(first example\). Further, the an association may be its own reverse \(second example\). This also works for to-many associations \(third example\).

```markup
Alice has right-neighbor and is left-neighbor of Bob.


Charlie has best-friend and is best-friend of Dude.


Bob has friends and is one of the friends of Alice and Charlie.

```

```java
alice.setRightNeighbor(bob);
// bob.getLeftNeighbor() == alice

charlie.setBestFriend(dude);
// => dude.getBestFriend() == charlie

bob.withFriends(alice, charlie);
// => alice.getFriends().contains(bob) && charlie.getFriends().contains(bob);
```

> #### ⚠ Warning
>
> Make sure that when creating associations that are their own reverse, you specify many values when using `one of` , or omit the `one of` when there is only one value. The following examples ignored this rule:
>
> ```scenario
> Alice has associate and is one of the associate of Bob. // "one of" but one item
> Charlie has enemies and is enemies of Alice and Dude.   // no "one of" but many items
> ```
>
> Intuitively, you can see that the sentences are grammatically weird, because of the mixed singular and plural forms. To fix the first example, we can change the association name \(first `associate`\) to plural and add another example item:
>
> ```scenario
> Alice has associates and is associate of Bob and Charlie.
> ```
>
> The second sentence can be fixed by changing the reverse name \(second `enemies`\) to singular:
>
> ```scenario
> Charlie has enemies and is enemy of Alice and Dude.
> ```
>
> The sentences now make sense again and can be compiled. Note however that the association names are now different from the reverse association names, so they are no longer their own reverse.
