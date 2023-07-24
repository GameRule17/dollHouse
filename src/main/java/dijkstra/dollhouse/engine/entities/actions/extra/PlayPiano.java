package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.JsonLoader;
import dijkstra.dollhouse.engine.ParsedInput;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Script for the piano in the game.
 */
public class PlayPiano extends GameAction implements GameScript {

  private boolean isRunning;
  private Exception exception;

  /**
   * Public Constructor.
   *
   * @param output - the output of the action.
   */
  public PlayPiano(String output) {
    super(output);
    isRunning = false;
    exception = null;
  }

  private GameEntity getGate() {
    FileReader fileReader = null;
    GameEntity gate = null;
    try {
      fileReader = new FileReader("./res/objects/gate.json", Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject jsonGate = (JSONObject) parser.parse(fileReader);
      gate = JsonLoader.getGameEntity(jsonGate);
    } catch (IOException | ParseException | ClassNotFoundException | NoSuchMethodException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
      exception = e;
    }
    return gate;
  }

  @Override
  public String execute() {

    String output = "Opzione non disponibile!";
    String msg = "";
    GameAction action = GameHandler.getCurrentAction();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    GameEntity melody = inventory.findGameObject("melodia");
    String newMelody = "\n3) Melodia segreta \n";

    if (melody != null) {
      msg = action.getOutput() + newMelody;
    } else {
      msg = action.getOutput();
    }

    if (isRunning) {

      ParsedInput input = GameHandler.getParsedInput();
      try {
        int choice = Integer.valueOf(input.getAction());

        if (choice == 0) {
          isRunning = false;
          output = "Hai smesso di suonare il pianoforte!";
          GameHandler.getGame().getMap().runAllBehavioralNpcs();
        } else if (choice == 1 || choice == 2) {
          output = "Hai suonato la melodia " + choice + "\n";
          output += msg;
        } else if (choice == 3 && melody != null) {
          GameRoom room = GameHandler.getGame().getMap().getRoom("Corridoio");
          room.removeEntity(room.findEntity("Cancello"));
          room.addEntity(getGate());
          output = "Hai suonato la melodia 3! Senti un rumore meccanico"
                  + " provenire dal cancello, forse si è sbloccato..\n" + msg;
        }
      } catch (NumberFormatException e) {
        output = "Stai ancora suonando il pianoforte!";
      }
    } else {
      GameHandler.getGame().getMap().stopAllBehavioralNpcs();
      isRunning = true;
      output = msg;
    }
    return output;
  }

  @Override
  public boolean isOver() {
    return !isRunning;
  }

  @Override
  public Exception getException() {
    return exception;
  }
    
}
