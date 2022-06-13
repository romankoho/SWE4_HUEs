package at.fhooe.swe4.model;
//File: Article.java

import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {
  private static long serialVersionUID = 1000000001L;
  private Integer id;
  private transient StringProperty name;
  private transient StringProperty description;
  private transient ObjectProperty<Condition> condition;
  private transient ObjectProperty<Category> category;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    out.writeObject(name.get());
    out.writeObject(description.get());
    out.writeObject(condition.get());
    out.writeObject(category.get());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    name = new SimpleStringProperty((String) in.readObject());
    description = new SimpleStringProperty((String) in.readObject());
    condition = new SimpleObjectProperty<>((Condition) in.readObject());
    category = new SimpleObjectProperty<>((Category) in.readObject());
  }


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
            "ArtikelNr: " + id + ", " + name.get() +  ", " + condition.get() + ", " + category.get();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Article article = (Article) o;
    return Objects.equals(id, article.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
