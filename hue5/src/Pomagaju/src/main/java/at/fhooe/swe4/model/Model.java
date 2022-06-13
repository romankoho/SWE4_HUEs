package at.fhooe.swe4.model;

import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.rmi.RemoteException;


public class Model {
  private Database db;
  private ObservableList<DemandItem> demand;
  private ObservableList<Donation> donations;
  private ObservableList<Article> articles;
  private ObservableList<ReceivingOffice> offices;

  private static ObjectProperty<User> currentUser;
  private static ObjectProperty<ReceivingOffice> activeOffice;

  public Model(Database db) throws RemoteException {
      this.db = db;
      currentUser = new SimpleObjectProperty<>();
      activeOffice = new SimpleObjectProperty<>();

      demand = FXCollections.observableArrayList();
      demand.addAll(db.getDemandItems());
      donations = FXCollections.observableArrayList();
      donations.addAll(db.getDonations());
      donations = FXCollections.observableArrayList();
      donations.addAll(db.getDonations());
      articles = FXCollections.observableArrayList();
      articles.addAll(db.getArticles());
      offices = FXCollections.observableArrayList();
      offices.addAll(db.getOffices());
  }

  public boolean demandIsLinkedToReceivingOffice(ReceivingOffice office) {
    for(int i = 0; i < demand.size(); i++) {
      DemandItem d = demand.get(i);
      ReceivingOffice currentOffice = d.getRelatedOffice();
      if(currentOffice.equals(office)) {
        return true;
      }
    }
    return false;
  }

  public ObservableList<DemandItem> getDemand() {
    return demand;
  }

  public void addDemand(DemandItem d) throws RemoteException {
    db.addDemand(d);
    updateModel();
  }

  public void deleteDemand(DemandItem d) throws RemoteException {
    db.deleteDemand(d);
    updateModel();
  }

  public void updateDemand(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) throws RemoteException {
    db.updateDemand(d, article, relatedOffice, amount);
    updateModel();
  }

  public void reduceDemand(DemandItem d, Integer amount) throws RemoteException {
    db.reduceDemand(d, amount);
    updateModel();
  }

  public ObservableList<Donation> getDonations() {
    return donations;
  }

  public void addDonation(Donation d) throws RemoteException {
    db.addDonation(d);
    updateModel();
  }

  public ObservableList<Article> getArticles() {
    return articles;
  }

  public void addArticle(Article a) throws RemoteException {
    db.addArticle(a);
    updateModel();
  }

  public void deleteArticle(Article a) throws RemoteException {
    db.deleteArticle(a);
    updateModel();
  }

  public void updateArticle(Article a, String name, String description, Condition condition, Category category) throws RemoteException {
    db.updateArticle(a, name, description, condition, category);
    updateModel();
  }


  public ObservableList<ReceivingOffice> getOffices() {
    return offices;
  }

  public void addOffice(ReceivingOffice o) throws RemoteException {
    db.addOffice(o);
    updateModel();
  }

  public void deleteOffice(ReceivingOffice o) throws RemoteException {
    db.deleteOffice(o);
    updateModel();
  }

  public void setActiveOffice(ReceivingOffice o) {
    activeOffice.set(o);
  }

  public ReceivingOffice getActiveOffice() throws RemoteException{
    return  activeOffice.get();
  }

  public void updateOffice(ReceivingOffice o, String name, FederalState federalState, String district,
                           String address, Status status) throws RemoteException {
    db.updateOffice(o, name, federalState, district, address, status);
    updateModel();
  }

  public void addUser(String key, User user) throws RemoteException {
    db.addUser(key, user);
    updateModel();
  }

  public User getCurrentUser() {
    return currentUser.get();
  }

  public void setCurrentUser(User user) {
    currentUser.set(user);
  }

  public User getUserByEMail(String email) throws RemoteException {
    return db.getUserByEMail(email);
  }

  public void updateModel() throws RemoteException {
    demand.clear();
    demand.addAll(db.getDemandItems());
    articles.clear();
    articles.addAll(db.getArticles());
    offices.clear();
    offices.addAll(db.getOffices());
    donations.clear();
    donations.addAll(db.getDonations());
  }

}
