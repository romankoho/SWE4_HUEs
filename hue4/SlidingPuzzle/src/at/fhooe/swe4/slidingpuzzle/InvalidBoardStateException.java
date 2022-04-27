package at.fhooe.swe4.slidingpuzzle;

public class InvalidBoardStateException extends BoardException {

  private static final long serialVersionUID = -5199185628047642512L;
  
  public InvalidBoardStateException(String message) {
    super(message);
  }

}
