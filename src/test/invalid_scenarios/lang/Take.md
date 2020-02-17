# Syntax

We take i 1 from 1 to 10 and we write i into j.
<!--    ^
warning: the 'take <name> <expr> from ...' syntax is deprecated [take.syntax.deprecated]
write 'take a i like 1 from ...' instead
-->

# Type

We take an i from my string and we write i into j.
<!--              ^
error: invalid take sentence - cannot iterate over source type 'String' [take.source.type]
-->
