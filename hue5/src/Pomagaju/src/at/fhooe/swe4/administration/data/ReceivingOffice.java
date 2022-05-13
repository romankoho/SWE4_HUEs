package at.fhooe.swe4.administration.data;

import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.enums.Status;

import java.util.Objects;

public class ReceivingOffice {
  private Integer id;
  private String name;
  private FederalState federalState;
  private String district;
  private String address;
  private Status status;

  public ReceivingOffice(Integer id, String name, FederalState federalState, String district,
                         String address, Status status) {
    this.id = id;
    this.name = name;
    this.federalState = federalState;
    this.district = district;
    this.address = address;
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReceivingOffice that = (ReceivingOffice) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && federalState == that.federalState && Objects.equals(district, that.district) && Objects.equals(address, that.address) && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, federalState, district, address, status);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FederalState getFederalState() {
    return federalState;
  }

  public void setFederalState(FederalState federalState) {
    this.federalState = federalState;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
