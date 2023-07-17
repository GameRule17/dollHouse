package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.ParsedInput;
import dijkstra.dollhouse.engine.entities.GameNpc;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;

/**
 * .
 */
public class TalkToNpc extends GameScriptedAction {

  private boolean isRunning;
  // private Exception exception;

  /**
   * Public Constructor for TalkToNpc.
   *
   * @param output .
   */
  public TalkToNpc(final String output) {
    super(output);
    isRunning = false;
    // exception = null;
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
          output = "Hai smesso di parlare con " + entity.getName();
        } else {
          output = entity.getDialogue(choice - 1).getAnswer();
        }
      } catch (NumberFormatException | IndexOutOfBoundsException e) {
        // exception = e;
      }
    } else {
      isRunning = true;
      output = entity.getStringDialogues() + "0) Smetti di parlare con " + entity.getName();
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
