package minesweeper;

public class MinesweeperTester{                                                                                // Class Block
   public static void main(String[] args){                                                                     // Method Block
      
      gameBoard theGame = new gameBoard();
      
      int count = 0;
      
      while(count < 10){
         
         theGame.printBoard(theGame.gameBoard);
         theGame.placeSelection();
         count++;
      }
   }
}