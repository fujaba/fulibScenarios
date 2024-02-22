
# Invalid Self-Associations

There are SelfEntity with name Alice, Bob, Charlie.
Alice has friend and is one of the friend of bob.
<!--      ^^^^^^
error: mismatching cardinalities of self-association 'SelfEntity.friend' [association.self.cardinality.mismatch]
-->

Charlie has enemies and is enemies of alice, bob.
<!--        ^^^^^^^
error: mismatching cardinalities of self-association 'SelfEntity.enemies' [association.self.cardinality.mismatch]
-->

# Invalid Reverse

There are the AssocReverseEntity Alice, Bob, Charlie.

Alice has related Bob.
<!--      ^^^^^^^
note: 'AssocReverseEntity.related' was first declared here [property.declaration.first]
-->

Bob has related and is reverse-related of Charlie.
<!--                   ^^^^^^^^^^^^^^^
error: cannot define reverse association name 'reverseRelated' for unidirectional association 'AssocReverseEntity.related' after the first declaration [association.reverse.late]
-->

Alice has parent and is child of Bob.
<!--                    ^^^^^
note: 'AssocReverseEntity.child' was first declared here [property.declaration.first]
-->

Bob has parent and is kid of Charlie.
<!--                  ^^^
error: conflicting redeclaration of reverse association of 'AssocReverseEntity.parent' [association.reverse.conflict]
                      ^^^
note: was: AssocReverseEntity.child, association to one 'AssocReverseEntity' [conflict.old]
                      ^^^
note: now: AssocReverseEntity.kid, association to one 'AssocReverseEntity' [conflict.new]
-->

Bob has parent and is one of the child of Charlie.
<!--                             ^^^^^
error: conflicting redeclaration of reverse association of 'AssocReverseEntity.parent' [association.reverse.conflict]
                                 ^^^^^
note: was: AssocReverseEntity.child, association to one 'AssocReverseEntity' [conflict.old]
                                 ^^^^^
note: now: AssocReverseEntity.child, association to many 'AssocReverseEntity' [conflict.new]
-->

# Invalid Redeclaration

There are the AssocReEntity Alice, Bob, Charlie.
Alice has related Bob.
<!--      ^^^^^^^
note: 'AssocReEntity.related' was first declared here [property.declaration.first]
-->

Alice has related and is reverse-related of Charlie.
<!--                     ^^^^^^^^^^^^^^^
error: cannot define reverse association name 'reverseRelated' for unidirectional association 'AssocReEntity.related' after the first declaration [association.reverse.late]
-->

Alice has intAttr 123.
<!--      ^^^^^^^
note: 'AssocReEntity.intAttr' was first declared here [property.declaration.first]
-->

Bob has intAttr Charlie.
<!--    ^^^^^^^
error: conflicting redeclaration of 'AssocReEntity.intAttr' [property.redeclaration.conflict]
        ^^^^^^^
note: was: attribute of one 'int' [conflict.old]
        ^^^^^^^
note: now: association to one 'AssocReEntity' [conflict.new]
-->

Charlie has related alice, bob.
<!--        ^^^^^^^
error: conflicting redeclaration of 'AssocReEntity.related' [property.redeclaration.conflict]
            ^^^^^^^
note: was: association to one 'AssocReEntity' [conflict.old]
            ^^^^^^^
note: now: association to many 'AssocReEntity' [conflict.new]
-->

There is a AssocReEntityOther.
Charlie has related AssocReEntityOther.
<!--        ^^^^^^^
error: conflicting redeclaration of 'AssocReEntity.related' [property.redeclaration.conflict]
            ^^^^^^^
note: was: association to one 'AssocReEntity' [conflict.old]
            ^^^^^^^
note: now: association to one 'AssocReEntityOther' [conflict.new]
-->

## External Associations

// TODO: Find an alternative to WebApp/Page for this.
// There is a WebApp.
// There is a Page.
// WebApp has foo and is bar of Page.
// <!--       ^^^
// error: cannot resolve association 'foo' in external class 'WebApp' [association.unresolved.external]
// -->
// 
// There is a NonExternal.
// NonExternal has webapp and is nonExternal of WebApp.
// <!--                          ^^^^^^^^^^^
// error: cannot resolve association 'nonExternal' in external class 'WebApp' [association.unresolved.external]
// -->
