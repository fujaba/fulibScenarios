# Invalid Associations

There is a Student alice.

Alice has name and is student of 'Alice'.
<!--                  ^
error: invalid reverse association name 'student' - 'name' is an attribute, not an association
-->

Alice has grades and is student of 1, 2, 3.
<!--                    ^
error: invalid reverse association name 'student' - 'grades' is a multi-attribute, not an association
-->
