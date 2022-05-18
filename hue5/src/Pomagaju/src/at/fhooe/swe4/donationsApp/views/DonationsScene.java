package at.fhooe.swe4.donationsApp.views;

import at.fhooe.swe4.donationsApp.controller.DonationSceneController;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.dbMock;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DonationsScene {

  private Stage window;
  private final Scene donationScene;
  private Pane mainPane;

  private DonationSceneController controller;

  private FilteredList<DemandItem> filteredDemand = new FilteredList<>(dbMock.getInstance().getDemandItems());
  private  VBox filteredResults = new VBox();

  private TextField textSearchField;
  private ChoiceBox federalStatesDropDown;
  private TextField addressSearchField;
  private  ChoiceBox categoryDropDown;
  private Button addDonationButton;
  private Button deleteFiltersBtn;
  private DemandItem selectedDemandItem;

  public DemandItem getSelectedDemandItem() {
    return selectedDemandItem;
  }

  public Stage getWindow() {
    return window;
  }

  public Button getDeleteFiltersBtn() {
    return deleteFiltersBtn;
  }

  public FilteredList<DemandItem> getFilteredDemand() {
    return filteredDemand;
  }

  public TextField getTextSearchField() {
    return textSearchField;
  }

  public ChoiceBox getFederalStatesDropDown() {
    return federalStatesDropDown;
  }

  public TextField getAddressSearchField() {
    return addressSearchField;
  }

  public ChoiceBox getCategoryDropDown() {
    return categoryDropDown;
  }

  public Button getAddDonationButton() {
    return addDonationButton;
  }

  public DonationsScene(Stage window) {
    this.window = window;
    mainPane = new VBox(createMainPane());
    mainPane.setId("donations-pane");
    updateResults();

    filteredResults.setId("filtered-results-vbox");

    donationScene = new Scene(mainPane, 270,550);
    donationScene.getStylesheets().add(DonationsScene.class.getResource("/donationsApp.css").toString());
    controller = new DonationSceneController(this);
  }

  public Scene getDonationScene() {return donationScene;}

  private Pane createFilterPane() {
    textSearchField = new TextField();
    textSearchField.setId("text-search-field");
    VBox textSearch = new VBox(new Label("Textsuche:"), textSearchField);

    federalStatesDropDown = new ChoiceBox();
    federalStatesDropDown.getItems().add(null);
    federalStatesDropDown.getItems().addAll(FederalState.values());
    VBox federalStateSearch = new VBox(new Label("Bundesland"), federalStatesDropDown);
    federalStateSearch.setId("filter-vBox");

    addressSearchField = new TextField();
    VBox addressSearch = new VBox(new Label("Adresse"), addressSearchField);
    addressSearch.setId("filter-vBox");

    categoryDropDown = new ChoiceBox();
    categoryDropDown.getItems().add(null);
    categoryDropDown.getItems().addAll(Category.values());
    VBox categorySearch = new VBox(new Label("Kategorie"), categoryDropDown);
    categorySearch.setId("filter-vBox");

    TilePane filterSelection = new TilePane(federalStateSearch, addressSearch, categorySearch);
    filterSelection.setId("filter-tile-pane");

    deleteFiltersBtn = new Button("Filter löschen");
    deleteFiltersBtn.setId("standard-button");

    VBox filters = new VBox(textSearch, filterSelection, deleteFiltersBtn);
    filters.setId("filters-pane");
    return filters;
  }

  public HashMap<Button, DemandItem> demandButtonHelperStructure = new HashMap<>();

  private VBox demandTile(DemandItem d) {
    selectedDemandItem = d;

    GridPane resultGrid = new GridPane();
    resultGrid.setId("donations-grid");
    resultGrid.add(new Label("Kategorie:"),0,0);
    String temp = d.getRelatedArticle().getCategory().toString();
    resultGrid.add(new Label(temp),1,0);

    resultGrid.add(new Label("benötigte Menge:"),0,1);
    temp = d.getAmount().toString();
    resultGrid.add(new Label(temp),1,1);

    resultGrid.add(new Label("Hilfsgut:"),0,2);
    temp = d.getRelatedArticle().getName();
    resultGrid.add(new Label(temp),1,2);

    resultGrid.add(new Label("Beschreibung:"),0,3);
    temp = d.getRelatedArticle().getDescription();
    resultGrid.add(new Label(temp),1,3);

    resultGrid.add(new Label("Zustand:"),0,4);
    temp = d.getRelatedArticle().getCondition().toString();
    resultGrid.add(new Label(temp),1,4);

    resultGrid.add(new Label("Annahmestelle:"),0,5);
    temp = d.getRelatedOffice().getName();
    resultGrid.add(new Label(temp),1,5);

    resultGrid.add(new Label("Adresse:"),0,6);
    temp = d.getRelatedOffice().getAddress() + " (" + d.getRelatedOffice().getDistrict() + ")";
    resultGrid.add(new Label(temp),1,6);

    addDonationButton = new Button("jetzt spenden");
    addDonationButton.setId("button-donate");
    demandButtonHelperStructure.put(addDonationButton, d);

    VBox demandDetailTile = new VBox(resultGrid, addDonationButton);
    demandDetailTile.setId("demand-tile-vbox");

    return demandDetailTile;
  }

  public void updateResults() {
    filteredResults.getChildren().clear();

    for(int i = 0; i < filteredDemand.size(); i++) {
      VBox demandTile = demandTile(filteredDemand.get(i));
      filteredResults.getChildren().add(demandTile);
    }
  }

  private Pane createMainPane() {
    Label curUser = new Label("Benutzer: " + dbMock.getInstance().getCurrentUser().getName());

    Button myDonationsButton = new Button();
    myDonationsButton.setId("my-donations-button");
    HBox myDonations = new HBox(curUser, myDonationsButton);
    myDonations.setId("myDonations-pane");

    Pane filters = createFilterPane();

    ScrollPane scrollArea = new ScrollPane();
    scrollArea.setId("donation-scene-scroll-area");
    scrollArea.setContent(filteredResults);

    VBox donationsPane = new VBox(myDonations, new Separator(), filters, new Separator(), scrollArea);
    donationsPane.setId("donations-pane-content");
    return donationsPane;
  }
}
