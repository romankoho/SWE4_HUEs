package at.fhooe.swe4.model.enums;
//File: Status.java

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

  public static Status fromText(String text) {
    switch (text) {
      case "aktiv":
        return Status.ACTIVE;
      default:
        return Status.INACTIVE;
    }
  }
}
