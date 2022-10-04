# BattleshipJava
🚢 💣 An implementation of Battleship in Java. ECE NTUA 7th semester project.<br>
In order to run the game, Java 11 or a newer version must be installed. Additionally, JavaFX SDK 11 and packages javafx.controls and javafx.xml are needed. <br>

The game takes place in two 10x10 boards and the player who sinks all of the opponent's ships or has the most points after 40 rounds wins.

📄 The game takes 2 txt files to run, which indicate where the player's ships and the computer's ships are:<br>
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

![image](https://user-images.githubusercontent.com/64773191/193804058-44988742-fc96-49a3-b13c-f5f4cc9e8c47.png)

In order to fire a shot at the opponent, please enter at the bottom left side of the window the coordinates of the shot, counting from 0. <br>
During the game, in the Details menu, statistics can be seen concerning the enemy's ships and the last 5 shots each player has made.<br>

Various statistics also displayed at the screen during the game, indicating each player's point tally and shot accuracy among others.<br>

---

The application is divided into two packages inside Medialab_BattleshipFX/src directory:
* <b>battleship.javaproj</b> package implements the rules of the game. It contains the following classes:
  * <b>BattleshipJava</b> Combines all of the game's elements and conducts the game's process. It also calculates the points of the players and the number of moves remaining.
  * <b>Board</b> is the class of the game board. It contains information about its contents and how they are visualized to the player, whether it is the player's board or the computer's board.
  * <b>ComputerPlayer</b> is the class of the computer player. It calculates the moves played by the computer. The computer initially chooses random squares until it successfully hits a target. Then it chooses squares adjacent to the target until the ship is sunk.
  * <b>RegistryQueue</b> is a queue data structure. It is used to store the last 5 moves by the player and the computer and the status of the computer's ships.
  * <b>Ship</b> is the class of a ship and contains information about its status and the points awarded for sinking it. 5 classes inherit from this class, which are the 5 different ships, the <b>Battleship</b>, the <b>Carrier</b>, the <b>Cruiser</b>, the <b>Destroyer</b> and the <b>Submarine</b>.
* <b>sample</b> package contains classes which implement the graphical interface. These are:
  * <b>Main</b> is the main class which runs the game and connects with the <i>BattleshipJava</i> class.
  * <b>BoardFX</b> is the class that connects with the <i>Board</i> class and visualizes the player's and the computer's boards.
  * <b>AlertBox</b> is the class that makes a pop-up box appear with some text.
  * <b>TextBoxPopup</b> is the class that makes a pop-up box appear, which contains text boxes so that the player inputs the game scenarios (the 2 .txt files) to load a game.

Have fun!
