package at.fhooe.swe4.donationsApp.controller;
//File: MakeDonationDialogController.java

import at.fhooe.swe4.donationsApp.views.MakeDonationDialog;
import at.fhooe.swe4.model.Donation;
import at.fhooe.swe4.model.Model;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;
import java.util.Random;

public class MakeDonationDialogController {
  private MakeDonationDialog view;
  private Model model;

  public MakeDonationDialogController(MakeDonationDialog view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getDonateButton().addEventHandler(ActionEvent.ACTION, e -> {
      try {
        handleDonateButton(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void handleDonateButton(ActionEvent e) throws RemoteException {
    if(view.getDatePicker().getValue() == null) {
      view.getDatePicker().setStyle("-fx-border-color: red; -fx-border-width: 1");
    } else {
      view.getDatePicker().setStyle(null);
    }

    if(view.getAmount().getText().equals("")) {
      view.getAmount().setStyle("-fx-border-color: red; -fx-border-width: 1");
    } else {
      view.getDatePicker().setStyle(null);
    }

    if((view.getDatePicker().getValue() != null) && (!view.getAmount().getText().equals(""))) {

      Integer intAmount = Integer.parseInt(view.getAmount().getText());
      if (intAmount > 0 && intAmount <= view.getSelectedDemandItem().getAmount()) {

        //if full demand is covered remove demand item from demand list
        if(view.getSelectedDemandItem().getAmount() - intAmount == 0) {
          model.deleteDemand(view.getSelectedDemandItem());
        } else { //if not full demand is covered just reduce needed amount
          model.reduceDemand(view.getSelectedDemandItem(), intAmount);
        }

        Random rand = new Random();
        Integer id = rand.nextInt(10000);

        model.addDonation(new Donation(id, view.getSelectedDemandItem(), view.getDatePicker().getValue(),
                model.getCurrentUser(), intAmount));

        //update displayed list of owner window
        view.showToken();

      } else {
        view.getAmount().setStyle("-fx-border-color: red; -fx-border-width: 1");
      }
    }
  }
}
