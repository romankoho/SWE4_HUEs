package at.fhooe.swe4.donationsApp;

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.LoginSceneDonations;
import javafx.application.Application;
import javafx.stage.Stage;

public class DonationsApp extends Application {
  private Stage window;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    LoginSceneDonations LoginSceneDonations = new LoginSceneDonations(window);
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
