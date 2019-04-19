
# Scenario solve Towers of Hanoi.

<!-- This is an old example used with scenarios. -->
<!-- There is a brand new paper about this, cf. Evalution_of_the_Story...pdf in the doc dir.-->
<!-- Code gen does not yet cover many of the statements below. -->
<!-- Especially, we need a concept for object diagram dumps from within -->
<!-- multiple (recursive) method calls. -->

There is the Towers card. 

There are Disk cards with id left, middle, right.

Towers has bases left, middle, right. 

There are Disk cards with id d1 to d9 and with size 1 to 9.

On the first run, we put d1 into up of left. 
We put 1 into stepCount of Towers.

![Towers](TowersOneDisk1.png)

We call move on Towers with diskToMove d1 and with target right.

As up from d1 is empty, move puts d1 into up of right and 
adds 1 to stepCount of Towers and 
move answers done. 

![Towers](TowersOneDisk2.png)

On scenario 2, we try 2 disks. 

We put d2 into up of left and we put d1 into up of d2. 
<!--We put 1 into stepCount of Towers.-->

![Towers](TowersTwoDisks1.png)

We call move on Towers with diskToMove d2 and with target right 
and with help middle. 

As up from d2 is not empty, move puts complex into situation and 
move calls move with diskToMove d1 and 
with target middle and with help right. 

On this move d1 call, move puts d1 into up of middle.  
<!-- and move adds 1 to stepCount of Towers-->  
Move d1 answers done. 

<!--As the situation is complex, move reads 2 from stepCounter of Towers. -->
![Towers](TowersTwoDisks2.png)

As the situation is complex, 
move d2 puts d2 into up of right and adds 1 to stepCounter of Towers.
<!--and reads 3 from stepCounter of Towers. -->
![Towers](TowersTwoDisks3.png)

As the situation is complex, 
move d2 calls move on Towers with diskToMove d1 and with target d2 
and with help left. 
Move d1 answers done. 

<!--As the situation is complex, move reads 4 from stepCounter of Towers. -->
![Towers](TowersTwoDisks4.png)

Move d2 answers done. 

