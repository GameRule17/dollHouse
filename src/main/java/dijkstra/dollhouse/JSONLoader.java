package dijkstra.dollhouse;

import dijkstra.dollhouse.entities.GameCraftableObject;
import dijkstra.dollhouse.entities.GameEntity;
import dijkstra.dollhouse.entities.GameNpc;
import dijkstra.dollhouse.entities.actions.GameAction;
import dijkstra.dollhouse.entities.details.GameDialogue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONLoader {
  public static final GameAction getScriptedAction(final String className,
                                        final String output)
                                        throws ClassNotFoundException, NoSuchMethodException,
                                        InstantiationException, IllegalAccessException,
                                        IllegalArgumentException, InvocationTargetException {
    if (className == null) {
      return new GameAction(output);
    }
    Class<?> classScript = Class.forName(className);
    Constructor<?> constructor = classScript.getDeclaredConstructor(String.class);
    Object script = constructor.newInstance(output);
    if (script instanceof GameAction) {
      return (GameAction) script;
    }
    throw new ClassNotFoundException("The script is not an istance of GameScript!\n");

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
