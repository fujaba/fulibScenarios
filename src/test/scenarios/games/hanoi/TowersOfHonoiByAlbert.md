# Towers of Hanoi

There are Towers with name left, middle, and right.
There are Disks with name d1, d2, and d3 and with size 1, 2, 3.
D1 has pos and is top of left.
D1 has below and is above of d2.
D2 has below d3.

![left, middle, right](step0.svg)

// move d1 from left to right
We call moveOne with start left and with target right.
MoveOne writes d1 from top of left into moving-disk.
MoveOne writes d2 from below of moving-disk into new-left-top.
MoveOne writes (empty from) top of right into new-below-disk.

As new-below-disk is empty or size of new-below-disk is greater than size of moving-disk,
MoveOne writes d1 from moving-disk into top of right and
MoveOne writes (empty from) new-below-disk into below of moving-disk and
MoveOne writes d2 from new-left-top into top of left.

![left, middle, right](step1.svg)

// move d2 from left to middle.
We call moveOne with start left and with target middle.
![left, middle, right](step2.svg)

// move d1 from right to middle.
We call moveOne with start right and with target middle.
![left, middle, right](step3.svg)

// move d3 from left to right.
We call moveOne with start left and with target right.
![left, middle, right](step4.svg)

// move d1 from middle to left.
We call moveOne with start middle and with target left.
![left, middle, right](step5.svg)

// move d2 from middle to right.
We call moveOne with start middle and with target right.
![left, middle, right](step6.svg)

// move d1 from left to right.
We call moveOne with start left and with target right.
![left, middle, right](step7.svg)

// reset
We write d1 into top of left.
![left, middle, right](step8.svg)

// Let us try to move a whole stack. Start with a stack of one: 
We call moveStack with big-disk d1 and with start left and with helper
middle and with target right.
As above of d1 is empty,
     moveStack calls moveOne with start left and with target right.
![left, middle, right](step9.svg)

// Undo that move. 
We call moveOne with start right and with target left. 
![left, middle, right](step10.svg)

// now move a stack of two
We call moveStack with big-disk d2 and with start left and with helper
middle and with target right.
MoveStack writes d1 from above of d2 into disk-above.
As disk-above is not empty,
     (first move stack above to helper) 
     moveStack calls moveStack with big-disk disk-above and  
        with start left and with helper right and with target middle
        (then move the biggest disk to the target tower)
     and moveStack calls moveOne with start left and with target right
        (and then move the stack above back onto the big disk)
     and moveStack calls moveStack with big-disk disk-above and with start
        middle and with helper left and with target right.

![left, middle, right](step11.svg)

// reset 
We write d3 into below of d2.
We write d1 into top of left.
![left, middle, right](step12.svg)

// Lets move them all: 
We call moveStack with big-disk d3 and with start left and with helper middle and with target right. 
![left, middle, right](step13.svg)


