# Scenario Register Student.

There is an SEGroup.

There is an SEStudent with name Alice and with studentId m42 and with SEGroup SEGroup.

There is an SEClass with topic Modeling and with SEGroup SEGroup.

There are Achievements with id A1, A3 and with state unregistered.
Alice has achievements and is student of A1 and A3.
Modeling has achievements and is seClass of A1 and A3.

![SEGroup](registerStudentSetup.png)

We call registerStudent with student Alice and with seClass Modeling.

RegisterStudent creates an Achievement with id A2 and with state registered.
RegisterStudent adds A2 to the achievements of Alice.
RegisterStudent adds A2 to the achievements of Modeling.

RegisterStudent answers with A2.

We expect that A2 has state registered.

![SEGroup](registerStudentResult.png)
