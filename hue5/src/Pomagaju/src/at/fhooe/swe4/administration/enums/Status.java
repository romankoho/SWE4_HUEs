package at.fhooe.swe4.administration.enums;

public enum Status {
  ACTIVE("aktiv"),
  INACTIVE("inaktiv");

  private String stringRepresentation;

  private Status(String status) {
    this.stringRepresentation = status;
  }

  @Override
  public String toString() {
    return stringRepresentation;
  }
}
