package at.fhooe.swe4.administration;

import java.util.Objects;
import at.fhooe.swe4.administration.Condition;

public class DemandItem {
  private Integer id;
  private String description;
  private String condition;
  private Integer amount;
  private ReceivingOffice ownerOffice;
  private Category category;

  public DemandItem(Integer id, String description, String condition, Integer amount, ReceivingOffice ownerOffice, Category category) {
    this.id = id;
    this.description = description;
    this.condition = condition;
    this.amount = amount;
    this.ownerOffice = ownerOffice;
    this.category = category;
  }

  public ReceivingOffice getOwnerOffice() {
    return ownerOffice;
  }

  public void setOwnerOffice(ReceivingOffice ownerOffice) {
    this.ownerOffice = ownerOffice;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCondition() {
    return condition;
  }

  public void setStatus(String condition) {
    this.condition = condition;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
