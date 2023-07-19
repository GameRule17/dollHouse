package dijkstra.dollhouse.engine.entities.details;

import java.io.Serializable;

/**
 * Class for managing statistics in game.
 */
public class GameStatistics implements Serializable {
  private static final int minNumberOfExecutedActions = 20;
  private GameTimer timer;
  private int numberOfExecutedActions;

  public GameStatistics() {
    numberOfExecutedActions = 1;
    timer = new GameTimer();
  }

  public GameTimer getTimer() {
    return timer;
  }

  public void incrementsNumberOfExecutedActions() {
    numberOfExecutedActions++;
  }

  public int getNumberOfExecutedActions() {
    return numberOfExecutedActions;
  }

  /**
   * Get points of this player.
   *
   * @return int.
   */
  public int getPoints() {
    int over = numberOfExecutedActions - minNumberOfExecutedActions;
    int points = 500;
    if (over > 0) {
      points -= (over * 5);
      if (points < 1) {
        points = 1;
      }
    }
    return points;
  }

  @Override
  public String toString() {
    return "Tempo: " + timer.getGameTime() + "\nPunti: " + getPoints();
  }
  
}
