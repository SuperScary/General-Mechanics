package fluxmachines.core.api.item.plastic;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.item.base.BaseItem;
import fluxmachines.core.api.tags.CoreTags;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class RawPlasticItem extends BaseItem {

    public static ArrayList<RawPlasticItem> RAW_PLASTIC_ITEMS = new ArrayList<>();

    private final DyeColor color;

    public RawPlasticItem(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
        RAW_PLASTIC_ITEMS.add(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static int getColorForItemStack(ItemStack stack, int index) {
        if (stack.getItem() instanceof RawPlasticItem item) {
            return item.getColor().getTextureDiffuseColor();
        }
        return -1;
    }

    public static void getRecipeFrom(RawPlasticItem item, RecipeOutput consumer, Criterion<InventoryChangeTrigger.TriggerInstance> criterion) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 1)
                .requires(CoreTags.Items.RAW_PLASTIC)
                .requires(item.getColor().getTag())
                .unlockedBy("has_plastic", criterion)
                .save(consumer, FluxMachines.getResource("crafting/plastic/" + item.getRegistryName().getPath() + "_from_" + item.getColor().getName().toLowerCase()));
    }

    public static ArrayList<RawPlasticItem> getPlasticItems() {
        return RAW_PLASTIC_ITEMS;
    }

}
