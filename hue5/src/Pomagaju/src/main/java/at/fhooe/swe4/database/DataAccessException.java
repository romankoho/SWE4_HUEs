package at.fhooe.swe4.database;

@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {
  public DataAccessException(String msg) {
    super(msg);
  }
}