# Model

We call init.
Init creates a university with name StudyRight and with 15 min-credits.
Init creates a student with name Alice and with 20 credits.
Init creates a student with name Bob and with 10 credits.
StudyRight has students and is uni of Alice and Bob.

Init takes an roomNo from 1 to 5
and init creates the room r with roomNo roomNo
and r has university and is one of the rooms of studyRight.

Init answers with StudyRight.

# Known Attribute

We call init and we write the answer into studyRight.

We expect that there is some object c20 with 20 credits.
We expect that there is some object c10 whose credits is 10.

# Unknown Attribute

We call init and we write the answer into studyRight.

We expect that there is some object sr1 where some attribute is 'StudyRight'.

# Multiple Results

We call init and we write the answer into studyRight.

We match all objects studis whose name matches '.*'.

We take a student from students of studyRight and we expect that studis contains student.

# Non-Root Object

We call init and we write the answer into studyRight.

We expect that there is some object r3 with roomNo 3.
We expect that there is some object r5 where some attribute is 5.

# Unknown Links

We call init and we write the answer into studyRight.

We expect that there is some object alice1 with name 'Alice'
and some object sr2 with some link to alice1.

We expect that sr2 is studyRight.

# Known Links

We call init and we write the answer into studyRight.

We expect that there is some object bob1 with name 'Bob'
and some object sr3 with students bob1.

We expect that sr3 is studyRight.

# Instance

We call init and we write the answer into studyRight.

We expect that there is some university sr4.
We expect that sr4 is studyRight.

# Fuzzy matching

We call init and we write the answer into studyRight.

We expect that there is some object alice2 whose name matches '(?i)alice'.
We expect that there is some object bob2 whose credits is less than 15.

# Match Constraint from Attribute

We call init and we write the answer into studyRight.
We match some university uni1 and some student alice1 whose credits is greater than min-credits of uni1.
We match some university uni2 and some student alice2 where some attribute is greater than min-credits of uni2.

# Match Constraint

We call init and we write the answer into studyRight.
We match some university uni and some student alice
where credits of alice is greater than min-credits of uni and min-credits of uni is greater than 0.
