package dimensional.core.api.inventory;

import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Interface for blocks that have an inventory
 */
public interface CoreInventory {

    /**
     * Get the inventory
     * @return the inventory
     */
    ItemStackHandler getInventory ();

    /**
     * Clear the contents of the inventory
     */
    void clearContents ();

}
