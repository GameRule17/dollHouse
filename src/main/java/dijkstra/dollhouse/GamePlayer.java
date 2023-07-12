package dijkstra.dollhouse;

import java.io.Serializable;

/**
 * Manages all about the player of one game run.
 */
public class GamePlayer implements Serializable {
    private String name;
    // statistiche di gioco

    public GamePlayer(final String name) {
        this.name = name;
    }
}
