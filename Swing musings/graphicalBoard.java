/* Jason Rudinsky
* July 3, 2024
* Independent Study

* This will be the class that will house the contents for creating the board that is used to show
* the minesweeper game. It should have all the necessary logic and other fun awesomeness that will make it work well.

* 7/3/2024 - Original Version
* 7/16/2024 - Added in some functions to allow for the ability to click on the buttons and then have said buttons correspond
   * to the users chosen coordinate. Have the Left click done rather well so far. There is room for improvement of course.
   * Right click still needs to be implemented for flag placement and I need to make sure that the game over function is still
   * working properly once again so that it suits the graphical implementation. But seeing as how I've really only spent like 
   * 2-3 hours on this it's a good amount of progress. 
* 7/17/2024 - Fixed a bug created when trying to show a space with no mines around it. Also added in the right click method for
   * the flag placement so that works correctly now. Now all that's left should just be to add the instructions and for the 
   * conditions that are used to determine when the game is over.
* 7/19/2024 - Added in the conditions for when the user wins. Now all that has to be done is just an instructional explaining the rules
*/

import javax.swing.JFrame;                                                                                                             // Imports the Java Swing JFrame capabilities
import javax.swing.JButton;                                                                                                            // Imports the Java Swing JButton capabilities
import javax.swing.JLabel;                                                                                                             // Imports the Java Swing JLabel capabilities
import javax.swing.JPanel;                                                                                                             // Imports the Java Swing JPanel capabilities
import javax.swing.*;                                                                                                                  // Imports all of the other Java Swing libraries 

import java.util.ArrayList;                                                                                                            // Imports the Java ArrayList Structures

import java.awt.*;                                                                                                                     // Imports all of the Java awt structures
import java.awt.event.ActionEvent;                                                                                                     // Imports the Java Swing Action event capabilites
import java.awt.event.ActionListener;                                                                                                  // Imports the Java Swing Action listener capabilites
import java.awt.event.MouseAdapter;                                                                                                    // Imports the Java Swing Mouse Adapter capabilites
import java.awt.event.MouseEvent;                                                                                                      // Imports the Java Swing Mouse Event capabilites

public class graphicalBoard extends graphicalLogicGameBoard{
   
   GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();                                                          // Creates an object of the GraphicsEnvironment
   Point p = e.getCenterPoint();                                                                                                       // Should be getting the center point on the computer
   
   JFrame board;                                                                                                                       // Defines board
   JPanel myPanel;
   
   Container thePane = null;                                                                                                           // Defines thePane
    
   JLabel timeLabel = new JLabel();                                                                                                    // Defines timeLabel
   
   int elapsedTime = 0;                                                                                                                // Defines elapsedTime
   int seconds = 0;                                                                                                                    // Defines seconds
     
   String seconds_string = "";                                                                                                         // Defines seconds_string
   String coordin = "";
   
     
   // Fundamentals for the Timer is from Bro Code on YouTube
   // Creates the Timer for the White Player
   Timer playerTimer = new Timer(1000, new ActionListener(){                                                                           // Creates an object of the Timer class
         
      public void actionPerformed(ActionEvent e){                                                                                      // Action performance
         elapsedTime = 1000;                                                                                                           // Sets the value of elapsedTime
         seconds = seconds + ((elapsedTime/1000) % 60);                                                                                // Adds to the value of seconds
         
            
         seconds_string = String.format("%02d", seconds);                                                                              // Sets the value of other_seconds_string
            
         timeLabel.setText(seconds_string);                                                                                            // Sets the text of timeLabel
      }
   });
   
   public graphicalBoard(int row, int column){                                                                                         // No argument constructor 
      
      
      super(row, column);                                                                                                              // Call to parent constructor
      
      timeLabel.setText(seconds_string);                                                                                               // Sets the text of timeLabel
      
      timeLabel.setFont(new Font("Verdana",Font.PLAIN,15));                                                                            // Sets the fond of timeLabel
      timeLabel.setBorder(BorderFactory.createBevelBorder(1));                                                                         // Sets the border of timeLabel
      timeLabel.setOpaque(true);                                                                                                       // Sets the opaque to true for timeLabel
      timeLabel.setHorizontalAlignment(JTextField.RIGHT);                                                                              // Sets the horizontal alignment of timeLabel
         
      board = new JFrame("Minesweeper");                                                                                               // Initializes board
         
      board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                                            // Sets the Default Close Operation of board
      board.setBounds(p.x / 2, p.y / 6, 650, 650);                                                                                     // Setting the bounds of board
   
      makeBoard(board);                                                                                                                // Calls to method makeBoard      

      board.setResizable(false);                                                                                                       // Fixes the size of the JFrame
      board.setVisible(true);                                                                                                          // Setting the visibility of the JFrame to true
      playerTimer.start();                                                                                                             // Starts the timer
      beginningMessage();
      
      playerTimer.start();   
   }
   
   
   
   
   
   public void makeBoard(JFrame theBoard){                                                                                             // Method Block
      
      Container thePane = board.getContentPane();                                                                                      // Sets the value of thePane
      thePane.setLayout(new BorderLayout());                                                                                           // Sets the layout of the Container

      board.add(topFunctions(thePane, board), BorderLayout.NORTH);                                                                     // Adding the top functions to board
      board.add(centerBoard(thePane), BorderLayout.CENTER);                                                                            // Adding the center functions to board
   }
   
   
   /*
      * This is the method that will be used to create the top functions for the game.
      * It will consist of The number of flags left to place on the board and the elapsed time since the game has begun. 
   */
   public JPanel topFunctions(Container thePane, JFrame board){                                                                        // Method Block
      
                                                                                                                                       // VARIABLE DEFINITIONS
      JPanel theTop = new JPanel(new FlowLayout());                                                                                    // Defines theTop with a new FlowLayout
      JLabel player = new JLabel();                                                                                                    // Defines player
      JLabel blank = new JLabel("                              ");                                                                     // Defines blank
      
      
      player.setText("Number of Flags left to place: " + flagsAdded);                                                               // Sets the text of player
      
      theTop.add(player);                                                                                                           // Adds player to theTop
      
      blank.setVisible(true);                                                                                                       // Sets the visibility of blank
      
      theTop.add(blank);                                                                                                            // Adds blank to theTop 
      theTop.add(timeLabel);                                                                                                        // Adds timeLabel to theTop
            
      return theTop;                                                                                                                   // Returns theTop to the user
   }
   
   
   
   // This is the method that will be used to create the grid for the board 
   public JPanel centerBoard(Container thePane){                                                                                       // Method Block
      
      
      int rowLength = gameBoard.length;
      int colLength = gameBoard[0].length;
      
                                                                                                                                       // VARIABLE DEFINITION
      JButton[][] buttonArray = new JButton[rowLength][colLength];
      JPanel boardPanel = new JPanel(new GridLayout(rowLength, colLength));                                                                             // Defines boardPanel and Sets the GridLayout
      boardPanel.setSize(450, 450);                                                                                                    // Sets the size of boardPanel
      
      MouseAdapter mouseListener = new MouseAdapter() {
            private boolean sentinal = false;
            @Override
            public void mousePressed(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                if(SwingUtilities.isLeftMouseButton(e)){
                    //System.out.println("Left button clicked on " + button.getActionCommand());
                    coordin = button.getActionCommand();
                    
                    
                    if(!sentinal){
                        placeSelection(coordin);
                        refresh();
                        //printBoard();
                        sentinal = true;
                    }
                    
                    // Handle left-click behavior
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                  //System.out.println("Right button clicked on " + button.getActionCommand());
                  
                  coordin = button.getActionCommand();
                    
                  if(!sentinal){
                     flagPlacementTime(coordin);
                     refresh();
                     //printBoard();
                     sentinal = true;
                  }
                    // Handle right-click behavior
                }
            }
        };
      
      
      for(int row  = 0; row < rowLength; row++){                                                                                               // For Loop
         for(int column = 0; column < colLength; column++){                                                                                    // Nested For Loop
            buttonArray[row][column] = new JButton(gameBoard[row][column]);
            buttonArray[row][column].setMargin(new Insets(0, 0, 0, 0));
            
            if(gameBoard[row][column].equals("*")){
               buttonArray[row][column].setText("");
            }
            else{
               if(!gameBoard[row][column].equals("F")){
                  buttonArray[row][column].setEnabled(false);
               }
               
               if(gameBoard[row][column].equals("0")){
                  buttonArray[row][column].setText("");
               }
               
               
            }
            
            
            
            buttonArray[row][column].setActionCommand(makeCoordinate(row, column));
            buttonArray[row][column].addMouseListener(mouseListener);
            boardPanel.add(buttonArray[row][column]);
         }
      }
      
      //addingPieces(boardPanel, checking);                                                                                              // Call to Method addingPieces
      thePane.add(boardPanel, BorderLayout.CENTER);                                                                                    // Adds boardPanel to the center of the Content Pane
      
      return boardPanel;                                                                                                               // Returns boardPanel to the user
   }
   
   public void refresh(){
      
      board.setVisible(false);
      timeLabel.setText(seconds_string);                                                                                               // Sets the text of timeLabel
      
      timeLabel.setFont(new Font("Verdana",Font.PLAIN,15));                                                                            // Sets the fond of timeLabel
      timeLabel.setBorder(BorderFactory.createBevelBorder(1));                                                                         // Sets the border of timeLabel
      timeLabel.setOpaque(true);                                                                                                       // Sets the opaque to true for timeLabel
      timeLabel.setHorizontalAlignment(JTextField.RIGHT);                                                                              // Sets the horizontal alignment of timeLabel
         
      board = new JFrame("Minesweeper");                                                                                               // Initializes board
         
      board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                                            // Sets the Default Close Operation of board
      board.setBounds(p.x / 2, p.y / 6, 650, 650);                                                                                     // Setting the bounds of board
   
      makeBoard(board);                                                                                                                // Calls to method makeBoard
      
      //playerTimer.start();

      board.setResizable(false);                                                                                                       // Fixes the size of the JFrame
      board.setVisible(true);                                                                                                          // Setting the visibility of the JFrame to true
      
      if(gameOver()){                                                                                                                  // If the game is over   
         if(getWonGame() || getHitMine()){                                                                                             // If the game is over
            playerTimer.stop();                                                                                                        // Stops the timer
            endingMessage();                                                                                                           // Displays the ending message to the user
         }
      }
   }
   
   
   /* 
      * This is the message that will be shown at the end of the game whether 
      * the user has hit a mine or whether the user has won the game
   */ 
   public void endingMessage(){                                                                                                        // Method Block
      
      Frame finalMessage = new JFrame("Message to player");                                                                            // Defines finalMessage
      
      if(getWonGame()){                                                                                                                // If the user won the game
         
         JOptionPane.showMessageDialog(finalMessage, "You won!!!");                                                                    // Displays the Message to the user                                                   
      }
      else{                                                                                                                            // Else Block
         
         JOptionPane.showMessageDialog(finalMessage, "You have hit a mine!!!");                                                        // Displays the Message to the user
      }
      
      System.exit(0);                                                                                                                  // Exits the Program
   }
   
   
   /* 
      * This is the message that will be shown at the beginning of the game whether 
      * the user has hit a mine or whether the user has won the game
   */ 
   public void beginningMessage(){                                                                                                     // Method Block
      
      Frame firstMessage = new JFrame("Message to player");                                                                            // Defines firstMessage
      
      String theMessage = "Welcome to minesweeper!!! Click on a space to reveal all the places where there are not mines. \n";         // Defines theMessage
      
      theMessage += "You will right click to place and remove flags where you think mines could be. Good Luck!!!";                     // Adds to the value of theMessage
      
      JOptionPane.showMessageDialog(firstMessage, theMessage);                                                                         // Displays the Message to the user      
   }
   
}