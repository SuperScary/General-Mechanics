package general.mechanics.api.item.tools;

import general.mechanics.api.item.base.BaseItem;
import general.mechanics.registries.CoreItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Consumer;

public abstract class ToolItem extends BaseItem {

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

    public int getRemainingUses(ItemStack stack) {
        return durability - stack.getDamageValue();
    }

    public abstract void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion);

}
