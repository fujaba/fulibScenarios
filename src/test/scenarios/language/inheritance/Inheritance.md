# Inheritance

## Attributes

Every Person has a name of type String.
Every Student is a Person.
Every Student has a student-id of type int.

There is a Person with name Alice.
There is a Student with name Bob and with student-id 1234.

## Associations

// These examples make no sense whatsoever semantically.
// But the types check out :)

We write Alice into Alice2.
We write Bob into Bob2.

Every Person has best-friend and is the best-friend of a Person.

Alice has best-friend Alice2. (1-1 P-P)
Bob has best-friend Alice. (1-1 S-P)
Alice has best-friend Bob. (1-1 P-S)
Bob has best-friend Bob2. (1-1 S-S)

Every Person has children and is parent of many Persons.

Alice has children Alice2. (1-N P-P)
Bob has children Alice. (1-N S-P)
Alice has children Bob. (1-N P-S)
Bob has children Bob2. (1-N S-S)

Alice has parent Alice2. (N-1 P-P)
Bob has parent Alice. (N-1 S-P)
Alice has parent Bob. (N-1 P-S)
Bob has parent Bob2. (N-1 S-S)

Every Person has friends and is one of the friends of many Persons.

Alice has friends Alice2. (N-N P-P)
Bob has friends Alice. (N-N S-P)
Alice has friends Bob. (N-N P-S)
Bob has friends Bob2. (N-N S-S)
