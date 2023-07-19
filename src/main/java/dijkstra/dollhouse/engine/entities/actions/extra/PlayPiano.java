package dijkstra.dollhouse.engine.entities.actions.extra;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.ParsedInput;
import dijkstra.dollhouse.engine.JSONLoader;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * .
 */
public class PlayPiano extends GameScriptedAction {

  private boolean isRunning;
  private Exception exception;

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
      gate = JSONLoader.getGameEntity(jsonGate);
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

    //System.out.println(setOutputMsg() + "\n");

    GameAction action = GameHandler.getCurrentAction();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    GameEntity melody = inventory.findGameObject("melodia");
    String newMelody = "\n3. Melodia segreta \n";
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
        } else if (choice == 1 || choice == 2){
          output = "Hai suonato la melodia " + choice + "\n";
          output += msg;
        } else if (choice == 3 && melody != null) {
          GameRoom room = GameHandler.getGame().getMap().getRoom("Corridoio");
          room.removeEntity(room.findEntity("Cancello"));
          room.addEntity(getGate());
          output = "Hai suonato la melodia 3!\n" + msg;
        }
      } catch (NumberFormatException e) {
        output = "Stai ancora suonando il pianoforte!";
      }
    } else {
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
