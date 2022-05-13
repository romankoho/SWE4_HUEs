package at.fhooe.swe4.administration.data;

import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;

public class DemandItem {
  private Integer id;
  private String description;
  private Condition condition;
  private Integer amount;
  private ReceivingOffice ownerOffice;
  private Category category;

  public DemandItem(Integer id, String description, Condition condition, Integer amount, ReceivingOffice ownerOffice, Category category) {
    this.id = id;
    this.description = description;
    this.condition = condition;
    this.amount = amount;
    this.ownerOffice = ownerOffice;
    this.category = category;
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

  public Condition getCondition() {
    return condition;
  }

  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public ReceivingOffice getOwnerOffice() {
    return ownerOffice;
  }

  public void setOwnerOffice(ReceivingOffice ownerOffice) {
    this.ownerOffice = ownerOffice;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
