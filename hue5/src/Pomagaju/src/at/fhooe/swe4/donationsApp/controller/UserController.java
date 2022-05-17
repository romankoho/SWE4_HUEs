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

  private void initWithTestData() {
    users.put("admin", new User("Roman Kofler-Hofer", "admin"));
    users.put("John Doe", new User("Jon Doe", "1234"));
    users.put("Jane Doe", new User("Jane Doe", "save"));
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
