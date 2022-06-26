package at.fhooe.swe4.administration.controller;
//file DemandSceneController.java

import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.LoginScene;
import at.fhooe.swe4.administration.views.ManageDemandDialog;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.Donation;
import at.fhooe.swe4.model.Model;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.function.Predicate;

public class DemandSceneController {
  private DemandScene view;
  private Model model;

  private FilteredList<DemandItem> filteredDemand;
  private FilteredList<Donation> filteredDonations;

  private LocalDate dateFilter;
  private String textFilter;

  public DemandSceneController(DemandScene view, Model model) throws RemoteException {
    this.view = view;
    this.model = model;
    setView();
  }

  public void setView() throws RemoteException {
    filteredDemand = new FilteredList<>(model.getDemand());
    filteredDonations = new FilteredList<>(model.getDonations());

    view.getDemandTable().setItems(filteredDemand);
    view.getDonationsTable().setItems(filteredDonations);

    view.getActiveOffice().setText(model.getActiveOffice().getId().toString() + ": " +
           model.getActiveOffice().getName());

    filteredDemand.setPredicate(
            demandItem -> {
              try {
                return demandItem.getRelatedOffice().equals(model.getActiveOffice());
              } catch (RemoteException e) {
                throw new RuntimeException(e);
              }
            }
    );

    filteredDonations.setPredicate(
            donationItem -> {
              try {
                return donationItem.getRelatedDemand().getRelatedOffice().equals(model.getActiveOffice());
              } catch (RemoteException e) {
                throw new RuntimeException(e);
              }
            }
    );

    view.getAddDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
        try {
            handleAddDemandEvent(e);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    });
    view.getDeleteDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleDeleteDemandEvent(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    view.getEditDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
        try {
            handleEditDemandEvent(e);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    });
    view.getChangeActiveOfficeBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleChangeActiveOfficeEvent(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
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
    Predicate<Donation> officePredicate = donationItem -> {
      try {
        return donationItem.getRelatedDemand().getRelatedOffice().equals(model.getActiveOffice());
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    };

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
              donationItem -> {
                try {
                  return donationItem.getRelatedDemand().getRelatedOffice().equals(model.getActiveOffice());
                } catch (RemoteException ex) {
                  throw new RuntimeException(ex);
                }
              }
      );
  }

  private void handleChangeActiveOfficeEvent(ActionEvent e) throws RemoteException {
      double x = view.getWindow().getWidth();
      double y = view.getWindow().getHeight();
      view.getWindow().setWidth(x);
      view.getWindow().setHeight(y);

      LoginScene loginScene = new LoginScene(view.getWindow(), model);
      view.getWindow().setScene(loginScene.getLoginScene());
  }

  private void handleDeleteDemandEvent(ActionEvent e) throws RemoteException {
    DemandItem demandI = view.getDemandTable().getSelectionModel().getSelectedItem();
    if (demandI != null) {
      model.reduceDemand(demandI, demandI.getAmount());
    }
  }

  private void handleAddDemandEvent(ActionEvent e) throws RemoteException {
    ManageDemandDialog dialog = new ManageDemandDialog(view.getWindow(), view.getDemandTable(), model);
    dialog.showAddDialog();
  }

  private void handleEditDemandEvent(ActionEvent e) throws RemoteException {
    DemandItem demandI = view.getDemandTable().getSelectionModel().getSelectedItem();
    if (demandI != null) {
      ManageDemandDialog dialog = new ManageDemandDialog(view.getWindow(), view.getDemandTable(), model);
      dialog.showEditDialog(demandI);
    }
  }
}
