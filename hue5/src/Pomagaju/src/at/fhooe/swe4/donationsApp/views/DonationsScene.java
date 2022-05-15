package at.fhooe.swe4.donationsApp.views;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.donationsApp.controller.DonationsController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class DonationsScene {

  private static Stage window;
  private final Scene donationScene;
  private static Pane mainPane;

  private static VBox filteredResults = new VBox();

  public DonationsScene(Stage window) {
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
    federalStatesDropDown.getItems().addAll(FederalState.values());
    VBox federalStateSearch = new VBox(new Label("Bundesland"), federalStatesDropDown);
    federalStateSearch.setId("filter-vBox");

    TextField addressSearchField = new TextField();
    VBox addressSearch = new VBox(new Label("Adresse"), addressSearchField);
    addressSearch.setId("filter-vBox");

    ChoiceBox categoryDropDown = new ChoiceBox();
    categoryDropDown.getItems().addAll(Category.values());
    VBox categorySearch = new VBox(new Label("Kategorie"), categoryDropDown);
    categorySearch.setId("filter-vBox");

    TilePane filterSelection = new TilePane(federalStateSearch, addressSearch, categorySearch);
    filterSelection.setId("filter-tile-pane");

    Button deleteFilters = new Button("Filter löschen");
    deleteFilters.setId("standard-button");

    //TODO die ganzen Eventlistener, dass bei Auswahl/Eingabe gleich gefiltert wird


    deleteFilters.setOnAction(event -> {
      textSearchField.clear();
      federalStatesDropDown.setValue(null);
      addressSearchField.clear();
      categoryDropDown.setValue(null);

      //delete all filters from filtered List
      DonationsController.getFilteredDonations().setPredicate(x -> true);
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

    //TODO button press handeln

    VBox demandDetailTile = new VBox(resultGrid, addDonationButton);
    demandDetailTile.setId("demand-tile-vbox");
    return demandDetailTile;
  }

  private void setFilteredResults() {
    filteredResults.getChildren().clear();
    FilteredList<DemandItem> filteredDemand = DemandController.getInstance().getFilteredDemand();

    for(int i = 0; i < filteredDemand.size(); i++) {
      VBox demandTile = demandTile(filteredDemand.get(i));
      filteredResults.getChildren().add(demandTile);
    }
  }

  private Pane createMainPane() {
    Button myDonationsButton = new Button();
    myDonationsButton.setId("my-donations-button");
    HBox myDonations = new HBox(myDonationsButton);
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