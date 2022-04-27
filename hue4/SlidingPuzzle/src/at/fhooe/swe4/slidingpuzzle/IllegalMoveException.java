package at.fhooe.swe4.slidingpuzzle;

public class IllegalMoveException extends BoardException {

  private static final long serialVersionUID = 1L;
  
  public IllegalMoveException(int row, int col, int emptyRow, int emptyCol) {
    super("cannot move empty tile from (" + emptyRow + ", " + emptyCol + ") to (" + row + ", " + col + ")");
  }

}
