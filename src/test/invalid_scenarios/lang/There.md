# Deprecated Syntax

There is a Student alice.
<!--     ^^^^^^^^^^^^^^^
warning: the 'a <type> <name>' syntax is deprecated [descriptor.indefinite.deprecated]
         ^^^^^^^^^^^^^^^
note: write 'the Student alice' instead [descriptor.indefinite.deprecated.hint]
-->

There are Students bob, charlie, dude.
<!--      ^^^^^^^^^^^^^^^^^^^^^^^^^^^
warning: the '<type>s <names>' syntax is deprecated [descriptor.multi.indefinite.deprecated]
          ^^^^^^^^^^^^^^^^^^^^^^^^^^^
note: write 'the Students bob charlie dude' instead [descriptor.indefinite.deprecated.hint]
-->

   # Invalid Redeclarations
<!--^^^^^^^^^^^^^^^^^^^^^^^
note: 'invalidRedeclarations' was first declared here [variable.declaration.first]
-->

There are the Students Alice, Bob, Charlie and Dude.
<!--                   ^^^^^
note: 'alice' was first declared here [variable.declaration.first]
                              ^^^
note: 'bob' was first declared here [variable.declaration.first]
                                               ^^^^
note: 'dude' was first declared here [variable.declaration.first]
-->

There is the Student alice.
<!--                 ^^^^^
error: invalid redeclaration of 'alice' [variable.redeclaration]
                     ^^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

There is a StudentWrapper with student alice.
<!--                                   ^^^^^
error: invalid redeclaration of 'alice' [variable.redeclaration]
                                       ^^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

Finn, Emil and Dude are Students.
<!--           ^^^^
error: invalid redeclaration of 'dude' [variable.redeclaration]
               ^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

We create the Student Bob.
<!--                  ^^^
error: invalid redeclaration of 'bob' [variable.redeclaration]
                      ^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

(#158) Alice is a Student.
<!--   ^^^^^
error: invalid redeclaration of 'alice' [variable.redeclaration]
       ^^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

(#159) InvalidRedeclarations is a Student.
<!--   ^^^^^^^^^^^^^^^^^^^^^
error: invalid redeclaration of 'invalidRedeclarations' [variable.redeclaration]
       ^^^^^^^^^^^^^^^^^^^^^
note: perhaps this name was inferred from the first attribute and you need to give this object an explicit name? [variable.redeclaration.hint]
-->

# has.subject.primitive

(#188) There is an object with name o1.
<!--                                ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) There is an object with number 1.
<!--               ^^^^^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) We create the object o2 with name o2.
<!--                        ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) We create the object o3 with number 2.
<!--                        ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) O4 and O5 are Objects with name 4 and 5.
<!--   ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
              ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) O6 and O7 are Objects with number 6 and 7.
<!--   ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
              ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) There are Objects with name o8 and o9.
<!--                               ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
                                          ^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

(#188) There are Objects with number 8 and 9.
<!--             ^^^^^^^
error: cannot set attributes for object of primitive type 'Object' [has.subject.primitive]
-->

# create.subject.primitive.attributes

(#190) O1 is an Object with name o1.
<!--            ^^^^^^
error: cannot instantiate primitive type 'Object' with attributes [create.subject.primitive.attributes]
-->

(#190) O2 is an Object with number 2.
<!--            ^^^^^^
error: cannot instantiate primitive type 'Object' with attributes [create.subject.primitive.attributes]
-->
