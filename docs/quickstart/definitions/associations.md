# Associations

There are two types of associations that can be created with fulibScenarios: unidirectional and bidirectional.
With unidirectional associations, only one object knows about the other.
Bidirectional assocations are linked so that both objects always have a reference to the other.
fulibScenarios ensures this even when one side changes.
This is called referential integrity.

## Unidirectional Associations

Let's suppose we already defined the following objects:

```scenario
There is the University Uni Kassel.
There is a Person with name Peter.
There are Persons with name Alice and Bob.
```

```java
University uniKassel = new University();
Person peter = new Person();
peter.setName("Peter");
Person alice = new Person();
Person bob = new Person();
alice.setName("Alice");
bob.setName("Bob");
```

A unidirectional to-one association is declared like a regular attribute.

```scenario
Uni Kassel has president Peter.
```

```java
uniKassel.setPresident(peter);
```

You can create to-many associations by listing multiple values, separated by ',' or 'and'.

```scenario
The Uni Kassel has employees Alice and Bob.
```

```java
uniKassel.withEmployees(alice, bob);
```

## Bidirectional Associations

Again, we start with some objects:

```scenario
There is the University Uni Kassel.
There are Students with name Alice, Bob, Charlie, Dude.
```

```java
University uniKassel = new University();
Student alice = new Student();
Student bob = new Student();
Student charlie = new Student();
Student dude = new Student();
alice.setName("Alice");
bob.setName("Bob");
charlie.setName("Charlie");
dude.setName("Dude");
```

A bidirectional association requires you to specify the name of the reverse role.
You can do that by adding `and is <name> of`.

```scenario
Uni Kassel has students and is uni of Alice, Bob, Charlie, Dude.
```

```java
uniKassel.withStudents(alice, bob, charlie, dude);
```

You can also define the association between Uni Kassel and Alice like this:

```scenario
Alice has uni and is one of the students of the Uni Kassel.
```

```java
alice.setUni(uniKassel);
```

Note that `one of` indicates that the reverse role is to-many.

## Special Associations

Once again, we need some objects for context:

```scenario
There are Students with name Alice, Bob, Charlie, Dude.
```

```java
Student alice = new Student();
Student bob = new Student();
Student charlie = new Student();
Student dude = new Student();
alice.setName("Alice");
bob.setName("Bob");
charlie.setName("Charlie");
dude.setName("Dude");
```

Associations can also target the original class:

```scenario
Alice has right-neighbor and is left-neighbor of Bob.
```

```java
alice.setRightNeighbor(bob);
```

You can also create an association whose reverse is itself:

```scenario
Charlie has best-friend and is best-friend of Dude.
```

```java
charlie.setBestFriend(dude);
```

This also works with to-many associations:

```scenario
Bob has friends and is one of the friends of Alice and Charlie.
```

```java
bob.withFriends(alice, charlie);
```
