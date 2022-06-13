package at.fhooe.swe4.donationsApp;
//File DonationsApp.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.Administration;
import at.fhooe.swe4.donationsApp.views.LoginSceneDonations;
import at.fhooe.swe4.model.Database;
import at.fhooe.swe4.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;


import java.rmi.Naming;

public class DonationsApp extends Application {
  private Stage window;
  private static Database db;


  @Override
  public void start(Stage primaryStage) throws Exception {
    db = (Database) Naming.lookup("rmi://localhost:1099/Database");

    window = primaryStage;
    Model model = new Model(db);

    LoginSceneDonations LoginSceneDonations = new LoginSceneDonations(window, model);
    window.setScene(LoginSceneDonations.getLoginScene());

    window.setMinWidth(200);
    window.setMinHeight(450);
    window.setWidth(470);
    window.setHeight(750);
    window.setTitle("Pomagaju - Hilfe für die Ukraine");
    window.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
