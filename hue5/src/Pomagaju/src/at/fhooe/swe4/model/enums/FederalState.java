package at.fhooe.swe4.model.enums;

public enum FederalState {
  VORARLBERG("Vorarlberg"),
  TYROL("Tirol"),
  SALZBURG("Salzburg"),
  CARINTHIA("Kärnten"),
  LOWERAUSTRIA("Niederösterreich"),
  UPPERAUSTRIA("Oberösterreich"),
  STYRIA("Steiermark"),
  BURGENLAND("Burgenland"),
  VIEANNA("Wien");

  private String stringRepresentation;

  private FederalState(String federalState) {
    this.stringRepresentation = federalState;
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }
}
