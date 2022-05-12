package at.fhooe.swe4.administration;

public enum Category {
  CLOTHS("Kleidung"),
  ELECTRONICS("Elektronikgeräte"),
  FOOD("Haltbare Lebensmittel"),
  HYGENE("Hygieneartikel");

  private String category;

  public String getCategory() {
    return category;
  }

  private Category(String category) {
    this.category = category;
  }

}
