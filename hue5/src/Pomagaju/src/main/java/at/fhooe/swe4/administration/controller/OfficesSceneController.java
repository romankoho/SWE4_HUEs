package at.fhooe.swe4.administration.controller;
//file OfficesSceneController.java

import at.fhooe.swe4.administration.views.ManageOfficesDialog;
import at.fhooe.swe4.administration.views.OfficesScene;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.ReceivingOffice;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;

public class OfficesSceneController {
  private OfficesScene view;
  private Model model;

  public OfficesSceneController(OfficesScene view, Model model) throws RemoteException {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() throws RemoteException {
    view.getOfficesTable().setItems(model.getOffices());
    view.getAddOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddOfficeEvent(e));
    view.getEditOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditOfficeEvent(e));
    view.getDeleteOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleDeleteOfficeEvent(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void handleAddOfficeEvent(ActionEvent e) {
    ManageOfficesDialog dialog = new ManageOfficesDialog(view.getWindow(), view.getOfficesTable(), model);
    dialog.showAddDialog();
  }

  private void handleDeleteOfficeEvent(ActionEvent e) throws RemoteException {
    ReceivingOffice office = view.getOfficesTable().getSelectionModel().getSelectedItem();
    if (office != null && !model.demandIsLinkedToReceivingOffice(office)) {
      model.deleteOffice(office);
    }
  }

  private void handleEditOfficeEvent(ActionEvent e) {
    ReceivingOffice office = view.getOfficesTable().getSelectionModel().getSelectedItem();
    if (office != null) {
      ManageOfficesDialog dialog = new ManageOfficesDialog(view.getWindow(), view.getOfficesTable(), model);
      dialog.showEditDialog(office);
    }
  }
}

