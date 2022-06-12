package at.fhooe.swe4.model;
//File: Donation.java

import java.time.LocalDate;

public class Donation {
  private DemandItem relatedDemand;
  private LocalDate date;
  private User user;
  private Integer amount;

  public Donation(DemandItem relatedDemand, LocalDate date, User user, Integer amount) {
    this.relatedDemand = relatedDemand;
    this.date = date;
    this.user = user;
    this.amount = amount;
  }

  @Override
  public String toString() {
    return relatedDemand +", " + date +
            ", " + user +
            ", " + amount;
  }

  public DemandItem getRelatedDemand() {
    return relatedDemand;
  }

  public void setRelatedDemand(DemandItem relatedDemand) {
    this.relatedDemand = relatedDemand;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
