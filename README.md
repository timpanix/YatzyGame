YatzyGame
=========

This is my version of the Yatzy game, also commonly known as Yahtzee. Dies ist meine Version des Spiels Yatzy/Yahtzee (auch unter dem Namen "Kniffel" bekannt).

The program consists of the following classes/interface:

- class Yatzy: the meat of the program. Yatzy implements interface Pausable (see below).

- class YatzyManager: this class contains the main method and is in charge of running the program.

- class Player: contains information about the players: name, game card (the current state of the player's game) and the amount of wins. This class implements the interface Serializable, so that Player objects can be written to file when the state of the game is saved.

- class Highscore: this class is used to compare the highest scores and to write them to file. Thus, it implements the interfaces Serializable and Comparable. 

- interface Pausable: contains 2 method declarations: 1 to save an unfinished game and 1 to restart a saved game.

- Please note: this program also requires a .txt file containing the rules of the game. Any file can be used (obviously the names have to match), but I suggest to use the one provided here.
