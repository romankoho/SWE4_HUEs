package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.donationsApp.models.User;
import java.util.HashMap;

public class UserController {

  private static UserController obj;
  private static User currentUser;

  //key = email (String); value = user object
  private static HashMap<String, User> users = new HashMap<>();

  private UserController() {}

  public static UserController getInstance() {
    if (obj == null) {
      obj = new UserController();
      obj.initWithTestData();
    }
    return obj;
  }

  public static HashMap<String, User> getUsers() {
    return users;
  }

  private void initWithTestData() {
    users.put("admin@admin.at", new User("Roman Kofler-Hofer", "admin", "admin@admin.at"));
    users.put("john.doe@gmail.com", new User("Jon Doe", "1234", "john.doe@gmail.com"));
    users.put("jane.doe@gmail.com", new User("Jane Doe", "save", "jane.doe@gmail.com"));
  }

  public void addUser(String key, User user) {
    users.put(key, user);
  }

  public User getUserByEMail(String email) {
    return users.get(email);
  }

  public void deleteUser(String key) {
    users.remove(key);
  }

  public void updatePassword(String key, String newPassword) {
    User user = users.get(key);
    user.setPassword(newPassword);
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    UserController.currentUser = currentUser;
  }
}
