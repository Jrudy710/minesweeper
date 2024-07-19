import java.util.Scanner;                                                                                               // Imports Java Scanner Libraries

public class minesweeperSwingTester{
   public static void main(String[] args){
      
      Scanner input = new Scanner(System.in);                                                                           // Creates Scanner Object
                                                                                                                        
                                                                                                                        // Prints out to the user
      System.out.println("Please enter \"1\" to choose easy and \"2\" to choose medium difficulty and \"3\" to choose hard difficulty for minesweeper");    
      int userInput = input.nextInt();                                                                                  // Sets the value of userInput
      
      if(userInput == 2){                                                                                               // If the user chose medium difficulty
         new graphicalBoard(14, 18);                                                                                    // Creates an object of the graphicalBoard class
      }
      else if(userInput == 3){                                                                                          // If the user chose the hardest difficulty
         new graphicalBoard(20, 24);                                                                                    // Creates an object of the graphicalBoard class
      }
      else{                                                                                                             // if the user wants easy mode   
         new graphicalBoard(8, 10);                                                                                     // Creates an object of the gameBoard class
      }
      
   }
}