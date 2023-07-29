package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.ParsedInput;
import dijkstra.dollhouse.engine.entities.GameNpc;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;

/**
 * The script that allows the player to talk to a NPC.
 */
public class TalkToNpc extends GameAction implements GameScript {

  protected boolean isRunning;

  /**
   * Public Constructor for TalkToNpc.
   *
   * @param output - the output of the action.
   */
  public TalkToNpc(final String output) {
    super(output);
    isRunning = false;
  }

  @Override
  public String execute() {
    GameNpc entity = (GameNpc) GameHandler.getCurrentEntity();
    String output = "Opzione non disponibile!";
    if (isRunning) {
      ParsedInput input = GameHandler.getParsedInput();
      try {
        int choice = Integer.valueOf(input.getAction());
        if (choice == 0) {
          isRunning = false;
          output = this.output + "Hai smesso di parlare con " + entity.getName();
          GameHandler.getGame().getMap().runAllBehavioralNpcs();
        } else {
          output = entity.getDialogue(choice - 1).getAnswer();
          output += "\n0) Smetti di parlare con " + entity.getName()
                    + "\n" + entity.getStringDialogues();
        }
      } catch (NumberFormatException | IndexOutOfBoundsException e) {
        output = "Stai ancora parlando con " + entity.getName() + "\n";
        output += "Premi 0) per smettere di parlare con " + entity.getName();
      }
    } else {
      isRunning = true;
      output = "0) Smetti di parlare con " + entity.getName() + "\n" + entity.getStringDialogues();
      GameHandler.getGame().getMap().stopAllBehavioralNpcs();
    }

    return output;
  }

  @Override
  public boolean isOver() {
    return !isRunning;
  }

  @Override
  public Exception getException() {
    return null;
  }
}
