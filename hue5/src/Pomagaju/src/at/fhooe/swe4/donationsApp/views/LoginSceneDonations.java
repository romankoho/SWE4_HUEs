package at.fhooe.swe4.donationsApp.views;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LoginSceneDonations {

    private Stage window;
    private Scene loginSceneDonations;

    public LoginSceneDonations(Stage window) {
        this.window = window;

        Pane loginPane = new VBox(createLoginPane());
        loginPane.setId("login-pane");

        loginSceneDonations = new Scene(loginPane,  270,550);
        loginSceneDonations.getStylesheets().add(getClass().getResource("/donationsApp.css").toString());
    }

    public Scene getLoginScene() {
        return loginSceneDonations;
    }

    private Pane createLoginPane() {
        Label registerLabel = new Label("Registriere dich jetzt");
        registerLabel.setId("register-label");
        Button registerBtn = new Button("Registrieren");
        registerBtn.setId("register-button");

        registerBtn.addEventHandler(ActionEvent.ACTION, (actionEvent -> {
            RegisterDialog diag = new RegisterDialog(window);
            diag.showRegisterDialog();
        }));

        VBox register = new VBox(registerLabel, registerBtn);
        register.setId("register-VBox");

        Label loginLabel = new Label("Du hast schon einen Account?");
        loginLabel.setId("login-label");
        Button loginBtn = new Button("Anmelden");
        loginBtn.setId("login-button");

        loginBtn.addEventHandler(ActionEvent.ACTION, (actionEvent -> {
            LoginDialog diag = new LoginDialog(window);
            diag.showLoginDialog();
        }));

        VBox login = new VBox(loginLabel, loginBtn);
        login.setId("login-VBox");

        VBox startScreen = new VBox(register, login);
        startScreen.setId("start-screen-VBox");

        return startScreen;
    }
}
