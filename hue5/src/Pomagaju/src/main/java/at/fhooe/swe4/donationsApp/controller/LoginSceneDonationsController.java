package at.fhooe.swe4.donationsApp.controller;
//File: LoginSceneDonationsController.java

import at.fhooe.swe4.donationsApp.views.LoginDialog;
import at.fhooe.swe4.donationsApp.views.LoginSceneDonations;
import at.fhooe.swe4.donationsApp.views.RegisterDialog;
import at.fhooe.swe4.model.Model;
import javafx.event.ActionEvent;

public class LoginSceneDonationsController {
  private LoginSceneDonations view;
  private Model model;

  public LoginSceneDonationsController(LoginSceneDonations view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getRegisterBtn().addEventHandler(ActionEvent.ACTION, (actionEvent -> {
      RegisterDialog diag = new RegisterDialog(view.getWindow(), model);
      diag.showRegisterDialog();
    }));

    view.getLoginBtn().addEventHandler(ActionEvent.ACTION, (actionEvent -> {
      LoginDialog diag = new LoginDialog(view.getWindow(), model);
      diag.showLoginDialog();
    }));
  }
}
