# Unresolved Properties

There is a PropertyEntity.
We write invalid of propertyEntity into x.
<!--     ^
error: unresolved attribute or association 'PropertyEntity.invalid' [property.unresolved]
-->

We write invalid of 'some string' into y.
<!--     ^
error: unresolved attribute or association 'String.invalid' - 'String' is a primitive type [property.unresolved.primitive]
-->

There are Students with name Alice, Charlie, Bob.
We write Alice, Charlie, Bob into students.
We write names of students into names.
<!--     ^
error: unresolved attribute or association 'Student.names' [property.unresolved]
-->

We write names of studens into invalidNames.
<!--     ^
error: unresolved attribute or association 'String.names' - 'String' is a primitive type [property.unresolved.primitive]
                  ^
note: perhaps you meant to refer to 'students' instead of the string literal 'studens'? [stringliteral.typo]
-->

We write 1,2,3 into numbers.
We write names of numbers into names.
<!--     ^
error: unresolved attribute or association 'int.names' - 'int' is a primitive type [property.unresolved.primitive]
-->

# Invalid Redeclaration

There is the Object o1.
There is the AttributeEntity entity-foo with id 123.
<!--                                         ^
note: 'AttributeEntity.id' was first declared here [property.declaration.first]
-->

There is the AttributeEntity entity-123 with id o1 and with friend entity-foo.
<!--                                         ^
error: conflicting redeclaration of 'AttributeEntity.id' [property.redeclaration.conflict]
was: attribute of one 'int'
now: attribute of one 'Object'
                                                            ^
note: 'AttributeEntity.friend' was first declared here [property.declaration.first]
-->

There is an AttributeEntity with id bar and with friend 123.
<!--                                             ^
error: conflicting redeclaration of 'AttributeEntity.friend' [property.redeclaration.conflict]
was: association to one 'AttributeEntity'
now: attribute of one 'int'
-->

# Invalid Reverse Name

There is the ReverseStudent alice.

Alice has uni and is student of Uni Kassel.
<!--                 ^
error: invalid reverse association name 'student' - 'ReverseStudent.uni' is an attribute, not an association [attribute.reverse.name]
-->

Alice has grades and is student of 1, 2, 3.
<!--                    ^
error: invalid reverse association name 'student' - 'ReverseStudent.grades' is an attribute, not an association [attribute.reverse.name]
                                   ^
note: elements of list expression have common type 'int' [list.type]
-->

## Hints

There is the ReverseStudent Bob.

Bob has friend and is friend of Alic and Alce.
<!--                  ^
error: invalid reverse association name 'friend' - 'ReverseStudent.friend' is an attribute, not an association [attribute.reverse.name]
                                ^
note: elements of list expression have common type 'String' [list.type]
                                ^
note: perhaps you meant to refer to 'alice' instead of the string literal 'Alic'? [stringliteral.typo]
                                         ^
note: perhaps you meant to refer to 'alice' instead of the string literal 'Alce'? [stringliteral.typo]
-->

# Primitive Has Subject

(   ) Asd has next 2.
<!--  ^
error: invalid has sentence - subject has primitive type 'String' [has.subject.primitive]
-->

## Hints

There is a HasPrimitivePerson with name Erbi.
There is a HasPrimitivePerson with name Herba.
There is a HasPrimitivePerson with name Ernie.
There is a HasPrimitivePerson with name Erni.

There is a HasPrimitiveCar with name Herbie.

(  ) Herbi has speed 100.
<!-- ^
error: invalid has sentence - subject has primitive type 'String' [has.subject.primitive]
     ^
note: perhaps you meant to refer to 'herbie' instead of the string literal 'Herbi'? [stringliteral.typo]
     ^
note: perhaps you meant to refer to 'erni' instead of the string literal 'Herbi'? [stringliteral.typo]
     ^
note: perhaps you meant to refer to 'erbi' instead of the string literal 'Herbi'? [stringliteral.typo]
     ^
note: perhaps you meant to refer to 'herba' instead of the string literal 'Herbi'? [stringliteral.typo]
-->
