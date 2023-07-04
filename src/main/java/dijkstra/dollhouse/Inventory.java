package dijkstra.dollhouse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage the inventory of the player.
 */
public class Inventory {
    private List<Object> inventory;

    /**
     * Constructor.
     */
    public Inventory() {
        inventory = new ArrayList<>();
    }

    /**
     * Returns the inventory.
     * @return
     */
    public List<Object> getInventory() {
        return inventory;
    }

    /**
     * Adds an item to the inventory.
     * @param item
     */
    public void addItem(final Object item) {
        inventory.add(item);
    }

    /**
     * Removes an item from the inventory.
     * @param item
     */
    public void removeItem(final Object item) {
        inventory.remove(item);
    }

    /**
     * Checks if the inventory contains an item.
     */
    public boolean contains(final Object item) {
        return inventory.contains(item);
    }
}
