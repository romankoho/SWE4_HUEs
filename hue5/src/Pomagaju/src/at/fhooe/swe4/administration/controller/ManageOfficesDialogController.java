package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.views.ManageOfficesDialog;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.model.dbMock;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import javafx.event.ActionEvent;

import java.util.Random;

public class ManageOfficesDialogController {
  private ManageOfficesDialog view;

  public ManageOfficesDialogController(ManageOfficesDialog view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getAddOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddOffice(e));
    view.getEditOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditOffice(e));
  }

  private void handleAddOffice(ActionEvent e) {
    String name = view.getNameInput().getText();
    FederalState fedState = (FederalState)view.getFedStateInput().getValue();
    String dist = view.getDistInput().getText();
    String addr = view.getAddressInput().getText();
    Status stat = (Status)view.getStatInput().getValue();

    if(name.length()>0 && fedState != null && dist.length()>0 && addr.length()>0 && stat != null) {
      Random rand = new Random();
      Integer id = rand.nextInt(10000);
      dbMock.getInstance().addOffice(new ReceivingOffice(id, name, fedState, dist, addr, stat));

      view.getNameInput().clear();
      view.getFedStateInput().setValue(null);
      view.getDistInput().clear();
      view.getAddressInput().clear();
      view.getStatInput().setValue(null);
    }
  }

  private void handleEditOffice(ActionEvent e) {
    ReceivingOffice selectedItem = view.getOfficesTable().getSelectionModel().getSelectedItem();
    dbMock.getInstance().updateOffice(
            selectedItem, view.getNameInput().getText(),
            (FederalState)view.getFedStateInput().getValue(), view.getDistInput().getText(),
            view.getAddressInput().getText(), (Status)view.getStatInput().getValue());

    view.getOfficesTable().refresh();
  }
}
