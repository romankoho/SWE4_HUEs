package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.enums.Status;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OfficesController {
  private static OfficesController obj;

  private static final ObservableList<ReceivingOffice> offices =
          FXCollections.observableArrayList();

  private static ReceivingOffice activeOffice;

  private OfficesController() {}

  public static OfficesController getInstance() {
    if(obj == null) {
      obj = new OfficesController();
      obj.initWithTestData();
      activeOffice = null;
    }
    return obj;
  }

  public void setActiveOffice(ReceivingOffice o) {
    activeOffice = o;
  }

  public ReceivingOffice getActiveOffice() {
    return  activeOffice;
  }

  private void initWithTestData() {
    offices.add(new ReceivingOffice(512, "Hilfe für die Ukraine", FederalState.CARINTHIA,
            "Spittal/Drau", "Lindenstraße 1", Status.ACTIVE));

    offices.add(new ReceivingOffice(124, "Omas gegen Rechts", FederalState.VIEANNA,
            "Wien", "Porzellangasse", Status.ACTIVE));
  }

  public ObservableList<ReceivingOffice> getArrayList() {return offices;}

  public void addOffice(ReceivingOffice o) {offices.add(o);}
  public void deleteOffice(ReceivingOffice o) {offices.remove(o);}
  public void updateOffice(ReceivingOffice o, String name, FederalState federalState, String district,
                           String address, Status status) {
    o.setName(name);
    o.setFederalState(federalState);
    o.setDistrict(district);
    o.setAddress(address);
    o.setStatus(status);
  }
}
