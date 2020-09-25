# Invalid Operators

We expect that has value 4.
<!--           ^
error: attribute check expression requires a receiver [attribute-check.receiver.missing]
-->

We expect that is less than 1.
<!--           ^
error: conditional operator requires a left-hand expression [conditional.lhs.missing]
-->

We expect that is empty.
<!--           ^
error: predicate operator requires a left-hand expression [predicate.lhs.missing]
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

We write 1 to 2.5 into range.
<!--       ^
error: mismatching range element types [range.element.type.mismatch]
         ^
note: lower bound: int [range.element.type.lower]
              ^
note: upper bound: double [range.element.type.upper]
              ^
error: invalid range operator - unsupported element type 'double' [range.element.type.unsupported]
-->
