
# Scenario work.

There is StudyRight.

We call init on StudyRight. 
Init answers with Student Carli.

We call work on Carli.

Work reads math from room of Carli. 
Work reads 23 from credits of math.
Work writes 0 into pointSum.
Work reads size from assignments of math into maxTaskNumber. 

One by one, work reads 1 to maxTaskNumber into position.
On position 1, 
work reads integrals from assignments_position of math into currentTask and
it adds 5 from points of currentTask into pointSum.
As 5 from pointSum is less than 23 from credits, work continues with position 2. 
On position 2, work adds 20 from points of series into pointSum.
As 25 from pointSum is greater equal 23 from credits, 
work adds 23 from credits into credits of Carli and 
it stops reading tasks with index now 2. 

Work writes integrals and series from assignments1 to assignments_position of math into taskLog of Carli.

We expect that Carli has credits 23.

![Carli](StudyRighMathDone.png)


