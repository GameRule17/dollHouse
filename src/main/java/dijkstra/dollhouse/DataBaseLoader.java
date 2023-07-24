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
   *
   * @throws SQLException .
   */
  public static void initializeDbConnection() throws SQLException {
    conn = DriverManager.getConnection(url);
  }

  public static void closeConnection() throws SQLException {
    conn.close();
  }

  /**
   * Creates the table in database.
   *
   * @throws SQLException .
   */
  public static void createTable() throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS statistics"
        + "(id INT PRIMARY KEY AUTO_INCREMENT,"
        + "username VARCHAR(255), points INT, time TIME)");
    stmt.close();
  }

  /**
   * Load statistics in the Database.
   *
   * @param username - the player username.
   * @param points   - the player points.
   * @param time     - the palyer game time.
   * @throws SQLException if a database access error occurs
   *                      or this method is called on a closed connection.
   */
  public static void updateStatistics(String username, int points, Time time) throws SQLException {
    // PreparedStatement stmt = conn.prepareStatement("SELECT * FROM statistics WHERE username = ?");
    // stmt.setString(1, username);
    // ResultSet rs = stmt.executeQuery();

    // if (rs.next()) {
    //   if (rs.getInt("points") < points) {
    //     stmt.close();
    //     stmt = conn.prepareStatement("UPDATE statistics SET points = ? WHERE username = ?");
    //     stmt.setInt(1, points);
    //     stmt.setString(2, username);
    //     stmt.executeUpdate();
    //     stmt.close();
    //   }
    //   if (rs.getTime("time").getTime() > time.getTime()) {
    //     stmt.close();
    //     stmt = conn.prepareStatement("UPDATE statistics SET time = ? WHERE username = ?");
    //     stmt.setTime(1, time);
    //     stmt.setString(2, username);
    //     stmt.executeUpdate();
    //     stmt.close();
    //   }
    // } else {
    //   stmt.close();
    //   stmt = conn.prepareStatement("INSERT INTO statistics"
    //                               + "(username, points, time) VALUES (?, ?, ?)");
    //   stmt.setString(1, username);
    //   stmt.setInt(2, points);
    //   stmt.setTime(3, time);
    //   stmt.executeUpdate();
    //   stmt.close();
    // }

    PreparedStatement stmt;
    stmt = conn.prepareStatement("INSERT INTO statistics"
                                + "(username, points, time) VALUES (?, ?, ?)");
    stmt.setString(1, username);
    stmt.setInt(2, points);
    stmt.setTime(3, time);
    stmt.executeUpdate();
    // rs.close();
  }

  /**
   * Returns the statistics of all players.
   *
   * @param mode - it determines the order of data.
   * @return String.
   * @throws SQLException if a database access error occurs
   *                      or this method is called on a closed connection.
   */
  public static String printUserData(String mode) throws SQLException {
    String result = "";
    ResultSet rs;
    Statement stmt = conn.createStatement();
    switch (mode) {
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
    rs.close();
    stmt.close();
    return result;
  }

  /**
   * Cheks if an username is alredy used.
   *
   * @param username - the username to check.
   * @return true if it is already used, flase otherwhise.
   * @throws SQLException if a database access error occurs
   *                      or this method is called on a closed connection.
   */
  public static boolean usernameAlreadyUsed(String username) throws SQLException {
    boolean result = false;
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM statistics WHERE username = ?");
    stmt.setString(1, username);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      result = true;
    }
    rs.close();
    stmt.close();
    return result;
  }
}