package at.fhooe.swe4.administration.views;
//file ManageOfficesDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ManageOfficesDialogController;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import at.fhooe.swe4.model.ReceivingOffice;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ManageOfficesDialog {
  private Window owner;
  private Stage dialogStage;
  private Model model;

  private ManageOfficesDialogController controller;

  private TextField nameInput;
  private ChoiceBox fedStateInput;
  private TextField distInput;
  private TextField addressInput;
  private ChoiceBox statInput;
  private TableView<ReceivingOffice> officesTable;
  Button addOfficeBtn = new Button("Annahmestelle hinzufügen");
  Button editOfficeBtn = new Button("Speichern");

  public ChoiceBox getFedStateInput() {
    return fedStateInput;
  }

  public TextField getNameInput() {
    return nameInput;
  }

  public TextField getDistInput() {
    return distInput;
  }

  public TextField getAddressInput() {
    return addressInput;
  }

  public ChoiceBox getStatInput() {
    return statInput;
  }

  public TableView<ReceivingOffice> getOfficesTable() {
    return officesTable;
  }

  public Button getAddOfficeBtn() {
    return addOfficeBtn;
  }

  public Button getEditOfficeBtn() {
    return editOfficeBtn;
  }

  public ManageOfficesDialog(Window owner, TableView<ReceivingOffice> officesTable, Model model) {
    this.owner = owner;
    this.model = model;
    this.dialogStage = new Stage();
    this.officesTable = officesTable;
  }

  private GridPane createInputGrid() {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("offices-input-grid");

    inputGrid.add(new Label("Name"), 0, 0);
    nameInput = new TextField();
    inputGrid.add(nameInput,1,0);

    inputGrid.add(new Label("Bundesland: "),0,1);
    fedStateInput = new ChoiceBox();
    fedStateInput.getItems().addAll(FederalState.values());
    inputGrid.add(fedStateInput,1,1);

    inputGrid.add(new Label("Bezirk"), 0, 2);
    distInput = new TextField();
    inputGrid.add(distInput,1,2);

    inputGrid.add(new Label("Adresse"), 0, 3);
    addressInput = new TextField();
    inputGrid.add(addressInput,1,3);

    inputGrid.add(new Label("Status: "),0,4);
    statInput = new ChoiceBox();
    statInput.getItems().addAll(Status.values());
    inputGrid.add(statInput,1,4);

    return inputGrid;
  }

  private void addOfficeDialog() {
    addOfficeBtn.setId("button-add-office");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addOfficeBtn);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,5,3,1);

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Annahmestelle hinzufügen");
    controller = new ManageOfficesDialogController(this, model);
  }

  private void editDemandDialog(ReceivingOffice selectedItem){
    editOfficeBtn.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(editOfficeBtn);

    GridPane inputGrid = createInputGrid();

    inputGrid.add(buttonBar,0,5,3,1);

    nameInput.setText(selectedItem.getName());
    fedStateInput.setValue(selectedItem.getFederalState());
    distInput.setText(selectedItem.getDistrict());
    addressInput.setText(selectedItem.getAddress());
    statInput.setValue(selectedItem.getStatus());

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Annahmestelle verwalten");
    controller = new ManageOfficesDialogController(this, model);
  }

  public void showAddDialog() {
    this.addOfficeDialog();
    dialogStage.show();
  }

  public void showEditDialog(ReceivingOffice selectedItem) {
    this.editDemandDialog(selectedItem);
    dialogStage.show();
  }

}
