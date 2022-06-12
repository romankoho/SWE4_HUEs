package at.fhooe.swe4.donationsApp.controller;
//File: LoginDialogController.java

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.LoginDialog;
import at.fhooe.swe4.model.User;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginDialogController {
  private LoginDialog view;

  public LoginDialogController(LoginDialog view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getLoginButton().addEventHandler(ActionEvent.ACTION, (e) -> handleLoginEvent(e, view.getEmail(), view.getPw()));
  }


  private void handleLoginEvent(ActionEvent e, TextField email, PasswordField pw) {

    User user = dbMock.getInstance().getUserByEMail(email.getText());

    if(user == null) {
      email.setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      email.setStyle(null);

      if (user.getPassword().equals(pw.getText())) {
        dbMock.getInstance().setCurrentUser(user);
        view.closeDialog();
        DonationsScene donationsScene = new DonationsScene(view.getWindow());
        view.getWindow().setScene(donationsScene.getDonationScene());
      } else {
        pw.setStyle("-fx-border-color: red; -fx-border-width: 1px");
      }
    }
  }

}
