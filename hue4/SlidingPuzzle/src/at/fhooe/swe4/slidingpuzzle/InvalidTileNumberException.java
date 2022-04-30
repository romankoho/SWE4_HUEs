package at.fhooe.swe4.slidingpuzzle;

public class InvalidTileNumberException extends BoardException{

  public InvalidTileNumberException(int number, int size) {
    super("cannot set tile number. Number must be > 0 and < " + (size*size-1));
  }
}
