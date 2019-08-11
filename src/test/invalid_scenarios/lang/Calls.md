# Invalid Calls

We call foo with bar 1 and with baz 2.

We call foo with bar 3.
<!--    ^
error: mismatching parameters and arguments: [call.mismatch.params.args]
parameters: bar baz
arguments : bar
-->

We call foo with baz 4.
<!--    ^
error: mismatching parameters and arguments: [call.mismatch.params.args]
parameters: bar baz
arguments : baz
-->

We call foo.
<!--    ^
error: mismatching parameters and arguments: [call.mismatch.params.args]
parameters: bar baz
arguments : 
-->
