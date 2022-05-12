package at.fhooe.swe4.administration;

public enum Condition {
  NEW("neu"),
  SLIGHTLYUSED("wenig benutzt"),
  HEAVILYUSED("stark benutzt");

  private String condition;

  public String getCondition() {return condition;};

  private Condition(String condition) {
    this.condition = condition;

  }
}
