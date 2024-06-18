package minesweeper;

public class MinesweeperTester{                                                                                // Class Block
   public static void main(String[] args){                                                                     // Method Block
      
      gameBoard theGame = new gameBoard();
      
      int count = 0;
      
      while(!theGame.gameOver()){
         
         theGame.printBoard(theGame.gameBoard);
         theGame.placeSelection();
         count++;
      }
      theGame.printBoard(theGame.gameBoard);
      
      if(theGame.revealedPieces + theGame.numMines == (theGame.gameBoard.length * theGame.gameBoard[0].length)){
         System.out.println("You Win!!!!");
      }
      System.out.println("Game over");
   }
}