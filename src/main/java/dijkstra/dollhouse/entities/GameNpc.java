package dijkstra.dollhouse.entities;

import dijkstra.dollhouse.entities.details.GameDialogue;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents npcs in game.
 */
public class GameNpc extends GameEntity {

  private List<GameDialogue> dialogues;
  // quest

  public GameNpc(String name) {
    super(name);
    dialogues = new ArrayList<>();
  }

  public void addDialogue(final GameDialogue dialogue) {
    dialogues.add(dialogue);
  }

  public GameDialogue getDialogue(final int position) {
    return dialogues.get(position);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(super.toString());
    s.append("\nDialogues: \n");
    for (GameDialogue dialogue : dialogues) {
      s.append(dialogue.toString() + "\n");
    }
    return s.toString();
  }

  // get questions and answers
}
