package at.fhooe.swe4.administration.enums;

import java.util.Arrays;

public enum Category {
  CLOTHS("Kleidung"),
  ELECTRONICS("Elektronikgeräte"),
  FOOD("Haltbare Lebensmittel"),
  HYGIENE("Hygieneartikel");

  private String stringRepresentation;

  public String getCategory() {
    return stringRepresentation;
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }

  private Category(String category) {
    this.stringRepresentation = category;
  }

}
