
# Scenario moving disks manually. 

There is a Towers card. 

There are Disk cards with id left, middle, right. 

Towers has baseDisks left, middle, and right. 

There are Disk cards d1 to d9. 

On init 1, we put d1 into up of left.

We put start into state of Towers. 

![Towers](TowersOneDisk1.png)

We put d1 into up of right. 

![Towers](TowersOneDisk2.png)

We put solved into state of Towers. 

On init 2, we use 2 disks. 

We put d2 into up of right and we put d1 into up of d2. 
We put start into state of Towers. 

![Towers](TowersTwoDisk1.png)

We put d1 into up of middle. 
![Towers](TowersTwoDisk2.png)
We put d2 into up of right.
![Towers](TowersTwoDisk3.png) 
We put d1 into up of d2. 
![Towers](TowersTwoDisk4.png)

On init 3, we use 3 disks. 

We put d1 to d3 into up of d2, d3, and left. 

![Towers](TowersThreeDisk1.png) 

On three disks, in order to move d3 to the right card we first need 
to move d2 to the middle card and in order to move d2 to the middle 
we need to move d1 to the right.

We put d1 into up of right. 
![Towers](TowersThreeDisk2.png) 
We put d2 into up of middle. 
![Towers](TowersThreeDisk3.png) 
We put d1 into up of d2. 
![Towers](TowersThreeDisk4.png) 

<!-- I would like to add some comment visible to the reader here: -->
<!-- Note, the similarities between moving 2 disks to the right and moving two diss to the middle. -->

On this state, we now move d3 to its target.
We put d3 into up of right

![Towers](TowersThreeDisk5.png)

On this step, we move d1 and d2 back onto d3.

We put d1 into up of left. 
![Towers](TowersThreeDisk6.png) 
We put d2 into up of d3. 
![Towers](TowersThreeDisk7.png) 
We put d1 into up of d2. 
![Towers](TowersThreeDisk8.png) 
 
We put solved into state of Towers. 
