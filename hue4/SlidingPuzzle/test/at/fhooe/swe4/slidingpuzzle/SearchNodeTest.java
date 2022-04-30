package at.fhooe.swe4.slidingpuzzle;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchNodeTest {

  private SearchNode node;
  private Board board = new Board(3);

  @BeforeEach
  void setUp() {
    node = new SearchNode(board);
  }

  @AfterEach
  void tearDown() { node = null; }


  @Test
  public void simpleNodeTest() {
    try {
      Board board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 0);     
      SearchNode node = new SearchNode(board);      
      assertEquals(0, node.estimatedCostsToTarget());
      
      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 2);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 0);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 5);     
      node = new SearchNode(board);      
      assertEquals(2, node.estimatedCostsToTarget());

      board = new Board(3);      
      board.setTile(1, 1, 1);
      board.setTile(1, 2, 0);
      board.setTile(1, 3, 3);
      board.setTile(2, 1, 4);
      board.setTile(2, 2, 5);
      board.setTile(2, 3, 6);
      board.setTile(3, 1, 7);
      board.setTile(3, 2, 8);
      board.setTile(3, 3, 2);     
      node = new SearchNode(board);      
      assertEquals(3, node.estimatedCostsToTarget());
    }
    catch (BoardException e) {
      fail("Unexpeced BoardException.");
    }
  }

  @Test
  void testEquals() {
    Board b2 = new Board(3);
    SearchNode n2 = new SearchNode(b2);
    assertEquals(node, n2);

    n2.getBoard().setTile(1,1,4);
    assertNotEquals(node, n2);
  }

  @Test
  void compareTo() {
    Board b2 = new Board(3);
    b2.moveLeft();
    b2.moveUp();
    b2.moveUp();
    SearchNode n2 = new SearchNode(b2);
    n2.setCostsFromStart(20);
    node.setCostsFromStart(20);

    //in n2 changes were made so more changes are neede. The cost of n2 should be higher (considering that the costs from start are the same
    assertEquals(-1, node.compareTo(n2));

    node.setCostsFromStart(100);
    assertEquals(1, node.compareTo(n2));
  }

  @Test
  void toMoves() {
    List<Move> moves = Arrays.asList(Move.LEFT, Move.UP, Move.UP, Move.RIGHT, Move.DOWN, Move.DOWN);

    //creating data structure for test
    //allocating some nodes and linking them via predecessor component
    SearchNode current = node;
    SearchNode theNewNode = null;
    for(Move m : moves) {
      Board copiedBoard = current.getBoard().copy();
      if(m == Move.LEFT) {
        copiedBoard.moveLeft();
      } else if(m == Move.RIGHT) {
        copiedBoard.moveRight();
      } else if (m == Move.UP) {
        copiedBoard.moveUp();
      } else {
        copiedBoard.moveDown();
      }
      theNewNode = new SearchNode(copiedBoard);
      theNewNode.setPredecessor(current);
      current = theNewNode;
    }

    //I'm standing now at "theNewNode". That's the last one in the list (that ist linked via predecessors)
    //For this node I'M now calling toMoves to get all previous moves that led to this state
    //The result should be the same as the List "moves" defined in the beginning
    List<Move> madeMoves = theNewNode.toMoves();
    assertEquals(madeMoves, moves);
  }


  /*
  remaining methods are just getter and setters. So I'm not testing them in detail
  */
}
