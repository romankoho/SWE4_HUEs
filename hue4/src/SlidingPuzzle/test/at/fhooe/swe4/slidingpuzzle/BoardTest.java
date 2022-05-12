package at.fhooe.swe4.slidingpuzzle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board(3);
  }

  @AfterEach
  void tearDown() { board = null; }

  @Test
  public void simpleIsValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 0);
      assertTrue(board.isValid());
    } catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 0);

      assertTrue(! board.isValid());
    } catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  public void simpleIsNotValidTest2() {
    Board board;
    try {
      board = new Board(3);      
      board.setTile(1, 1, 8);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 0);
      board.setTile(2, 1, 7);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 4);
      board.setTile(3, 1, 3);
      board.setTile(3, 2, 1);
      board.setTile(3, 3, 6);

      assertTrue(board.isValid());
    } catch (BoardException e) {
      fail("BoardException not expected.");
    } 
  }

  @Test
  void testToString() {
    assertEquals("1\t2\t3\t\n4\t5\t6\t\n7\t8\t0\t\n", board.toString());
  }

  @Test
  void testEquals() {
    Board b2 = new Board(3);
    assertEquals(board,b2);

    b2.setTile(2,1,7);
    assertNotEquals(board,b2);
  }

  @Test
  void compareTo() {
    Board b2 = new Board(3);
    Board b3 = new Board(4);
    Board b4 = new Board(8);

    assertEquals(0, board.compareTo(b2));
    assertEquals(-1, board.compareTo(b3));
    assertEquals(1, b3.compareTo(board));
    assertEquals(0, board.compareTo(b4));
  }

  @Test
  void getTile() {
    assertThrows(InvalidBoardIndexException.class, () -> board.getTile(2,7));
    assertEquals(0, board.getTile(3,3));
    assertEquals(1, board.getTile(1,1));
    assertEquals(5, board.getTile(2,2));
  }

  @Test
  void setTile() {
    assertEquals(6, board.getTile(2,3));
    board.setTile(2,3,7);
    assertEquals(7, board.getTile(2,3));

    assertThrows(InvalidBoardIndexException.class, () -> board.setTile(4,5, 4));
    assertThrows(InvalidTileNumberException.class, () -> board.setTile(1,3, 99));
  }


  @Test
  void setEmptyTile() {
    assertEquals(0, board.getTile(3,3));
    assertNotEquals(0, board.getTile(1,2));
    board.setEmptyTile(1,2);
    assertEquals(0, board.getTile(1,2));

  }

  @Test
  void getEmptyTileRow() {
    assertEquals(3, board.getEmptyTileRow());
    board.setEmptyTile(1,2);
    board.setTile(3,3, 2);
    assertEquals(1, board.getEmptyTileRow());
  }

  @Test
  void getEmptyTileColumn() {
    assertEquals(3, board.getEmptyTileColumn());
    board.setEmptyTile(1,2);
    board.setTile(3,3, 2);
    assertEquals(2, board.getEmptyTileColumn());
  }

  @Test
  void size() {
    assertEquals(3, board.size());
    assertFalse(board.size() != 3);
  }

  @Test
  void copy() {
    Board b2 = board.copy();
    assertEquals(b2, board);

    b2.setTile(1,3, 6);
    assertNotEquals(b2, board);

    assertTrue(board.getTile(1,3) == 3);
    assertTrue(b2.getTile(1,3) == 6);
    assertFalse(board.getTile(1,3) == 6);
    assertFalse(b2.getTile(1,3) == 3);
  }

  @Test
  void isSolvable() {
    List<Integer> a1 = Arrays.asList(0,5,2,1,8,3,4,7,6);
    List<Integer> a2 = Arrays.asList(4,1,2,5,8,3,7,0,6);
    Board b2 = new Board(a1,3);
    Board b3 = new Board(a2,3);
    assertTrue(b2.isSolvable());
    assertTrue(b3.isSolvable());

    List<Integer> a3 = Arrays.asList(1,2,3,4,5,6,8,7,0);
    List<Integer> a4 = Arrays.asList(1,5,0,3,2,8,4,6,7);
    Board b4 = new Board(a3,3);
    Board b5 = new Board(a4,3);
    assertFalse(b4.isSolvable());
    assertFalse(b5.isSolvable());
  }

  @Test
  void shuffle() {
    assertEquals("1\t2\t3\t\n4\t5\t6\t\n7\t8\t0\t\n", board.toString());
    board.shuffle();
    assertNotEquals("1\t2\t3\t\n4\t5\t6\t\n7\t8\t0\t\n", board.toString(),board.toString());
    assertTrue(board.isSolvable());
    assertTrue(board.isValid());
  }

  @Test
  void move() {
      board.move(3,2);
      assertEquals(0, board.getTile(3,2));
      assertEquals(8, board.getTile(3,3));

      //index must be valid
      assertThrows(IllegalMoveException.class, () -> board.move(2,7));

      //index must be neighbour of empty field (currently 3|2)
      assertThrows(IllegalMoveException.class, () -> board.move(1,2));

      board.move(2,2);
      assertEquals(0, board.getTile(2,2));
      assertEquals(5, board.getTile(3,2));
  }

  @Test
  void moveLeft() {
    board.moveLeft();
    assertEquals(0, board.getTile(3,2));
    assertEquals(8, board.getTile(3,3));

    board.moveLeft();
    assertEquals(0, board.getTile(3,1));
    assertEquals(7, board.getTile(3,2));
    assertEquals(8, board.getTile(3,3));

    //now can't move any further
    assertThrows(IllegalMoveException.class, () -> board.moveLeft());
  }

  @Test
  void moveRight() {
    //set start position of empty field
    board.moveLeft();
    board.moveLeft();
    board.moveUp();

    assertEquals(0, board.getTile(2,1));
    assertEquals(8, board.getTile(3,3));

    board.moveRight();
    assertEquals(5, board.getTile(2,1));
    assertEquals(0, board.getTile(2,2));

    board.moveRight();
    assertEquals(5, board.getTile(2,1));
    assertEquals(6, board.getTile(2,2));
    assertEquals(0, board.getTile(2,3));

    //now can't move any further
    assertThrows(IllegalMoveException.class, () -> board.moveRight());
  }

  @Test
  void moveUp() {
    board.moveUp();
    assertEquals(0, board.getTile(2,3));
    assertEquals(6, board.getTile(3,3));

    board.moveUp();
    assertEquals(6, board.getTile(3,3));
    assertEquals(3, board.getTile(2,3));
    assertEquals(0, board.getTile(1,3));

    //now can't move any further
    assertThrows(IllegalMoveException.class, () -> board.moveUp());
  }

  @Test
  void moveDown() {
      //set start position of empty field
    board.moveUp();
    board.moveUp();
    board.moveLeft();

    assertEquals(0, board.getTile(1,2));
    assertEquals(6, board.getTile(3,3));

    board.moveDown();
    assertEquals(5, board.getTile(1,2));
    assertEquals(0, board.getTile(2,2));

    board.moveDown();
    assertEquals(5, board.getTile(1,2));
    assertEquals(8, board.getTile(2,2));
    assertEquals(0, board.getTile(3,2));

    //now can't move any further
    assertThrows(IllegalMoveException.class, () -> board.moveDown());
  }

  @Test
  void makeMoves() {
    board.moveLeft();
    board.moveLeft();
    board.moveUp();
    board.moveUp();
    board.moveRight();
    board.moveRight();

    Board b1 = new Board(3);
    List<Move> m = Arrays.asList(Move.LEFT, Move.LEFT, Move.UP, Move.UP, Move.RIGHT, Move.RIGHT);
    b1.makeMoves(m);
    assertEquals(0, b1.getTile(1,3));

    assertEquals(board, b1);
  }

  @Test
  void isSolved() {
    assertTrue(board.isSolved());
    board.moveLeft();
    board.moveUp();
    assertFalse(board.isSolved());
  }
}
