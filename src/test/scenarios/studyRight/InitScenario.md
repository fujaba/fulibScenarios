# managing links

StudyRight is a University. 

There are Students with name Alice, Bob, Carli, Dave, Eyshe
and with credits 10, 10, 0, 42, 50.
There are Rooms  with name lecture hall, gym, music hall.

StudyRight has students and is uni of Alice and Bob
and has rooms and is uni of lecture hall, gym, music hall.
Lecture hall has students and is room of Alice and Carli.
Music hall has students Bob. 

We write Alice, Bob and Carli into first-students. 
We write uni of room of Alice into tmp. 
We write students of rooms of StudyRight into tmpList.
![tmpList](out.svg)

// We expect that first-students is students of rooms of StudyRight. 

// We write Carli and Dave and Eyshe into students of StudyRight. 

// hm