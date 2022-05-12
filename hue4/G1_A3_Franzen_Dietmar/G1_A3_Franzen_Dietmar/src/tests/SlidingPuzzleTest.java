import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlidingPuzzleTest {

  @Test
  void solve() {
    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(3);
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 0);
      board.setTile(3, 3, 8);

      List<Move> moves = solver.solve(board);
      assertEquals(1, moves.size());
      assertTrue(moves.get(0).getRow() == 3 && moves.get(0).getCol() == 3);
    } catch (Exception e) {
      fail("Exception is not expected.");
    }

    try {
      SlidingPuzzle solver = new SlidingPuzzle();
      Board board = new Board(3);
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 0);
      board.setTile(3, 2, 7);
      board.setTile(3, 3, 8);

      List<Move> moves = solver.solve(board);
      assertEquals(2, moves.size());
      assertTrue(moves.get(0).getRow() == 3 && moves.get(0).getCol() == 2);
      assertTrue(moves.get(1).getRow() == 3 && moves.get(1).getCol() == 3);
    } catch (Exception e) {
      fail("Exception is not expected.");
    }
  }
}