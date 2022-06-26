package at.fhooe.swe4.model.enums;
//File: Condition.java

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

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

  public static Condition fromText(String text) {
    if (text == "neu") {
      return Condition.NEW;
    } else if (text == "wenig benutzt") {
      return Condition.SLIGHTLYUSED;
    } else {
      return Condition.HEAVILYUSED;
    }
  }
}
