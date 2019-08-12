
# Invalid Self-Associations

There are SelfEntity with name Alice, Bob, Charlie.
Alice has friend and is one of the friend of bob.
<!--      ^
error: mismatching cardinalities of self-association 'SelfEntity.friend' [association.self.cardinality.mismatch]
-->

Charlie has enemies and is enemies of alice, bob.
<!--        ^
error: mismatching cardinalities of self-association 'SelfEntity.enemies' [association.self.cardinality.mismatch]
-->

# Invalid Reverse

There are the AssocReverseEntity Alice, Bob, Charlie.

Alice has related Bob.
Bob has related and is reverse-related of Charlie.
<!--                   ^
error: invalid reverse association name 'reverseRelated' - 'AssocReverseEntity.related' was already declared as unidirectional [association.reverse.late]
-->

Alice has parent and is child of Bob.
Bob has parent and is kid of Charlie.
<!--                  ^
error: conflicting redeclaration of reverse association of 'AssocReverseEntity.parent' [association.reverse.conflict]
was: AssocReverseEntity.child, association to one 'AssocReverseEntity'
now: AssocReverseEntity.kid, association to one 'AssocReverseEntity'
-->

Bob has parent and is one of the child of Charlie.
<!--                             ^
error: conflicting redeclaration of reverse association of 'AssocReverseEntity.parent' [association.reverse.conflict]
was: AssocReverseEntity.child, association to one 'AssocReverseEntity'
now: AssocReverseEntity.child, association to many 'AssocReverseEntity'
-->

# Invalid Redeclaration

There are the AssocReEntity Alice, Bob, Charlie.
Alice has related Bob.

Alice has related and is reverse-related of Charlie.

Alice has intAttr 123.
Bob has intAttr Charlie.
<!--    ^
error: conflicting redeclaration of 'AssocReEntity.intAttr' [property.redeclaration.conflict]
was: attribute of one 'int'
now: association to one 'AssocReEntity'
-->

Charlie has related alice, bob.
<!--        ^
error: conflicting redeclaration of 'AssocReEntity.related' [property.redeclaration.conflict]
was: association to one 'AssocReEntity'
now: association to many 'AssocReEntity'
-->

There is a AssocReEntityOther.
Charlie has related AssocReEntityOther.
<!--        ^
error: conflicting redeclaration of 'AssocReEntity.related' [property.redeclaration.conflict]
was: association to one 'AssocReEntity'
now: association to one 'AssocReEntityOther'
-->
