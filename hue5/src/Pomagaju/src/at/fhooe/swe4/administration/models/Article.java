package at.fhooe.swe4.administration.models;

import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;

public class Article {
  private Integer id;
  private String name;
  private String description;
  private Condition condition;
  private Category category;

  public Article(Integer id, String name, String description, Condition condition, Category category) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.condition = condition;
    this.category = category;
  }

  @Override
  public String toString() {
    return
            "AritkelNr: " + id +
            ", " + name +
            ", " + condition +
            ", " + category;
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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
