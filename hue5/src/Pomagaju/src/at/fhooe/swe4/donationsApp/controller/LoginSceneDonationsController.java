package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.donationsApp.views.LoginDialog;
import at.fhooe.swe4.donationsApp.views.LoginSceneDonations;
import at.fhooe.swe4.donationsApp.views.RegisterDialog;
import javafx.event.ActionEvent;

public class LoginSceneDonationsController {
  private LoginSceneDonations view;

  public LoginSceneDonationsController(LoginSceneDonations view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getRegisterBtn().addEventHandler(ActionEvent.ACTION, (actionEvent -> {
      RegisterDialog diag = new RegisterDialog(view.getWindow());
      diag.showRegisterDialog();
    }));

    view.getLoginBtn().addEventHandler(ActionEvent.ACTION, (actionEvent -> {
      LoginDialog diag = new LoginDialog(view.getWindow());
      diag.showLoginDialog();
    }));
  }
}
