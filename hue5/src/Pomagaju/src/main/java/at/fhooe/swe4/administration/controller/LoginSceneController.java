package at.fhooe.swe4.administration.controller;
//file LoginSceneController.java

import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.LoginScene;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.ReceivingOffice;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;

public class LoginSceneController {
  private LoginScene view;
  private Model model;

  public LoginSceneController(LoginScene view, Model model) throws RemoteException {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() throws RemoteException {
    view.getOfficeTable().setItems(model.getOffices());
    view.getStartButton().addEventHandler(ActionEvent.ACTION, (e) -> {
        try {
            handleLoginButtonEvent(e);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    });
  }

  private void handleLoginButtonEvent(ActionEvent e) throws RemoteException {

    ReceivingOffice selectedOffice = view.getOfficeTable().getSelectionModel().getSelectedItem();
    if(selectedOffice != null) {
     model.setActiveOffice(selectedOffice);

      double x = view.getWindow().getWidth();
      double y = view.getWindow().getHeight();
      view.getWindow().setWidth(x);
      view.getWindow().setHeight(y);

      DemandScene demandScene = new DemandScene(view.getWindow(), model);
      view.getWindow().setScene(demandScene.getMainScene());
    }
  }
}
