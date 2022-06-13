package at.fhooe.swe4.model;
//File: Donation.java

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Donation implements Serializable {
  private static final long serialVersionUID = 1000000004L;
  private Integer id;
  private transient ObjectProperty<DemandItem> relatedDemand;
  private transient ObjectProperty<LocalDate> date;
  private transient ObjectProperty<User> user;
  private transient IntegerProperty amount;

  public Donation(Integer id, DemandItem relatedDemand, LocalDate date, User user, Integer amount) {
    this.id = id;
    this.relatedDemand = new SimpleObjectProperty<>(relatedDemand);
    this.date = new SimpleObjectProperty<>(date);
    this.user = new SimpleObjectProperty<>(user);
    this.amount = new SimpleIntegerProperty(amount);
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    out.writeObject(relatedDemand.get());
    out.writeObject(date.get());
    out.writeObject(user.get());
    out.writeObject(amount.get());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    relatedDemand = new SimpleObjectProperty<>((DemandItem) in.readObject());
    date = new SimpleObjectProperty<>((LocalDate) in.readObject());
    user = new SimpleObjectProperty<>((User) in.readObject());
    amount = new SimpleIntegerProperty((Integer) in.readObject());
  }

  @Override
  public String toString() {
    return relatedDemand +", " + date.get() +
            ", " + user.get() +
            ", " + amount.get();
  }

  public DemandItem getRelatedDemand() {
    return relatedDemand.get();
  }

  public void setRelatedDemand(DemandItem relatedDemand) {
    this.relatedDemand.set(relatedDemand);
  }

  public LocalDate getDate() {
    return date.get();
  }

  public void setDate(LocalDate date) {
    this.date.set(date);
  }

  public User getUser() {
    return user.get();
  }

  public void setUser(User user) {
    this.user.set(user);
  }

  public Integer getAmount() {
    return amount.get();
  }

  public void setAmount(Integer amount) {
    this.amount.set(amount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Donation donation = (Donation) o;
    return Objects.equals(id, donation.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
