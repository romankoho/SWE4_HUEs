package at.fhooe.swe4.administration.controller;
//file LoginSceneController.java

import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.LoginScene;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;

public class LoginSceneController {
  private LoginScene view;

  public LoginSceneController(LoginScene view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getStartButton().addEventHandler(ActionEvent.ACTION, (e) -> handleLoginButtonEvent(e));
  }

  private void handleLoginButtonEvent(ActionEvent e) {

    ReceivingOffice selectedOffice = view.getOfficeTable().getSelectionModel().getSelectedItem();
    if(selectedOffice != null) {
      dbMock.getInstance().setActiveOffice(selectedOffice);

      double x = view.getWindow().getWidth();
      double y = view.getWindow().getHeight();
      view.getWindow().setWidth(x);
      view.getWindow().setHeight(y);

      DemandScene demandScene = new DemandScene(view.getWindow());
      view.getWindow().setScene(demandScene.getMainScene());
    }
  }
}
