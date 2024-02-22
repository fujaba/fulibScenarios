# Inheritance

Every int is a long.
<!--  ^^^
error: cannot make non-class type 'int' inherit from a type [inherit.subtype.not.class]
               ^^^^
error: cannot inherit from non-class type 'long' [inherit.supertype.not.class]
-->

// TODO: Find an alternative for WebApp.
// Every WebApp is a Page.
// <!--  ^^^^^^
// error: cannot make external class 'WebApp' inherit from a type [inherit.subtype.external]
//                   ^^^^
// error: cannot inherit from external class 'Page' [inherit.supertype.external]
// -->

Every Foo is a Bar.
<!--           ^^^
note: 'Foo' was first declared to inherit from 'Bar' here [inherit.declaration.first]
-->

Every Foo is a Baz.
<!--  ^^^
error: cannot change supertype of 'Foo' which already inherits from 'Bar' [inherit.conflict]
-->
