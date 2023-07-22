package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GamePlayer;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import java.sql.Time;

/**
 * Script for the final battle of the game.
 */
public class FinalBattleScript extends GameAction implements GameScript {

  private Exception exception;

  public FinalBattleScript(String output) {
    super(output);
  }

  @Override
  public String execute() {
    String output = null;

    try {
      GamePlayer player = GameHandler.getGame().getPlayer();
      if (player.getGameInventory().findGameObject("coltello") != null) {
        output = this.output;
      } else {
        output = "Non conviene andare senza coltello!";
      }
      GameHandler.getGame().getPlayer().getGameStatistics().getTimer().updateGameTime();
      GameHandler.saveGame();
      DataBaseLoader.updateStatistics(player.getName(),
                                      player.getGameStatistics().getPoints(),
                                      Time.valueOf(player.getGameStatistics()
                                                  .getTimer().getGameTime()));
      GameHandler.endGame();
    } catch (Exception e) {
      exception = e;
    }
    return output;
  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return exception;
  }
}
