import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTests {
  Board board;

  @BeforeEach
  void setUp() {
    /* Bord for testing
     * 6 4 3
     * - 1 2
     * 8 5 7
     *
     * solved in 25 moves
     * */
    board = new Board(3);
    board.setTile(1, 1, 6);
    board.setTile(1, 2, 4);
    board.setTile(1, 3, 3);

    board.setEmptyTile(2, 1);
    board.setTile(2, 2, 1);
    board.setTile(2, 3, 2);

    board.setTile(3, 1, 8);
    board.setTile(3, 2, 5);
    board.setTile(3, 3, 7);
  }

  @AfterEach
  void tearDown() {
    board = null;
  }

  @Test
  void testEquals() {
    Board otherBoard = new Board(3);
    otherBoard.setTile(1, 1, 6);
    otherBoard.setTile(1, 2, 4);
    otherBoard.setTile(1, 3, 3);

    otherBoard.setEmptyTile(2, 1);
    otherBoard.setTile(2, 2, 1);
    otherBoard.setTile(2, 3, 2);

    otherBoard.setTile(3, 1, 8);
    otherBoard.setTile(3, 2, 5);
    otherBoard.setTile(3, 3, 7);

    assertTrue(board.equals(otherBoard));

    otherBoard = null;
    /*
     * 6 4 3
     * 1 5 -
     * 8 7 2
     * */
    otherBoard = new Board(3);
    otherBoard.setTile(1, 1, 6);
    otherBoard.setTile(1, 2, 4);
    otherBoard.setTile(1, 3, 3);

    otherBoard.setTile(1, 2, 1);
    otherBoard.setTile(2, 2, 5);
    otherBoard.setEmptyTile(2, 3);

    otherBoard.setTile(3, 1, 8);
    otherBoard.setTile(3, 2, 7);
    otherBoard.setTile(3, 3, 2);

    assertFalse(board.equals(otherBoard));
  }

  @Test
  void compareTo() {
    assertEquals(0, board.compareTo(new Board(3)));
    assertTrue(board.compareTo(new Board(4)) > 0);
    assertTrue(new Board(4).compareTo(board) < 0);
  }

  @Test
  void getTile() {
    assertEquals(6, board.getTile(1, 1));
    assertEquals(1, board.getTile(2, 2));
    assertEquals(7, board.getTile(3, 3));
  }

  @Test
  void setTile() {
    board=null;
    board = new Board(3);
    board.setTile(1,1,1);
    assertEquals(1,board.getTile(1,1));
    board.setTile(3,3,8);
    assertEquals(8,board.getTile(3,3));
  }

  @Test
  void setEmptyTile() {
    board = null;
    board = new Board(3);
    board.setEmptyTile(1,1);
    assertEquals(0,board.getTile(1,1));
    board.setEmptyTile(2,3);
    assertEquals(0,board.getTile(2,3));
  }

  @Test
  void getEmptyTileRow() {
    assertEquals(2,board.getEmptyTileRow());
  }

  @Test
  void getEmptyTileColumn() {
    assertEquals(1,board.getEmptyTileColumn());
  }

  @Test
  void size() {
    assertEquals(3, board.size());
    assertEquals(4,new Board(4).size());
    assertEquals(6,new Board(6).size());
  }

  @Test
  void isValid() {
    assertTrue(board.isValid());
  }

  @Test
  void copy() {
    Board otherBoard = board.copy();
    assertTrue(board.equals(otherBoard));
  }

  @Test
  void shuffle() {
    Board originalBoad = board.copy();
    board.shuffle();
    assertFalse(originalBoad.equals(board));
  }

  @Test
  void move() {
    board.move(2,2);
    assertEquals(1,board.getTile(2,1));
  }

  @Test
  void moveLeft() {
    board = null;
    board = new Board(3);
    board.setTile(1,1,6);
    board.setTile(1,2,4);
    board.setTile(1,3,3);

    board.setTile(2,1,1);
    board.setEmptyTile(2,2);
    board.setTile(2,3,2);

    board.setTile(3,1,8);
    board.setTile(3,2,5);
    board.setTile(3,3,7);
    board.moveLeft();
    assertEquals(1,board.getTile(2,2));
  }

  @Test
  void moveRight() {
    board.moveRight();
    assertEquals(1,board.getTile(2,1));
  }

  @Test
  void moveUp() {
    board.moveUp();
    assertEquals(6,board.getTile(2,1));
  }

  @Test
  void moveDown() {
    board.moveDown();
    assertEquals(8,board.getTile(2,1));
  }
}