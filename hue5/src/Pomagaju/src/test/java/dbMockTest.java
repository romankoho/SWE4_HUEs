import at.fhooe.swe4.model.*;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;
import at.fhooe.swe4.database.Database;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class dbMockTest {

  Database db;
  Model model;

  Article article1 = new Article(1, "Babynahrung", "in Gläsern, alle Sorten", Condition.NEW, Category.FOOD);
  ReceivingOffice office1 = new ReceivingOffice(512, "Hilfe für die Ukraine", FederalState.CARINTHIA,
          "Spittal/Drau", "Lindenstraße 1", Status.ACTIVE);
  DemandItem demandItem1 = new DemandItem(1, article1, office1, 3);

  User user1 = new User("Roman", "unsafePW", "roman@gmail.com");
  Donation donation1 = new Donation(1,demandItem1, LocalDate.now(), user1,2);

  @BeforeEach
  void setUp() throws RemoteException, MalformedURLException, NotBoundException {
    db = (Database) Naming.lookup("rmi://localhost:1099/Database");
    db.clearDB();
    model = new Model(db);
  }

  @AfterEach
  void tearDown() {
    db = null;
    model = null;
  }

  @DisplayName("dbMock.addDemandAddsDemandItem")
  @Test
  void addDemandAddsDemandItem() throws RemoteException, SQLException {
    db.addDemand(demandItem1);
    assertThat(db.getDemandItems()).contains(demandItem1);
    assertEquals(1, db.getDemandItems().size());
  }

  @DisplayName("dbMock.addDemandWhenItemIsNullThrowsNullPointerException")
  @Test
  void addDemandWhenItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.addDemand(null);
    });
  }

  @DisplayName("dbMock.deleteDemandWhenDemandIsNullThrowsNullPointerException")
  @Test
  void deleteDemandWhenDemandIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.deleteDemand(null);
    });
  }

  @DisplayName("dbMock.deleteDemandWhenDemandIsNotFound")
  @Test
  void deleteDemandWhenDemandIsNotFoundDeletesDemand() throws RemoteException {
    db.deleteDemand(demandItem1);
    assertThat(db.getDemandItems()).doesNotContain(demandItem1);
    assertEquals(0, db.getDemandItems().size());
  }

  @DisplayName("dbMock.deleteDemandWhenDemandIsFoundDeletesDemandItem")
  @Test
  void deleteDemandWhenDemandIsFoundDeletesDemand() throws RemoteException, SQLException {
      db.addDemand(demandItem1);
      db.deleteDemand(demandItem1);
      assertThat(db.getDemandItems()).doesNotContain(demandItem1);
      assertEquals(0, db.getDemandItems().size());
  }

  @DisplayName("dbMock.updateDemandWhenInputIsValidUpdatesDemandItem")
  @Test
  void updateDemandWhenInputIsValidUpdatesDemandItem() throws RemoteException, SQLException {
    db.addDemand(demandItem1);
    assertEquals(3, db.getDemandItems().get(0).getAmount());
    db.updateDemand(demandItem1, article1, office1, 99);
    assertEquals(99, db.getDemandItems().get(0).getAmount());
  }

  @DisplayName("dbMock.updateDemandWhenInputIsInvalidThrowsNullPointerException")
  @Test
  void updateDemandWhenInputIsInvalidThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.updateDemand(null, null, null, 10);
    });
  }

  @DisplayName("dbMock.updateDemandWhenInputIsValidButDemandNotInDB")
  @Test
  void updateDemandWhenInputIsValidButDemandNotInDB() throws RemoteException, SQLException {
    db.addDemand(demandItem1);
    List<DemandItem> demandListPreUpdate = db.getDemandItems();

    db.updateDemand(new DemandItem(48, article1, office1, 70), article1, office1, 87);

    List<DemandItem> demandListPostUpdate = db.getDemandItems();

    assertTrue(demandListPreUpdate.size() == demandListPostUpdate.size() &&
            demandListPreUpdate.containsAll(demandListPostUpdate) &&
            demandListPostUpdate.containsAll(demandListPreUpdate));
  }

  @DisplayName("dbMock.reduceDemandWhenDemandItemIsNullThrowsNullPointerException")
  @Test
  void reduceDemandWhenDemandItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.reduceDemand(null, 10);
    });
  }

  @DisplayName("dbMock.reduceDemandWhenAmountLessZeroThrowsIllegalArgumentException")
  @Test
  void reduceDemandWhenAmountLessZeroThrowsIllegalArgumentException() throws RemoteException {
    assertThrows(IllegalArgumentException.class, () -> {
      db.reduceDemand(demandItem1, -5);
    });
  }

  @DisplayName("dbMock.reduceDemandWhenInputIsValid")
  @Test
  void reduceDemandWhenInputIsValid() throws RemoteException, SQLException {
    Integer amountPreReduction = demandItem1.getAmount();
    Integer reduction = 2;
    db.addDemand(demandItem1);

    db.reduceDemand(demandItem1, reduction);
    assertEquals(amountPreReduction-reduction,
                          db.getDemandItems().get(0).getAmount());
  }

  @DisplayName("dbMock.reduceDemandWhenInputIsValidButDemandNotInDB")
  @Test
  void reduceDemandWhenInputIsValidButDemandNotInDB() throws RemoteException, SQLException {
    db.addDemand(demandItem1);
    List<DemandItem> demandListPreUpdate = db.getDemandItems();

    db.reduceDemand(new DemandItem(48, article1, office1, 70), 87);

    List<DemandItem> demandListPostUpdate = db.getDemandItems();

    assertTrue(demandListPreUpdate.size() == demandListPostUpdate.size() &&
            demandListPreUpdate.containsAll(demandListPostUpdate) &&
            demandListPostUpdate.containsAll(demandListPreUpdate));
  }


  @DisplayName("dbMock.addDonationAddsDonation")
  @Test
  void addDonationAddsDonation() throws RemoteException, SQLException {
    db.addDonation(donation1);
    assertThat(db.getDonations()).contains(donation1);
    assertEquals(1, db.getDonations().size());
  }

  @DisplayName("dbMock.addDonationWhenItemIsNullThrowsNullPointerException")
  @Test
  void addDonationWhenItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.addDonation(null);
    });
  }


  @DisplayName("dbMock.addArticleAddsArticle")
  @Test
  void addArticleAddsArticle() throws RemoteException, SQLException {
    db.addArticle(article1);
    assertThat(db.getArticles()).contains(article1);
    assertEquals(1, db.getArticles().size());
  }

  @DisplayName("dbMock.addArticleWhenItemIsNullThrowsNullPointerException")
  @Test
  void addArticleWhenItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.addArticle(null);
    });
  }


  @DisplayName("dbMock.deleteArticleWhenArticleIsNullThrowsNullPointerException")
  @Test
  void deleteArticleWhenArticleIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.deleteArticle(null);
    });
  }

  @DisplayName("dbMock.deleteArticleWhenArticleIsNotFoundInDb")
  @Test
  void deleteArticleWhenArticleIsNotFoundInDB() throws RemoteException, SQLException {
    db.addArticle(new Article(4, "TestArtikel", "ein Test", Condition.NEW, Category.FOOD));
    Integer sizeBeforeDelete = db.getArticles().size();

    db.deleteArticle(article1);
    assertThat(db.getArticles()).doesNotContain(article1);
    assertEquals(sizeBeforeDelete, db.getArticles().size());
  }

  @DisplayName("dbMock.deleteArticleWhenDemandIsFoundDeletesArticle")
  @Test
  void deleteArticleWhenDemandIsFoundDeletesArticle() throws RemoteException, SQLException {
    db.addArticle(article1);
    db.deleteArticle(article1);
    assertThat(db.getArticles()).doesNotContain(article1);
    assertEquals(0, db.getArticles().size());
  }

  @DisplayName("dbMock.updateArticleWhenInputIsValidUpdatesArticle")
  @Test
  void updateArticleWhenInputIsValidUpdatesArticle() throws RemoteException, SQLException {
    db.addArticle(article1);
    assertEquals("Babynahrung", db.getArticles().get(0).getName());
    assertEquals("in Gläsern, alle Sorten", db.getArticles().get(0).getDescription());
    assertEquals(Condition.NEW, db.getArticles().get(0).getCondition());
    assertEquals(Category.FOOD, db.getArticles().get(0).getCategory());

    db.updateArticle(article1, "neuer Name", "neue Beschreibung",
            Condition.SLIGHTLYUSED, Category.OTHER);

    assertEquals("neuer Name", db.getArticles().get(0).getName());
    assertEquals("neue Beschreibung", db.getArticles().get(0).getDescription());
    assertEquals(Condition.SLIGHTLYUSED, db.getArticles().get(0).getCondition());
    assertEquals(Category.OTHER, db.getArticles().get(0).getCategory());
  }

  @DisplayName("dbMock.updateArticleWhenInputIsInValidThrowsNullPointerException")
  @Test
  void updateArticleWhenInputIsInvalidThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.updateArticle(null,"text", "text", null, null);
    });
  }

  @DisplayName("dbMock.updateArticleWhenInputIsValidButArticleNotInDB")
  @Test
  void updateArticleWhenInputIsValidButArticleNotInDB() throws RemoteException, SQLException {
    db.addArticle(article1);
    List<Article> articleListPreUpdate = db.getArticles();

    db.updateArticle(new Article(48, "test", "test", Condition.NEW, Category.ELECTRONICS),
            "neuer Name", "neue Desc", Condition.HEAVILYUSED, Category.FOOD);

    List<Article> articleListPostUpdate = db.getArticles();

    assertTrue(articleListPreUpdate.size() == articleListPostUpdate.size() &&
            articleListPreUpdate.containsAll(articleListPostUpdate) &&
            articleListPostUpdate.containsAll(articleListPreUpdate));
  }

  @DisplayName("dbMock.addOfficeAddsOffice")
  @Test
  void addOfficeAddsOffice() throws RemoteException, SQLException {
    db.addOffice(office1);
    assertThat(db.getOffices()).contains(office1);
    assertEquals(1, db.getOffices().size());
  }

  @DisplayName("dbMock.addOfficeWhenItemIsNullThrowsNullPointerException")
  @Test
  void addOfficeWhenItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.addOffice(null);
    });
  }

  @DisplayName("dbMock.deleteOfficeWhenOfficeIsNullThrowsNullPointerException")
  @Test
  void deleteOfficeWhenOfficeIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.deleteOffice(null);
    });
  }

  @DisplayName("dbMock.deleteOfficeWhenOfficeIsNotFoundInDb")
  @Test
  void deleteOfficeWhenOfficeIsNotFoundInDb() throws RemoteException, SQLException {
    db.addOffice(new ReceivingOffice(43, "Test", FederalState.SALZBURG, "Salzburg",
            "Testadresse", Status.ACTIVE));
    Integer sizeBeforeDelete = db.getOffices().size();

    db.deleteOffice(office1);
    assertThat(db.getOffices()).doesNotContain(office1);
    assertEquals(sizeBeforeDelete, db.getOffices().size());
  }

  @DisplayName("dbMock.deleteOfficeWhenOfficeIsFoundDeletesOffice")
  @Test
  void deleteOfficeWhenOfficeIsFoundDeletesOffice() throws RemoteException, SQLException {
    db.addOffice(office1);
    db.deleteOffice(office1);
    assertThat(db.getOffices()).doesNotContain(office1);
    assertEquals(0, db.getOffices().size());
  }

  @DisplayName("dbMock.updateOfficeWhenInputIsValidUpdatesOffice")
  @Test
  void updateOfficeWhenInputIsValidUpdatesOffice() throws RemoteException, SQLException {
    db.addOffice(office1);
    assertEquals("Hilfe für die Ukraine", db.getOffices().get(0).getName());
    assertEquals(FederalState.CARINTHIA, db.getOffices().get(0).getFederalState());
    assertEquals("Spittal/Drau", db.getOffices().get(0).getDistrict());
    assertEquals("Lindenstraße 1", db.getOffices().get(0).getAddress());
    assertEquals(Status.ACTIVE, db.getOffices().get(0).getStatus());

    db.updateOffice(office1, "neuer Name", FederalState.TYROL,
            "Lienz", "Südtiroler Platz 1", Status.INACTIVE);

    assertEquals("neuer Name", db.getOffices().get(0).getName());
    assertEquals(FederalState.TYROL, db.getOffices().get(0).getFederalState());
    assertEquals("Lienz", db.getOffices().get(0).getDistrict());
    assertEquals("Südtiroler Platz 1", db.getOffices().get(0).getAddress());
    assertEquals(Status.INACTIVE, db.getOffices().get(0).getStatus());
  }

  @DisplayName("dbMock.updateOfficeWhenInputIsInValidThrowsNullPointerException")
  @Test
  void updateOfficeWhenInputIsInValidThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.updateOffice(office1, "neuer Name", null,
              "Lienz", "Südtiroler Platz 1", null);
    });
  }

  @DisplayName("dbMock.updateOfficeWhenInputIsValidButOfficeNotInDB")
  @Test
  void updateOfficeWhenInputIsValidButOfficeNotInDB() throws RemoteException, SQLException {
    db.addOffice(office1);
    List<ReceivingOffice> officesListPreUpdate = db.getOffices();

    db.updateOffice(new ReceivingOffice(43, "Test", FederalState.SALZBURG, "Salzburg",
            "Testadresse", Status.ACTIVE), "neuer Name", FederalState.TYROL,
            "Lienz", "Südtiroler Platz 1", Status.INACTIVE);

    List<ReceivingOffice> officesListPostUpdate = db.getOffices();

    assertTrue(officesListPreUpdate.size() == officesListPostUpdate.size() &&
            officesListPreUpdate.containsAll(officesListPostUpdate) &&
            officesListPostUpdate.containsAll(officesListPreUpdate));
  }

  @DisplayName("dbMock.addUserAddsUser")
  @Test
  void addUserAddsUser() throws RemoteException, SQLException {
    db.addUser(user1.getEmail(), user1);
    assertThat(db.getUsers()).hasSize(1);
  }

  @DisplayName("dbMock.addUserWhenItemIsNullThrowsNullPointerException")
  @Test
  void addUserWhenItemIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(NullPointerException.class, () -> {
      db.addUser(user1.getEmail(), null);
    });
  }

  @DisplayName("dbMock.getUserByEMailWhenKeyIsEmptyThrowsIllegalArgumentException")
  @Test
  void getUserByEMailWhenUserIsNullThrowsNullPointerException() throws RemoteException {
    assertThrows(IllegalArgumentException.class, () -> {
      db.getUserByEMail("");
    });
  }

  @DisplayName("dbMock.getUserByEMailReturnsUser")
  @Test
  void getUserByEMailReturnsUser() throws RemoteException, SQLException {
    db.addUser(user1.getEmail(), user1);
    User u = db.getUserByEMail(user1.getEmail());

    assertEquals(user1.getEmail(), u.getEmail());
    assertEquals(user1.getName(), u.getName());
    assertEquals(user1.getPassword(), u.getPassword());
  }

}