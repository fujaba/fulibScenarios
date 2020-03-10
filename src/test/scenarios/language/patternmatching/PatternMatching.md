# Pattern Matching

## Solution

There is a university with name StudyRight.
There is a student with name Alice and with 20 credits.
There is a student with name Bob and with 10 credits.
StudyRight has students and is uni of Alice and Bob.

We take an roomNo from 1 to 5
and we create the room r with roomNo roomNo
and r has university and is one of the rooms of studyRight. 

## Verification

// Known Attribute
We expect that there is some object c20 with 20 credits.
We expect that there is some object c10 where credits is 10.

// Unknown Attribute
We expect that there is some object sr where some attribute is 'StudyRight'.

// Non-Root Object
We expect that there is some object r3 with roomNo 3.
We expect that there is some object r5 where some attribute is 5.
