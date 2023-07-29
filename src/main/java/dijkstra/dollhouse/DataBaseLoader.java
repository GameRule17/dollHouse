package dijkstra.dollhouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

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
    PreparedStatement stmt;
    stmt = conn.prepareStatement("INSERT INTO statistics"
                                + "(username, points, time) VALUES (?, ?, ?)");
    stmt.setString(1, username);
    stmt.setInt(2, points);
    stmt.setTime(3, time);
    stmt.executeUpdate();
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
    StringBuilder result = new StringBuilder();
    ResultSet rs;
    Statement stmt = conn.createStatement();
    String username;
    String time;
    int points;
    rs = stmt.executeQuery("SELECT * FROM statistics");
    Collection<Statistic> statistics = new ArrayList<>();
    while (rs.next()) {
      username = rs.getString("username");
      points = rs.getInt("points");
      time = rs.getString("time");
      statistics.add(new Statistic(username, points, time));
    }
    switch (mode) {
      case "time":
        statistics.stream()
                  .sorted((s1, s2) -> {
                    Time t1 = Time.valueOf(s1.getTime());
                    Time t2 = Time.valueOf(s2.getTime());
                    return t1.compareTo(t2);
                  })
                  .map(s -> {
                    return "<tr> <td>" + s.getUsername()
                      + "</td> <td>" + s.getPoints()
                      + "</td><td>" + s.getTime() + "</td> </tr>";
                  })
                  .forEach(row -> result.append(row));
        break;
      case "points":
        statistics.stream()
                  .sorted((s1, s2) -> {
                    if (s1.getPoints() > s2.getPoints()) {
                      return -1;
                    }
                    if (s1.getPoints() < s2.getPoints()) {
                      return 1;
                    }
                    return 0;
                  })
                  .map(s -> {
                    return "<tr> <td>" + s.getUsername()
                      + "</td> <td>" + s.getPoints()
                      + "</td><td>" + s.getTime() + "</td> </tr>";
                  })
                  .forEach(row -> result.append(row));
        break;
      default:
        statistics.stream()
                  .map(s -> {
                    return "<tr> <td>" + s.getUsername()
                      + "</td> <td>" + s.getPoints()
                      + "</td><td>" + s.getTime() + "</td> </tr>";
                  })
                  .forEach(row -> result.append(row));
        break;
    }
    rs.close();
    stmt.close();
    return result.toString();
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