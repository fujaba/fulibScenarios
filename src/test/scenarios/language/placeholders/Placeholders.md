# Placeholders

// "Every X" acts as a placeholder for a concrete object.
// "a Y of type Z" is a placeholder for a concrete attribute value.
// None of the lines below generate anything in the test method.

Every student has a name of type string.
// Every student has an age of type int.
Every student has a motivation of type double.

Every university has students and is uni of many Students.
Every student has uni and is one of the students of a University.

// The placeholders can be combined with concrete objects.

There are the Students Alice and Bob.
There is the University StudyRight.

// None of the lines below generate anything in the test method.

Alice has a name of type string.
// Alice has an age of type int.
Alice has a motivation of type double.

StudyRight has students and is uni of many Students.
Alice has uni and is one of the students of a University.

// You can optionally supply examples with 'like':

Every student like Alice has a name of type string.
// Every student like Alice has an age of type int.
Every student like Alice has a motivation of type double.

StudyRight has students and is uni of many Students like Alice and Bob.
Alice has uni and is one of the students of a University like StudyRight.

// For attributes, you can also keep the example and remove the type.
// None of the lines below generate anything in the test method.

Every student has a name like "Alice".
// Every student has an age like 20.
Every student has a motivation like 12.3.
