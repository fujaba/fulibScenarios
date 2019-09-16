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

# Invalid Redeclaration

There is the Object o1.
There is the AttributeEntity entity-foo with id 123.
There is the AttributeEntity entity-123 with id o1 and with friend entity-foo.
<!--                                         ^
error: conflicting redeclaration of 'AttributeEntity.id' [property.redeclaration.conflict]
was: attribute of one 'int'
now: attribute of one 'Object'
-->

There is an AttributeEntity with id bar and with friend 123.
<!--                                             ^
error: conflicting redeclaration of 'AttributeEntity.friend' [property.redeclaration.conflict]
was: association to one 'AttributeEntity'
now: attribute of one 'int'
-->

# Invalid Reverse Name

There is the ReverseStudent alice.

Alice has name and is student of 'Alice'.
<!--                  ^
error: invalid reverse association name 'student' - 'ReverseStudent.name' is an attribute, not an association [attribute.reverse.name]
-->

Alice has grades and is student of 1, 2, 3.
<!--                    ^
error: invalid reverse association name 'student' - 'ReverseStudent.grades' is an attribute, not an association [attribute.reverse.name]
-->
