package at.fhooe.swe4.donationsApp.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.donationsApp.controller.UserController;
import at.fhooe.swe4.donationsApp.models.User;
import javafx.event.ActionEvent;
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

  protected LoginDialog(Stage window) {
    dialogStage = new Stage();
    this.window = window;
  }

  private void loginDialog() {
    TextField email = new TextField();
    email.setId("user-input");
    VBox userBox = new VBox(new Label("E-Mail Adresse:"), email);

    PasswordField pw = new PasswordField();
    pw.setId("password-input");
    VBox pwBox = new VBox(new Label("Passwort:"), pw);

    Button loginButton = new Button("anmelden");
    loginButton.setId("login-button");

    loginButton.addEventHandler(ActionEvent.ACTION, (e) -> handleLoginEvent(e, email, pw));

    VBox loginPane = new VBox(userBox, pwBox, loginButton);
    loginPane.setId("loginBox-dialog");

    Scene dialogScene = new Scene(loginPane);
    dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
    Utilities.sceneSetup(window, dialogStage, dialogScene, "Registrieren");
  }

  private void handleLoginEvent(ActionEvent e, TextField email, PasswordField pw) {

    User user = UserController.getInstance().getUserByEMail(email.getText());

    if(user == null) {
      email.setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      email.setStyle(null);

      if (user.getPassword().equals(pw.getText())) {
        UserController.getInstance().setCurrentUser(user);
        dialogStage.close();
        DonationsScene donationsScene = new DonationsScene(window);
        window.setScene(donationsScene.getDonationScene());
      } else {
        pw.setStyle("-fx-border-color: red; -fx-border-width: 1px");
      }
    }
  }

  public void showLoginDialog() {
    this.loginDialog();
    dialogStage.show();
  }
}
