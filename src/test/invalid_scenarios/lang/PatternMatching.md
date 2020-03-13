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
