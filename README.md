// NAMES: Vyacheslav Lukiyanchuk and Linnea P. Castro 
// DATE: 21 May 2023
// COURSE: CSE 223
// ASSIGNMENT #: PA5

// PA5 A Dots Game Written in Java using Eclipse with WindowBuilder's Swing Package
// Dots.java

// PROGRAM SYNOPSIS:
/*
This program contains three files (Dots.java, MyPanel.java, and Box.java) creating the code for a two-player
Dots game, written in Java, and delivered as a GUI using WindowBuilder's Swing Package.  The
game takes place inside a JFrame, containing two JPanels: a standard JPanel called ContentPane,
and a custom MyPanel, extending JPanel, where actual gameplay occurs. The ContentPane JPanel contains
a start button, reset button, score label, turn label, and the actual game board MyPanel.  

This program demonstrates event driven code; the events of gameplay being driven by a user's clicks
on the start or reset buttons, and then primarily by mouse clicks on the game board.  Changes to the
game made through these mouse clicks are reflected in the GUI in the paint and repaint methods in 
the Graphics class.  These updates allow the game to have a dynamic interface that updates lines drawn,
the score, player turns, and ownership of boxes.  

After inputting player names and pressing the start button, players take turns drawing lines between
dots on the game board.  Lines are drawn with a single click, the line the player was intending to click 
inferred through calculations based on the coordinate and the shortest distance to a given box wall.  
One turn warrants one click, with the exception of turns that complete a box.  If a player completed a box,
their initial gets drawn inside that box and an extra turn is granted.  When all boxes have been claimed,
the player with the most boxes drawn is declared winner (or a tie if all is even).  

Code is modularized in the MyPanel Class, which parcels tasks out into methods representing elements that need
to be evaluated with every mouse click.   Some methods are singular in task, like the method that calculates 
Player 1's score.  Other methods, like the whatSideOfBox method is more broad in that it determines which side 
of the box is being clicked and also account for adjacent sides.  The methods, when called in appropriate
succession, give a way for game information to be relayed to the board (score, turn, lines, etc).  The methods
also give a basis for decision making.  For example, if total boxes are calculated before a turn and after a turn,
by comparing the two, the program is able to tell if any boxes were completed on that current turn, which will
influence future turns.

This assignment required being methodical in order to retrieve accurate information about every box in the game board.
Having a clear view of what was needed and a way to obtain that was crucial. This assignment also tested code
organization skills, particularly when using several nested if statements and evaluating outcomes of conditional 
statements. 

Skills practiced include:
- Working with Eclipse IDE, testing and debugging.  
- Working with a partner to bring two separate programs together.  Implementing and integrating methods from each 
to build a stronger program. 
- Understanding event driven programming and how to create a GUI using WindowBuilder.  
- Starting with a basic Box class, understanding the rules of the game, forecasting what will be needed,
and asking - what method do I create that gets us the information we need?  Then, creating that method.
 */
