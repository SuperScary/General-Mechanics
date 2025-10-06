package dimensional.core.api.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CoreItemStackHandler extends ItemStackHandler {

    public static final CoreItemStackHandler EMPTY = new CoreItemStackHandler(0);

    public CoreItemStackHandler() {
        this(1);
    }

    public CoreItemStackHandler(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public CoreItemStackHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 128;
    }

    /**
     * Returns true if the stack can be inserted. Does not check if stack is over limit.
     * @param slot slot to be checked.
     * @param stack stack to be checked against.
     * @return true if you can insert.
     */
    public boolean canInsert (int slot, ItemStack stack) {
        return getStackInSlot(slot).getItem() == stack.getItem() || getStackInSlot(slot).isEmpty();
    }

}