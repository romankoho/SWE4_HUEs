package at.fhooe.swe4.administration.controller;
//file ManageOfficesDialogController.java

import at.fhooe.swe4.administration.views.ManageOfficesDialog;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;
import java.util.Random;

public class ManageOfficesDialogController {
  private ManageOfficesDialog view;
  private Model model;

  public ManageOfficesDialogController(ManageOfficesDialog view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getAddOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleAddOffice(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    view.getEditOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleEditOffice(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void handleAddOffice(ActionEvent e) throws RemoteException {
    String name = view.getNameInput().getText();
    FederalState fedState = (FederalState)view.getFedStateInput().getValue();
    String dist = view.getDistInput().getText();
    String addr = view.getAddressInput().getText();
    Status stat = (Status)view.getStatInput().getValue();

    if(name.length()>0 && fedState != null && dist.length()>0 && addr.length()>0 && stat != null) {
      Random rand = new Random();
      Integer id = rand.nextInt(10000);
      model.addOffice(new ReceivingOffice(id, name, fedState, dist, addr, stat));

      view.getNameInput().clear();
      view.getFedStateInput().setValue(null);
      view.getDistInput().clear();
      view.getAddressInput().clear();
      view.getStatInput().setValue(null);
    }
  }

  private void handleEditOffice(ActionEvent e) throws RemoteException {
    ReceivingOffice selectedItem = view.getOfficesTable().getSelectionModel().getSelectedItem();
    int selectedRow = view.getOfficesTable().getSelectionModel().getSelectedIndex();

    model.updateOffice(
            selectedItem, view.getNameInput().getText(),
            (FederalState)view.getFedStateInput().getValue(), view.getDistInput().getText(),
            view.getAddressInput().getText(), (Status)view.getStatInput().getValue());

    view.getOfficesTable().refresh();
    view.getOfficesTable().getSelectionModel().select(selectedRow);
  }
}
