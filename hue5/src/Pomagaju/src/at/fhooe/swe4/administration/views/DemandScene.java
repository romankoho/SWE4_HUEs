package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.Utilities;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemandScene {

  private static Stage window;
  private final Scene mainScene;
  private static Pane mainPane;

  protected static TableView<DemandItem> demandTable;

  public DemandScene(Stage window) {
    this.window = window;

    mainPane = new VBox();
    mainPane.getChildren().add(Utilities.createMenuBar(window));
    mainPane.getChildren().add(createMainPane());
    mainPane.setId("demand-pane");

    mainScene = new Scene(mainPane, 600,600);
    mainScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
  }

  public Scene getMainScene() {
    return mainScene;
  }

  private static TableView<DemandItem> createDemandTable() {
    TableView<DemandItem> demandTable = new TableView<>();
    demandTable.setId("demand-table");
    demandTable.setItems(DemandController.getInstance().getFilteredDemand());
    demandTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<DemandItem, Integer> idCol = new TableColumn<>("ID");
    TableColumn<DemandItem, String> articleCol = new TableColumn<>("Hilfsgut");
    TableColumn<DemandItem, Integer> amountCol = new TableColumn<>("Menge");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    articleCol.setCellValueFactory(new PropertyValueFactory<>("relatedArticle"));
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

    idCol.setMinWidth(40);
    amountCol.setMinWidth(20);
    articleCol.setMinWidth(400);

    demandTable.getColumns().add(idCol);
    demandTable.getColumns().add(amountCol);
    demandTable.getColumns().add(articleCol);

    return demandTable;
  }

  protected static Pane createMainPane() {
    Label demandHL = new Label("Bedarfsübersicht");
    demandHL.setId("headline");

    Label activeOfficeLabel = new Label("aktive Annahmestelle:");
    Label activeOffice = new Label(OfficesController.getInstance().getActiveOffice().getId().toString() + ": " +
            OfficesController.getInstance().getActiveOffice().getName());

    VBox activeOfficeInfo = new VBox(activeOfficeLabel, activeOffice);
    activeOfficeInfo.setId("active-office-info");

    Button changeActiveOffice = new Button("Annahmestelle ändern");
    changeActiveOffice.setId("standard-button");

    changeActiveOffice.addEventHandler(ActionEvent.ACTION, (e) -> handleChangeActiveOfficeEvent(e));

    HBox activeOfficeControlBox = new HBox(activeOfficeInfo, changeActiveOffice);
    activeOfficeControlBox.setId("activeOfficeControl-hbox");

    VBox topBox = new VBox(activeOfficeControlBox, demandHL);
    topBox.setId("topBox");

    demandTable = createDemandTable();
    DemandController.getInstance().getFilteredDemand().setPredicate(
            demandItem -> demandItem.getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
    );

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

    VBox demandPane = new VBox(topBox, demandTable, demandButtonsPane);
    demandPane.setId("demand-pane-content");
    return demandPane;
  }

  private static void handleChangeActiveOfficeEvent(Object e) {
      double x = window.getWidth();
      double y = window.getHeight();
      window.setWidth(x);
      window.setHeight(y);

      window.setScene(LoginScene.getLoginScene());
  }

  private static void handleDeleteDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      DemandController.getInstance().deleteDemand(demandI);
    }
    //demandTable.getSelectionModel().clearSelection();
  }

  private static void handleAddDemandEvent(ActionEvent e) {
    ManageDemandDialog dialog = new ManageDemandDialog(window);
    dialog.showAddDialog();
  }

  private static void handleEditDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      ManageDemandDialog dialog = new ManageDemandDialog(window);
      dialog.showEditDialog(demandI);
    }
  }
}
