package at.fhooe.swe4.donationsApp.views;
//File: LoginDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.donationsApp.controller.LoginDialogController;
import at.fhooe.swe4.model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginDialog {
  private Stage window;
  private Stage dialogStage;
  private LoginDialogController controller;
  private Model model;

  private TextField email;
  private PasswordField pw;
  private Button loginButton;

  public Stage getWindow() {
    return window;
  }

  public TextField getEmail() {
    return email;
  }

  public PasswordField getPw() {
    return pw;
  }

  public Button getLoginButton() {
    return loginButton;
  }

  public LoginDialog(Stage window, Model model) {
    dialogStage = new Stage();
    this.model = model;
    this.window = window;
  }

  private void loginDialog() {
    email = new TextField();
    email.setId("user-input");
    VBox userBox = new VBox(new Label("E-Mail Adresse:"), email);

    pw = new PasswordField();
    pw.setId("password-input");
    VBox pwBox = new VBox(new Label("Passwort:"), pw);

    loginButton = new Button("anmelden");
    loginButton.setId("login-button");

    VBox loginPane = new VBox(userBox, pwBox, loginButton);
    loginPane.setId("loginBox-dialog");

    Scene dialogScene = new Scene(loginPane);
    dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
    Utilities.sceneSetup(window, dialogStage, dialogScene, "Anmelden");
    controller = new LoginDialogController(this, model);
  }

  public void closeDialog() {
    dialogStage.close();
  }

  public void showLoginDialog() {
    this.loginDialog();
    dialogStage.show();
  }
}
