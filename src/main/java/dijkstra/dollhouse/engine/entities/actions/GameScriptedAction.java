package dijkstra.dollhouse.engine.entities.actions;

import dijkstra.dollhouse.engine.entities.scripts.GameScript;

/**
 * All istances of GameScriptedAction represents all scripted actions in game.
 * A scripted action is an action that updates a Game istance status.
 */
public abstract class GameScriptedAction extends GameAction implements GameScript {

  public GameScriptedAction(String output) {
    super(output);
  }

  @Override
  public String toString() {
    return super.toString() + "\n\033[32mClass: " + this.getClass().getName() + "\033[0m";
  }

}
