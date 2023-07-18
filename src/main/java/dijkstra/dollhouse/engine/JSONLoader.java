package dijkstra.dollhouse.engine;

import dijkstra.dollhouse.engine.entities.GameBehavioralNpc;
import dijkstra.dollhouse.engine.entities.GameCraftableObject;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.GameNpc;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameDialogue;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.entities.scripts.predefined.RandomPhrase;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONLoader is an util class that is used to interpret data in a JSON file.
 */
public class JSONLoader {
  
  /**
   * This method returns a GameScriptedAction object of the class identified by
   * the parameter className with the output described by the parameter output.
   * If className is null returns a GameAction object with the output provided.
   *
   * @param className - the class name of the GameScriptedAction.
   * @param output - the output of the GameAction.
   * @return GameAction
   * @throws ClassNotFoundException .
   * @throws NoSuchMethodException .
   * @throws InstantiationException .
   * @throws IllegalAccessException .
   * @throws IllegalArgumentException .
   * @throws InvocationTargetException .
   */
  public static final GameAction getScriptedAction(final String className, final String output)
                                        throws ClassNotFoundException, NoSuchMethodException,
                                        InstantiationException, IllegalAccessException,
                                        IllegalArgumentException, InvocationTargetException {
    if (className == null) {
      return new GameAction(output);
    }
    Class<?> classScript = Class.forName(className);
    Constructor<?> constructor = classScript.getDeclaredConstructor(String.class);
    Object script = constructor.newInstance(output);
    if (script instanceof GameScriptedAction) {
      return (GameScriptedAction) script;
    }
    throw new ClassNotFoundException("The action is not an istance of GameAction!\n");

  }

  public static final GameAction getGameAction(JSONObject jsonObject)
                                  throws ClassNotFoundException, NoSuchMethodException,
                                  InstantiationException, IllegalAccessException,
                                  IllegalArgumentException, InvocationTargetException {
    JSONArray jsonAliases = (JSONArray) ((JSONObject) jsonObject).get("aliases");
    String output  = (String) ((JSONObject) jsonObject).get("output");
    GameAction action = getScriptedAction((String) ((JSONObject) jsonObject).get("script"), output);
    
    for (Object alias : jsonAliases) {
      action.addAlias(alias.toString());
    }

    return action;
  }

  public static final GameEntity getGameEntity(JSONObject jsonObject)
                                  throws ClassNotFoundException,
                                  NoSuchMethodException, InstantiationException,
                                  IllegalAccessException, IllegalArgumentException,
                                  InvocationTargetException {
    String name = (String) ((JSONObject) jsonObject).get("name");
    // System.out.println(name);
    GameEntity gameObject = new GameEntity(name);
    JSONArray jsonArray = (JSONArray) ((JSONObject) jsonObject).get("aliases");

    for (Object alias : jsonArray) {
      gameObject.addAlias(alias.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonObject).get("actions");
    for (Object action : jsonArray) {
      gameObject.addAction(getGameAction((JSONObject) action));
    }

    return gameObject;
  }

  public static GameNpc getGameNpc(final JSONObject jsonNpc)
                            throws ClassNotFoundException, NoSuchMethodException,
                            InstantiationException, IllegalAccessException,
                            IllegalArgumentException, InvocationTargetException {
    String name = (String) ((JSONObject) jsonNpc).get("name");
    GameNpc gameNpc = new GameNpc(name);
    JSONArray jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("dialogues");
    String question;
    String answer;

    for (Object dialogue : jsonArray) {
      question = ((JSONObject) dialogue).get("question").toString();
      answer = ((JSONObject) dialogue).get("answer").toString();
      gameNpc.addDialogue(new GameDialogue(question, answer));
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("aliases");

    for (Object alias : jsonArray) {
      gameNpc.addAlias(alias.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("actions");
    for (Object action : jsonArray) {
      gameNpc.addAction(getGameAction((JSONObject) action));
    }

    return gameNpc;
  }

  public static final GameScript getGameScript(final String className)
                                        throws ClassNotFoundException, NoSuchMethodException,
                                        InstantiationException, IllegalAccessException,
                                        IllegalArgumentException, InvocationTargetException {
    Class<?> classScript = Class.forName(className);
    Constructor<?> constructor = classScript.getDeclaredConstructor();
    Object script = constructor.newInstance();
    if (script instanceof GameScript) {
      return (GameScript) script;
    }
    throw new ClassNotFoundException("The script is not an istance of GameScript!\n");
  }

  public static GameBehavioralNpc getGameBehavioralNpc(final JSONObject jsonNpc)
                            throws ClassNotFoundException, NoSuchMethodException,
                            InstantiationException, IllegalAccessException,
                            IllegalArgumentException, InvocationTargetException {
    String name = (String) ((JSONObject) jsonNpc).get("name");
    GameBehavioralNpc gameNpc = new GameBehavioralNpc(name);
    JSONArray jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("dialogues");
    String question;
    String answer;
    String script = (String) ((JSONObject) jsonNpc).get("behavior");
    RandomPhrase npcScript;
    GameScript gameScript = getGameScript(script);

    gameNpc.setGameScript(gameScript);

    for (Object dialogue : jsonArray) {
      question = ((JSONObject) dialogue).get("question").toString();
      answer = ((JSONObject) dialogue).get("answer").toString();
      gameNpc.addDialogue(new GameDialogue(question, answer));
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("aliases");

    for (Object alias : jsonArray) {
      gameNpc.addAlias(alias.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("actions");
    for (Object action : jsonArray) {
      gameNpc.addAction(getGameAction((JSONObject) action));
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("phrases");
    if (jsonArray != null) {
      npcScript = (RandomPhrase) gameScript;
      for (Object phrase : jsonArray) {
        npcScript.addPhrase(phrase.toString());
      }
    }

    jsonArray = (JSONArray) ((JSONObject) jsonNpc).get("rooms");
    for (Object room : jsonArray) {
      gameNpc.addRoom(room.toString());
    }

    return gameNpc;
  }

  public static GameCraftableObject getGameCraftableObject(final JSONObject jsonObject)
                                              throws ClassNotFoundException, NoSuchMethodException,
                                              InstantiationException, IllegalAccessException,
                                              IllegalArgumentException, InvocationTargetException {
    String name = (String) ((JSONObject) jsonObject).get("name");
    GameCraftableObject craftableObject = new GameCraftableObject(name);
    JSONArray jsonArray = (JSONArray) ((JSONObject) jsonObject).get("recipe");

    for (Object object : jsonArray) {
      craftableObject.addToRecipe(object.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonObject).get("aliases");

    for (Object alias : jsonArray) {
      craftableObject.addAlias(alias.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonObject).get("actions");
    for (Object action : jsonArray) {
      craftableObject.addAction(getGameAction((JSONObject) action));
    }

    return craftableObject;
  }
}
