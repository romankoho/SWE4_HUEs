package at.fhooe.swe4.model;
//File: ReceivingOffice.java

import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class ReceivingOffice implements Serializable {
  private static final long serialVersionUID = 1000000005L;

  private Integer id;
  private transient StringProperty name;
  private transient ObjectProperty<FederalState> federalState;
  private transient StringProperty district;
  private transient StringProperty address;
  private transient ObjectProperty<Status> status;

  public ReceivingOffice(Integer id, String name, FederalState federalState, String district,
                         String address, Status status) {
    this.id = id;
    this.name = new SimpleStringProperty(name);
    this.federalState = new SimpleObjectProperty<>(federalState);
    this.district = new SimpleStringProperty(district);
    this.address = new SimpleStringProperty(address);
    this.status = new SimpleObjectProperty<>(status);
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    out.writeObject(name.get());
    out.writeObject(federalState.get());
    out.writeObject(district.get());
    out.writeObject(address.get());
    out.writeObject(status.get());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    name = new SimpleStringProperty((String) in.readObject());
    federalState = new SimpleObjectProperty<>((FederalState) in.readObject());
    district = new SimpleStringProperty((String) in.readObject());
    address = new SimpleStringProperty((String) in.readObject());
    status = new SimpleObjectProperty<>((Status) in.readObject());
  }

  @Override
  public String toString() {
    return "Annahmestelle: " + id + ", "
            + ", "+ name.get() +", " + district.get() + ", " + address.get();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public FederalState getFederalState() {
    return federalState.get();
  }

  public void setFederalState(FederalState federalState) {
    this.federalState.set(federalState);
  }

  public String getDistrict() {
    return district.get();
  }

  public void setDistrict(String district) {
    this.district.set(district);
  }

  public String getAddress() {
    return address.get();
  }

  public void setAddress(String address) {
    this.address.set(address);
  }

  public Status getStatus() {
    return status.get();
  }

  public void setStatus(Status status) {
    this.status.set(status);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReceivingOffice that = (ReceivingOffice) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
