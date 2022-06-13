package at.fhooe.swe4.administration.views;
//file LoginScene.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.LoginSceneController;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.ReceivingOffice;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class LoginScene {

  private Stage window;
  private Scene loginScene;

  private LoginSceneController controller;

  private TableView<ReceivingOffice> officeTable;
  private Button startButton;

  public Stage getWindow() {
    return window;
  }

  public Button getStartButton() {
    return startButton;
  }

  public TableView<ReceivingOffice> getOfficeTable() {
    return officeTable;
  }

  public Scene getLoginScene() {
    return loginScene;
  }

  public LoginScene(Stage window, Model model) throws RemoteException {
    this.window = window;

    Pane loginPane = new VBox(createLoginPane());
    loginPane.setId("login-pane");

    loginScene = new Scene(loginPane, 600,600);
    loginScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
    controller = new LoginSceneController(this, model);
  }


  private Pane createLoginPane(){
    Text loginInfo = new Text("Bitte wählen Sie Ihre Annahmestelle aus.");
    loginInfo.setId("login-pane-info-text");
    loginInfo.setWrappingWidth(300);

    startButton = Utilities.createTextButton("button-login", "Login");
    officeTable = Utilities.createOfficesTable();

    VBox startPane = new VBox(loginInfo, officeTable, startButton);
    startPane.setId("login-screen-start-pane");

    return startPane;
  }

}
