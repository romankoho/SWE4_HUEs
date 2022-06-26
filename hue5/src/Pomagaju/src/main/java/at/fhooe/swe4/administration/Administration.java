package at.fhooe.swe4.administration;
//File Administration.java

import at.fhooe.swe4.administration.views.LoginScene;
import at.fhooe.swe4.database.Database;
import at.fhooe.swe4.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.Naming;

public class Administration extends Application {

  private Stage window;
  private Model model;
  private Database db;

  @Override
  public void start(Stage primaryStage) throws Exception {
    db = (Database) Naming.lookup("rmi://localhost:1099/Database");

    model = new Model(db);
    window = primaryStage;
    LoginScene loginScene = new LoginScene(window, model);

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
