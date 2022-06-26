package at.fhooe.swe4.donationsApp.views;
//File: RegisterDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.donationsApp.controller.RegisterDialogController;
import at.fhooe.swe4.model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterDialog {
    private Stage window;
    private Stage dialogStage;
    private Model model;
    private RegisterDialogController controller;

    private TextField name;
    private TextField email;
    private PasswordField pw;
    private PasswordField pwConfirm;
    private Button registerButton;

    public Stage getWindow() {
        return window;
    }

    public TextField getName() {
        return name;
    }

    public TextField getEmail() {
        return email;
    }

    public PasswordField getPw() {
        return pw;
    }

    public PasswordField getPwConfirm() {
        return pwConfirm;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public RegisterDialog(Stage window, Model model) {
        dialogStage = new Stage();
        this.model = model;
        this.window = window;
    }

    private void registerDialog() {
        name = new TextField();
        name.setId("name-input");
        VBox nameBox = new VBox(new Label("Name:"), name);

        email = new TextField();
        email.setId("user-input");
        VBox userBox = new VBox(new Label("E-Mail Adresse:"), email);

        pw = new PasswordField();
        pw.setId("password-input");
        VBox pwBox = new VBox(new Label("Passwort:"), pw);

        pwConfirm = new PasswordField();
        pwConfirm.setId("password-input");
        VBox pwConfirmBox = new VBox(new Label("Passwort bestätigen:"), pwConfirm);

        registerButton = new Button("Jetzt registieren");
        registerButton.setId("register-button");

        VBox registerPane = new VBox(nameBox, userBox, pwBox, pwConfirmBox, registerButton);
        registerPane.setId("registerBox-dialog");

        Scene dialogScene = new Scene(registerPane);
        dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
        Utilities.sceneSetup(window, dialogStage, dialogScene, "Registrieren");
        controller = new RegisterDialogController(this, model);
    }

    public void closeDialog(){
        dialogStage.close();
    }

    public void showRegisterDialog() {
        this.registerDialog();
        dialogStage.show();
    }
}
