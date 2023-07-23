package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.ParsedInput;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * .
 */
public class GuardianScript extends GameAction implements GameScript {

  protected boolean isRunning;
  private String[] questions;
  private String[][] answers;
  private String[] correctAnswers;
  private int index;
  private int count;

  /**
   * .
   */
  private void init() {
    questions = new String[] {
      "Viandante, cosa faresti nel caso in cui ti liberassi?",
      "Sei veramente convinto di poter uccidere la strega?"
    };
    answers = new String[][] {
      {"1) Uccidero' te e la strega che mi ha intrappolato!",
        "2) Cerchero' di salvare le vittime della strega e ti liberero'!"},
      {"1) Io non sono come le altre persone che hanno fallito prima di me. Sono determinato"
          + "a sconfiggere la strega e liberare tutti quelli che ha trasformato in bambole."
          + "Se mi liberi adesso, ti prometto che non te ne pentirai. ",
        "2) Daro' il meglio di me per poter uccidere quella maledetta!"}
    };
    correctAnswers = new String[] {
      "2", "1"
    };
  }

  /**
   * .
   *
   * @param output .
   */
  public GuardianScript(String output) {
    super(output);
    isRunning = false;
    init();
    index = 0;
    count = 0;
  }

  @Override
  public String execute() {
    int size = questions.length;
    String choice;
    ParsedInput input = GameHandler.getParsedInput();
    String output;

    if (isRunning) {
      choice = input.getAction();
      if (choice.compareToIgnoreCase(correctAnswers[index]) == 0) {
        index++;
        if (index >= size) {
          isRunning = false;
          output = "Guardiano: Va bene mi hai convinto. Cerca di non morire"
            + " e ricordati di liberarmi una volta uccisa la strega!\n"
            + "Guardiano: Torna a parlare con me quando vuoi!\n"
            + "* Ora sei libero di muoverti ed interagire con le entità della stanza. *\n"
            + "* Digita \"esamina\" per esaminare la stanza corrente. *";
          GameRoom room = GameHandler.getGame().getMap().getRoom("Scantinato");
          GameHandler.getGame().getMap().setCurrentRoom(room);
        } else {
          output = "Guardiano: " + questions[index];
          for (String answer : answers[index]) {
            output += "\n" + answer;
          }
        }
      } else {
        index = 0;
        isRunning = false;
        output = "Guardiano: Mi hai rotto il ca<<o\n";
      }
    } else {
      isRunning = true;
      output = "Guardiano: " + this.output + "\n" + questions[index];
      for (String answer : answers[index]) {
        output += "\n" + answer;
      }
      this.output = "Dopo " + (count + 1) + " tentativi sarà la volta buona?!";
      count++;
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
