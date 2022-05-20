package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.donationsApp.views.MakeDonationDialog;
import at.fhooe.swe4.model.Donation;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;

public class MakeDonationDialogController {
  private MakeDonationDialog view;

  public MakeDonationDialogController(MakeDonationDialog view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getDonateButton().addEventHandler(ActionEvent.ACTION, e -> handleDonateButton(e) );
  }

  private void handleDonateButton(ActionEvent e) {
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
          dbMock.getInstance().deleteDemand(view.getSelectedDemandItem());
        } else { //if not full demand is covered just reduce needed amount
          dbMock.getInstance().reduceDemand(view.getSelectedDemandItem(), intAmount);
        }

        dbMock.getInstance().addDonation(new Donation(view.getSelectedDemandItem(), view.getDatePicker().getValue(),
                dbMock.getInstance().getCurrentUser(), intAmount));

        //update displayed list of owner window
        view.showToken();

      } else {
        view.getAmount().setStyle("-fx-border-color: red; -fx-border-width: 1");
      }
    }
  }
}
