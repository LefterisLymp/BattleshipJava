# BattleshipJava
ECE NTUA 7th semester project. An implementation of Battleship in Java. <br>
In order to run the game, Java 11 or a newer version must be installed. <br>

The game takes place in two 10x10 boards and the player who sinks all of the opponent's ships or has the most points after 40 rounds wins.

The game takes 2 txt files to run, which indicate where the player's ships and the computer's ships are:<br>
Take a look at the example files player_default.txt and enemy_default.txt. <br>

The format in these files is:
(a, b, c, d), where <br>

<b>a</b>: Ship id, where 1 is the carrier (5 tiles long), 2 is the battleship (4 tiles), 3 is the cruiser (3 tiles), 4 is the submarine (3 tiles), 5 is the destroyer (2 tiles) <br>
<b>b</b>: Row of the starting tile, counting from 0 <br>
<b>c</b>: Column of the starting tile, counting from 0 <br>
<b>d</b>: Alignment, where 1 is horizontal alignment and 2 is vertical. <br>

To run the game, compile and run Medialab_BattleshipFX/src/sample/Main.java. A pop up window will appear.
Open the "Application" menu and press the Load button in order to load the two files.<br>
In the "Enter player/computer scenario" section, state the absolute computer path where the txt files are located. <br>

Then click the Start button in the Application menu and the game begins.<br>
In order to fire a shot at the opponent, please enter at the bottom left side of the window the coordinates of the shot, counting from 0. <br>
During the game, in the Details menu, statistics can be seen concerning the enemy's ships and the last 5 shots each player has made.<br>

Various statistics also displayed at the screen during the game, indicating each player's point tally and shot accuracy among others.<br>

Have fun!
