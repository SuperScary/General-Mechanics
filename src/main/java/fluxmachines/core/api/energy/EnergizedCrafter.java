package fluxmachines.core.api.energy;

import fluxmachines.core.api.entity.Crafter;
import fluxmachines.core.api.inventory.CoreItemStackHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * An interface for block entities that can be powered by Forge Energy and have the ability to craft items.
 */
public interface EnergizedCrafter<T extends Recipe<?>> extends Crafter<T> {

    /**
     * Has enough energy to iterate once in the crafting process.
     * @param required the amount of energy required to do a single tick process
     * @return true if the block entity has enough energy, false otherwise
     */
    boolean hasEnoughEnergy (int required);

    /**
     * Checks if the item can be inserted into the input slot.
     * @return true if the item can be inserted, false otherwise.
     */
    default boolean canInsertItem (ItemStackHandler inventory, Item item, int slot) {
        return inventory.getStackInSlot(slot).isEmpty() || inventory.getStackInSlot(slot).is(item);
    }

    /**
     * TODO: Logic is probably flawed and will lead to bugs.
     * Checks if the amount can be inserted into the input slot.
     * @return true if the amount can be inserted, false otherwise.
     */
    default boolean canInsertAmount (int count, int fromSlot, int toSlot, CoreItemStackHandler inventory) {
        if (inventory.getStackInSlot(toSlot) == ItemStack.EMPTY) return true;
        return inventory.getStackInSlot(toSlot).getCount() + count <= inventory.getStackInSlot(toSlot).getMaxStackSize();
    }

    /**
     * FE/t - The Forge Energy used per tick operation.
     * @return any amount
     */
    int getEnergyAmount ();

}
