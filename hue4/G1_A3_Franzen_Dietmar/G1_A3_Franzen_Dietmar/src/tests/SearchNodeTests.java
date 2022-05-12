import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchNodeTests {
  Board board;
  SearchNode node;

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

    node = new SearchNode(board);
  }

  @AfterEach
  void tearDown() {
    board = null;
    node = null;
  }

  @Test
  void costsFromStart() {
    System.out.println(node.costsFromStart());
  }

  @Test
  void estimatedCostsToTarget() {
    System.out.println(node.estimatedCostsToTarget());
  }

  @Test
  void estimatedTotalCosts() {
    System.out.println(node.estimatedTotalCosts());
  }

  @Test
  void toMoves() {
    SearchNode node = new SearchNode(board);
    node.setCostsFromStart(0);

    board = board.copy();
    board.move(2, 2);
    SearchNode newNode =new SearchNode(board);
    newNode.setPredecessor(node);
    newNode.setCostsFromStart(1);
    newNode.setMove(new Move(1,2));
    node = newNode;


    List<Move> moves = node.toMoves();
    assertEquals(1, moves.size());
    assertEquals(1, moves.get(0).getRow());
    assertEquals(2, moves.get(0).getCol());
  }
}