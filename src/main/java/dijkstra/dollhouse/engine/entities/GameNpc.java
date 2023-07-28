package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.engine.entities.details.GameDialogue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents NPCs in game.
 * NPCs are all those entities which players can dialogue with.
 * All NPCs have a list of dialogues that a player can choose
 * in order to get informations about the game.
 */
public class GameNpc extends GameEntity {

  private List<GameDialogue> dialogues;

  /**
   * Public constructor for GameNpc.
   * Here the list of dialogues is initialized as an empty list.
   *
   * @param name - the name of this NPC.
   */
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

  public int getNumberOfDialogues() {
    return dialogues.size();
  }

  /**
   * Returns all dialogues to be printed.
   *
   * @return String.
   */
  public String getStringDialogues() {
    StringBuilder s = new StringBuilder();
    int i = 1;
    Iterator<GameDialogue> it = dialogues.iterator();
    s.append(i + ") " + it.next().getQuestion());
    while (it.hasNext()) {
      s.append("\n" + ++i);
      s.append(") " + it.next().getQuestion());
    }
    return s.toString();
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(super.toString());
    s.append("\nDialogues:");
    dialogues.stream().forEach(dialogue -> s.append("\n" + dialogue.toString()));
    return s.toString();
  }
}
