package at.fhooe.swe4.model;
//File: DemandItem.java

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DemandItem {
  private Integer id;
  private Article relatedArticle;
  private ReceivingOffice relatedOffice;
  private IntegerProperty amount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DemandItem(Integer id, Article relatedGood, ReceivingOffice relatedOffice, Integer amount) {
    this.id = id;
    this.relatedArticle = relatedGood;
    this.relatedOffice = relatedOffice;
    this.amount = new SimpleIntegerProperty();
    this.amount.setValue(amount);
  }

  public IntegerProperty getObsAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return  id + ", " + relatedArticle + ", " + relatedOffice + ", " + amount;
  }

  public Article getRelatedArticle() {
    return relatedArticle;
  }

  public void setRelatedArticle(Article relatedArticle) {
    this.relatedArticle = relatedArticle;
  }

  public ReceivingOffice getRelatedOffice() {
    return relatedOffice;
  }

  public void setRelatedOffice(ReceivingOffice relatedOffice) {
    this.relatedOffice = relatedOffice;
  }

  public Integer getAmount() {
    return amount.getValue();
  }

  public void setAmount(Integer amount) {
    this.amount.setValue(amount);
  }
}
