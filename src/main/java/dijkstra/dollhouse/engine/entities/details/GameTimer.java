package dijkstra.dollhouse.engine.entities.details;

import java.io.Serializable;

/**
 * Class to manage the game time.
 */
public class GameTimer implements Serializable {
  private transient long initialTime;
  private long gameTime;
  private static final double NANOTIME_TO_SECONDS = 1e9;
  private static final double SECONDS_TO_MINUTES = 60;

  public GameTimer() {
    gameTime = 0;
  }

  /**
   * Starts the timer.
   */
  public void startTimer() {
    initialTime = System.nanoTime();
  }

  public long getElapsedTimeInSeconds() {

    return (long) (((System.nanoTime() - initialTime) / NANOTIME_TO_SECONDS)) + gameTime;
  }

  public void updateGameTime() {
    gameTime = getElapsedTimeInSeconds();
    initialTime = System.nanoTime();
  }

  /**
   * Returns the total time played by the player in a string format.
   *
   * @return String
   */
  public String getGameTime() {
    long seconds = getElapsedTimeInSeconds();
    long hour = (long) ((seconds / SECONDS_TO_MINUTES) / SECONDS_TO_MINUTES);
    long minutes = (long) ((seconds / SECONDS_TO_MINUTES) % SECONDS_TO_MINUTES);
    String hh;
    String mm;
    String ss;
    seconds %= SECONDS_TO_MINUTES;
    if (hour < 10) {
      hh = "0" + Long.toString(hour);
    } else {
      hh = Long.toString(hour);
    }
    if (minutes < 10) {
      mm = "0" + Long.toString(minutes);
    } else {
      mm = Long.toString(minutes);
    }
    if (seconds < 10) {
      ss = "0" + Long.toString(seconds);
    } else {
      ss = Long.toString(seconds);
    }
    return hh + ":" + mm + ":" + ss;
  }
}
