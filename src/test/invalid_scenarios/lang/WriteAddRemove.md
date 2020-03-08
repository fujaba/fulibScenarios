# Invalid Write Targets

We write 1 into 2.
<!--            ^
error: invalid write target - cannot write into IntLiteral [write.target.invalid]
-->

# Write to Multi-Valued

(#157)

There is the WMVStudent stud with values 1,2,3.
We write 4,5,6 into values of stud.
<!--                ^
error: cannot write into attribute of many 'int' - only single-valued attributes and associations are allowed [write.target.list]
-->

There is the WMVUni uni with students stud,stud,stud.
We write stud into students of uni.
<!--               ^
error: cannot write into association to many 'WMVStudent' - only single-valued attributes and associations are allowed [write.target.list]
-->

We write stud,stud into students of uni.
<!--                    ^
error: cannot write into association to many 'WMVStudent' - only single-valued attributes and associations are allowed [write.target.list]
-->

# Invalid Type Conversions

We write 1 into i.
<!--            ^
note: 'i' was first declared here [variable.declaration.first]
-->

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

We add "a" to "b".
<!--          ^
error: cannot add to expression of type 'String' [add.target.type]
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

We remove 1 from 2.
<!--             ^
error: cannot remove from 'IntLiteral' - must be a name or attribute access [remove.target.not.name]
-->

We remove "a" from "b".
<!--               ^
error: cannot remove from expression of type 'String' [remove.target.type]
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

// TODO hint examples
