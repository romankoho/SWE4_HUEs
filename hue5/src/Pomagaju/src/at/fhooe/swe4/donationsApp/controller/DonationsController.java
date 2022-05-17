package at.fhooe.swe4.donationsApp.controller;

import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.donationsApp.models.Donation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DonationsController {
  private static DonationsController obj;

  private static final ObservableList<Donation> donations =
          FXCollections.observableArrayList();

  public DonationsController() {
  }

  public static DonationsController getInstance() {
    if(obj == null) {
      obj = new DonationsController();
      //obj.initWithTestData();
    }
    return obj;
  }

  public ObservableList<Donation> getDonations() {return donations;}

  public void addDonation(Donation d) {
    donations.add(d);
  }

  public void deleteDonation(Donation d) {
    donations.remove(d);
    //TODO relevatnes DemandItem wieder anpassen (benötigte Menge erhöhen)
  }
}
