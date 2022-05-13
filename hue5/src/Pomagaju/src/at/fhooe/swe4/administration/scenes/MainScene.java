package at.fhooe.swe4.administration.scenes;

import at.fhooe.swe4.administration.data.ReceivingOffice;
import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;
import at.fhooe.swe4.administration.data.DemandItem;
import at.fhooe.swe4.administration.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScene {

  private static Stage window;
  private final Scene mainScene;
  private ReceivingOffice activeoffice;

  private static TableView<DemandItem> demandTable;
  private static final ObservableList<DemandItem> demand =
          FXCollections.observableArrayList();

  public MainScene(Stage window, ReceivingOffice activeOffice) {
    this.window = window;
    this.activeoffice = activeOffice;

    Pane mainPane = new VBox(MainScene.createMainPane());
    mainPane.setId("main-pane");

    mainScene = new Scene(mainPane, 600,600);
    mainScene.getStylesheets().add(LoginScene.class.getResource("/mainScene.css").toString());
  }

  public Scene getMainScene() {
    return mainScene;
  }

  private static TableView<DemandItem> createDemandTable() {
    TableView<DemandItem> demandTable = new TableView<>();
    demandTable.setId("demand-table");
    demandTable.setItems(demand);

    TableColumn<DemandItem, Integer> idCol = new TableColumn<>("ID");
    TableColumn<DemandItem, String> descCol = new TableColumn<>("Beschreibung");
    TableColumn<DemandItem, String> condCol = new TableColumn<>("Zustand");
    TableColumn<DemandItem, Integer> amountCol = new TableColumn<>("Menge");
    TableColumn<DemandItem, String> catCol = new TableColumn<>("Kategorie");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    condCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    catCol.setCellValueFactory(new PropertyValueFactory<>("category"));

    idCol.setMinWidth(40);
    descCol.setMinWidth(160);
    condCol.setMinWidth(40);
    amountCol.setMinWidth(20);
    catCol.setMinWidth(60);

    demandTable.getColumns().add(idCol);
    demandTable.getColumns().add(descCol);
    demandTable.getColumns().add(condCol);
    demandTable.getColumns().add(amountCol);
    demandTable.getColumns().add(catCol);

    return demandTable;
  }

  private static MenuBar createMenuBar(Stage stage) {
    MenuItem manageReceivingOffices = new MenuItem("Annahmestellen verwalten");
    Menu adminMenu = new Menu("Verwaltung");
    adminMenu.getItems().add((manageReceivingOffices));

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(adminMenu);
    return menuBar;
  }

  protected static Pane createMainPane() {
    Label demandHL = new Label("Bedarfsübersicht");
    demandHL.setId("headline");
    demandTable = createDemandTable();
    demand.add(new DemandItem(1,"Das ist eine Beschreibung", Condition.SLIGHTLYUSED,10, null, Category.CLOTHS));

    Button addDemand = Utilities.createTextButton("add-demand", "+");
    addDemand.setId("standard-button");
    Button deleteDemand = Utilities.createTextButton("delete-demand", "löschen");
    deleteDemand.setId("standard-button");
    Button editDemand = Utilities.createTextButton("edit-demand", "ändern");
    editDemand.setId("standard-button");

    addDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleAddDemandEvent(e));
    deleteDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteDemandEvent(e));
    editDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleEditDemandEvent(e));

    HBox demandButtonsPane = new HBox(addDemand, deleteDemand, editDemand);
    demandButtonsPane.setId("demand-buttons-pane");

    Label donationNoticeHL = new Label("Spendenankündigungen");
    donationNoticeHL.setId("headline");

    VBox mainPane = new VBox(createMenuBar(window), demandHL, demandTable, demandButtonsPane);      //da gehören die anderen Bestandteile noch rein
    return mainPane;
  }

  private static void handleDeleteDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      demand.remove(demandI);
    }
    //demandTable.getSelectionModel().clearSelection();
  }

  private static void handleAddDemandEvent(ActionEvent e) {
    ManageDemandDialog dialog = new ManageDemandDialog(window, demand, demandTable);
    dialog.showAddDialog();
  }

  private static void handleEditDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      ManageDemandDialog dialog = new ManageDemandDialog(window, demand, demandTable);
      dialog.showEditDialog(demandI);
    }
  }

}
