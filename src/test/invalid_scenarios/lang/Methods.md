# External Methods

There is the WebApp app.
We call run on app.
<!--    ^
error: cannot resolve or add method 'run' in external class 'WebApp' [method.unresolved.external]
-->

# Mismatching Parameters and Arguments

We call foo with bar 1 and with baz "2".

We call foo with bar 3.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  bar
-->

We call foo with baz "4".
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

We call foo with bar 5 and with baz "6" and with moo 7.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  bar baz moo
-->

We call foo with baz "8" and with bar 9.
<!--    ^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  baz bar
-->

# Mismatching Parameter Types

We call foo with bar "a"
<!--                 ^
error: incompatible parameter and argument types [call.mismatch.type]
parameter type: int
argument type:  String
-->
and with baz 1.5, 2.5, 3.5.
<!--         ^
error: incompatible parameter and argument types [call.mismatch.type]
parameter type: String
argument type:  list of double
-->

# Invalid Answer Literal

We expect that the answer is 42.
<!--               ^
error: invalid answer literal - no preceding call [answer.unresolved]
-->

# Invalid Answer Literal after void Call

We call moo.
Moo answers with 1 into x.

We expect that the answer is 1.

We call voidMethod.
We write the answer into y.
<!--         ^
error: invalid answer literal - no preceding call [answer.unresolved]
-->

# Invalid Call Frames

(  ) barbaz writes 2 into j.
<!-- ^
error: unknown actor 'barbaz' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->

We call foobar.
foobar answers with 1.

(  ) foobar writes 1 into i.
<!-- ^
error: unknown actor 'foobar' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->

(  ) moobaz answers with 1.
<!-- ^
error: unknown actor 'moobaz' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->
