package at.fhooe.swe4.donationsApp.views;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.donationsApp.controller.UserController;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class DonationsScene {

  private Stage window;
  private final Scene donationScene;
  private Pane mainPane;

  private  VBox filteredResults = new VBox();

  private Category categoryFilter = null;
  private FederalState federalStateFilter = null;
  private String addressFilter = null;
  private String textFilter = null;

  public DonationsScene(Stage window) {
    this.window = window;
    mainPane = new VBox(createMainPane());
    mainPane.setId("donations-pane");
    setFilteredResults();

    filteredResults.setId("filtered-results-vbox");

    donationScene = new Scene(mainPane, 270,550);
    donationScene.getStylesheets().add(DonationsScene.class.getResource("/donationsApp.css").toString());
  }

  public Scene getDonationScene() {return donationScene;}

  private Pane createFilterPane() {
    TextField textSearchField = new TextField();
    textSearchField.setId("text-search-field");
    VBox textSearch = new VBox(new Label("Textsuche:"), textSearchField);

    ChoiceBox federalStatesDropDown = new ChoiceBox();
    federalStatesDropDown.getItems().add(null);
    federalStatesDropDown.getItems().addAll(FederalState.values());
    VBox federalStateSearch = new VBox(new Label("Bundesland"), federalStatesDropDown);
    federalStateSearch.setId("filter-vBox");

    TextField addressSearchField = new TextField();
    VBox addressSearch = new VBox(new Label("Adresse"), addressSearchField);
    addressSearch.setId("filter-vBox");

    ChoiceBox categoryDropDown = new ChoiceBox();
    categoryDropDown.getItems().add(null);
    categoryDropDown.getItems().addAll(Category.values());
    VBox categorySearch = new VBox(new Label("Kategorie"), categoryDropDown);
    categorySearch.setId("filter-vBox");

    TilePane filterSelection = new TilePane(federalStateSearch, addressSearch, categorySearch);
    filterSelection.setId("filter-tile-pane");

    Button deleteFilters = new Button("Filter löschen");
    deleteFilters.setId("standard-button");

    FilteredList<DemandItem> filteredDemand = DemandController.getInstance().filteredDemandDonationsApp();
    textSearchField.textProperty().addListener ((observable, oldValue, newValue)-> {
      textFilter = newValue.toLowerCase();
      Predicate<DemandItem> textPredicate = demandItem -> textFilter.equals("")
              || demandItem.toString().toLowerCase().contains(textFilter)
              || demandItem.getRelatedArticle().getDescription().toLowerCase().contains(textFilter);

      filteredDemand.setPredicate(textPredicate.and(filteredDemand.getPredicate()));
      setFilteredResults();
    });

    addressSearchField.textProperty().addListener ((observable, oldValue, newValue)-> {
      addressFilter = newValue.toLowerCase();
      Predicate<DemandItem> addressPredicate = demandItem -> addressFilter.equals("")
              || demandItem.getRelatedOffice().getAddress().toLowerCase().contains(addressFilter)
              || demandItem.getRelatedOffice().getDistrict().toLowerCase().contains(addressFilter);

      filteredDemand.setPredicate(addressPredicate.and(filteredDemand.getPredicate()));
      setFilteredResults();
    });

    categoryDropDown.setOnAction( e-> {
      categoryFilter = (Category)categoryDropDown.getValue();
      Predicate<DemandItem> categoryPredicate = demandItem -> categoryFilter == null
              || categoryFilter.equals(demandItem.getRelatedArticle().getCategory());
      filteredDemand.setPredicate(categoryPredicate.and(filteredDemand.getPredicate()));
      setFilteredResults();
    });

    federalStatesDropDown.setOnAction( e-> {
      federalStateFilter = (FederalState) federalStatesDropDown.getValue();
      Predicate<DemandItem> federalStatePredicate = demandItem -> federalStateFilter == null
              || federalStateFilter.equals(demandItem.getRelatedOffice().getFederalState());
      filteredDemand.setPredicate(federalStatePredicate.and(filteredDemand.getPredicate()));
      setFilteredResults();
    });

    deleteFilters.setOnAction(event -> {
      textSearchField.clear();
      federalStatesDropDown.setValue(null);
      addressSearchField.clear();
      categoryDropDown.setValue(null);

    });

    VBox filters = new VBox(textSearch, filterSelection, deleteFilters);
    filters.setId("filters-pane");
    return filters;
  }

  private VBox demandTile(DemandItem d) {
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

    Button addDonationButton = new Button("jetzt spenden");
    addDonationButton.setId("button-donate");

    addDonationButton.addEventHandler(ActionEvent.ACTION, (e) -> handleAddDonationEvent(e, d));

    VBox demandDetailTile = new VBox(resultGrid, addDonationButton);
    demandDetailTile.setId("demand-tile-vbox");

    return demandDetailTile;
  }

  private void handleAddDonationEvent(ActionEvent e, DemandItem demandItem) {
    MakeDonationDialog dialog = new MakeDonationDialog(window, demandItem, this);
    dialog.showDonationDialog();
  }

  protected void setFilteredResults() {
    filteredResults.getChildren().clear();
    FilteredList<DemandItem> filteredDemand = DemandController.getInstance().filteredDemandDonationsApp();

    for(int i = 0; i < filteredDemand.size(); i++) {
      VBox demandTile = demandTile(filteredDemand.get(i));
      filteredResults.getChildren().add(demandTile);
    }
  }

  private Pane createMainPane() {
    Label curUser = new Label("Benutzer: " + UserController.getInstance().getCurrentUser().getName());

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
