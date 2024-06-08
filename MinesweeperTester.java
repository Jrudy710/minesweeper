package minesweeper;

public class MinesweeperTester{                                                                                // Class Block
   public static void main(String[] args){                                                                     // Method Block
      
      gameBoard theGame = new gameBoard(14, 18);
      
      int count = 0;
      
      while(count < 10){
         
         theGame.printBoard(theGame.gameBoard);
         theGame.placeSelection();
         count++;
      }
   }
}