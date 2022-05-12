import java.util.*;

public class SlidingPuzzle {
  // Berechnet die Zugfolge, welche die gegebene Board-Konfiguration in die
  // Ziel-Konfiguration überführt.
  // Wirft NoSolutionException (Checked Exception), falls es eine keine derartige
  // Zugfolge gibt.
  public List<Move> solve(Board board) throws NoSolutionException {
    Queue<SearchNode> openQueue = new PriorityQueue<SearchNode>();
    HashSet<SearchNode> closedSet = new HashSet<SearchNode>();

    // create search node from current board
    SearchNode current = new SearchNode(board);
    openQueue.add(current);

    while (!openQueue.isEmpty()) {
      // get next node
      current = openQueue.poll();

      // estimatedCostsToTarget = 0 means we found a solution
      if (current.estimatedCostsToTarget() == 0) {
        return current.toMoves();
      }

      closedSet.add(current);

      // calculate the successors
      final List<SearchNode> successors = getSuccessors(current);
      for (SearchNode successor : successors) {

        if (!closedSet.contains(successor)) {
          if (openQueue.contains(successor)
                  && current.estimatedTotalCosts() >= successor
                  .estimatedTotalCosts()) {
            // remove old node
            openQueue.remove(successor);
          }
          openQueue.add(successor);
        }
      }
    }
    return null;
  }

  // Gibt die Folge von Board-Konfigurationen auf der Konsole aus, die sich durch
  // Anwenden der Zugfolge moves auf die Ausgangskonfiguration board ergibt.
  public static void printMoves(Board board, List<Move> moves) {
    System.out.println("Starting board");
    System.out.println(board);
    moves.stream().forEach((x) -> {
      board.move(x.getRow(), x.getCol());
      System.out.println(board);
    });
  }

  private List<SearchNode> getSuccessors(SearchNode parent) {
    final List<SearchNode> result = new ArrayList<SearchNode>();
    for (int i = 0; i < 4; i++) {
      Board newBoard = parent.getBoard().copy();
      try {
        switch (i) {
          case 0:
            newBoard.moveLeft();
            break;
          case 1:
            newBoard.moveUp();
            break;
          case 2:
            newBoard.moveRight();
            break;
          case 3:
            newBoard.moveDown();
            break;
        }
        SearchNode node = new SearchNode(newBoard);
        node.setPredecessor(parent);
        node.setCostsFromStart(parent.costsFromStart() + 1);
                node.setMove(new Move(
                newBoard.getEmptyTileRow(),
                newBoard.getEmptyTileColumn()));
        result.add(node);
      } catch (IllegalMoveException ex) {
        // nothing to do here
      }
    }
    return result;
  }
  private class BoardException extends RuntimeException {
    public BoardException(String message) {
      super(message);
    }
  }
  private class IllegalMoveException extends BoardException {
    public IllegalMoveException(String message) {
      super(message);
    }
  }

  private class NoSolutionException extends Exception{
    public NoSolutionException(String message) {
      super(message);
    }
  }
}
