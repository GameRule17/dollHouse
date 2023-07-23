package dijkstra.dollhouse.engine.entities.scripts.predefined;

import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Si auto carica .
 */
public class RandomPhrase implements GameScript, Serializable {
  private List<String> phrases;
  private Random random;
  
  public RandomPhrase() {
    phrases = new ArrayList<>();
    random = new Random();
  }

  public void addPhrase(final String phrase) {
    phrases.add(phrase);
  }
  
  public void removePhrase(final String phrase) {
    phrases.remove(phrase);
  }

  @Override
  public String execute() {
    return phrases.get(random.nextInt(0, phrases.size()));
  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return null;
  }
}
