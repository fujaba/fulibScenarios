# Definition Sentences

## Is Sentences

Is Sentences are the simplest way to define objects. They define a type, a name and various attributes. Is Sentences can be used in tests as well as calls.

{% tabs %}
{% tab title="Scenario" %}
```markup
Kassel is a City.

Frankfurt is a City with 750000 inhabitants.
```
{% endtab %}

{% tab title="Java" %}
```markup
City kassel = new City();

City frankfurt = new City().setInhabitants(750000);
```
{% endtab %}
{% endtabs %}

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

{% tabs %}
{% tab title="Scenario" %}
```markup
Kassel has postcode 34117.
Kassel has 200000 inhabitants.
Kassel has area 106.78 (sq km).
```
{% endtab %}

{% tab title="Java" %}
```markup
kassel.setPostcode(34117);
kassel.setInhabitants(200000);
kassel.setArea(106.78);
```
{% endtab %}
{% endtabs %}

```markup
<hasSentence> ::= <nameAccess> <hasClauses> .

<hasClauses> ::= <hasClause> (<sep> <hasClause>)*
<hasClause> ::= has <namedExpr>
```

## There Sentences

There Sentences are similar to Is Sentences. In addition to being only usable in tests, There Sentences have the following extra features on top of the functionaly provided by Is Sentences:

* You can omit the name of the object. In this case, it will be inferred from the first string attribute, or the class name.

{% tabs %}
{% tab title="Scenarios" %}
```text
There is the university.
There is a student.
There is a student with name Karli.
There is a student with age 21 and with name Anna.
```
{% endtab %}

{% tab title="Java" %}
```
University university = new University();
Student student = new Student();
Student karli = new Student().setName("Karli");
Student anna = new Student().setAge(21).setName("Anna");
```
{% endtab %}
{% endtabs %}

The equivalent sentences with an explicit name are:

```markup
There is the university university.
There is the student student.
There is the student karli with name Karli.
There is the student anna with age 21 and with name Anna.
```

{% hint style="info" %}
Note the use of `the` instead of `a` in these examples. In definitions, you use `the` when you give an object an explicit name, and `a` when you use an inferred name.
{% endhint %}

* You can declare multiple objects at once. This saves you from typing the class name and the attribute names multiple times when you want to create multiple objects with of the same type and with the same attribute. If you use the same number of values for every attribute, they are assigned to the objects in order \(second example\). If you have only one value of an attribute, this value is assigned to the attribute of each object \(third example\). Note that the number of generated lines grows quickly with the number of attributes and objects. You may want to split a There Sentence with many objects into multiple sentences for readability.

{% tabs %}
{% tab title="Scenarios" %}
```text
There are students with names Karli and Anna.




There are students with names Bob, Charlie and David and with credits 10, 20, 30.









There are students with names Emil and Fred and with 40 credits.






```
{% endtab %}

{% tab title="Java" %}
```text
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
{% endtab %}
{% endtabs %}

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

{% hint style="info" %}
You get the equivalent Create Sentence from a There Sentence by replacing `There is` / `There are` with `We create` and vice-versa.
{% endhint %}

```markup
<createSentence> ::= <actor> create(s) <descriptor> .
```

