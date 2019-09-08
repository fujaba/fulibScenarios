# Deprecated Syntax

There is a Student alice.
<!--     ^
warning: the 'a <type> <name>' syntax is deprecated and will be unsupported in v0.9.0 [descriptor.indefinite.deprecated]
write 'the Student alice' instead
-->

There are Students bob, charlie, dude.
<!--      ^
warning: the '<type>s <names>' syntax is deprecated and will be unsupported in v0.9.0 [descriptor.multi.indefinite.deprecated]
write 'the Students bob charlie dude' instead
-->

# Invalid Redeclarations

There are the Students Alice, Bob, Charlie and Dude.

There is the Student alice.
<!--                 ^
error: invalid redeclaration of 'alice' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

There is a StudentWrapper with student alice.
<!--                                   ^
error: invalid redeclaration of 'alice' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

Finn, Emil and Dude are Students.
<!--           ^
error: invalid redeclaration of 'dude' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

We create the Student Bob.
<!--                  ^
error: invalid redeclaration of 'bob' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->
