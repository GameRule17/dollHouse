package dijkstra.dollhouse.engine.entities.details;

import java.io.Serializable;

/**
 * An istance of GameDialogue represents a possible question followed by its answer.
 */
public class GameDialogue implements Serializable {
  private String question;
  private String answer;

  public GameDialogue(final String question, final String answer) {
    this.question = question;
    this.answer = answer;
  }

  public String getQuestion() {
    return question;
  }

  public String getAnswer() {
    return answer;
  }

  @Override
  public String toString() {
    return "Question: " + question + "\nAnswer: " + answer;
  }
}
