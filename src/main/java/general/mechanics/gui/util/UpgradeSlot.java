package general.mechanics.gui.util;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.upgrade.UpgradeBase;
import general.mechanics.registries.CoreUpgrades;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSlot extends SlotItemHandler {

    private final Block block;
    private final BaseBlockEntity blockEntity;

    public UpgradeSlot (Block block, IItemHandler itemHandler, int index, int x, int y, BaseBlockEntity blockEntity) {
        super(itemHandler, index, x, y);
        this.block = block;
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean mayPlace (ItemStack stack) {
        if (!(stack.getItem() instanceof UpgradeBase upgrade)) return false;

        var upgrades = CoreUpgrades.getCompatibleUpgrades(block);

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
        return currentCount < maxAllowed;
    }

    @Override
    public int getMaxStackSize () {
        if (!(getItem().getItem() instanceof UpgradeBase upgrade)) return super.getMaxStackSize();

        var upgrades = CoreUpgrades.getCompatibleUpgrades(block);

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
        return Math.max(0, Math.min(super.getMaxStackSize(), remaining));
    }

    @Override
    public boolean isActive () {
        // Only show upgrade slots when the settings panel is open
        return blockEntity.isSettingsPanelOpen();
    }

    /**
     * Gets the tooltip for this upgrade slot, showing compatible upgrades
     */
    public List<Component> getTooltip() {
        if (this.hasItem()) return List.of();
        var tooltip = new ArrayList<Component>();
        var upgrades = getCompatibleUpgrades();
        
        tooltip.add(Component.translatable("gui.gm.upgrade_tooltip"));
        
        if (upgrades.isEmpty()) {
            tooltip.add(Component.translatable("gui.gm.upgrade_tooltip.none"));
        } else {
            for (var upgrade : upgrades) {
                tooltip.add(Component.translatable("gui.gm.upgrade_tooltip.item",
                    Component.translatable(upgrade.getFirst().asItem().getDescriptionId()), 
                    upgrade.getSecond()));
            }
        }
        
        return tooltip;
    }

    /**
     * Gets the compatible upgrades for this block
     */
    private ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> getCompatibleUpgrades() {
        return CoreUpgrades.getCompatibleUpgrades(block);
    }

}
