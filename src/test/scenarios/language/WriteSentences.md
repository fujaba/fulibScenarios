# Scenario Write Sentences.

There are ListHolders x,y,z.
We write y,z into xs.

We write 1,2,3 into vals.          // * -> 1
We write 4,5,6 into v1,v2,v3.      // n -> n
We write 7,8,9 into vals of x.     // * -> 1.*
// We write 10,11,12 into vals of xs. // * -> *.*

We write 13 into v4.               // 1 -> 1
We write 14 into v5,v6,v7.         // 1 -> n
// We write 15 into vals.             // 1 -> *
// We write 16 into vals of x.        // 1 -> 1.*
// We write 17 into vals of xs.       // 1 -> *.*

// We expect that vals is 1,2,3,15.
// We expect that v1,v2,v3,v4,v5,v6,v7 is 4,5,6,13,14,14,14.
// We expect that vals of x is 7,8,9,16.
// We expect that vals of y is 10,11,12,17.
// We expect that vals of z is 10,11,12,17.
