package at.fhooe.swe4.administration.enums;

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
