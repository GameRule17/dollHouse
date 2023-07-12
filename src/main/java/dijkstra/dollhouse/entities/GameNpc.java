package dijkstra.dollhouse.entities;

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
    StringBuilder s = new StringBuilder("Name: " + name + "\n");
    s.append("Aliases: ");
    for (String alias : aliases) {
      s.append(alias + " ");
    }
    s.append("\nDialogues: \n");
    for (GameDialogue dialogue : dialogues) {
      s.append(dialogue.toString() + "\n");
    }
    return s.toString();
  }

  // get questions and answers
}
