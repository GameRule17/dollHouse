package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.GUIHandler;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GamePlayer;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;

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
      GameHandler.saveGame();
      DataBaseLoader.updateStatistics(player.getName(),
                                      player.getGameStatistics().getPoints(),
                                      java.sql.Time.valueOf(player.getGameStatistics().getTimer().getGameTime()));
      // Thread.sleep((long) 10e3);
      GUIHandler.popUpMessage("Complimenti! Hai finito Dollhouse!\n"
                              + "Per vedere le tue statistiche di gioco torna al menu principale"
                              + " e clicca sulla voce \"Statistiche\".");
      GUIHandler.returnMenu();
      GameHandler.resetGame();
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
