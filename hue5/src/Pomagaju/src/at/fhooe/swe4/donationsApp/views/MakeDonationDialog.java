package at.fhooe.swe4.donationsApp.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.donationsApp.controller.DonationsController;
import at.fhooe.swe4.donationsApp.controller.UserController;
import at.fhooe.swe4.donationsApp.models.Donation;
import at.fhooe.swe4.donationsApp.models.User;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.time.LocalDate;

public class MakeDonationDialog {
  private Window owner;
  private Stage dialogStage;
  private DemandItem demandItem;
  private DonationsScene ownerScene;
  private Scene dialogScene;
  private VBox makeDonationVBox;

  public MakeDonationDialog(Window owner, DemandItem demandItem, DonationsScene ownerScene) {
    dialogStage = new Stage();
    this.owner = owner;
    this.demandItem = demandItem;
    this.ownerScene = ownerScene;
  }

  private void makeDonationDialog() {

    DatePicker datePicker = new DatePicker();
    datePicker.setId("make-donation-DatePicker");

    //allow only future dates
    datePicker.setDayCellFactory(param -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date,empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 0);
      }
    });

    VBox dateBox = new VBox(new Label("Abgabedatum:"), datePicker);

    TextField amount = new TextField();
    amount.setId("make-donation-amount-input");

    VBox amountBox = new VBox(new Label("Menge" + " (" + demandItem.getAmount().toString() + " = max. Anzahl)"),amount);
    VBox makeDonationInputVBox = new VBox(dateBox, amountBox);
    makeDonationInputVBox.setId("make-donation-input-VBox");

    Button donateButton = new Button("Spende anmelden");
    donateButton.setId("make-donation-button");

    donateButton.addEventHandler(ActionEvent.ACTION, e -> handleDonateButton(e, amount, datePicker) );

    makeDonationVBox = new VBox(makeDonationInputVBox, donateButton);
    makeDonationVBox.setId("make-donation-dialog");

    dialogScene = new Scene(makeDonationVBox);
    dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Spende anmelden");
  }

  private void handleDonateButton(ActionEvent e, TextField amount, DatePicker datePicker) {
    if(datePicker.getValue() == null) {
      datePicker.setStyle("-fx-border-color: red; -fx-border-width: 1");
    } else {
      datePicker.setStyle(null);
    }

    if(amount.getText().equals("")) {
      amount.setStyle("-fx-border-color: red; -fx-border-width: 1");
    } else {
      datePicker.setStyle(null);
    }

    if((datePicker.getValue() != null) && (!amount.getText().equals(""))) {

      Integer intAmount = Integer.parseInt(amount.getText());
      if (intAmount > 0 && intAmount <= demandItem.getAmount()) {

        //if full demand is covered remove demand item from demand list
        if(demandItem.getAmount() - intAmount == 0) {
          DemandController.getInstance().deleteDemand(demandItem);
        } else { //if not full demand is covered just reduce needed amount
          DemandController.getInstance().reduceDemand(demandItem, intAmount);
        }

        DonationsController.getInstance().addDonation(new Donation(demandItem, datePicker.getValue(),
                UserController.getInstance().getCurrentUser(), intAmount));

        //update displayed list of owner window
        ownerScene.setFilteredResults();
        showToken();

      } else {
        amount.setStyle("-fx-border-color: red; -fx-border-width: 1");
      }
    }
  }

  public void showToken() {
    Label intro = new Label ("Bitte zeigen weisen Sie diesen Code bei der Abgabe der Spenden vor");
    intro.setId("token-intro-label");
    intro.setPrefWidth(300);
    intro.setWrapText(true);

    Label token = new Label ("#r3kc7");
    token.setId("token-label");

    Button closeBtn = new Button("ist notiert! Fenster schließen");
    closeBtn.setId("tokenClose-btn");

    closeBtn.addEventHandler(ActionEvent.ACTION, e -> {
      dialogStage.close();
    });

    VBox tokenBox = new VBox(intro, token, closeBtn);
    tokenBox.setId("token-VBox");

    makeDonationVBox.getChildren().clear();
    makeDonationVBox.getChildren().add(tokenBox);
  }


  public void showDonationDialog() {
    this.makeDonationDialog();
    dialogStage.show();
  }
}
