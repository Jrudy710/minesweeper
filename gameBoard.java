/* Jason Rudinsky
* May 18, 2024
* Independent Study

* This is the the class declarations for the minesweeper game. It will house all the methods required
* to run a game of minesweeper from start to end. It contains constructors to initialize two boards, 
* one that will be displayed to the user for them to play on and one that will house all of the information 
* necessary for a game to run correctly, i.e. the flags on the board, the number of flags near a given coordinate, etc.
* The class will also contain the methods to determine when a win or lose condition for the user has been reached.

* 5/18/2024 - Original Version - Made the methods to initialize the instance vairables for the boards as well as the methods
   * to convert the row and column values based on the string that is passed for a given coordinate.
   * Also played a couple of games so I have a couple of test boards for when the game is in its implementation
   * stages and I want to test certain parts of the game.
* 5/19/2024 - Moved the calling of initBoards into the constructors so the boards are automatically filled.   
* 5/28/2024 - Added some methods to help with placing flags and the number of flags around a given position onto the board.
* 6/3/2024 @ 12:19 AM - Started the method for picking a place to select on the board. 
   * Also implemented the "undo" feature to make the revealSurroundings easier to check once it's fully implemented.
* 6/3/2024 @ 6:19 PM - When a player chooses a coordinate the coordinate and any surrouding coordinates will now be correctly revealed.
   * Also found and fixed a bug in regards to the values of the singleReversion board. Now the "undo" feature works correctly.
   * Also changed the original mines that used to be thought of as with the character "F" to the character "M" on secretBoard so when the 
   * method for placing flags is created, "F" as the flag will not conflict with the secret board. All that I have left should just be to when to determine
   * the game is over and how the player can place flags on the board
* 6/5/2024 - Found and fixed a bug if the user were to enter in a phrase that was not recognized by the placeSelection method. 
   * Also created the necessary methods to now allow for flags to be placed on the board by the user.
*/
package minesweeper;

import java.util.ArrayList;                                                                                    // Imports ArrayList Libraries
import java.util.Scanner;                                                                                      // Imports Java Scanner Libraries

public class gameBoard{                                                                                        // Class Block
   
                                                                                                               // VARIABLE DEFINITIONS
   public String[][] gameBoard;                                                                                // INSTANCE VARIABLE 
   public String[][] secretBoard;                                                                              // INSTANCE VARIABLE
   public String[][] singleReversion;                                                                          // INSTANCE VARIABLE
      
   public gameBoard(int row, int column){                                                                      // Argument Constructor
   
      gameBoard = new String[row][column];                                                                     // Initializes gamesBoard
      secretBoard = new String[row][column];                                                                   // Initializes secretBoard
      singleReversion = new String[row][column];                                                               // Initializes singleReversion
      
      initBoards();                                                                                            // Call to method initBoards
   }
   
   public gameBoard(){                                                                                         // No Argument Constructor
      
      gameBoard = new String[8][10];                                                                           // Initializes gamesBoard
      secretBoard = new String[8][10];                                                                         // Initializes secretBoard
      singleReversion = new String[8][10];                                                                     // Initializes singleReversion
      
      initBoards();                                                                                            // Call to method initBoards
   }
   
   
   
   
   /*
      * This will be the method that will be used to call the two methods that will initially set the default values
      * for the board that will be displayed to the user, "gameBoard" and the board that will not be displayed which is "secretBoard".
      
      * secretBoard will be what stores the coordinates for the mines as well as the number of mines that are around a given coordinate.
      * It's basically the solved version of the board that the user is trying to reveal.
   */
   public void initBoards(){                                                                                   // Setter Method
      
      fillGameBoard();                                                                                         // Call to method fillGameBoard
      fillSecretBoard();                                                                                       // Call to method fillSecretBoard
      makeTheSame(true);                                                                                       // Fills the singleReversion board
   }
   
   
   
   
   /*
      * This is the method that will be used to fill gameBoard for the very start of the game. 
   */
   public void fillGameBoard(){                                                                                // Setter Method
      
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      for(row = 0; row < gameBoard.length; row++){                                                             // For Loop
         for(column = 0; column < gameBoard[row].length; column++){                                            // Nested For Loop
            gameBoard[row][column] = "*";                                                                      // Sets the value of gameBoard at the index of row and column
            secretBoard[row][column] = " ";                                                                    // Sets the value of secretBoard at the index of row and column
            
         }
      }
   }
   
   
   
   
   /*
      * This is the method that will be used to place the flags on secretBoard.
      * For the moment, until the process has been double, triple, and quadruple 
      * checked, the flag positions will be hard coded to ensure the method works correctly.
   */
   public void fillSecretBoard(){                                                                              // Setter Method
      
                                                                                                               // VARIABLE DEFINITIONS
      String flagPositions[] = {"a7", "b7", "c5", "c8", "d2", "f3", "f8", "f9", "g2", "h5"};                   // Defines flagPositions
      
      int LCV = 0;                                                                                             // Defines LCV
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      
      /*
         * For each loop to add the flags to secretBoard
      */
      for(String singularFlag: flagPositions){                                                                 // For-each Loop
         
         row = getCoordinate(singularFlag, true);                                                              // Sets the value of row
         column = getCoordinate(singularFlag, false);                                                          // Sets the value of column 
         
         secretBoard[row][column] = "M";                                                                       // Sets the flag value at the index of row and column
      }
      
      
      /*
         * For Loop to set the rest of the board to blank spaces for now. 
         * Will later be used to set the number of flags that are around a given position, maybe 
      */
      for(row = 0; row < secretBoard.length; row++){                                                           // For Loop
         for(column = 0; column < secretBoard[row].length; column++){                                          // Nested For Loop
         
            if(!secretBoard[row][column].equals("M")){                                                         // If there isn't a flag at the index of row and column
               secretBoard[row][column] = surroundingFlags(row, column, secretBoard);                          // Sets the value at the index of row and column
            }
         }
      }
      
      
      //System.out.printf("The coordinate (%d, %d) is equal to %2s\n", row, column, surroundingFlags(row, column, secretBoard));
      
   }
   
   
   
   
   /*
      * This is the method that will be used to determine the number of flags that are surrounding a given space.
      * This is going to be done in a rather lazy way for right now and will probably done more efficiently once everything works.
   */
   public String surroundingFlags(int row, int column, String[][] theBoard){                                   // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      int numFlags = 0;                                                                                        // Defines numFlags
      
      String checkPositions[] = {makeCoordinate(row - 1, column - 1), makeCoordinate(row - 1, column), makeCoordinate(row - 1, column + 1), 
                                 makeCoordinate(row, column - 1), makeCoordinate(row, column + 1), 
                                 makeCoordinate(row + 1, column - 1), makeCoordinate(row + 1, column), makeCoordinate(row + 1, column + 1)
      };                                                                                                       // Defines checkPositions
      
      for(String position: checkPositions){                                                                    // Foreach Loop
         if(possiblyValid(position)){                                                                          // Checking to make sure the position is valid
            //System.out.printf("Will look at position: %s\n", position);                                      // Debug print statement
            
            row = getCoordinate(position, true);                                                               // Sets the value of row
            column = getCoordinate(position, false);                                                           // Sets the value of column
            
            if(theBoard[row][column].equals("M")){                                                             // If the position has a flag
               numFlags++;                                                                                     // Adds to the value of numFlags
            }
            
         }
      }
      
      return String.valueOf(numFlags);
   }
   
   
   
   
   /*
      * This is the method that will be used to determine if a possible coordinate position is 
      * able to analyzed further when either checking to see how many flags are at a given
      * position or if a given position can be expanded when it is chosen by the user to see 
      * if the place is or isn't a bomb
      
      * In other words, it's a boundary checker
   */
   public boolean possiblyValid(String position){
      
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      if(position.length() < 2){                                                                               // Makes sure the correct coordinate was entered
         return false;                                                                                         // Returns false to the user
      }
      
      row = getCoordinate(position, true);                                                                     // Sets the value of row
      column = getCoordinate(position, false);                                                                 // Sets the value of column
      
      if(row < 0 || row >= gameBoard.length){                                                                  // Checking to see if the row value is out of bounds
         return false;                                                                                         // Returns false to the user
      }
      
      if(column < 0 || column >= gameBoard[row].length){                                                       // Checking to see if the column value is out of bounds
         return false;                                                                                         // Returns false to the user
      }
      
      return true;                                                                                             // Returns true to the user
   }
   
   
   
   
   /*
      * This will be the method that will be used to allow the user to select a place on the
      * board to reveal, and eventually to place a flag as well. The first thing we will have
      * to do is make sure the place entered is valid and then the piece and any surrounding
      * pieces that may need to be revealed.
   */
   public void placeSelection(){                                                                               // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      Scanner input = new Scanner(System.in);                                                                  // New Scanner Object
      
      boolean notFirstTime = true;                                                                             // Defines notFirstTime
      
      String coordinate = "";                                                                                  // Defines coordinate      
      
      ArrayList<String> coordinatesToReveal = new ArrayList<String>();                                         // Defines coordinatesToReveal
      
      while(!coordinate.toLowerCase().equals("undo") && !coordinate.toLowerCase().equals("flag") && !possiblyValid(coordinate)){
         
         if(notFirstTime){                                                                                     // If statement
            notFirstTime = false;                                                                              // Sets notFirstTime to false
         }
         else{                                                                                                 // If this is not the first time through the while loop
            System.out.print("Incorrect coordinate entered! ");                                                // Prints out to the user
         }
         
         System.out.print("Please enter the coordinate: ");                                                    // Prints out to the user
         coordinate = input.nextLine();                                                                        // Sets the value of coordinate
      }
      
      //System.out.printf("Coordinate to look at is %s\n", coordinate);                                        // Debug print statement
      
      if(coordinate.toLowerCase().equals("undo")){                                                             // If the user chose to undo their latest move
         if(!theSame(gameBoard, singleReversion)){                                                             // Making sure that the boards are different, i.e. they don't do undo more than once because it's meaningless
            makeTheSame(false);                                                                                // Call to method makeTheSame
         }
      }
      else if(coordinate.toLowerCase().equals("flag")){                                                        // If the user chose to add a flag to the board
         flagPlacementTime();                                                                                  // Call to method flagPlacementTime
      }
      else{                                                                                                    // If the user entered a valid coordinate
         coordinatesToReveal.add(coordinate);                                                                  // Adds to the arrayList coordinatesToReveal
         makeTheSame(true);                                                                                    // Call to method makeTheSame   
         revealSurroundings(coordinatesToReveal);                                                              // Call to Method revealSurroundings
      }
      
      
   }
   
   
   
   
   /*
      * This is the method that will be used to place and remove flags from the board. 
      * The method will consist of a while loop so that the user can place multiple flags
      * after another as opposed to having to repeatedly go back to the original placeSelection
      * method and repeatedly go through this process.
   */
   public void flagPlacementTime(){                                                                            // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      boolean firstTime = true;                                                                                // Defines firstTime
      
      String coordinate = "";                                                                                  // Defines coordinate    
      
      Scanner input = new Scanner(System.in);                                                                  // Creates Scanner Object
      
      do{                                                                                                      // Do-While Loop
         
         if(!firstTime){                                                                                       // If it is not the first time placing a flag
            printBoard(gameBoard);                                                                             // Call to method printBoard
         }
         else{                                                                                                 // Not the first time in the loop
            firstTime = false;                                                                                 // Sets the value of firstTime
         }
         
         System.out.printf("(Flag placement) Please enter the coordinate (type \"done\" to return to normal): ");
         coordinate = input.nextLine();                                                                        // Sets the value of coordinate
         
         if(possiblyValid(coordinate)){                                                                        // Making sure the coordinate entered is within the bounds of the array
            
            row = getCoordinate(coordinate, true);                                                             // Sets the value of row
            column = getCoordinate(coordinate, false);                                                         // Ses the value of column
            
            if(gameBoard[row][column].equals("F")){                                                            // If the value at the index of row and column already has a flag
               gameBoard[row][column] = "*";                                                                   // Sets the value of gameboard at the index of row and column
            }
            else if(gameBoard[row][column].equals("*")){                                                       // If the value at the index of row and column has not been revealed
               gameBoard[row][column] = "F";                                                                   // Sets the value of gameboard at the index of row and column
            }
            else{
               System.out.println("You can't place a flag here");                                              // Prints out to the user
               firstTime = true;                                                                               // Sets the value of firstTime to true
            }
         }
         
      }
      while(!coordinate.equals("done"));                                                                       // Conditional for do-while loop
      
      
   }
   
   
   
   
   /*
      * This is the method that will be used to set singleReversion to the values of gameBoard
   */
   public void makeTheSame(boolean originalWay){                                                               // Method Block
         
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      for(row = 0; row < gameBoard.length; row++){                                                             // For Loop
         for(column = 0; column < gameBoard[row].length; column++){                                            // Nested For Loop
            if(originalWay){
               singleReversion[row][column] = gameBoard[row][column];                                          // Sets the value of singleReversion at the index of row and column
            }
            else{
               gameBoard[row][column] = singleReversion[row][column];                                          // Sets the value of gameBoard at the index of row and column
            }
         }
      }
   }
   
   
   
   /*
      * This is a method that will be used to check to make sure that the two boards are 
      * different from each other when doing the undo function of the minesweeper game. 
      * If the boards are the same then we don't want to undo anything. 
   */
   public boolean theSame(String[][] curBoard, String[][] pastBoard){                                          // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      for(row = 0; row < curBoard.length; row++){                                                              // For Loop
         for(column = 0; column < curBoard[row].length; column++){                                             // Nested For Loop
            //System.out.printf("Comparing %s and %s\n", curBoard[row][column], pastBoard[row][column]);       // Debug print statement
            if(!curBoard[row][column].equals(pastBoard[row][column])){                                         // If the values at the specific index of the board are different
               return false;                                                                                   // Returns false to the user
            }
         }
      }
      return true;                                                                                             // Returns true to the user
   }
   
   
   
   /*
      * This is the method that will be used to expand the coordinates on the board to reveal
      * all the correct ones for expansion if the user were to select a specific piece.
   */
   public void revealSurroundings(ArrayList<String> theStack){                                                 // Method Block
   
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      
      String coordinate = "";                                                                                  // Defines coordinate
      
      
      
      while(theStack.size() != 0){                                                                             // Loops for as long as the stack isn't empty
         
         coordinate = theStack.remove(theStack.size() - 1);                                                    // LIFO Removal
         //System.out.printf("Looking at position %s\n", coordinate);                                          // Debug print statement          
         
         row = getCoordinate(coordinate, true);                                                                // Sets the value of row
         column = getCoordinate(coordinate, false);                                                            // Sets the value of column   
         
         if(column != -1){                                                                                     // Making sure we are in range
            if(gameBoard[row][column].equals("*") && secretBoard[row][column].equals("0")){                    // Time to reveal the things surrounding the place
               
               String checkPositions[] = {makeCoordinate(row - 1, column - 1), makeCoordinate(row - 1, column), makeCoordinate(row - 1, column + 1), 
                                    makeCoordinate(row, column - 1), makeCoordinate(row, column + 1), 
                                    makeCoordinate(row + 1, column - 1), makeCoordinate(row + 1, column), makeCoordinate(row + 1, column + 1)
               };
               
               for(String position: checkPositions){                                                           // Foreach Loop
                  
                  if(possiblyValid(position)){                                                                 // Makes sure the position is within the bounds of the board
                     theStack.add(position);                                                                   // Adds to theStack
                  }
               }
            }
            if(gameBoard[row][column].equals("*")){
               gameBoard[row][column] = secretBoard[row][column];                                              // Sets the value in gameBoard at the index of row and column
            }
         }
      }
      
      
   }
   
   
   
   /*
      * This is the method that will be used to return either the row or column value to 
      * the user, depending on the value of the boolean "firstCoordinate"  
   */
   public int getCoordinate(String coordinate, boolean firstCoordinate){                                       // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      char charInString = ' ';                                                                                 // Defines charInString
      
      int convertedVal = 0;                                                                                    // Defines convertedVal
      
      if(firstCoordinate){                                                                                     // If we are only looking at the row value
         
         charInString = coordinate.charAt(0);                                                                  // Gets the value of the first character in coordinate
         convertedVal = (int)charInString - 97;                                                                // Sets the value of convertedVal
         //System.out.printf("The first value in %s is %s or %d\n", coordinate, charInString, convertedVal);   // Debug print statement         
      }
      else{                                                                                                    // Otherwise we're looking at the column value
         
         coordinate = coordinate.substring(1);                                                                 // Sets the value of coordinate
         
         if(allDigits(coordinate)){
            convertedVal = Integer.valueOf(coordinate);                                                           // Sets the value of columnVal
         }
         else{
            return -1;
         }
         //System.out.printf("The column's we are trying to look at is: %s ", coordinate);                     // Debug print statement
         //System.out.printf("and we were successful: %d\n", convertedVal);                                    // Debug print statement        
      }
      
      return convertedVal;                                                                                     // Returns the value of convertedVal
   }
   
   
   
   
   /*
      * This is a method to make sure that all the characters in a string are digits.
   */
   public boolean allDigits(String theCoordinate){                                                             // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      int LCV = 0;                                                                                             // Defines LCV
      
      char charInString = ' ';                                                                                 // Defines charInString
      
      for(LCV = 0; LCV < theCoordinate.length(); LCV++){                                                       // For Loop
         charInString = theCoordinate.charAt(LCV);                                                             // Sets the value of charInString
         
         if(!Character.isDigit(charInString)){                                                                 // Seeing if the character is not a digit
            return false;                                                                                      // Returns false to the user
         }
      }
      return true;                                                                                             // Returns true to the user
   }
   
   
   
   /*
      * This is the method that will be used to perform the reverse functionality of the getCoordinate method.
      * It will take in a given row and column value and spit out the converted coordinates that are suited for the game board.
   */
   public String makeCoordinate(int row, int column){                                                          // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      String coordinate = "";                                                                                  // Defines coordinate
      
      char rowVal = ' ';                                                                                       // Defines rowVal
      
      rowVal = (char)(row + 97);                                                                               // Sets the value of rowVal
      
      coordinate = rowVal + "" + column;                                                                       // Sets the value of coordinate
      
      return coordinate;                                                                                       // Returns the converted coordinate to the user 
   }
   
   
   
   
   /*
      * This is the method that will be used to print out the board to the user. For the forseeable future
      * the method will take in an array to be printed out so I can make sure that both gameBoard and secretBoard
      * are initialized with the correct values.
      
      * The Default Look for board is as follows:
         
         a   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         b   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         c   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         d   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         e   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         f   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         g   |*|*|*|*|*|*|*|*|*|*|
             ---------------------
         h   |*|*|*|*|*|*|*|*|*|*|
            
              0 1 2 3 4 5 6 7 8 9 
                       
   */
   public void printBoard(String[][] theBoard){                                                                // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      int row = 0;                                                                                             // Defines row
      int column = 0;                                                                                          // Defines column
      int columnVal = 0;                                                                                       // Defines columnVal
      
      char rowVal = 'a';                                                                                       // Defines rowVal
      
      for(row = 0; row < theBoard.length; row++){                                                              // For Loop
         
         System.out.printf("\t-");                                                                              // Prints the tab character
         
         for(column = 0; column < theBoard[row].length; column++){                                             // Nested For Loop
            System.out.print("----");                                                                          // Prints out to the user
         }
         
         System.out.printf("\n%s\t|", rowVal);                                                                 // Prints out the rowVal to the user
         
         for(column = 0; column < theBoard[row].length; column++){                                             // Nested For Loop
            
            if(!theBoard[row][column].equals("0")){
               System.out.printf(" %s |", theBoard[row][column]);                                              // Prints out to the user
            }
            else{
               String space = " ";                                                                             // Defines space
               System.out.printf(" %s |", space);                                                              // Prints out to the user
            }
         }         
         System.out.println();                                                                                 // Prints the newline character
         
         rowVal++;                                                                                             // Adds to the value of rowVal         
      }
      
      System.out.printf("\t-");                                                                                // Prints the tab character
      
      for(column = 0; column < theBoard[0].length; column++){                                                  // Nested For Loop
         System.out.print("----");                                                                             // Prints out to the user
      }
      
      System.out.printf("\n  ");                                                                               // Prints out to the user
      
      for(column = 0; column < theBoard[0].length; column++){                                                  // For Loop
         System.out.printf(" %3d", column);                                                                    // Prints the formatted information
      }
      
      System.out.println();                                                                                    // Prints the newline character
   }
   
   
   
   
}

/* 

Default Look for board

a   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
b   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
c   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
d   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
e   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
f   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
g   |*|*|*|*|*|*|*|*|*|*|
    ---------------------
h   |*|*|*|*|*|*|*|*|*|*|
   
     0 1 2 3 4 5 6 7 8 9 

When a guess is given:
   - If they are trying to mark where a flag is then the '*' will be replaced with 'F'
   - If there is no flag next to the place guessed then the '*' will be replaced with ' '
      - and the recursive function will be done to clear out all the surrounding areas until flags are found
   - If they hit a flag by accident then a message will be displayed, the '*' will be replaced with 'H'
      - and the game will terminate


Test Board 1 

a   | | | | | | |2|F|2| |
    ---------------------
b   | | | | |1|1|3|F|3|1|
    ---------------------
c   | |1|1|1|1|F|2|2|F|1|
    ---------------------
d   | |1|F|1|1|1|1|1|1|1|
    ---------------------
e   | |1|2|2|1| | |1|2|2|
    ---------------------
f   | |1|2|F|1| | |1|F|F|
    ---------------------
g   | |1|F|2|2|1|1|1|2|2|
    ---------------------
h   | |1|1|1|1|F|1| | | |
   
     0 1 2 3 4 5 6 7 8 9 
     
Flag Positions = {a7, b7, c5, c8, d2, f3, f8, f9, g2, h5}
Board Size: 8 by 10
Number of Flags: 10
   
Test Board 2

a   | | |1|1|1| | | | | |
    ---------------------
b   | | |1|F|2|1|2|1|1| |
    ---------------------
c   | | |1|1|2|F|2|F|2|1|
    ---------------------
d   | | | | |1|1| | |3|F|
    ---------------------
e   |1|1| |1|1|1| | |3|F|
    ---------------------
f   |F|1| |1|F|1| | |2|F|
    ---------------------
g   |1|2|1|3|2|2| | |1|1|
    ---------------------
h   | |1|F|2|F|1| | | | |

     0 1 2 3 4 5 6 7 8 9

Flag Positions = {b3, c5, c7, d9, e9, f0, f4, f9, h2, h4}     
Board Size: 8 by 10
Number of Flags: 10

Test Board 3

a   |F|1| |1|F|2|2|1|2|1|
    ---------------------
b   |2|2| |1|2|F|2|F|2|F|
    ---------------------
c   |F|1| | |1|1|2|1|2|1|
    ---------------------
d   |1|1| |1|1|1| | | | |
    ---------------------
e   | | | |1|F|1| |1|1|1|
    ---------------------
f   | | | |1|1|1| |1|F|1|
    ---------------------
g   | | | |1|1|1| |1|2|2|
    --------------------
h   | | | |1|F|1| | |1|F|

     0 1 2 3 4 5 6 7 8 9

Flag Positions = {a0, a4, b5, b7, b9, c0, e4, f8, h4, h9}
Board Size: 8 by 10
Number of Flags: 10

Test Board 4

a   | | | | | | |1|F| | |
    ---------------------
b   | | | |1|1|1|1| | | |
    ---------------------
c   |1|1| |2|F|3|1| | | |
    ---------------------
d   |F|1| |2|F|F|1| | | |
    ---------------------
e   |2|2|2|2|3|2|2| | | |
    ---------------------
f   |2|F|3|F|1| |1|F| | |
    ---------------------
g   |2|F|3|1|1| |1|2| | |
    ---------------------
h   |1|1|1| | | | |1|F|1|

     0 1 2 3 4 5 6 7 8 9

Flag Positions = {c4, d0, d4, f3, d5, g1, f1, h8, f7, a7}
Board Size: 8 by 10
Number of Flags: 10


Test Board 5

0   | |1|1|1| | | | | |1|1|2|F|F|1|1|F|2|
   ---------------------------------------
1   | |1|F|1| | | | | |1|F|2|2|2|1|1|3|F|
   ---------------------------------------
2   | |1|1|1|1|1|1|1|1|2|2|2|1| | | |2|F|
   ---------------------------------------
3   |1|1|1| |1|F|1|1|F|2|2|F|2|1|2|1|2|1|
   ---------------------------------------
4   |1|F|2|1|2|1|2|2|2|2|F|2|2|F|2|F|1| |
   ---------------------------------------
5   |2|2|2|F|2|1|2|F|2|3|3|2|1|1|2|1|1| |
   ---------------------------------------
6   |F|2|2|2|2|F|2|1|3|F|F|1| | | | | | |
   ---------------------------------------
7   |1|2|F|1|2|2|2| |2|F|3|1| | |1|1|1| |
   ---------------------------------------
8   | |1|1|2|2|F|2|2|3|3|2| | | |1|F|3|2|
   ---------------------------------------
9   | | | |1|F|3|F|2|F|F|3|2|1|1|1|2|F|F|
   ---------------------------------------
10  |1|1| |1|1|2|1|3|4|F|F|3|F|1| |2|3|3|
   ---------------------------------------
11  |F|1| |1|1|1| |1|F|3|3|F|2|1| |1|F|1|
   ---------------------------------------
12  |1|1| |1|F|2|1|1|1|1|1|1|1| | |2|2|2|
   ---------------------------------------
13  | | | |1|2|F|1| | | | | | | | |1|F|1| 
   
     0 1 2 3 4 5 6 7 8 9 a b c d e f g h 
     
Flag Positions = {(11, 0), (7, 2), (9, 4), (12, 4), (13, 5), (11, 8), (11, b), (10, 3), (8, f), (4, f),
                  (4, d), (0, g), (1, h), (2, h), (0, c), (0, d), (3, b), (6, a), (4, a), (1, a),
                  (3, 8), (3, 5), (5, 3), (4, 1), (6, 0), (1, 2), (5, 7), (6, 5), (8, 5), (9, 6),
                  (9, g), (9, h), (11, g), (13, g), (10, a), (10, 9), (9, 8), (9, 9), (6, 9), (7, 9)
                  }
Board Size: 14 by 18
Number of Flags: 40
*/

/* Generator where the first selection and the first initial surrounding spots are not mines */