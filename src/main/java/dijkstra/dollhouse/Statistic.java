package dijkstra.dollhouse;

/**
 * Class for the database statstic.
 */
public class Statistic {
  private String username;
  private int points;
  private String time;

  /**
   * Public constructor.
   *
   * @param username - player username
   * @param points - player points
   * @param time - player game time
   */
  public Statistic(final String username, final int points, final String time) {
    this.username = username;
    this.time = time;
    this.points = points;
  }

  public String getUsername() {
    return username;
  }

  public int getPoints() {
    return points;
  }

  public String getTime() {
    return time;
  }
}