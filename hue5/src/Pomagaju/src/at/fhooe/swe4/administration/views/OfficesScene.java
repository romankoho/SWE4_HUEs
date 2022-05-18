package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfficesScene {

  private Stage window;
  private final Scene manageOfficesScene;
  private Pane mainPane;

  protected TableView<ReceivingOffice> officesTable;

  public Scene getOfficesScene() {return this.manageOfficesScene;}

  public OfficesScene(Stage window) {
    this.window = window;

    mainPane = new VBox();
    mainPane.getChildren().add(Utilities.createMenuBar(window));
    mainPane.getChildren().add(createMainPane());
    mainPane.setId("offices-pane");

    manageOfficesScene = new Scene(mainPane, 600,600);
    manageOfficesScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
  }

  protected Pane createMainPane() {
    Label articlesHL = new Label("Übersicht über Annahmestellen");
    articlesHL.setId("headline");
    officesTable = Utilities.createOfficesTable();

    Button addOffice = Utilities.createTextButton("add-office", "+");
    addOffice.setId("standard-button");
    Button editOffice = Utilities.createTextButton("edit-office", "ändern");
    editOffice.setId("standard-button");
    Button deleteOffice = Utilities.createTextButton("delete-office", "löschen");
    deleteOffice.setId("standard-button");

    addOffice.addEventHandler(ActionEvent.ACTION, (e) -> handleAddOfficeEvent(e));
    editOffice.addEventHandler(ActionEvent.ACTION, (e) -> handleEditOfficeEvent(e));
    deleteOffice.addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteOfficeEvent(e));

    HBox officesButtonPane = new HBox(addOffice, deleteOffice, editOffice);
    officesButtonPane.setId("offices-buttons-pane");

    VBox articlesPane = new VBox(articlesHL, officesTable, officesButtonPane);
    articlesPane.setId("offices-pane-content");

    return articlesPane;
  }

  private void handleDeleteOfficeEvent(ActionEvent e) {
    ReceivingOffice office = officesTable.getSelectionModel().getSelectedItem();
    if (office != null && !DemandController.getInstance().demandIsLinkedToReceivingOffice(office)) {
      OfficesController.getInstance().deleteOffice(office);
    }
  }

  private void handleEditOfficeEvent(ActionEvent e) {
    ReceivingOffice office = officesTable.getSelectionModel().getSelectedItem();
    if (office != null) {
      ManageOfficesDialog dialog = new ManageOfficesDialog(window, officesTable);
      dialog.showEditDialog(office);
    }
  }

  private void handleAddOfficeEvent(ActionEvent e) {
    ManageOfficesDialog dialog = new ManageOfficesDialog(window, officesTable);
    dialog.showAddDialog();
  }

}
