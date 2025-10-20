package general.mechanics.api.item.tools;

import general.mechanics.GM;
import general.mechanics.registries.CoreElements;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class HammerItem extends ToolItem {

    public HammerItem(Properties properties) {
        super(properties, 143);
    }

    @Override
    public void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this, 1)
                .pattern("II ")
                .pattern("IIS")
                .pattern("II ")
                .define('I', CoreElements.STEEL_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_any", criterion)
                .save(consumer, GM.getResource("tools/hammer"));
    }
}
