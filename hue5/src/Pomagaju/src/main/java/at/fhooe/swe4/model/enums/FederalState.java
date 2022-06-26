package at.fhooe.swe4.model.enums;
//File: FederalSate.java

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

  public static FederalState fromText(String text) {
    switch (text) {
      case "Vorarlberg":
        return FederalState.VORARLBERG;
      case "Tirol":
        return FederalState.TYROL;
      case "Salzburg":
        return FederalState.SALZBURG;
      case "Kärnten":
        return FederalState.CARINTHIA;
      case "Niederösterreich":
        return FederalState.LOWERAUSTRIA;
      case "Oberösterreich":
        return FederalState.UPPERAUSTRIA;
      case "Steiermark":
        return FederalState.STYRIA;
      case "Burgenland":
        return FederalState.BURGENLAND;
      default:
        return FederalState.VIEANNA;
    }
  }
}
