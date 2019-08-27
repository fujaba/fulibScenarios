# Helper Minus 1

We call minus1 with n 5.
minus1 writes 0 into result.
minus1 takes an i from 1 to n and as i is not n, minus1 writes i into result.
minus1 answers with result.
We expect that the answer is 4.

We call minus1 with n 10.
We expect that the answer is 9.

We call minus1 with n 0.
We expect that the answer is 0.

# Helper Move Direct

There are pegs with name source and target.

There are disks with id d1, d2.
d1 has below and is above of d2.
Source has top d1.

![source, target](move-direct-1.svg)

We call moveDirect with source source and with target target.
moveDirect writes top of source into top.
moveDirect writes below of top into top of source.
moveDirect writes top of target into below of top.
moveDirect writes top into top of target.

![source, target](move-direct-2.svg)

We call moveDirect with source source and with target target.

![source, target](move-direct-3.svg)

# Towers of Hanoi

There are disks with id d1, d2, d3, d4, d5.

d1 has below and is above of d2.
d2 has below d3.
d3 has below d4.
d4 has below d5.

There are pegs with name source, spare, target.

source has top d1.

![source, spare, target](before.svg)

We call moveRecur with n 5 and with source source and with spare spare and with target target.

moveRecur calls minus1 with n n.
moveRecur writes the answer into n1.
As n is greater than 0,
moveRecur calls moveRecur with n n1, with source source, with spare target, with target spare.
As n is greater than 0,
moveRecur calls moveDirect with source source and with target target.
As n is greater than 0,
moveRecur calls moveRecur with n n1, with source spare, with spare source, with target target.

![source, spare, target](after.svg)
