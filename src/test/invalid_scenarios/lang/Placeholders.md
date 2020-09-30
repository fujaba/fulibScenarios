# has.placeholder.concrete

Every Student has name Alice.
<!--                   ^^^^^
error: cannot combine has sentence with 'Every' with a concrete value assignment [has.placeholder.concrete]
                       ^^^^^
note: to make this an example, write 'has a/an name like Alice' instead [has.placeholder.concrete.hint]
-->

There is a Student with name Alice.

Every Student like Alic has a name of type string.
<!--               ^^^^
error: placeholder example of type 'String' is not a subtype of explicit type 'Student' [placeholder.example.type.mismatch]
                   ^^^^
note: perhaps you meant to refer to 'alice' instead of the string literal 'Alic'? [stringliteral.typo]
-->

Every Student has friends and is one of the friends of many Students like Alice.
<!--                                                                      ^^^^^
error: placeholder example of type 'Student' is not a subtype of explicit type 'list of Student' [placeholder.example.type.mismatch]
-->
