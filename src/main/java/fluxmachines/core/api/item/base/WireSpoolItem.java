package fluxmachines.core.api.item.base;

import fluxmachines.core.registries.CoreItems;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WireSpoolItem extends BaseItem {

    public static final int MAX_USES = 5;

    public WireSpoolItem(Properties properties) {
        super(properties.stacksTo(1).durability(MAX_USES));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if (itemStack.getDamageValue() >= MAX_USES - 1) {
            // Return basic spool when uses are exhausted
            return new ItemStack(CoreItems.WIRE_SPOOL.get());
        } else {
            // Damage the item by 1 use
            ItemStack result = itemStack.copy();
            result.setDamageValue(itemStack.getDamageValue() + 1);
            return result;
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) MAX_USES);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = Math.max(0.0F, (float) (MAX_USES - stack.getDamageValue()) / (float) MAX_USES);
        return java.awt.Color.HSBtoRGB(f / 3.0F, 1.0F, 1.0F);
    }

    /**
     * Checks if this wire spool is a copper wire spool
     */
    public boolean isCopperWireSpool(ItemStack stack) {
        return stack.getItem() == CoreItems.COPPER_WIRE_SPOOL.get();
    }

    /**
     * Checks if this wire spool is a redstone wire spool
     */
    public boolean isRedstoneWireSpool(ItemStack stack) {
        return stack.getItem() == CoreItems.REDSTONE_WIRE_SPOOL.get();
    }

    /**
     * Checks if this wire spool is a basic spool
     */
    public boolean isBasicSpool(ItemStack stack) {
        return stack.getItem() == CoreItems.WIRE_SPOOL.get();
    }

    /**
     * Gets the remaining uses of the wire spool
     */
    public int getRemainingUses(ItemStack stack) {
        return MAX_USES - stack.getDamageValue();
    }

    /**
     * Checks if the wire spool has any uses remaining
     */
    public boolean hasUsesRemaining(ItemStack stack) {
        return getRemainingUses(stack) > 0;
    }
}
