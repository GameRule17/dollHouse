package dijkstra.dollhouse;

/**
 * Class for representing game Objects.
 */
public class GameObject extends GameEntity {
  private boolean pickupable;
  private boolean craftable;

  /**
   * Constructor.

   * @param name - sets the name of the object
   * @param actionsOutput - sets the outputs of every action type
   * @param pickupable - sets if this object is pickupable
   * @param craftable - sets if this object can be used to craft another object
   */
  public GameObject(final String name, final String[] actionsOutput,
                    final boolean pickupable, final boolean craftable) {
    super(name, actionsOutput);
    this.pickupable = pickupable;
    this.craftable = craftable;
  }
}
