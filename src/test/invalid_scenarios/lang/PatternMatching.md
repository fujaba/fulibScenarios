# No Roots in Scope

We   match some object test1.
<!-- ^^^^^
error: match has no root objects - no objects are in scope or declared with 'on ...' [match.no.roots]
-->

# Link Constraint

There is a LinkConstraintObj with name Test.
We match some object test1 with some link to test.
<!--                                         ^^^^
error: link target 'test' is not a pattern object [link-constraint.target.not.pattern-object]
-->

We match some object test2 with some link to foo.
<!--                                         ^^^
error: unresolved link target 'foo' [link-constraint.target.unresolved]
-->

# Redeclaration

There is the object test2.
<!--                ^^^^^
note: 'test2' was first declared here [variable.declaration.first]
-->

We match some object test2.
<!--                 ^^^^^
error: invalid redeclaration of 'test2' [variable.redeclaration]
                     ^^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

# Duplicate Pattern Object Name

There is a LinkConstraintObj.

We match:
- some object test3
<!--          ^^^^^
note: 'test3' was first declared here [pattern.object.first]
-->
- some object test3.
<!--          ^^^^^
error: duplicate pattern object name 'test3' [pattern.object.duplicate]
-->

# Illegal 'do/does not contain'

There is a Game.
There is a Player with name Alice.
Alice has game and is one of the players of game.

We match:
- some object g whose players does not contain p1
<!--                          ^^^^^^^^^^^^^^^^
error: conditional operator 'do/does not contain' cannot be used here [attribute-constraint.conditional.not-contains]
-->
- some object p1 where some attribute matches '[Aa]lice'.
