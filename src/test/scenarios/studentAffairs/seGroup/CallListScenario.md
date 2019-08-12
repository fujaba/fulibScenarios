# Scenario Calls and Lists.

There is an SEGroup.

We call init on SEGroup.

Init creates SEStudent with name Alice and Bob and Carli and Chris Robin and 
                       with studentId m42, m23, m84, and m1337.

Init creates SEStudent with name Ali and with studentId m20.

Ali has partners Alice, Bob, Carli and Chris Robin.

Init answers with Ali.

We call testInit.

TestInit creates an SEStudent with name Testus and with studentId m00.

TestInit answers with Testus.

<!-- This does not belong to the call frame and should thus appear in the test method.
     Otherwise, Ali won't be available. -->
Testus has partners Ali.

We expect that Testus has studentId m00.
