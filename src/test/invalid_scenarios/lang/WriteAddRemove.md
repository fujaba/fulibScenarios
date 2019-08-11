# Invalid Write, Add and Remove

We write 1 into 2.
<!--       ^
error: invalid write target - cannot write into IntLiteral [write.target.invalid]
-->

We add 1 to 2.
<!--     ^
error: invalid add target - cannot add to IntLiteral [add.target.invalid]
-->

We remove 3 from 4.
<!--        ^
error: invalid remove target - cannot remove from IntLiteral [remove.target.invalid]
-->
