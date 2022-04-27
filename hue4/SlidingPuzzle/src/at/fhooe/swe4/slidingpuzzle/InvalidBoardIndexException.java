package at.fhooe.swe4.slidingpuzzle;

public class InvalidBoardIndexException extends BoardException {

  private static final long serialVersionUID = 8036307699818410761L;
  
  int i, j, size;
  
  public InvalidBoardIndexException(int i, int j, int size) {
    super("Invalid board index (" + i + ", " + j + ") in board of size " + size);
    this.i = i;
    this.j = j;
    this.size = size;
  }
  
}
