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
      "Ma sei veramente convinto di poter uccidere la strega?",
      "Per ucciderla potrebbe essere necessario l'utilizzo della magia."
        + " Questo comporta degli effetti collaterali e in rari casi anche la morte."
        + "Sei disposto a sacrificare la tua vita per questa causa?"
    };
    answers = new String[][] {
      {"1) Cercherei di scappare facendo fuori chiunque mi intralci la strada!",
        "2) Cercherei di salvare le vittime della strega, compreso te!"},
      {"1) Io non sono come le altre persone che hanno fallito prima di me. Sono determinato"
          + " a sconfiggere la strega e liberare tutti quelli che ha trasformato in bambole."
          + "Se mi liberi adesso, ti prometto che non te ne pentirai. ",
        "2) Daro' il meglio di me per poter uccidere quella maledetta!"},
        {
          "1) Si, sacrificherei la mia vita pur di uccidere la strega e salvare le sue vittime.",
          "2) Faro' in modo che la strega muoia e lo faro' senza rischiare la pelle"
        }
    };
    correctAnswers = new String[] {
      "2", "1", "1"
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
            + "* Ora sei libero di muoverti ed interagire con le entita' della stanza. *\n"
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
        output = "Guardiano: Non mi hai convinto!\n* Hai smesso di parlare con il guardiano *";
      }
    } else {
      isRunning = true;
      output = "Guardiano: " + this.output + "\n" + questions[index];
      for (String answer : answers[index]) {
        output += "\n" + answer;
      }
      if (count + 1 > 1) {
        this.output = "Dopo " + (count + 1) + " tentativi sara' la volta buona?!";
      } else {
        this.output = "Dopo un tentativo sara' la volta buona?!";
      }
      
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
