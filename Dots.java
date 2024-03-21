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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingConstants;


public class Dots extends JFrame {

	private JPanel contentPane;	
	private MyPanel gameArea;
	private JLabel scoreLabel; 
	
	boolean gamestart = false; // Boolean tracks if start button has been pressed
                               // false means start not yet pressed
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dots frame = new Dots();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	

	/**
	 * Create the frame.
	 */
	public Dots() {
		
		setTitle("DOTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// WINSTATUS LABEL - shows turn and win
		JLabel winStatusLabel = new JLabel(" ");
		winStatusLabel.setVerticalAlignment(SwingConstants.TOP);
		winStatusLabel.setBounds(464, 223, 111, 51);
		contentPane.add(winStatusLabel);
		
		// CREATE GAMEAREA
		gameArea = new MyPanel(); // gameArea is a new MyPanel, which extends JPanel
		gameArea.setBounds(20, 40, 415, 415);
	
		// MOUSE EVENT REGISTERING CLICKS ON GAMEAREA
		gameArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (gamestart){ // If start button has been pressed, this will be true
					
					int currentX=e.getX(); // Get coordinates of click
					int currentY=e.getY();
					
					// Check if point is within boundaries of gameboard
					if(currentX < 0 || currentX > 399 || currentY < 0 || currentY > 399) {
						return;
					}

					else { // Point is within boundary of game board, do the following
					
						gameArea.savePoint(currentX, currentY); // Save x and y coordinates
						int currentRow=e.getY()/50; // Divide by 50 to determine row and column
						int currentCol=e.getX()/50;
						gameArea.saveRowCol(currentRow, currentCol); // Save that row and column
					
						gameArea.boxesCompletedBeforeTurn(); // Check how many boxes are completed now
						if(gameArea.whatSideOfBox()) { // If line is already true, this will return true, display message, otherwise
							                           // this line will make line true in this box and adjacent, claiming ownership if appropriate
							scoreLabel.setText("This spot is already taken");
							return;
						}
					
						gameArea.boxesCompletedAfterTurn(); // Calculate how many boxes are completed after turn
						gameArea.calculatePlayer1Score(); // Calculate P1 Score
						gameArea.calculatePlayer2Score(); // Calculate P2 Score
					
						gameArea.turn(); // Turn method, will change turn if box wasn't just completed
					
						gameArea.repaint(); // Call repaint
					
						winStatusLabel.setText("turn: " + gameArea.getTurn()); // Update turn label and score
						scoreLabel.setText(gameArea.player1Name + ": " + gameArea.P1ScoreAsInt() + "    " + gameArea.player2Name + ": " + gameArea.P2ScoreAsInt());
					
					
					if(!gameArea.isGameInPlay()) { // If all boxes are taken, this will return false and a winner will be announced
						scoreLabel.setText(gameArea.whoIsTheWinner());
					}
					
					} // End of else block
					
				}
				else{ // If start button has not been pressed and user clicks on game board
					winStatusLabel.setText("<HTML>Click the Start button to begin</HTML>");
				}
				
			}
		});
		
		gameArea.setLayout(null);
		gameArea.createGameBoard(); // call createGameBoard method to create 8x8 array and fill each with a Box
		contentPane.add(gameArea);
		
		
		// PLAYER 1 NAME FIELD
		JFormattedTextField player1NameTextField = new JFormattedTextField();
		player1NameTextField.setBounds(464, 40, 100, 26);
		player1NameTextField.setText("1 Name");
		contentPane.add(player1NameTextField);
		
		// PLAYER 2 NAME FIELD
		JFormattedTextField player2NameTextField = new JFormattedTextField();
		player2NameTextField.setBounds(464, 88, 100, 26);
		player2NameTextField.setText("2 Name");
		contentPane.add(player2NameTextField);
		
		// SCORE JLABEL
		scoreLabel = new JLabel(gameArea.player1Name + ": " + gameArea.player1Score + "    " + gameArea.player2Name + ": " + gameArea.player2Score);
		scoreLabel.setBounds(20, 6, 340, 16);
		contentPane.add(scoreLabel);
		
		// START BUTTON 
		JButton startButton = new JButton("Start");
		startButton.setBounds(464, 148, 100, 26);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(gamestart) return; // If game has already started, button will do nothing
				
				if(player1NameTextField.getText().isEmpty()) // Prompt if name field left empty
				{
					scoreLabel.setText("Please enter a name for Player 1");
					return;
				}
				if(player2NameTextField.getText().isEmpty()) // Prompt if name field left empty
				{
					scoreLabel.setText("Please enter a name for Player 2");
					return;
				}
				if(player2NameTextField.getText().charAt(0) == player1NameTextField.getText().charAt(0)) // Prompt if players have matching initials
				{
					scoreLabel.setText("Players must have different first initials");
					return;
				}
				
				gamestart = true; // gamestart boolean set to true, now gameArea will be active

				player1NameTextField.setEditable(false); // Gray out name field when Start button is clicked
				gameArea.player1Name=player1NameTextField.getText(); // Pass custom player 1 name to player1Name variable
				gameArea.player1Initial=gameArea.player1Name.charAt(0); // Store 1st initial of name in player1Initial variable
				
				player2NameTextField.setEditable(false);
				gameArea.player2Name=player2NameTextField.getText();
				gameArea.player2Initial=gameArea.player2Name.charAt(0);
				
				// SAVE PLAYER INITIALS INTO AN ARRAY
				gameArea.saveInitials(player1NameTextField.getText().charAt(0), player2NameTextField.getText().charAt(0));
				
				gameArea.isGameInPlay=true; // Change isGameInPlay boolean to true
				
				scoreLabel.setText(gameArea.player1Name + ": " + gameArea.P1ScoreAsInt() + "    " + gameArea.player2Name + ": " + gameArea.P2ScoreAsInt());
			    
				winStatusLabel.setText("turn: " + gameArea.getTurn());
				
			    gameArea.repaint(); // Call repaint
				
			}
		});
		contentPane.add(startButton);
		
		
		// RESET BUTTON
		JButton resetButton = new JButton("Reset");
		resetButton.setBounds(464, 418, 100, 29);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				player1NameTextField.setText("1 Name"); // Reset name fields and make them editable
				player1NameTextField.setEditable(true); 
				
				player2NameTextField.setEditable(true);
				player2NameTextField.setText("2 Name");
				
				gameArea.gameReset(); // Call reset method to clear box booleans, initials, and turn and initial arrays
				gameArea.repaint();
				gamestart = false; // Make gameArea inactive until Start button is pressed again
				
				winStatusLabel.setText(" ");
				scoreLabel.setText("Game reset, please hit the start button to play again");
			}
		});
		contentPane.add(resetButton);
		
		
	}
}
