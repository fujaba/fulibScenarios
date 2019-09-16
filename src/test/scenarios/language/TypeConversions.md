# Type Conversions

There is a TCHolder with i 1.
The TCHolder has i "12".
We write "123" into i of TCHolder.
We write 1 into i.
We write "2" into i.

We write 1,2,3 into ilist.
We add "4","5","6" to ilist.
We add "7",8,"9" to ilist.
We add "10" to ilist.

We write "4" into str.
We write str into i.

We write str into i of TCHolder.
We write str,"3" into ilist.

# Calls

We call foo with i 1.
As i is 1, Foo answers with i. (ensure return type int)

We call foo with i "123". (conversion of argument)
Foo answers with "-1". (conversion of result)
