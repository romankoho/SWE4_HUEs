package at.fhooe.swe4.donationsApp.controller;
//File: RegisterDialogController.java

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.RegisterDialog;
import at.fhooe.swe4.model.User;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class RegisterDialogController {

  private RegisterDialog view;

  public RegisterDialogController(RegisterDialog view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getRegisterButton().addEventHandler(ActionEvent.ACTION, (e) -> handleRegisterEvent(e, view.getName(), view.getEmail(),
            view.getPw(), view.getPwConfirm()));
  }

  private void handleRegisterEvent(ActionEvent e, TextField name, TextField email, PasswordField pw, PasswordField pwConfirm) {
    boolean checkName = checkName();
    boolean checkEmail = checkEMail();
    boolean checkPW = checkPW();

    if(!checkName) {
      view.getName().setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      view.getName().setStyle(null);
    }

    if(!checkEmail) {
      view.getEmail().setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      view.getEmail().setStyle(null);
    }

    if(!checkPW) {
      view.getPw().setStyle("-fx-border-color: red; -fx-border-width: 1px");
      view.getPwConfirm().setStyle("-fx-border-color: red; -fx-border-width: 1px");
    } else {
      view.getPw().setStyle(null);
      view.getPwConfirm().setStyle(null);
    }

    if(checkName && checkEmail && checkPW) {
      email.setStyle(null);
      pw.setStyle(null);
      pwConfirm.setStyle(null);
      User newUser = new User(name.getText(), pw.getText(), email.getText());
      dbMock.getInstance().addUser(email.getText(), newUser);
      dbMock.getInstance().setCurrentUser(newUser);

      view.closeDialog();
      DonationsScene donationsScene = new DonationsScene(view.getWindow());
      view.getWindow().setScene(donationsScene.getDonationScene());
    }
  }

  private boolean checkEMail() {
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    return Pattern.compile(regexPattern).matcher(view.getEmail().getText()).matches();
  }

  private boolean checkName() {
    TextField name = view.getName();
    return  name != null && !name.getText().equals("");
  }

  private boolean checkPW() {
    return view.getPw().getText().length()>4 &&
            view.getPw().getText().equals(view.getPw().getText());
  }
}

