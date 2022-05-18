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

import java.util.regex.Pattern;

public class RegisterDialog {
    private Stage window;
    private Stage dialogStage;

    public RegisterDialog(Stage window) {
        dialogStage = new Stage();
        this.window = window;
    }

    private void registerDialog() {
        TextField name = new TextField();
        name.setId("name-input");
        VBox nameBox = new VBox(new Label("Name:"), name);

        TextField email = new TextField();
        email.setId("user-input");
        VBox userBox = new VBox(new Label("E-Mail Adresse:"), email);

        PasswordField pw = new PasswordField();
        pw.setId("password-input");
        VBox pwBox = new VBox(new Label("Passwort:"), pw);

        PasswordField pwConfirm = new PasswordField();
        pwConfirm.setId("password-input");
        VBox pwConfirmBox = new VBox(new Label("Passwort bestätigen:"), pwConfirm);

        Button registerButton = new Button("Jetzt registieren");
        registerButton.setId("register-button");

        registerButton.addEventHandler(ActionEvent.ACTION, (e) -> handleRegisterEvent(e, name, email, pw, pwConfirm));

        VBox registerPane = new VBox(nameBox, userBox, pwBox, pwConfirmBox, registerButton);
        registerPane.setId("registerBox-dialog");

        Scene dialogScene = new Scene(registerPane);
        dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
        Utilities.sceneSetup(window, dialogStage, dialogScene, "Registrieren");
    }

    private void handleRegisterEvent(ActionEvent e, TextField name, TextField email, PasswordField pw, PasswordField pwConfirm) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        //email or pw not valid
        if(!Pattern.compile(regexPattern).matcher(email.getText()).matches() ||
                pw.getText().length()<5 ||
                !pw.getText().equals(pwConfirm.getText())) {

            //email not valid
            if (!Pattern.compile(regexPattern).matcher(email.getText()).matches()) {
                e.consume();
                email.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            } else {
                email.setStyle(null);
            }

            //pw not valid
            if (pw.getText().length() < 5 || !pw.getText().equals(pwConfirm.getText())) {
                e.consume();
                pw.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                pwConfirm.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            } else {
                pw.setStyle(null);
                pwConfirm.setStyle(null);
            }
        } else {        //everything ok
            email.setStyle(null);
            pw.setStyle(null);
            pwConfirm.setStyle(null);
            User newUser = new User(name.getText(), pw.getText(), email.getText());
            UserController.getInstance().addUser(email.getText(), newUser);
            UserController.getInstance().setCurrentUser(newUser);

            dialogStage.close();
            DonationsScene donationsScene = new DonationsScene(window);
            window.setScene(donationsScene.getDonationScene());
        }
    }

    public void showRegisterDialog() {
        this.registerDialog();
        dialogStage.show();
    }
}
