package at.fhooe.swe4.model;
//File: User.java

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
  private static final long serialVersionUID = 1000000006L;
  private transient StringProperty name;
  private transient StringProperty password;
  private transient StringProperty email;

  public User(String name, String password, String email) {
    this.name = new SimpleStringProperty(name);
    this.password = new SimpleStringProperty(password);
    this.email = new SimpleStringProperty(email);
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();

    out.writeObject(name.get());
    out.writeObject(password.get());
    out.writeObject(email.get());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    name = new SimpleStringProperty((String) in.readObject());
    password = new SimpleStringProperty((String) in.readObject());
    email = new SimpleStringProperty((String) in.readObject());
  }

  @Override
  public String toString() {
    return  name + ", " + email;
  }

  public String getEmail() {
    return email.get();
  }

  public void setEmail(String email) {
    this.email.set(email);
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getPassword() {
    return password.get();
  }

  public void setPassword(String password) {
    this.password.set(password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, password, email);
  }
}
