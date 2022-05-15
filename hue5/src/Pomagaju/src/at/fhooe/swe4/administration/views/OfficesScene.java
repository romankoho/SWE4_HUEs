package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.Utilities;
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

  private static Stage window;
  private final Scene manageOfficesScene;
  private static Pane mainPane;

  protected static TableView<ReceivingOffice> officesTable;

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

  protected static TableView<ReceivingOffice> createOfficesTable() {
    TableView<ReceivingOffice> officesTable = new TableView<>();
    officesTable.setId("offices-table");
    officesTable.setItems(OfficesController.getInstance().getOffices());
    officesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ReceivingOffice, Integer> idCol = new TableColumn<>("ID");
    TableColumn<ReceivingOffice, String> nameCol = new TableColumn<>("Name");
    TableColumn<ReceivingOffice, String> fedStateCol = new TableColumn<>("Bundesland");
    TableColumn<ReceivingOffice, String> distCol = new TableColumn<>("Bezirk");
    TableColumn<ReceivingOffice, String> addCol = new TableColumn<>("Adresse");
    TableColumn<ReceivingOffice, String> statCol = new TableColumn<>("Status");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    fedStateCol.setCellValueFactory(new PropertyValueFactory<>("federalState"));
    distCol.setCellValueFactory(new PropertyValueFactory<>("district"));
    addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    statCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    idCol.setMinWidth(40);
    nameCol.setMinWidth(150);
    fedStateCol.setMinWidth(80);
    distCol.setMinWidth(80);
    addCol.setMinWidth(150);
    statCol.setMinWidth(80);

    officesTable.getColumns().add(idCol);
    officesTable.getColumns().add(nameCol);
    officesTable.getColumns().add(addCol);
    officesTable.getColumns().add(fedStateCol);
    officesTable.getColumns().add(distCol);
    officesTable.getColumns().add(statCol);

    return officesTable;
  }

  protected static Pane createMainPane() {
    Label articlesHL = new Label("Übersicht über Annahmestellen");
    articlesHL.setId("headline");
    officesTable = createOfficesTable();

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

  private static void handleDeleteOfficeEvent(ActionEvent e) {
    ReceivingOffice office = officesTable.getSelectionModel().getSelectedItem();
    if (office != null && !DemandController.getInstance().demandIsLinkedToReceivingOffice(office)) {
      OfficesController.getInstance().deleteOffice(office);
    }
  }

  private static void handleEditOfficeEvent(ActionEvent e) {
    ReceivingOffice office = officesTable.getSelectionModel().getSelectedItem();
    if (office != null) {
      ManageOfficesDialog dialog = new ManageOfficesDialog(window);
      dialog.showEditDialog(office);
    }
  }

  private static void handleAddOfficeEvent(ActionEvent e) {
    ManageOfficesDialog dialog = new ManageOfficesDialog(window);
    dialog.showAddDialog();
  }

}
