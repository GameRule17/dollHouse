package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.GUIHandler;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.JSONLoader;
import dijkstra.dollhouse.engine.ParsedInput;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.GameNpc;
import dijkstra.dollhouse.engine.entities.actions.predefined.TalkToNpc;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Script for Ciro .
 */
public class CiroScript extends TalkToNpc {

  private Exception exception;

  public CiroScript(String output) {
    super(output);
    exception = null;
  }

  private GameEntity getMelody() {
    FileReader fileReader = null;
    GameEntity melody = null;
    try {
      fileReader = new FileReader("./res/objects/melody.json", Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject jsonMelody = (JSONObject) parser.parse(fileReader);
      melody = JSONLoader.getGameEntity(jsonMelody);
    } catch (IOException | ParseException | ClassNotFoundException | NoSuchMethodException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
      exception = e;
    }
    return melody;
  }

  @Override
  public String execute() {
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    GameEntity bottle = inventory.findGameObject("bottiglia");
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
          if (choice == entity.getNumberOfDialogues() + 1) {
            if (bottle != null) {
              inventory.remove(bottle);
              GUIHandler.removeInventory(bottle.getName());
              // System.out.println(getMelody());
              inventory.add(getMelody());
              output = "Ciro> Brav uagliu!\nCiro ti ha donato una melodia";
            }
            String msg = "Eh ma guarda stuquia', proprio 'na"
                          + "domanda intelligente! Cosa pozzo mai fa' cu 'na melodia?"
                          + "Beh, ovviamente pozzo usala pe' evoca' 'n'esercito"
                          + "'e pizzaioli ca ballano 'e cuje pale e faceno 'a pizza"
                          + "perfetta 'a nu lampo. O pure pozzo fa' balla' e semafore"
                          + "pe' evitare 'o traffico a Napoli. Saje, cu 'na melodia pozzo"
                          + "persino fa' sciorre sfogliatelle 'e celo!";
            entity.getDialogue(1).setAnswer(msg);
          } else {
            output = entity.getDialogue(choice - 1).getAnswer();
          }
          output += "\n0) Smetti di parlare con " + entity.getName()
                  + "\n" + entity.getStringDialogues();
        }
      } catch (NumberFormatException | IndexOutOfBoundsException e) {
        output = "Stai ancora parlando con " + entity.getName() + "\n";
        output += "Premi 0) per smettere di parlare con " + entity.getName();
      }
    } else {
      GameHandler.getGame().getMap().stopAllBehavioralNpcs();
      isRunning = true;
      output = "0) Smetti di parlare con " + entity.getName() + "\n" + entity.getStringDialogues();
      if (bottle != null) {
        output += "\n" + (entity.getNumberOfDialogues() + 1) + ") Dai la bottiglia a Ciro";
      }
    }

    return output;
  }

  @Override
  public Exception getException() {
    return exception;
  }
    
}
