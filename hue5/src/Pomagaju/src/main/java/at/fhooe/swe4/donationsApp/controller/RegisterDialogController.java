package at.fhooe.swe4.donationsApp.controller;
//File: RegisterDialogController.java

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.RegisterDialog;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

public class RegisterDialogController {

  private RegisterDialog view;
  private Model model;

  public RegisterDialogController(RegisterDialog view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getRegisterButton().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleRegisterEvent(e, view.getName(), view.getEmail(),
                view.getPw(), view.getPwConfirm());
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void handleRegisterEvent(ActionEvent e, TextField name, TextField email, PasswordField pw, PasswordField pwConfirm) throws RemoteException {
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
      model.addUser(email.getText(), newUser);
      model.setCurrentUser(newUser);

      view.closeDialog();
      DonationsScene donationsScene = new DonationsScene(view.getWindow(), model);
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

