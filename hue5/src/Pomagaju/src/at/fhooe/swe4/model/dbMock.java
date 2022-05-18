package at.fhooe.swe4.model;

import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;

public class dbMock {
  private static dbMock obj;

  private static final ObservableList<DemandItem> demand =
          FXCollections.observableArrayList();

  private static final ObservableList<Donation> donations =
          FXCollections.observableArrayList();

  private static final ObservableList<Article> articles =
          FXCollections.observableArrayList();

  private static final ObservableList<ReceivingOffice> offices =
          FXCollections.observableArrayList();
  private static ReceivingOffice activeOffice;

  //key = email (String); value = user object
  private static HashMap<String, User> users = new HashMap<>();
  private static User currentUser;

  private dbMock() {};

  public static dbMock getInstance() {
    if(obj == null) {
      obj = new dbMock();
      obj.initWithTestData();
    }
    return obj;
  }

  private void initWithTestData(){

    //articles
    Article temp = new Article(1, "Babynahrung", "in Gläsern, alle Sorten", Condition.NEW, Category.FOOD);
    Article temp2 = new Article(2, "Damenhygieneartikel", "alles mögliche", Condition.NEW, Category.HYGIENE);
    Article temp3 = new Article(3, "Spielzeug", "für alle Altersstufen", Condition.NEW, Category.OTHER);
    Article temp4 = new Article(4, "Handys", "frei, müssen keine Smartphones sein", Condition.NEW, Category.ELECTRONICS);
    articles.addAll(temp, temp2, temp3, temp4);

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
    Donation d1 = new Donation(demand.get(0), LocalDate.of(2022,5,30), getUserByEMail("admin@admin.at"),1);
    Donation d2 = new Donation(demand.get(0), LocalDate.of(2022,6,10), getUserByEMail("john.doe@gmail.com"),2);
    Donation d3 = new Donation(demand.get(1), LocalDate.of(2022,5,27), getUserByEMail("jane.doe@gmail.com"),1);
    Donation d4 = new Donation(demand.get(1), LocalDate.of(2022,6,9), getUserByEMail("admin@admin.at"),3);
    Donation d5 = new Donation(demand.get(2), LocalDate.of(2022,6,7), getUserByEMail("john.doe@gmail.com"),4);
    Donation d6 = new Donation(demand.get(2), LocalDate.of(2022,6,10), getUserByEMail("jane.doe@gmail.com"),2);
    donations.addAll(d1,d2,d3,d4,d5,d6);
  }

  /*
  ------------------------Demand data------------------------
   */

  public ObservableList<DemandItem> getDemandItems() {return demand;}

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

  public void addDemand(DemandItem d) {demand.add(d);}
  public void deleteDemand(DemandItem d) {demand.remove(d);}
  public void updateArticle(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) {
    d.setRelatedArticle(article);
    d.setRelatedOffice(relatedOffice);
    d.setAmount(amount);
  }

  public void reduceDemand(DemandItem d, Integer amount) {
    d.setAmount(d.getAmount()-amount);
  }


  /*
  ------------------------Donation data------------------------
   */
  public ObservableList<Donation> getDonations() {return donations;}

  public void addDonation(Donation d) {
    donations.add(d);
  }


    /*
  ------------------------Article data------------------------
   */

  public ObservableList<Article> getArticles() {
    return articles;
  }

  public void addArticle(Article a) {
    articles.add(a);
  }

  public void deleteArticle(Article a) {
    articles.remove(a);
  }

  public void updateArticle(Article a, String name, String description, Condition condition, Category category) {
    a.setName(name);
    a.setDescription(description);
    a.setCondition(condition);
    a.setCategory(category);
  }

    /*
  ------------------------Offices data------------------------
  */
  public ObservableList<ReceivingOffice> getOffices() {return offices;}

  public void setActiveOffice(ReceivingOffice o) {
    activeOffice = o;
  }

  public ReceivingOffice getActiveOffice() {
    return  activeOffice;
  }

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

    /*
  ------------------------User data------------------------
  */
  public static HashMap<String, User> getUsers() {
    return users;
  }

  public void addUser(String key, User user) {
    users.put(key, user);
  }

  public User getUserByEMail(String email) {
    return users.get(email);
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

}
