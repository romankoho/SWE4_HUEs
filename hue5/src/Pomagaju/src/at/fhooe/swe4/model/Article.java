package at.fhooe.swe4.model;
//File: Article.java

import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Article {
  private Integer id;
  private SimpleStringProperty name;
  private SimpleStringProperty description;
  private SimpleObjectProperty<Condition> condition;
  private SimpleObjectProperty<Category> category;

  public Article(Integer id, String name, String description, Condition condition, Category category) {
    this.id = id;
    this.name = new SimpleStringProperty(name);
    this.description = new SimpleStringProperty(description);
    this.condition = new SimpleObjectProperty<>(condition);
    this.category = new SimpleObjectProperty<>(category);
  }

  @Override
  public String toString() {
    return
            "AritkelNr: " + id + ", " + name +  ", " + condition + ", " + category;
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

  public String getDescription() {
    return description.get();
  }

  public void setDescription(String description) {
    this.description.set(description);
  }

  public Condition getCondition() {
    return condition.get();
  }

  public void setCondition(Condition condition) {
    this.condition.set(condition);
  }

  public Category getCategory() {
    return category.get();
  }

  public void setCategory(Category category) {
    this.category.set(category);
  }
}
