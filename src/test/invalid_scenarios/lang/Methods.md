# External Methods

There is a WebApp app.
We call run on app.
<!--    ^
error: cannot resolve or add method 'run' in external class 'WebApp' [method.unresolved.external]
-->

# Invalid Calls

We call foo with bar 1 and with baz 2.

We call foo with bar 3.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  bar
-->

We call foo with baz 4.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  baz
-->

We call foo.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  
-->
