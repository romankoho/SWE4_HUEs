package at.fhooe.swe4.administration;

import at.fhooe.swe4.administration.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Administration extends Application {

  private Stage window;

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    LoginScene loginScene = new LoginScene(window);

    window.setScene(loginScene.getLoginScene());
    window.setMinWidth(600);
    window.setMinHeight(400);

    window.setTitle("Pomagaju Annahmestellen Verwaltungsservice");
    window.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
