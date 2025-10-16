package general.mechanics.api.item.tools;

import general.mechanics.api.item.base.BaseItem;
import general.mechanics.registries.CoreItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Consumer;

public class ToolItem extends BaseItem {

    private final int durability;

    public ToolItem (Properties properties, int durability) {
        super(properties.durability(durability).setNoRepair().stacksTo(1));
        this.durability = durability;
    }

    @Override
    public boolean isDamageable (@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if (itemStack.getDamageValue() >= durability - 1) {
            return ItemStack.EMPTY;
        } else {
            ItemStack result = itemStack.copy();
            result.setDamageValue(itemStack.getDamageValue() + 1);
            return result;
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    /*@Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) durability);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        // Always render a left-to-right gradient: red → yellow → green
        // Since the bar only supports a single color, we’ll fix it midway (yellow-green)
        // For full gradient, custom renderer required
        float progress = Math.max(0.0F, (float)(durability - stack.getDamageValue()) / (float) durability);
        // Hue for green-to-red is roughly 0.0 (red) to 0.33 (green)
        // We invert it so the left side (low progress) starts red, right side goes green
        return Color.HSBtoRGB((1.0F - progress) * 0.33F, 1.0F, 1.0F);
    }*/

    public int getRemainingUses(ItemStack stack) {
        return durability - stack.getDamageValue();
    }

}
