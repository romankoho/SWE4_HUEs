package at.fhooe.swe4.donationsApp.controller;
//File: DonationsSceneController.java

import at.fhooe.swe4.donationsApp.views.DonationsScene;
import at.fhooe.swe4.donationsApp.views.MakeDonationDialog;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.dbMock;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.FederalState;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.Set;
import java.util.function.Predicate;

public class DonationSceneController {
  private DonationsScene view;

  private FilteredList<DemandItem> filteredDemand = new FilteredList<>(dbMock.getInstance().getDemandItems());
  private String textFilter;
  private String addressFilter;
  private Category categoryFilter;
  private FederalState federalStateFilter;

  public DonationSceneController(DonationsScene view) {
    this.view = view;
    setView();
  }

  public void addEventListenersToResultList(){
    Set<Button> keySet = view.getDemandButtonHelperStructure().keySet();
    for(Button b : keySet) {
      b.addEventHandler(ActionEvent.ACTION, e -> handleAddDonationEvent(e, view.getDemandButtonHelperStructure().get(b)));
    }
  }

  private void setView() {
    addEventListenersToResultList();

    //EventListener for completely deleted demandItem
    dbMock.getInstance().getDemandItems().addListener(new ListChangeListener<DemandItem>() {
      @Override
      public void onChanged(Change<? extends DemandItem> c) {
        view.updateResults();
        addEventListenersToResultList();
      }
    });

    //EventListener for changes of amount of deamnd
    for(int i = 0; i < dbMock.getInstance().getDemandItems().size(); i++) {
      dbMock.getInstance().getDemandItems().get(i).getObsAmount().addListener((observable, oldValue, newValue) -> {
        view.updateResults();
        addEventListenersToResultList();
      });
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
  }

  private void handleDeleteFiltersEvent(ActionEvent e) {
    view.getTextSearchField().clear();
    view.getAddressSearchField().clear();
    view.getFederalStatesDropDown().setValue(null);
    view.getCategoryDropDown().setValue(null);

    view.getFilteredDemand().setPredicate(null);
    addEventListenersToResultList();
  }

  private void handleAddDonationEvent(ActionEvent e, DemandItem demandItem) {
    MakeDonationDialog dialog = new MakeDonationDialog(view.getWindow(), demandItem, view);
    dialog.showDonationDialog();
  }

  private void filterDonations() {
    Predicate<DemandItem> textPredicate = demandItem -> textFilter == null
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
    addEventListenersToResultList();
  }

}
