package at.fhooe.swe4.slidingpuzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlidingPuzzleSolverTest {

  @Test
  public void solveSimplePuzzleTest1() {
    try {
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setEmptyTile(3, 2);
      board.setTile(3, 3, 8);
    
      List<Move> moves = SlidingPuzzle.solve(board);
      assertEquals(1, moves.size());
      assertEquals(Move.RIGHT, moves.get(0));
    } catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }


  @Test
  public void solveSimplePuzzleTest2() {
    try {
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setEmptyTile(3, 1);
      board.setTile(3, 2, 7);
      board.setTile(3, 3, 8);
    
      List<Move> moves = SlidingPuzzle.solve(board);
      assertEquals(2, moves.size());
      assertEquals(Move.RIGHT, moves.get(0));
      assertEquals(Move.RIGHT, moves.get(1));
    } catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }


  @Test
  public void solveComplexPuzzleTest1() {

    try {
      //  8  2  7 
      //  1  4  6 
      //  3  5  X 
      Board board = new Board(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 7);
      board.setTile(2, 1, 1);
      board.setTile(2, 2, 4);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 5);
      board.setEmptyTile(3, 3);
    
      List<Move> moves = SlidingPuzzle.solve(board);
      board.makeMoves(moves);
      assertEquals(new Board(3), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }


  @Test
  public void solveRandomPuzzlesTest() {

    for (int k = 0; k < 40; k++) {
      try {
        Board board = new Board(3);
        int n = 1;
        int maxN = board.size() * board.size();
        for (int i = 1; i <= board.size(); i++)
          for (int j = 1; j <= board.size(); j++)
            board.setTile(i, j, (n++) % maxN);

        board.shuffle();
        Board shuffledBoard = board.copy();

        List<Move> moves = SlidingPuzzle.solve(board);
        board.makeMoves(moves);

        Board freshBoard = new Board(3);
        if(!freshBoard.equals(board)) {
          shuffledBoard.toString();
        }
        assertEquals(new Board(3), board);


      } catch (NoSolutionException nse) {
        fail("NoSolutionException is not expected.");
      }
    }
  }

  @Test
  public void solveSimplePuzzleTest_4x4() {
    try {
      Board board = new Board(4);      

      board.moveLeft();
      
      List<Move> moves = SlidingPuzzle.solve(board);
      assertEquals(1, moves.size());
      assertEquals(Move.RIGHT, moves.get(0));
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }


  @Test
  public void solveComplexPuzzleTest_4x4() {
    try {
      Board board = new Board(4);      

      board.moveLeft();
      board.moveLeft();
      board.moveUp();
      board.moveLeft();
      board.moveUp();
      board.moveUp();
      board.moveRight();
      board.moveDown();
      board.moveLeft();
      
      List<Move> moves = SlidingPuzzle.solve(board);
      board.makeMoves(moves);
      assertEquals(new Board(4), board);
    }
    catch (NoSolutionException nse) {
      fail("NoSolutionException is not expected.");
    }
  }

  @Test
  void printMoves() {
    Board b = new Board(3);
    List<Move> movesIn = Arrays.asList(Move.LEFT, Move.UP, Move.UP, Move.RIGHT, Move.DOWN, Move.DOWN);
    b.makeMoves(movesIn);
    List<Move> movesOut = SlidingPuzzle.solve(b);
    SlidingPuzzle.printMoves(b,movesOut);
  }

}
