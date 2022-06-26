package at.fhooe.swe4.database;

import at.fhooe.swe4.model.*;
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
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class dbConnector implements Database {

  ConnectionHandler conn = null;

  public dbConnector(ConnectionHandler conn) {
    this.conn = conn;
  }

  public DemandItem getRelatedDemandById(int demandId) {
    DemandItem foundDemand = null;

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM demanditems WHERE id =" + demandId)) {
      if(resultSet.next()) {
        Article relatedArticle = getArticleById(resultSet.getInt("relatedArticle"));
        ReceivingOffice relatedOffice = getOfficeById(resultSet.getInt("relatedOffice"));

        foundDemand = new DemandItem(
                resultSet.getInt("id"),
                relatedArticle,
                relatedOffice,
                resultSet.getInt("amount"));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return foundDemand;
  }

  @Override
  public List<DemandItem> getDemandItems() throws RemoteException {
    List<DemandItem> resultList = new ArrayList<>();

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM demanditems WHERE amount > 0")) {

      while(resultSet.next()) {
        Article relatedArticle = getArticleById(resultSet.getInt("relatedArticle"));
        ReceivingOffice relatedOffice = getOfficeById(resultSet.getInt("relatedOffice"));

        resultList.add(new DemandItem(
                resultSet.getInt("id"),
                relatedArticle,
                relatedOffice,
                resultSet.getInt("amount")));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return resultList;
  }

  @Override
  public void addDemand(DemandItem d) throws RemoteException, SQLException {
    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "INSERT INTO demanditems" +
                    "(relatedArticle, relatedOffice, amount)" +
                    "values(?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setInt(1, d.getRelatedArticle().getId());
      statement.setInt(2, d.getRelatedOffice().getId());
      statement.setInt(3, d.getAmount());
      statement.executeUpdate();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet != null && resultSet.next())
          d.setId(resultSet.getInt(1));
        else
          throw new DataAccessException("Auto generated keys not supported.");
      } catch (SQLException ex) {
        throw new DataAccessException("SQL Exception " + ex.getMessage());
      }
    }
    conn.disposeConnection();
  }


  @Override
  public void deleteDemand(DemandItem d) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM demanditems WHERE id = ?")) {
      statement.setInt(1, d.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }


  @Override
  public void updateDemand(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "UPDATE demanditems " +
                    "SET relatedArticle=?, relatedOffice=?, amount=? " +
                    "WHERE id = ?")) {
      statement.setInt(1, article.getId());
      statement.setInt(2, relatedOffice.getId());
      statement.setInt(3, amount);
      statement.setInt(4, d.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  @Override
  public void reduceDemand(DemandItem d, Integer amount) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "UPDATE demanditems " +
                    "SET amount=? " +
                    "WHERE id = ?")) {
      statement.setInt(1, d.getAmount()-amount);
      statement.setInt(2, d.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public void reduceDemandDEPRECATED(DemandItem d, Integer amount) throws RemoteException {
    int newAmount = d.getAmount() - amount;

    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "UPDATE demanditems " +
                    "SET amount = ?" +
                    "WHERE id = ?")) {
      statement.setInt(1, (newAmount));
      statement.setInt(2, d.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  @Override
  public List<Donation> getDonations() throws RemoteException {
    List<Donation> resultList = new ArrayList<>();

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM donations")) {

      while(resultSet.next()) {
        DemandItem relatedDemand = getRelatedDemandById(resultSet.getInt("relatedDemand"));
        User relatedUser = getUserById(resultSet.getInt("user"));

        resultList.add(new Donation(
                resultSet.getInt("id"),
                relatedDemand,
                LocalDate.parse(resultSet.getDate("date").toString()),
                relatedUser,
                resultSet.getInt("amount")));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return resultList;
  }

  @Override
  public void addDonation(Donation d) throws RemoteException, SQLException {
    int userId = getUserIdByEmail(d.getUser().getEmail());

    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "INSERT INTO donations" +
                    "(relatedDemand, date, user, amount)" +
                    "values(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setInt(1, d.getRelatedDemand().getId());
      statement.setDate(2,java.sql.Date.valueOf(d.getDate()));
      statement.setInt(3, userId);
      statement.setInt(4, d.getAmount());
      statement.executeUpdate();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet != null && resultSet.next())
          d.setId(resultSet.getInt(1));
        else
          throw new DataAccessException("Auto generated keys not supported.");
      } catch (SQLException ex) {
        throw new DataAccessException("SQL Exception " + ex.getMessage());
      }
    }
    conn.disposeConnection();
  }

  @Override
  public List<Article> getArticles() throws RemoteException {
    List<Article> resultList = new ArrayList<>();

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM articles")) {

      while(resultSet.next()) {
        resultList.add(new Article(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                Condition.fromText(resultSet.getString("condition")),
                Category.fromText(resultSet.getString("category"))));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return resultList;
  }


  public Article getArticleById(int articleId) {
    Article foundArticle = null;

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM articles WHERE id =" + articleId)) {
      if(resultSet.next()) {
        foundArticle = new Article(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                Condition.fromText(resultSet.getString("condition")),
                Category.fromText(resultSet.getString("category")));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return foundArticle;
  }

  @Override
  public void addArticle(Article a) throws RemoteException, SQLException {
    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "INSERT INTO articles" +
                    "(name, description, `condition`, category)" +
                    "values(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, a.getName());
      statement.setString(2, a.getDescription());
      statement.setString(3, a.getCondition().toString());
      statement.setString(4, a.getCategory().toString());

      statement.executeUpdate();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet != null && resultSet.next())
          a.setId(resultSet.getInt(1));
        else
          throw new DataAccessException("Auto generated keys not supported.");
      } catch (SQLException ex) {
        throw new DataAccessException("SQL Exception " + ex.getMessage());
      }
    }
    conn.disposeConnection();
  }

  @Override
  public void deleteArticle(Article a) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM articles WHERE id = ?")) {
      statement.setInt(1, a.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  @Override
  public void updateArticle(Article a, String name, String description, Condition condition, Category category) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "UPDATE articles " +
                    "SET name = ?, description = ?, `condition` = ?, category = ?" +
                    "WHERE id = ?")) {
      statement.setString(1, name);
      statement.setString(2, description);
      statement.setString(3, condition.toString());
      statement.setString(4, category.toString());
      statement.setInt(5, a.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public ReceivingOffice getOfficeById(int officeId) {
    ReceivingOffice foundOffice = null;

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM offices WHERE id =" + officeId)) {
        if(resultSet.next()) {
          foundOffice = new ReceivingOffice(
                  resultSet.getInt("id"),
                  resultSet.getString("name"),
                  FederalState.fromText(resultSet.getString("federalState")),
                  resultSet.getString("district"),
                  resultSet.getString("address"),
                  Status.fromText(resultSet.getString("status")));
        }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return foundOffice;
  }

  @Override
  public List<ReceivingOffice> getOffices() throws RemoteException {
    List<ReceivingOffice> resultList = new ArrayList<>();

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM offices")) {

      while(resultSet.next()) {
        resultList.add(new ReceivingOffice(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                FederalState.fromText(resultSet.getString("federalState")),
                resultSet.getString("district"),
                resultSet.getString("address"),
                Status.fromText(resultSet.getString("status"))));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return resultList;
  }

  @Override
  public void addOffice(ReceivingOffice o) throws RemoteException, SQLException {
    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "INSERT INTO offices" +
                    "(name, federalState, district, address, status)" +
                    "values(?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, o.getName());
      statement.setString(2, o.getFederalState().toString());
      statement.setString(3, o.getDistrict());
      statement.setString(4, o.getAddress());
      statement.setString(5, o.getStatus().toString());
      statement.executeUpdate();
      try (ResultSet resultSet = statement.getGeneratedKeys()) {
        if(resultSet != null && resultSet.next())
          o.setId(resultSet.getInt(1));
        else
          throw new DataAccessException("Auto generated keys not supported.");
      } catch (SQLException ex) {
        throw new DataAccessException("SQL Exception " + ex.getMessage());
      }
    }
    conn.disposeConnection();
  }

  @Override
  public void deleteOffice(ReceivingOffice o) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM offices WHERE id = ?")) {
      statement.setInt(1, o.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  @Override
  public void updateOffice(ReceivingOffice o, String name, FederalState federalState, String district, String address, Status status) throws RemoteException {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "UPDATE offices " +
                    "SET name = ?, federalState = ?, district = ?, address = ?, status=?" +
                    "WHERE id = ?")) {
      statement.setString(1, name);
      statement.setString(2, federalState.toString());
      statement.setString(3, district);
      statement.setString(4, address);
      statement.setString(5, status.toString());
      statement.setInt(6, o.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public User getUserById(int userId) {
    User foundUser = null;

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id =" + userId)) {
      if(resultSet.next()) {
        foundUser = new User(
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getString("email"));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return foundUser;
  }

  public int getUserIdByEmail(String email) {
    int userId = -1;

    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "SELECT * FROM users WHERE email = ?")) {
      statement.setString(1, email);
      try(ResultSet resultSet = statement.executeQuery()) {
        if(resultSet != null && resultSet.next()) {
          userId = resultSet.getInt("id");
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return userId;
  }


  @Override
  public HashMap<String, User> getUsers() throws RemoteException {
    HashMap<String, User> resultMap = new HashMap<>();

    try(Statement statement = conn.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {

      while(resultSet.next()) {
        resultMap.put(resultSet.getString("email"),
                new User(resultSet.getString("name"),
                         resultSet.getString("password"),
                         resultSet.getString("email")));
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return resultMap;
  }

  @Override
  public void addUser(String key, User user) throws RemoteException, SQLException {
    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "INSERT INTO users" +
                    "(email, name, password)" +
                    "values(?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, user.getEmail());
      statement.setString(2, user.getName());
      statement.setString(3, user.getPassword());
      statement.executeUpdate();
    }
    conn.disposeConnection();
  }

  @Override
  public User getUserByEMail(String email) throws RemoteException, SQLException {
    User foundUser = null;

    try(PreparedStatement statement = conn.getConnection().prepareStatement(
            "SELECT * FROM users WHERE email = ?")) {
      statement.setString(1, email);
      try(ResultSet resultSet = statement.executeQuery()) {
        if(resultSet != null && resultSet.next()) {
          foundUser = new User(
                  resultSet.getString("name"),
                  resultSet.getString("password"),
                  resultSet.getString("email"));
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQL Exception: " + ex.getMessage());
    }
    conn.disposeConnection();
    return foundUser;
  }

  public void deleteArticles() {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM articles")) {
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public void deleteDemandItems() {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM demanditems")) {
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public void deleteDonations() {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM donations")) {
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public void deleteOffices() {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM offices")) {
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }

  public void deleteUsers() {
    try (PreparedStatement statement = conn.getConnection().prepareStatement(
            "DELETE FROM users")) {
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close();
    conn.disposeConnection();
  }


  @Override
  public void clearDB() throws RemoteException {
    deleteArticles();
    deleteDemandItems();
    deleteDonations();
    deleteOffices();
    deleteUsers();
  }

  public synchronized static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
    ConnectionHandler connect = null;

    try{
      connect = new ConnectionHandler("jdbc:mariadb://localhost:3306/pomagajudb",
                                "pomagaju",
                                "pomagajuUser");

      Database db = new dbConnector(connect);

      Remote r = UnicastRemoteObject.exportObject(db, 0);
      LocateRegistry.createRegistry(1099);
      Naming.rebind("rmi://localhost:1099/Database", r);

      System.out.println("Database running, waiting for connections");
    } finally {
      connect.disposeConnection();
    }
  }
}
