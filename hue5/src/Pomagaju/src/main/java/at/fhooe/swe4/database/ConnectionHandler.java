package at.fhooe.swe4.database;

import at.fhooe.swe4.database.DataAccessException;

import java.sql.*;

public class ConnectionHandler {

  private Connection connection;
  private String connectionString;
  private String username;
  private String password;

  public ConnectionHandler(String connectionString, String username, String password) {
    this.connectionString = connectionString;
    this.username = username;
    this.password = password;
  }

  private void openConnection() {
    try {
      connection = DriverManager.getConnection(connectionString, username, password);
      System.out.println("openConnection in DBConn called");
    } catch (SQLException ex) {
      throw new DataAccessException("Can't establish connection to database.");
    }
  }

  public Connection getConnection() {
    if (connection == null)
      openConnection();
    return connection;
  }

  public void disposeConnection() {
    try {
      if (connection != null)
        connection.close();
        System.out.println("connection closed");
        connection = null;
    } catch (SQLException ex) {
      throw new DataAccessException("Can't close database connection.");
    }
  }
}
