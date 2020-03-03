# Adding Language Constructs

## General

The addition of language constructs roughly consists of three steps.

1. **Grammar** – you change the grammar to add the new rules.
2. **Tree definition** – you change the tree definition file to add new AST node classes.
3. **AST Translation** – you update the `ASTListener` class to implement translation of your new grammar rules to the new AST nodes.
4. **Visitors** – you update existing `Visitor` implementations to conform to the updated interfaces.

The following chapter describes these steps in detail.

