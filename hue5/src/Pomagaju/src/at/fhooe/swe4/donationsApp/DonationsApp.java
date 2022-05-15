package at.fhooe.swe4.donationsApp;

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class DonationsApp extends Application {
  private Stage window;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    DonationsScene donationsScene = new DonationsScene(window);

    window.setScene(donationsScene.getDonationScene());
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
