package at.fhooe.swe4.donationsApp.controller;
//File: LoginDialogController.java

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.LoginDialog;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class LoginDialogController {
  private LoginDialog view;
  private Model model;

  public LoginDialogController(LoginDialog view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getLoginButton().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleLoginEvent(e, view.getEmail(), view.getPw());
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }


  private void handleLoginEvent(ActionEvent e, TextField email, PasswordField pw) throws RemoteException {

    User user = model.getUserByEMail(email.getText());

    if(user == null) {
      email.setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      email.setStyle(null);

      if (user.getPassword().equals(pw.getText())) {
        model.setCurrentUser(user);
        view.closeDialog();
        DonationsScene donationsScene = new DonationsScene(view.getWindow(), model);
        view.getWindow().setScene(donationsScene.getDonationScene());
      } else {
        pw.setStyle("-fx-border-color: red; -fx-border-width: 1px");
      }
    }
  }

}
