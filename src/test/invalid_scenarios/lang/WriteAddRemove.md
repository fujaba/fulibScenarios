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
error: invalid add target - cannot add to expression of type 'int' [add.target.invalid]
-->

# Invalid Remove

We remove 3 from 4.
<!--             ^
error: invalid remove target - cannot remove from expression of type 'int' [remove.target.invalid]
-->
