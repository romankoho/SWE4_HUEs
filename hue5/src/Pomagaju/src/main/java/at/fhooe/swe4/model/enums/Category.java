package at.fhooe.swe4.model.enums;
//File: Category.java

public enum Category {
  CLOTHS("Kleidung"),
  ELECTRONICS("Elektronikgeräte"),
  FOOD("Haltbare Lebensmittel"),
  HYGIENE("Hygieneartikel"),
  OTHER("andere");

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

  public static Category fromText(String text) {
    switch (text) {
      case "Kleidung":
        return Category.CLOTHS;
      case "Elektronikgeräte":
        return Category.ELECTRONICS;
      case "Haltbare Lebensmittel":
        return Category.FOOD;
      case "Hygieneartikel":
        return Category.HYGIENE;
      default:
        return Category.OTHER;
    }
  }

}
