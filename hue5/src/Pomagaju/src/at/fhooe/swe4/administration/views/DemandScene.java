package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.Article;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import at.fhooe.swe4.donationsApp.controller.DonationsController;
import at.fhooe.swe4.donationsApp.models.Donation;
import at.fhooe.swe4.donationsApp.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class DemandScene {

  private Stage window;
  private final Scene mainScene;
  private Pane mainPane;

  private TableView<DemandItem> demandTable;
  private TableView<Donation> donationsTable;

  private String textFilter = null;
  private LocalDate dateFilter = null;

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

  private TableView<DemandItem> createDemandTable() {
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

  private HBox createActiveOfficePane() {
    Label activeOfficeLabel = new Label("aktive Annahmestelle:");
    Label activeOffice = new Label(OfficesController.getInstance().getActiveOffice().getId().toString() + ": " +
            OfficesController.getInstance().getActiveOffice().getName());

    VBox activeOfficeInfo = new VBox(activeOfficeLabel, activeOffice);
    activeOfficeInfo.setId("active-office-info");

    Button changeActiveOffice = new Button("Annahmestelle ändern");
    changeActiveOffice.setId("standard-button");

    changeActiveOffice.addEventHandler(ActionEvent.ACTION, (e) -> handleChangeActiveOfficeEvent(e));

    HBox activeOfficeControlBox = new HBox(activeOfficeInfo, changeActiveOffice);
    return activeOfficeControlBox;
  }

  private VBox createDemandOverview() {
    Label demandHL = new Label("Bedarfsübersicht");
    demandHL.setId("headline");

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

    VBox demandPane = new VBox(demandHL, demandTable, demandButtonsPane);
    demandPane.setId("demand-pane-content");
    return demandPane;
  }

  private VBox createDonationsOverview(){
    Label donationsHL = new Label("Spendenankündigungen");
    donationsHL.setId("headline");

    VBox filters = createDonationsFilterPane();

    donationsTable = createDonationsTable();
    DonationsController.getInstance().getFilteredDonations().setPredicate(
            donationItem -> donationItem.getRelatedDemand().getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
    );

    VBox donationsBox = new VBox(donationsHL, filters, donationsTable);
    donationsBox.setId("donations-pane-content");


    return donationsBox;
  }

  private TableView<Donation> createDonationsTable() {
    TableView<Donation> donationTable = new TableView<>();
    donationTable.setId("donations-table");
    donationTable.setItems(DonationsController.getInstance().getFilteredDonations());
    donationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<Donation, Article> articleCol = new TableColumn("Hilfsgut");
    TableColumn<Donation, Integer> amountCol = new TableColumn("Menge");
    TableColumn<Donation, LocalDate> dateCol = new TableColumn("Abgabedatum");
    TableColumn<Donation, User> userCol = new TableColumn<>("E-Mail SpenderIn");
    TableColumn<Donation, ReceivingOffice> officeCol = new TableColumn<>("Annahemstelle");

    articleCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation, Article>, ObservableValue<Article>>() {
      @Override
      public ObservableValue call(TableColumn.CellDataFeatures<Donation, Article> param) {
        return new SimpleStringProperty(param.getValue().getRelatedDemand().getRelatedArticle().getName());
      }
    });

    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    userCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation, User>, ObservableValue<User>>() {
      @Override
      public ObservableValue call(TableColumn.CellDataFeatures<Donation, User> param) {
        return new SimpleStringProperty(param.getValue().getUser().getEmail());
      }
    });

    officeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation, ReceivingOffice>, ObservableValue<ReceivingOffice>>() {
      @Override
      public ObservableValue call(TableColumn.CellDataFeatures<Donation, ReceivingOffice> param) {
        return new SimpleStringProperty(param.getValue().getRelatedDemand().getRelatedOffice().getName());
      }
    });

    articleCol.setMinWidth(100);
    amountCol.setMinWidth(40);
    dateCol.setMinWidth(60);
    userCol.setMinWidth(100);
    officeCol.setMinWidth(100);

    donationTable.getColumns().addAll(articleCol, amountCol, dateCol, userCol, officeCol);


    return donationTable;
  }


  private VBox createDonationsFilterPane() {
    TextField textSearchField = new TextField();
    textSearchField.setId("text-search-field");
    VBox textSearch = new VBox(new Label("Textsuche:"), textSearchField);

    DatePicker datePicker = new DatePicker();
    VBox datePickerFilter = new VBox(new Label("Abgabedatum;"), datePicker);
    datePickerFilter.setId("date-search-field");

    TilePane filterSelection = new TilePane(textSearch, datePickerFilter);
    filterSelection.setId("filter-tile-pane");

    Button deleteFilters = new Button("Filter löschen");
    deleteFilters.setId("standard-button");

    VBox filters = new VBox(filterSelection, deleteFilters);
    filters.setId("filters-pane");

    FilteredList<Donation> filteredDonations = DonationsController.getInstance().getFilteredDonations();

    textSearchField.textProperty().addListener ((observable, oldValue, newValue)-> {
      textFilter = newValue.toLowerCase();
      Predicate<Donation> textPredicate = donationItem -> textFilter.equals("")
              || donationItem.toString().toLowerCase().contains(textFilter);

      filteredDonations.setPredicate(textPredicate.and(filteredDonations.getPredicate()));
    });

    datePicker.addEventHandler(ActionEvent.ACTION, (e) -> {
      dateFilter = datePicker.getValue();
      Predicate<Donation> datePredicate = donationItem -> dateFilter == null || donationItem.getDate().equals(dateFilter);
      filteredDonations.setPredicate(datePredicate.and(filteredDonations.getPredicate()));
    });



    deleteFilters.addEventHandler(ActionEvent.ACTION, (e) -> {
      textSearchField.clear();
      datePicker.setValue(null);
    });

    return filters;
  }


  private Pane createMainPane() {
    HBox activeOfficeControlBox = createActiveOfficePane();
    activeOfficeControlBox.setId("activeOfficeControl-hbox");

    VBox demandPane = createDemandOverview();
    VBox donationsPane = createDonationsOverview();



    VBox mainPane = new VBox(activeOfficeControlBox, new Separator(), demandPane, new Separator(), donationsPane);
    mainPane.setId("demand-pane-content");

    //TODO: Spendenankündigungen nach aktivem Office filtern + überhaupt noch Filter einbauen

    return mainPane;
  }

  private void handleChangeActiveOfficeEvent(Object e) {
      double x = window.getWidth();
      double y = window.getHeight();
      window.setWidth(x);
      window.setHeight(y);

      LoginScene loginScene = new LoginScene(window);
      window.setScene(loginScene.getLoginScene());
  }

  private void handleDeleteDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      DemandController.getInstance().deleteDemand(demandI);
    }
    //demandTable.getSelectionModel().clearSelection();
  }

  private void handleAddDemandEvent(ActionEvent e) {
    ManageDemandDialog dialog = new ManageDemandDialog(window, demandTable);
    dialog.showAddDialog();
  }

  private void handleEditDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      ManageDemandDialog dialog = new ManageDemandDialog(window, demandTable);
      dialog.showEditDialog(demandI);
    }
  }
}
