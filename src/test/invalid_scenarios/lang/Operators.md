# Invalid Operators

We expect that has value 4.
<!--           ^
error: invalid attribute check - missing receiver [attribute-check.receiver.missing]
-->

We expect that is less than 1.
<!--           ^
error: invalid conditional operator - missing left-hand expression [conditional.lhs.missing]
-->

We expect that is empty.
<!--           ^
error: invalid predicate operator - missing left-hand expression [predicate.lhs.missing]
-->

# Invalid Ranges

We write a1
<!--     ^
error: invalid range operator - unsupported element type 'String' [range.element.type.unsupported]
-->
  to a4 into range.
<!-- ^
error: invalid range operator - unsupported element type 'String' [range.element.type.unsupported]
-->

We write 1 to
<!--       ^
error: mismatching range element types [range.element.type.mismatch]
lower bound: int
upper bound: double
-->
(  ) 2.5 into range.
<!-- ^
error: invalid range operator - unsupported element type 'double' [range.element.type.unsupported]
-->
