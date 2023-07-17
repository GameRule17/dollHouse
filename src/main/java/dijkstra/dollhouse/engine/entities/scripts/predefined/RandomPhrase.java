package dijkstra.dollhouse.engine.entities.scripts.predefined;

import java.util.ArrayList;
import java.util.List;

/**
 * Si auto carica .
 */
public class RandomPhrase {
  private List<String> phrases;
  
  public RandomPhrase(final String url) {
    phrases = new ArrayList<>();
    // carica le frasi
  }

  public void addPhrase(final String phrase) {
    phrases.add(phrase);
  }
  
  public void removePhrase(final String phrase) {
    phrases.remove(phrase);
  }
}
