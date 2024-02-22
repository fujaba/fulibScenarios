# Invalid Diagram Extensions

There is the Object foo.

     ![foo](foo.gif)
<!-- ^^^^^^^^^^^^^^^
error: unsupported diagram file name extension 'foo.gif' [diagram.filename.extension.unsupported]
     ^^^^^^^^^^^^^^^
note: extension must be one of [.png, .svg, .yaml] [diagram.filename.extension.hint]
-->
