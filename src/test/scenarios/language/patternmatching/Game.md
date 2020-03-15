# Game Model

We call newGame.

NewGame creates a Player with name Alice.
NewGame creates a Player with name Bob.
NewGame creates a Player with name Charlie.
NewGame creates a Game with win-score 50.

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

# Task 1

We call newGame and we write the answer into game1.

// 1. Create a game object and give it a minimum score of 50.
We match some object g where some attribute is 50.

# Task 2

We call newGame and we write the answer into game1.

// 2. Create three players: Alice, Bob and Charlie. Set their name in an attribute.
We match:
- some object alice where some attribute matches '(?i)alice'
- some object bob where some attribute matches '(?i)bob'
- some object charlie where some attribute matches '(?i)charlie'
.

# Task 3

We call newGame and we write the answer into game1.

// 3. Link the players with the game.
We match:
- some object alice where some attribute matches '(?i)alice' and with some link to g
- some object bob where some attribute matches '(?i)bob' and with some link to g
- some object charlie where some attribute matches '(?i)charlie' and with some link to g
- some object g where some attribute is 50
.

# Task 4

We call newGame and we write the answer into game1.

// 4. Create four houses, with 30, 20, 40 and 60 units.
We match:
- some object h1 where some attribute is 30
- some object h2 where some attribute is 20
- some object h3 where some attribute is 40
- some object h4 where some attribute is 60
.

# Task 5

We call newGame and we write the answer into game1.

// 5. Link the first two houses with Alice, and the other two with Bob and Charlie.
We match:
- some object h1 where some attribute is 30 and with some link to alice
- some object h2 where some attribute is 20 and with some link to alice
- some object h3 where some attribute is 40 and with some link to bob
- some object h4 where some attribute is 60 and with some link to charlie
- some object alice where some attribute matches '(?i)alice'
- some object bob where some attribute matches '(?i)bob'
- some object charlie where some attribute matches '(?i)charlie'
.
