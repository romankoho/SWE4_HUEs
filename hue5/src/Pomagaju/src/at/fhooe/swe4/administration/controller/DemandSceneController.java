package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.LoginScene;
import at.fhooe.swe4.administration.views.ManageDemandDialog;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.Donation;
import at.fhooe.swe4.model.dbMock;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.function.Predicate;

public class DemandSceneController {
  private DemandScene view;

  private FilteredList<DemandItem> filteredDemand = new FilteredList<>(dbMock.getInstance().getDemandItems());
  private FilteredList<Donation> filteredDonations = new FilteredList<>(dbMock.getInstance().getDonations());

  private LocalDate dateFilter;
  private String textFilter;

  public DemandSceneController(DemandScene view) {
    this.view = view;
    setView();
  }

  public void setView() {
    view.getDemandTable().setItems(filteredDemand);
    view.getDonationsTable().setItems(filteredDonations);

    filteredDemand.setPredicate(
            demandItem -> demandItem.getRelatedOffice().equals(dbMock.getInstance().getActiveOffice())
    );

    filteredDonations.setPredicate(
            donationItem -> donationItem.getRelatedDemand().getRelatedOffice().equals(dbMock.getInstance().getActiveOffice())
    );

    view.getAddDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddDemandEvent(e));
    view.getDeleteDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteDemandEvent(e));
    view.getEditDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditDemandEvent(e));
    view.getChangeActiveOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleChangeActiveOfficeEvent(e));
    view.getDeleteFiltersBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteFiltersEvent(e));

    view.getTextSearchField().textProperty().addListener ((observable, oldValue, newValue)-> {
      textFilter = newValue.toLowerCase();
      filterDonations();
    });

    view.getDatePicker().addEventHandler(ActionEvent.ACTION, (e) -> handlePickDateEvent(e));
  }

  private void filterDonations() {
    Predicate<Donation> stringPredicate = donationItem -> textFilter.equals("") || donationItem.toString().toLowerCase().contains(textFilter);
    Predicate<Donation> datePredicate = donationItem -> dateFilter == null || donationItem.getDate().equals(dateFilter);
    Predicate<Donation> officePredicate = donationItem -> donationItem.getRelatedDemand().getRelatedOffice().equals(dbMock.getInstance().getActiveOffice());

    filteredDonations.setPredicate(datePredicate.and(stringPredicate).and(officePredicate));
  }

  private void handlePickDateEvent(ActionEvent e) {
      dateFilter = view.getDatePicker().getValue();
      filterDonations();
  }

  private void handleDeleteFiltersEvent(ActionEvent e) {
      view.getTextSearchField().clear();
      view.getDatePicker().setValue(null);
      filteredDonations.setPredicate(null);
      filteredDonations.setPredicate(
              donationItem -> donationItem.getRelatedDemand().getRelatedOffice().equals(dbMock.getInstance().getActiveOffice())
      );
  }

  private void handleChangeActiveOfficeEvent(ActionEvent e) {
      double x = view.getWindow().getWidth();
      double y = view.getWindow().getHeight();
      view.getWindow().setWidth(x);
      view.getWindow().setHeight(y);

      LoginScene loginScene = new LoginScene(view.getWindow());
      view.getWindow().setScene(loginScene.getLoginScene());
  }

  private void handleDeleteDemandEvent(ActionEvent e) {
    DemandItem demandI = view.getDemandTable().getSelectionModel().getSelectedItem();
    if (demandI != null) {
      dbMock.getInstance().deleteDemand(demandI);
    }
  }

  private void handleAddDemandEvent(ActionEvent e) {
    ManageDemandDialog dialog = new ManageDemandDialog(view.getWindow(), view.getDemandTable());
    dialog.showAddDialog();
  }

  private void handleEditDemandEvent(ActionEvent e) {
    DemandItem demandI = view.getDemandTable().getSelectionModel().getSelectedItem();
    if (demandI != null) {
      ManageDemandDialog dialog = new ManageDemandDialog(view.getWindow(), view.getDemandTable());
      dialog.showEditDialog(demandI);
    }
  }
}
