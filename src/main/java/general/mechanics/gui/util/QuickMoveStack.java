package general.mechanics.gui.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class QuickMoveStack {

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private final int slotCount;

    private AbstractContainerMenu menu;
    private Player player;
    private int index;

    public QuickMoveStack (AbstractContainerMenu menu, int slotCount, Player player, int index) {
        this.slotCount = slotCount;
        this.menu = menu;
        this.player = player;
        this.index = index;
    }

    public ItemStack quickMoveStack () {
        return quickMoveStack(player, index);
    }

    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = menu.slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            int inputSlotsEnd = TE_INVENTORY_FIRST_SLOT_INDEX + slotCount - 1;
            if (!menu.moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, inputSlotsEnd, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + slotCount) {
            if (!menu.moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + slotCount + 4) {
            if (!menu.moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

}
