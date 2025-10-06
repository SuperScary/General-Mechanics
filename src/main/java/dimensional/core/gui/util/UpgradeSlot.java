package dimensional.core.gui.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class UpgradeSlot extends SlotItemHandler {

    private final Block block;

    public UpgradeSlot (Block block, IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
        this.block = block;
    }

    @Override
    public boolean mayPlace (ItemStack stack) {
        /*if (!(stack.getItem() instanceof UpgradeBase upgrade)) return false;

        var upgrades = FMUpgrades.getCompatibleUpgrades(block);

        var optionalPair = upgrades.stream()
                .filter(pair -> pair.getFirst().asItem().equals(upgrade))
                .findFirst();

        if (optionalPair.isEmpty()) return false;

        int maxAllowed = optionalPair.get().getSecond();
        int currentCount = 0;

        for (int i = 0; i < getItemHandler().getSlots(); i++) {
            ItemStack existing = getItemHandler().getStackInSlot(i);
            if (existing.getItem().equals(upgrade)) {
                currentCount += existing.getCount();
            }
        }

        // Prevent inserting if the max is already reached
        return currentCount < maxAllowed;*/
        return false;
    }

    @Override
    public int getMaxStackSize () {
        /*if (!(getItem().getItem() instanceof UpgradeBase upgrade)) return super.getMaxStackSize();

        var upgrades = FMUpgrades.getCompatibleUpgrades(block);

        var optionalPair = upgrades.stream()
                .filter(pair -> pair.getFirst().asItem().equals(upgrade))
                .findFirst();

        if (optionalPair.isEmpty()) return super.getMaxStackSize();

        int maxAllowed = optionalPair.get().getSecond();
        int currentCount = 0;

        for (int i = 0; i < getItemHandler().getSlots(); i++) {
            ItemStack existing = getItemHandler().getStackInSlot(i);
            if (existing.getItem().equals(upgrade)) {
                currentCount += existing.getCount();
            }
        }

        // Don't allow more than the remaining amount
        int remaining = maxAllowed - currentCount;
        return Math.max(0, Math.min(super.getMaxStackSize(), remaining));*/
        return 0;
    }

    @Override
    public boolean isActive () {
        return true;
    }
}
