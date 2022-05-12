package at.fhooe.swe4.slidingpuzzle;

public abstract class BoardException extends RuntimeException {

  private static final long serialVersionUID = -656528383983648518L;

  public BoardException(String message) {
    super(message);
  }

}
