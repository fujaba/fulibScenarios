# Link Constraint

There is a LinkConstraintObj with name Test.
We match some object test1 with some link to test.
<!--                                         ^
error: link target 'test' is not a pattern object [link-constraint.target.not.pattern-object]
-->

We match some object test2 with some link to foo.
<!--                                         ^
error: unresolved link target 'foo' [link-constraint.target.unresolved]
-->

# Redeclaration

There is the object test2.
<!--                ^
note: 'test2' was first declared here [variable.declaration.first]
-->

We match some object test2.
<!--                 ^
error: invalid redeclaration of 'test2' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

# Duplicate Pattern Object Name

We match:
- some object test3
<!--          ^
note: 'test3' was first declared here [pattern.object.first]
-->
- some object test3.
<!--          ^
error: duplicate pattern object name 'test3' [pattern.object.duplicate]
-->
