package at.fhooe.swe4.administration;

import at.fhooe.swe4.administration.controller.LoginSceneController;
import at.fhooe.swe4.administration.views.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

public class Administration extends Application {

  private Stage window;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    LoginScene loginScene = new LoginScene(window);

    window.setScene(loginScene.getLoginScene());
    window.setMinWidth(400);
    window.setWidth(1000);
    window.setHeight(800);
    window.setMinHeight(400);

    window.setTitle("Pomagaju Annahmestellen Verwaltungsservice");
    window.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
