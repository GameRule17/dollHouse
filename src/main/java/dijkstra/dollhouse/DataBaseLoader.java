package dijkstra.dollhouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 * Class for managing the game database.
 */
public final class DataBaseLoader {
  private static Connection conn = null;
  private static final String url = "jdbc:h2:./database/statistics;"
                                    + "DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";

  private DataBaseLoader() {
  }

  /**
   * Initialize the DB connection.
   */
  public static void initializeDbConnection() {
    try {
      conn = DriverManager.getConnection(url);
    } catch (SQLException ex) {
      System.out.println("Errore: " + ex.getMessage());
    }
  }

  /**
   * Creates the table in database.
   */
  public static void createTable() {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS statistics"
                        + "(id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "username VARCHAR(255), points INT, time TIME)");
      stmt.close();
    } catch (SQLException ex) {
      System.out.println("Errore: " + ex.getMessage());
    }
  }

  /**
   * Check if a user with that nickname already exists in the table, 
   * if so, update the statistics values ​​by verifying that the new values 
   * ​are better than the previous ones either for the number of points
   * or for a new shorter time.
   *
   * @param username - the player username.
   * @param points - the player points.
   * @param time - the palyer game time.
   */
  public static void updateStatistics(String username, int points, Time time) {
    try {
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM statistics WHERE username = ?");
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        if (rs.getInt("points") < points) {
          stmt = conn.prepareStatement("UPDATE statistics SET points = ? WHERE username = ?");
          stmt.setInt(1, points);
          stmt.setString(2, username);
          stmt.executeUpdate();
        }
        if (rs.getTime("time").getTime() > time.getTime()) {
          stmt = conn.prepareStatement("UPDATE statistics SET time = ? WHERE username = ?");
          stmt.setTime(1, time);
          stmt.setString(2, username);
          stmt.executeUpdate();
        }
      } else {
        stmt = conn.prepareStatement("INSERT INTO statistics (username, points, time) VALUES (?, ?, ?)");
        stmt.setString(1, username);
        stmt.setInt(2, points);
        stmt.setTime(3, time);
        stmt.executeUpdate();
      }
      stmt.close();
    } catch (SQLException ex) {
      System.out.println("Errore: " + ex.getMessage());
    }
  }

  /**
   * .
   *
   * @param mode .
   * @return .
   */
  public static String printUserData(String mode) {
    String result = "";
    ResultSet rs;
    try {
      Statement stmt = conn.createStatement();
      switch (mode) {
        // This must be converted with lambda expressions
        case "time":
          rs = stmt.executeQuery("SELECT * FROM statistics ORDER BY time");
          break;
        case "points":
          rs = stmt.executeQuery("SELECT * FROM statistics ORDER BY points DESC");
          break;
        default:
          rs = stmt.executeQuery("SELECT * FROM statistics");
          break;
      }
      while (rs.next()) {
        result += "<tr> <td>" + rs.getString("username")
                + "</td> <td>" + rs.getInt("points")
                + "</td><td>" + rs.getTime("time") + "</td> </tr>";
      }
      stmt.close();
    } catch (SQLException ex) {
      System.out.println("Errore: " + ex.getMessage());
    }
    return result;
  }
}