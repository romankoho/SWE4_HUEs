package at.fhooe.swe4.donationsApp.views;
//File: MakeDonationDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.donationsApp.controller.MakeDonationDialogController;
import at.fhooe.swe4.model.DemandItem;
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
  private DemandItem selectedDemandItem;
  private DonationsScene ownerScene;
  private Scene dialogScene;
  private VBox makeDonationVBox;
  private MakeDonationDialogController controller;

  private Button donateButton;
  private DatePicker datePicker;
  private TextField amount;

  public DonationsScene getOwnerScene() {
    return ownerScene;
  }

  public DemandItem getSelectedDemandItem() {
    return selectedDemandItem;
  }

  public DatePicker getDatePicker() {
    return datePicker;
  }

  public TextField getAmount() {
    return amount;
  }

  public Button getDonateButton() {
    return donateButton;
  }

  public MakeDonationDialog(Window owner, DemandItem demandItem, DonationsScene ownerScene) {
    dialogStage = new Stage();
    this.owner = owner;
    this.selectedDemandItem = demandItem;
    this.ownerScene = ownerScene;
  }

  private void makeDonationDialog() {

    datePicker = new DatePicker();
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

    amount = new TextField();
    amount.setId("make-donation-amount-input");

    VBox amountBox = new VBox(new Label("Menge" + " (" + selectedDemandItem.getAmount().toString() + " = max. Anzahl)"),amount);
    VBox makeDonationInputVBox = new VBox(dateBox, amountBox);
    makeDonationInputVBox.setId("make-donation-input-VBox");

    donateButton = new Button("Spende anmelden");
    donateButton.setId("make-donation-button");

    makeDonationVBox = new VBox(makeDonationInputVBox, donateButton);
    makeDonationVBox.setId("make-donation-dialog");

    dialogScene = new Scene(makeDonationVBox);
    dialogScene.getStylesheets().add(getClass().getResource("/donationsApp.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Spende anmelden");
    controller = new MakeDonationDialogController(this);
  }

  public void showToken() {
    Label intro = new Label ("Bitte zeigen Sie diesen Code bei der Abgabe der Spenden vor");
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
