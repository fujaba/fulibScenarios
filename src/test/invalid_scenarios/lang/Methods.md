# External Methods

There is the WebApp app.
We call run on app.
<!--    ^^^
error: cannot resolve or add method 'run' in external class 'WebApp' [method.unresolved.external]
-->

# Primitive Receiver

We call foo on bar.
<!--           ^^^
error: cannot call method 'foo' on receiver of primitive type 'String' [call.receiver.primitive]
-->

We call foo on bar of moo.
<!--           ^^^
error: unresolved attribute or association 'String.bar' - 'String' is a primitive type [property.unresolved.primitive]
-->

## Hints

There is the PrimitiveReceiver primrec.
We call foo on primrc.
<!--           ^^^^^^
error: cannot call method 'foo' on receiver of primitive type 'String' [call.receiver.primitive]
               ^^^^^^
note: perhaps you meant to refer to 'primrec' instead of the string literal 'primrc'? [stringliteral.typo]
-->

# Mismatching Parameters and Arguments

We call foo with bar 1 and with baz "2".
<!--    ^^^
note: 'MethodsTest.foo' was first declared here [property.declaration.first]
-->

We call foo with bar 3.
<!--    ^^^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  bar
-->

We call foo with baz "4".
<!--    ^^^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  baz
-->

We call foo.
<!--    ^^^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  
-->

We call foo with bar 5 and with baz "6" and with moo 7.
<!--    ^^^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  bar baz moo
-->

We call foo with baz "8" and with bar 9.
<!--    ^^^
error: mismatching parameters and arguments of method 'MethodsTest.foo' [call.mismatch.params.args]
parameters: bar baz
arguments:  baz bar
-->

# Mismatching Parameter Types

There are the Objects o1, o2.
We call foo with bar o1 and with baz o1, o2.
<!--                 ^^
error: incompatible parameter and argument types [call.mismatch.type]
parameter type: int
argument type:  Object
-->

# Incompatible Return Type

We call bar.
Bar answers with 1.

We call bar.
Bar creates the Object o1 and bar answers with o1.
<!--                                           ^^
error: cannot return expression of type 'Object' from method 'bar' with return type 'int' [call.return.type]
-->

# Invalid Answer Literal

We expect that the answer is 42.
<!--               ^^^^^^
error: invalid answer literal - no preceding call [answer.unresolved]
-->

# Invalid Answer Literal after void Call

We call moo.
Moo answers with 1 into x.

We expect that the answer is 1.

We call voidMethod.
We write the answer into y.
<!--         ^^^^^^
error: invalid answer literal - no preceding call [answer.unresolved]
-->

# Invalid Call Frames

(  ) barbaz writes 2 into j.
<!-- ^^^^^^
error: unknown actor 'barbaz' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->

We call foobar.
foobar answers with 1.

(  ) foobar writes 1 into i.
<!-- ^^^^^^
error: unknown actor 'foobar' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->

(  ) moobaz answers with 1.
<!-- ^^^^^^
error: unknown actor 'moobaz' [frame.incompatible.actor]
perhaps you did not call the method or the call was already closed?
-->

We   answer with 1.
<!-- ^^^^^^
error: cannot answer from the test method indicated by actor 'we' [answer.we]
-->

We call foo2.
We   answer with 2.
<!-- ^^^^^^
error: cannot answer from the test method indicated by actor 'we' [answer.we]
perhaps you meant to write 'foo2 answers ...' instead of 'we answer ...'?
-->
