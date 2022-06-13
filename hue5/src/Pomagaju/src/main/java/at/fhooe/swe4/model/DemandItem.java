package at.fhooe.swe4.model;
//File: DemandItem.java

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.*;
import java.util.Objects;

public class DemandItem implements Serializable {
  private static final long serialVersionUID = 1000000003L;
  private Integer id;
  private transient ObjectProperty<Article> relatedArticle;
  private transient ObjectProperty<ReceivingOffice> relatedOffice;
  private transient IntegerProperty amount;


  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    out.writeObject(relatedArticle.get());
    out.writeObject(relatedOffice.get());
    out.writeObject(amount.get());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    relatedArticle = new SimpleObjectProperty<>((Article) in.readObject());
    relatedOffice = new SimpleObjectProperty<>((ReceivingOffice) in.readObject());
    amount = new SimpleIntegerProperty((Integer) in.readObject());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DemandItem(Integer id, Article relatedGood, ReceivingOffice relatedOffice, Integer amount) {
    this.id = id;
    this.relatedArticle = new SimpleObjectProperty<>(relatedGood);
    this.relatedOffice = new SimpleObjectProperty<>(relatedOffice);
    this.amount = new SimpleIntegerProperty();
    this.amount.setValue(amount);
  }

  public IntegerProperty getObsAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return  id + ", " + relatedArticle.get() + ", " + relatedOffice.get() + ", " + amount.get();
  }

  public Article getRelatedArticle() {
    return relatedArticle.get();
  }

  public void setRelatedArticle(Article relatedArticle) {
    this.relatedArticle.set(relatedArticle);
  }

  public ReceivingOffice getRelatedOffice() {
    return relatedOffice.get();
  }

  public void setRelatedOffice(ReceivingOffice relatedOffice) {
    this.relatedOffice.set(relatedOffice);
  }

  public Integer getAmount() {
    return amount.getValue();
  }

  public void setAmount(Integer amount) {
    this.amount.setValue(amount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DemandItem that = (DemandItem) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
