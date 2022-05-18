package at.fhooe.swe4.donationsApp.models;

import java.util.Objects;

public class User {
  private String name;
  private String password;
  private String email;

  public User(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
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

  @Override
  public String toString() {
    return  name + ", " + email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
