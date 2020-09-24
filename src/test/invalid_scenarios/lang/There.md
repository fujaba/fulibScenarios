# Deprecated Syntax

There is a Student alice.
<!--     ^
warning: the 'a <type> <name>' syntax is deprecated [descriptor.indefinite.deprecated]
         ^
note: write 'the Student alice' instead [descriptor.indefinite.deprecated.hint]
-->

There are Students bob, charlie, dude.
<!--      ^
warning: the '<type>s <names>' syntax is deprecated [descriptor.multi.indefinite.deprecated]
          ^
note: write 'the Students bob charlie dude' instead [descriptor.indefinite.deprecated.hint]
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
                     ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

There is a StudentWrapper with student alice.
<!--                                   ^
error: invalid redeclaration of 'alice' [variable.redeclaration]
                                       ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

Finn, Emil and Dude are Students.
<!--           ^
error: invalid redeclaration of 'dude' [variable.redeclaration]
               ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

We create the Student Bob.
<!--                  ^
error: invalid redeclaration of 'bob' [variable.redeclaration]
                      ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

(#158) Alice is a Student.
<!--   ^
error: invalid redeclaration of 'alice' [variable.redeclaration]
       ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

(#159) InvalidRedeclarations is a Student.
<!--   ^
error: invalid redeclaration of 'invalidRedeclarations' [variable.redeclaration]
       ^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
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
