# Scenario Register Student.

There is the SEGroup.

There is an SEStudent with name Alice and with studentId m42 and with SEGroup SEGroup.

There is an SEClass with topic Modeling and with SEGroup SEGroup.

![SEGroup](registerStudentSetup.png)

We call registerStudent with student Alice and with seClass Modeling.

RegisterStudent creates an Achievement with id A2 and with state registered.
RegisterStudent adds A2 to the achievements of Alice.
RegisterStudent adds A2 to the achievements of Modeling.

RegisterStudent answers with A2.

We expect that A2 has state registered.

![SEGroup](registerStudentResult.png)
