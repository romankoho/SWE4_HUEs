package at.fhooe.swe4.model.enums;
//File: Condition.java

public enum Condition {
  NEW("neu"),
  SLIGHTLYUSED("wenig benutzt"),
  HEAVILYUSED("stark benutzt");

  private String stringRepresentation;

  private Condition(String condition) {
    this.stringRepresentation = condition;
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }
}
