package at.fhooe.swe4.model;
//File: dbMock.java

import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class dbMock implements Database {

  private final List<DemandItem> demand = new ArrayList<>();
  private final List<Donation> donations = new ArrayList<>();
  private final List<Article> articles = new ArrayList<>();
  private final List<ReceivingOffice> offices = new ArrayList<>();

  //key = email (String); value = user object
  private static HashMap<String, User> users = new HashMap<>();

  public dbMock() throws RemoteException {
    //articles
    Article temp = new Article(1, "Babynahrung", "in Gläsern, alle Sorten", Condition.NEW, Category.FOOD);
    Article temp2 = new Article(2, "Damenhygieneartikel", "alles mögliche", Condition.NEW, Category.HYGIENE);
    Article temp3 = new Article(3, "Spielzeug", "für alle Altersstufen", Condition.NEW, Category.OTHER);
    Article temp4 = new Article(4, "Handys", "frei, müssen keine Smartphones sein", Condition.NEW, Category.ELECTRONICS);
    articles.addAll(Arrays.asList(temp, temp2, temp3, temp4));

    //Offices
    offices.add(new ReceivingOffice(512, "Hilfe für die Ukraine", FederalState.CARINTHIA,
            "Spittal/Drau", "Lindenstraße 1", Status.ACTIVE));
    offices.add(new ReceivingOffice(124, "Omas gegen Rechts", FederalState.VIEANNA,
            "Wien", "Porzellangasse", Status.ACTIVE));
    offices.add(new ReceivingOffice(98, "Caritas Salzburg", FederalState.SALZBURG,
            "Gnigl", "Red Bull Gasse", Status.ACTIVE));

    //demand items
    demand.add(new DemandItem(1, articles.get(0), offices.get(0), 3));
    demand.add(new DemandItem(2, articles.get(1), offices.get(0), 12));
    demand.add(new DemandItem(3, articles.get(2), offices.get(1), 4));

    //users
    users.put("admin", new User("admin", "admin", "admin@admin.at"));
    users.put("john.doe@gmail.com", new User("Jon Doe", "1234", "john.doe@gmail.com"));
    users.put("jane.doe@gmail.com", new User("Jane Doe", "save", "jane.doe@gmail.com"));

    //donations
    Donation d1 = new Donation(1,demand.get(0), LocalDate.of(2022,5,30), getUserByEMail("admin"),1);
    Donation d2 = new Donation(2,demand.get(0), LocalDate.of(2022,6,10), getUserByEMail("john.doe@gmail.com"),2);
    Donation d3 = new Donation(3,demand.get(1), LocalDate.of(2022,5,27), getUserByEMail("jane.doe@gmail.com"),1);
    Donation d4 = new Donation(4,demand.get(1), LocalDate.of(2022,6,9), getUserByEMail("admin"),3);
    Donation d5 = new Donation(5,demand.get(2), LocalDate.of(2022,6,7), getUserByEMail("john.doe@gmail.com"),4);
    Donation d6 = new Donation(6,demand.get(2), LocalDate.of(2022,6,10), getUserByEMail("jane.doe@gmail.com"),2);
    donations.addAll(Arrays.asList(d1,d2,d3,d4,d5,d6));
  };

  /*
  ------------------------Demand data------------------------
   */

  public synchronized List<DemandItem> getDemandItems() throws RemoteException {return demand;}

  public synchronized void addDemand(DemandItem d) throws RemoteException, NullPointerException {
    if(d == null) {
      throw new NullPointerException();
    }
    demand.add(d);}

  public synchronized void deleteDemand(DemandItem d) throws RemoteException, NullPointerException {
    if(d == null) {
      throw new NullPointerException();
    }
    for(DemandItem dem : demand) {
      if (dem.getId().equals(d.getId())) {
        demand.remove(dem);
        break;
      }
    }
  }

  public synchronized void updateDemand(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) throws RemoteException, NullPointerException {
    if(d == null || article == null || relatedOffice == null) {
      throw new NullPointerException();
    }

    DemandItem demItem = null;
    for(DemandItem temp : demand) {
      if(d.getId().equals(temp.getId())) {
        demItem = temp;
      }
    }

    if(demItem != null) {
      demItem.setRelatedArticle(article);
      demItem.setRelatedOffice(relatedOffice);
      demItem.setAmount(amount);
    }

  }

  public synchronized void reduceDemand(DemandItem d, Integer amount)  throws RemoteException, NullPointerException, IllegalArgumentException{
    if(d == null) {
      throw new NullPointerException();
    }

    if(amount < 0) {
      throw new IllegalArgumentException("amount must be zero or positive");
    }

    for(DemandItem dem : demand) {
      if (dem.getId().equals(d.getId())) {
        dem.setAmount(d.getAmount()-amount);
        break;
      }
    }
  }


  /*
  ------------------------Donation data------------------------
   */
  public synchronized List<Donation> getDonations()  throws RemoteException{
    return donations;
  }

  public synchronized void addDonation(Donation d) throws RemoteException, NullPointerException {
    if(d == null) {
      throw new NullPointerException();
    }
    donations.add(d);
  }


    /*
  ------------------------Article data------------------------
   */

  public synchronized List<Article> getArticles() throws RemoteException {
    return articles;
  }

  public synchronized void addArticle(Article a) throws RemoteException, NullPointerException {
    if(a == null) {
      throw new NullPointerException();
    }
    articles.add(a);
  }

  public synchronized void deleteArticle(Article a) throws RemoteException, NullPointerException {
    if(a == null) {
      throw new NullPointerException();
    }
    articles.remove(a);
  }

  public synchronized void updateArticle(Article a, String name, String description, Condition condition, Category category)  throws RemoteException, NullPointerException, IllegalArgumentException{
    if(a == null || condition == null || category == null) {
      throw new NullPointerException();
    }

    if(name.length() < 1 || description.length() < 1) {
      throw new IllegalArgumentException("name and description must be longer than 0 characters");
    }

    Article art = null;
    for(Article temp : articles) {
      if(a.getId().equals(temp.getId())) {
        art = temp;
        break;
      }
    }

    if(art != null) {
      art.setName(name);
      art.setDescription(description);
      art.setCondition(condition);
      art.setCategory(category);
    }
  }

  /*
  ------------------------Offices data------------------------
  */
  public synchronized List<ReceivingOffice> getOffices()  throws RemoteException{return offices;}

  public synchronized void addOffice(ReceivingOffice o)  throws RemoteException, NullPointerException{
    if(o == null) {
      throw new NullPointerException();
    }
    offices.add(o);}

  public synchronized void deleteOffice(ReceivingOffice o)  throws RemoteException, NullPointerException{
    if(o == null) {
      throw new NullPointerException();
    }
    offices.remove(o);
  }

  public synchronized void updateOffice(ReceivingOffice o, String name, FederalState federalState, String district,
                           String address, Status status) throws RemoteException, NullPointerException, IllegalArgumentException {

    if(o == null || federalState == null || status == null) {
      throw new NullPointerException();
    }

    if(name.length() < 1 || district.length() < 1 || address.length() < 1) {
      throw new IllegalArgumentException("input strings must have more than 0 characters");
    }

    ReceivingOffice off = null;
    for(ReceivingOffice temp : offices) {
      if(o.getId().equals(temp.getId())) {
        off = temp;
        break;
      }
    }

    if(off != null) {
      off.setName(name);
      off.setFederalState(federalState);
      off.setDistrict(district);
      off.setAddress(address);
      off.setStatus(status);
    }
  }

    /*
  ------------------------User data------------------------
  */
  public synchronized HashMap<String, User> getUsers() throws RemoteException {
    return users;
  }

  public synchronized void addUser(String key, User user) throws RemoteException, NullPointerException, IllegalArgumentException {
    if(user == null) {
      throw new NullPointerException();
    }

    if(key.length() < 1) {
      throw new IllegalArgumentException("key must be longer than 0 characters");
    }

    users.put(key, user);
  }

  public synchronized User getUserByEMail(String email) throws RemoteException, IllegalArgumentException {
    if(email.length() < 1) {
      throw new IllegalArgumentException("email must be longer than 0 characters");
    }
    return users.get(email);
  }

  //needed for testing
  public synchronized void clearDB() throws RemoteException {
    demand.clear();
    donations.clear();
    articles.clear();
    offices.clear();
    users.clear();
  }

  public synchronized static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
    Database db = new dbMock();
    Remote r = UnicastRemoteObject.exportObject(db, 0);
    LocateRegistry.createRegistry(1099);
    Naming.rebind("rmi://localhost:1099/Database", r);

    System.out.println("Database running, waiting for connections");
  }


}
