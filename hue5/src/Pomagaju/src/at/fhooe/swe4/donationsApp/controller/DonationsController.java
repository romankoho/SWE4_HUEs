package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.donationsApp.models.Donation;
import at.fhooe.swe4.donationsApp.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.HashMap;

public class DonationsController {
  private static DonationsController obj;

  private static final ObservableList<Donation> donations =
          FXCollections.observableArrayList();

  private static FilteredList<Donation> filteredDonations = new FilteredList<>(donations);

  public DonationsController() {
  }

  public static DonationsController getInstance() {
    if(obj == null) {
      obj = new DonationsController();
      obj.initWithTestData();
    }
    return obj;
  }

  private void initWithTestData(){
    ObservableList<DemandItem> demandList = DemandController.getInstance().getDemandList();

    Donation d1 = new Donation(demandList.get(0), LocalDate.of(2022,5,30), UserController.getInstance().getUserByEMail("admin@admin.at"),1);
    Donation d2 = new Donation(demandList.get(0), LocalDate.of(2022,6,10), UserController.getInstance().getUserByEMail("john.doe@gmail.com"),2);
    Donation d3 = new Donation(demandList.get(1), LocalDate.of(2022,5,27), UserController.getInstance().getUserByEMail("jane.doe@gmail.com"),1);
    Donation d4 = new Donation(demandList.get(1), LocalDate.of(2022,6,9), UserController.getInstance().getUserByEMail("admin@admin.at"),3);
    Donation d5 = new Donation(demandList.get(2), LocalDate.of(2022,6,7), UserController.getInstance().getUserByEMail("john.doe@gmail.com"),4);
    Donation d6 = new Donation(demandList.get(2), LocalDate.of(2022,6,10), UserController.getInstance().getUserByEMail("jane.doe@gmail.com"),2);

    donations.addAll(d1,d2,d3,d4,d5,d6);
  }

  public ObservableList<Donation> getDonations() {return donations;}
  public FilteredList<Donation> getFilteredDonations() {return filteredDonations;}

  public void addDonation(Donation d) {
    donations.add(d);
  }

}
