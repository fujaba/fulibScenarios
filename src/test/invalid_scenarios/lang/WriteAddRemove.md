# Invalid Write Targets

We write 1 into 2.
<!--            ^
error: invalid write target - cannot write into IntLiteral [write.target.invalid]
-->

# Invalid Type Conversions

We write 1 into i.
We write 1,2,3 into i.
<!--     ^
error: cannot assign expression of type 'list of int' to variable 'i' of type 'int' [assign.type]
-->

There is a Student.
We write the Student into i.
<!--         ^
error: cannot assign expression of type 'Student' to variable 'i' of type 'int' [assign.type]
-->

# Invalid Add

We add 1 to 2.
<!--        ^
error: cannot add to 'IntLiteral' - must be a name or attribute access [add.target.not.name]
-->

There are the Objects o1,o2.
We add o1 to 1,2,3.
<!--   ^
error: cannot add expression of type 'Object' to 'list of int' [add.source.type]
-->

We add o1, o2 to 1,2,3.
<!--   ^
error: cannot add expression of type 'list of Object' to 'list of int' [add.source.type]
-->

# Invalid Remove

We remove 3 from 4.
<!--             ^
error: cannot remove from expression of type 'int' [remove.target.type]
-->

There are the Objects o1,o2.
We remove o1 from 1,2,3.
<!--      ^
error: cannot remove expression of type 'Object' from 'list of int' [remove.source.type]
-->

We remove o1, o2 from 1,2,3.
<!--      ^
error: cannot remove expression of type 'list of Object' from 'list of int' [remove.source.type]
-->
