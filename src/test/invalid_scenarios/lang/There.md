# Deprecated Syntax

There is a Student alice.
<!--     ^
warning: the 'a <type> <name>' syntax is deprecated [descriptor.indefinite.deprecated]
write 'the Student alice' instead
-->

There are Students bob, charlie, dude.
<!--      ^
warning: the '<type>s <names>' syntax is deprecated [descriptor.multi.indefinite.deprecated]
write 'the Students bob charlie dude' instead
-->

   # Invalid Redeclarations
<!--^
note: 'invalidRedeclarations' was first declared here [variable.declaration.first]
-->

There are the Students Alice, Bob, Charlie and Dude.
<!--                   ^
note: 'alice' was first declared here [variable.declaration.first]
                              ^
note: 'bob' was first declared here [variable.declaration.first]
                                               ^
note: 'dude' was first declared here [variable.declaration.first]
-->

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

(#158) Alice is a Student.
<!--   ^
error: invalid redeclaration of 'alice' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

(#159) InvalidRedeclarations is a Student.
<!--   ^
error: invalid redeclaration of 'invalidRedeclarations' [variable.redeclaration]
perhaps this name was inferred from the first attribute and you need to give this object an explicit name?
-->

(#188) There is an object with name o1.
<!--                                ^
error: invalid has sentence - subject has primitive type 'Object' [has.subject.primitive]
-->

(#188) We create the object o2 with name o2.
<!--                        ^
error: invalid has sentence - subject has primitive type 'Object' [has.subject.primitive]
-->

(#190) O3 is an Object with name o3.
<!--            ^
error: primitive type 'Object' cannot be instantiated with attributes [create.subject.primitive.attributes]
-->
