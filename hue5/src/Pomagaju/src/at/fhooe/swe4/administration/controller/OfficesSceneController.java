package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.views.ManageOfficesDialog;
import at.fhooe.swe4.administration.views.OfficesScene;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;

public class OfficesSceneController {
  private OfficesScene view;

  public OfficesSceneController(OfficesScene view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getAddOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddOfficeEvent(e));
    view.getEditOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditOfficeEvent(e));
    view.getDeleteOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteOfficeEvent(e));
  }

  private void handleAddOfficeEvent(ActionEvent e) {
    ManageOfficesDialog dialog = new ManageOfficesDialog(view.getWindow(), view.getOfficesTable());
    dialog.showAddDialog();
  }

  private void handleDeleteOfficeEvent(ActionEvent e) {
    ReceivingOffice office = view.getOfficesTable().getSelectionModel().getSelectedItem();
    if (office != null && !dbMock.getInstance().demandIsLinkedToReceivingOffice(office)) {
      dbMock.getInstance().deleteOffice(office);
    }
  }

  private void handleEditOfficeEvent(ActionEvent e) {
    ReceivingOffice office = view.getOfficesTable().getSelectionModel().getSelectedItem();
    if (office != null) {
      ManageOfficesDialog dialog = new ManageOfficesDialog(view.getWindow(), view.getOfficesTable());
      dialog.showEditDialog(office);
    }
  }
}

