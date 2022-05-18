package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.controller.DemandSceneController;
import at.fhooe.swe4.model.*;
import at.fhooe.swe4.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
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

public class DemandScene {

  //global scene management
  private Stage window;
  private final Scene mainScene;
  private Pane mainPane;

  //controller for this scene
  DemandSceneController controller;

  //View nodes

  //Tables
  private TableView<DemandItem> demandTable;
  private FilteredList<DemandItem> filteredDemand = new FilteredList<>(dbMock.getInstance().getDemandItems());

  private TableView<Donation> donationsTable;
  private FilteredList<Donation> filteredDonations = new FilteredList<>(dbMock.getInstance().getDonations());

  //Buttons and input fields
  private Button addDemandBtn;
  private Button deleteDemandBtn;
  private Button editDemandBtn;
  private Button deleteFiltersBtn;
  private Button changeActiveOfficeBtn;
  private TextField textSearchField;
  private DatePicker datePicker;

  public Stage getWindow() {return window;}

  public FilteredList<DemandItem> getFilteredDemand() {
    return filteredDemand;
  }
  public FilteredList<Donation> getFilteredDonations() {return filteredDonations;}
  public TableView<DemandItem> getDemandTable() {
    return demandTable;
  }
  public TableView<Donation> getDonationsTable() {
    return donationsTable;
  }

  public Button getAddDemandBtn() {
    return addDemandBtn;
  }
  public Button getDeleteDemandBtn() {
    return deleteDemandBtn;
  }
  public Button getEditDemandBtn() {
    return editDemandBtn;
  }
  public Button getChangeActiveOfficeBtn() {return changeActiveOfficeBtn;}
  public Button getDeleteFiltersBtn() {return deleteFiltersBtn;}
  public TextField getTextSearchField() {return textSearchField;}
  public DatePicker getDatePicker() {return datePicker;}

  public DemandScene(Stage window) {
    this.window = window;

    mainPane = new VBox();
    mainPane.getChildren().add(Utilities.createMenuBar(window));
    mainPane.getChildren().add(createMainPane());
    mainPane.setId("demand-pane");

    mainScene = new Scene(mainPane, 600,600);
    mainScene.getStylesheets().add(getClass().getResource("/administration.css").toString());

    controller = new DemandSceneController(this);
  }

  public Scene getMainScene() {
    return mainScene;
  }

  private TableView<DemandItem> createDemandTable() {
    TableView<DemandItem> demandTable = new TableView<>();
    demandTable.setId("demand-table");
    demandTable.setItems(filteredDemand);
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
    Label activeOffice = new Label(dbMock.getInstance().getActiveOffice().getId().toString() + ": " +
            dbMock.getInstance().getActiveOffice().getName());

    VBox activeOfficeInfo = new VBox(activeOfficeLabel, activeOffice);
    activeOfficeInfo.setId("active-office-info");

    changeActiveOfficeBtn = new Button("Annahmestelle ändern");
    changeActiveOfficeBtn.setId("standard-button");

    HBox activeOfficeControlBox = new HBox(activeOfficeInfo, changeActiveOfficeBtn);
    return activeOfficeControlBox;
  }

  private VBox createDemandOverview() {
    Label demandHL = new Label("Bedarfsübersicht");
    demandHL.setId("headline");

    demandTable = createDemandTable();

    addDemandBtn = Utilities.createTextButton("add-demand", "+");
    addDemandBtn.setId("standard-button");
    deleteDemandBtn = Utilities.createTextButton("delete-demand", "löschen");
    deleteDemandBtn.setId("standard-button");
    editDemandBtn = Utilities.createTextButton("edit-demand", "ändern");
    editDemandBtn.setId("standard-button");

    HBox demandButtonsPane = new HBox(addDemandBtn, deleteDemandBtn, editDemandBtn);
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
    VBox donationsBox = new VBox(donationsHL, filters, donationsTable);
    donationsBox.setId("donations-pane-content");

    return donationsBox;
  }

  private TableView<Donation> createDonationsTable() {
    TableView<Donation> donationTable = new TableView<>();
    donationTable.setId("donations-table");
    donationTable.setItems(filteredDonations);
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
    textSearchField = new TextField();
    textSearchField.setId("text-search-field");
    VBox textSearch = new VBox(new Label("Textsuche:"), textSearchField);

    datePicker = new DatePicker();
    VBox datePickerFilter = new VBox(new Label("Abgabedatum:"), datePicker);
    datePickerFilter.setId("date-search-field");

    TilePane filterSelection = new TilePane(textSearch, datePickerFilter);
    filterSelection.setId("filter-tile-pane");

    deleteFiltersBtn = new Button("Filter löschen");
    deleteFiltersBtn.setId("standard-button");

    VBox filters = new VBox(filterSelection, deleteFiltersBtn);
    filters.setId("filters-pane");

    return filters;
  }

  private Pane createMainPane() {
    HBox activeOfficeControlBox = createActiveOfficePane();
    activeOfficeControlBox.setId("activeOfficeControl-hbox");

    VBox demandPane = createDemandOverview();
    VBox donationsPane = createDonationsOverview();

    VBox mainPane = new VBox(activeOfficeControlBox, new Separator(), demandPane, new Separator(), donationsPane);
    mainPane.setId("demand-pane-content");

    return mainPane;
  }
}
