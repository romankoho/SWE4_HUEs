package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.MakeDonationDialog;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.FederalState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.Set;
import java.util.function.Predicate;

public class DonationSceneController {
  private DonationsScene view;
  private String textFilter;
  private String addressFilter;
  private Category categoryFilter;
  private FederalState federalStateFilter;

  public DonationSceneController(DonationsScene view) {
    this.view = view;
    setView();
  }

  private void setView() {
    Set<Button> keySet = view.demandButtonHelperStructure.keySet();
    for(Button b : keySet) {
      b.addEventHandler(ActionEvent.ACTION, e -> handleAddDonationEvent(e, view.demandButtonHelperStructure.get(b)));
    }

    view.getTextSearchField().textProperty().addListener ((observable, oldValue, newValue)-> {
      textFilter = newValue.toLowerCase();
      filterDonations();
    });

    view.getAddressSearchField().textProperty().addListener ((observable, oldValue, newValue)-> {
      addressFilter = newValue.toLowerCase();
      filterDonations();
    });

    view.getCategoryDropDown().addEventHandler(ActionEvent.ACTION, (event -> {
      categoryFilter = (Category) view.getCategoryDropDown().getValue();
      filterDonations();
    }));

    view.getFederalStatesDropDown().addEventHandler(ActionEvent.ACTION, e-> {
      federalStateFilter = (FederalState) view.getFederalStatesDropDown().getValue();
      filterDonations();
    });

    view.getDeleteFiltersBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteFiltersEvent(e));

    //view.getAddDonationButton().addEventHandler(ActionEvent.ACTION, (e) -> handleAddDonationEvent(e, view.getSelectedDemandItem()));
  }

  private void handleDeleteFiltersEvent(ActionEvent e) {
    view.getTextSearchField().clear();
    view.getAddressSearchField().clear();
    view.getFederalStatesDropDown().setValue(null);
    view.getCategoryDropDown().setValue(null);

    view.getFilteredDemand().setPredicate(null);
  }

  private void handleAddDonationEvent(ActionEvent e, DemandItem demandItem) {
    System.out.println(demandItem.toString());
    MakeDonationDialog dialog = new MakeDonationDialog(view.getWindow(), demandItem, null);
    dialog.showDonationDialog();
  }

  private void filterDonations() {
    Predicate<DemandItem> textPredicate = demandItem -> textFilter.equals("")
            || demandItem.toString().toLowerCase().contains(textFilter)
            || demandItem.getRelatedArticle().getDescription().toLowerCase().contains(textFilter);

    Predicate<DemandItem> addressPredicate = demandItem -> addressFilter == null || addressFilter.equals("")
            || demandItem.getRelatedOffice().getAddress().toLowerCase().contains(addressFilter)
            || demandItem.getRelatedOffice().getDistrict().toLowerCase().contains(addressFilter);

    Predicate<DemandItem> categoryPredicate = demandItem -> categoryFilter == null ||
            demandItem.getRelatedArticle().getCategory().equals(categoryFilter);

    Predicate<DemandItem> federalStatePredicate = demandItem -> federalStateFilter == null ||
            demandItem.getRelatedOffice().getFederalState().equals(federalStateFilter);

    view.getFilteredDemand().setPredicate(textPredicate.and(addressPredicate).
            and(categoryPredicate).and(federalStatePredicate));

    view.updateResults();
  }
}
