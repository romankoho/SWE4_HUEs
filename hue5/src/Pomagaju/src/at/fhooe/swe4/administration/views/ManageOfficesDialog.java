package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.enums.Status;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Random;

public class ManageOfficesDialog {
  private Window owner;
  private Stage dialogStage;

  private TextField nameInput;
  private ChoiceBox fedStateInput;
  private TextField distInput;
  private TextField addInput;
  private ChoiceBox statInput;

  public ManageOfficesDialog(Window owner) {
    this.owner = owner;
    this.dialogStage = new Stage();
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
    addInput = new TextField();
    inputGrid.add(addInput,1,3);

    inputGrid.add(new Label("Status: "),0,4);
    statInput = new ChoiceBox();
    statInput.getItems().addAll(Status.values());
    inputGrid.add(statInput,1,4);

    return inputGrid;
  }

  private void addOfficeDialog() {
    Button addButton = new Button("Annahmestelle hinzufügen");
    addButton.setId("button-add-office");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addButton);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,5,3,1);

    addButton.setOnAction(e-> {
      String name = nameInput.getText();
      FederalState fedState = (FederalState)fedStateInput.getValue();
      String dist = distInput.getText();
      String addr = addInput.getText();
      Status stat = (Status)statInput.getValue();

      if(name.length()>0 && fedState != null && dist.length()>0 && addr.length()>0 && stat != null) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        OfficesController.getInstance().addOffice(new ReceivingOffice(id, name, fedState, dist, addr, stat));

        nameInput.clear();
        fedStateInput.setValue(null);
        distInput.clear();
        addInput.clear();
        statInput.setValue(null);
      }
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    sceneSetup(dialogScene, "Annahmestelle hinzufügen");
  }

  private void editDemandDialog(ReceivingOffice selectedItem){
    Button saveButton = new Button("Speichern");
    saveButton.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(saveButton);

    GridPane inputGrid = createInputGrid();

    inputGrid.add(buttonBar,0,5,3,1);

    nameInput.setText(selectedItem.getName());
    fedStateInput.setValue(selectedItem.getFederalState());
    distInput.setText(selectedItem.getDistrict());
    addInput.setText(selectedItem.getAddress());
    statInput.setValue(selectedItem.getStatus());

    saveButton.setOnAction(e -> {
      OfficesController.getInstance().updateOffice(
              selectedItem, nameInput.getText(),
              (FederalState)fedStateInput.getValue(), distInput.getText(),
              addInput.getText(), (Status)statInput.getValue());
      OfficesScene.officesTable.refresh();
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    sceneSetup(dialogScene, "Annahmestelle verwalten");
  }

  private void sceneSetup(Scene scene, String stageTitle) {
    dialogStage.setScene(scene);
    dialogStage.setTitle(stageTitle);
    dialogStage.setMinWidth(300);
    dialogStage.setMinHeight(300);
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initStyle(StageStyle.UTILITY);
    dialogStage.initOwner(owner);
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
