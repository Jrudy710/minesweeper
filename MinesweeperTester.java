/* Jason Rudinsky
* May 18, 2024
* Independent Study

* This is the test program to make sure that the game can be successfully played. 
* Once it is all said and done the program will be allowed to select between two 
* different modes of difficulty and the program will print out the board to the
* console for the user to play a game of minesweeper to completion.

* 5/18/2024 - Original Version that just consists of making sure an object can be created and the printBoard method of the gameBoard class works
* 6/7/2024 - Now checking to make sure that the placeSelection method works 
   * and that the gameBoard is printed to reflect the changes that were made based on the user input
* 6/18/2024 - The gameOver method from the gameBoard class has now been added to make sure that the
   * user can play a game until they reached the desired endgame requirements, whether that is revealing
   * all of the pieces or by accidentally selecting a mine. Also added a print statement to say if the user 
   * won as well as another call to the method printBoard to make sure that the final conditions that cause 
   * the user to make it to the exit condition of the while loop are displayed to the user.

*/

package minesweeper;

import java.util.Scanner;                                                                                               // Imports Java Scanner Libraries

public class MinesweeperTester{                                                                                         // Class Block
   public static void main(String[] args){                                                                              // Method Block
      
      Scanner input = new Scanner(System.in);                                                                           // Creates Scanner Object
      gameBoard theGame;                                                                                                // Initializes theGame
      
      
      System.out.println("Please enter \"1\" to choose easy and \"2\" to choose medium difficulty for minesweeper");    // Prints out to the user
      int userInput = input.nextInt();                                                                                  // Sets the value of userInput
      
      if(userInput == 2){                                                                                               // If the user chose medium difficulty
         theGame = new gameBoard(14, 18);                                                                               // Creates an object of the gameBoard class
      }
      else{                                                                                                             // if the user wants easy mode   
         theGame = new gameBoard();                                                                                     // Creates an object of the gameBoard class
      }
      
      theGame.initialInfoDump();                                                                                        // Performs an action of the gameBoard class
      
      while(!theGame.gameOver()){                                                                                       // While the game isn't over
         
         theGame.printBoard();                                                                                          // Prints out the gameBoard to the user
         theGame.placeSelection();                                                                                      // Prompts the user to enter a place to try and "sweep"
      }

      theGame.printBoard();                                                                                             // Prints out the gameBoard to the user
      
      if(theGame.revealedPieces + theGame.numMines == (theGame.gameBoard.length * theGame.gameBoard[0].length)){        // Conditional for if the user won the game
         System.out.println("You Win!!!!");                                                                             // Prints out to the user
      }     
      System.out.println("Game over");                                                                                  // Prints out to the user
   }
}