# Game Model

We call newGame.

NewGame creates a Player with name Alice.
NewGame creates a Player with name Bob.
NewGame creates a Player with name Charlie.
NewGame creates a Game with win-score 60.

The game has players and is game of Alice, Bob and Charlie.

NewGame creates a House with id a1 and with 30 units.
NewGame creates a House with id a2 and with 20 units.
Alice has houses and is player of a1 and a2.

NewGame creates a House with id b1 and with 40 units.
Bob has houses b1.

NewGame creates a House with id c1 and with 60 units.
Charlie has houses c1.

NewGame answers with the game.

# Pattern matching

We call newGame and we write the answer into game1.

We match:
- some game g whose winScore is greater than 0 and with players winner
- some player winner with houses winningHouse
- some house winningHouse whose units is not less than winScore of g.

We expect that winner has name 'Charlie'.

# Name-Agnostic Pattern Matching with defect

<!--
We call newGame and we write the answer into game1.

// here, the game and the houses of alice cannot be distinguished
// as such, setting g=a2 is also valid
// the final table is:

| g 	| winScore 	| winner 	| winnerName 	| winningHouse 	| winningHouseAttr1 	| winningHouseAttr2 	|
| --- | --- | --- | --- | --- | --- | --- |
| g 	| 60 	| Charlie 	| Charlie 	| c1 	| 60 	| c1 	|
| a2 	| 20 	| Alice 	| Alice 	| a1 	| 30 	| a1 	|

We match:
- some object g with some link to winScore and with some link to winner
- some integer winScore
- some object winner with some link to winnerName and with some link to winningHouse
- some string winnerName
- some object winningHouse where some attribute is not less than winScore and where some attribute matches '[a-z][1-9]'.

We expect that winnerName is 'Charlie'.
-->

# Name-Agnostic Pattern Matching

We call newGame and we write the answer into game1.

We match:
- some object g with some link to winScore and with some link to winner
- some integer winScore where winScore is not less than 50
- some object winner with some link to winnerName and with some link to winningHouse
- some string winnerName
- some object winningHouse where some attribute is not less than winScore and where some attribute matches '[a-z][1-9]'.

We expect that winnerName is 'Charlie'.
