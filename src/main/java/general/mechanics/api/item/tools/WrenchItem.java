package general.mechanics.api.item.tools;

import general.mechanics.GM;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.registries.CoreElements;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

public class WrenchItem extends ToolItem {

    public WrenchItem(Properties properties) {
        super(properties, 143);
    }

    @Override
    public void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this, 1)
                .pattern("PHP")
                .pattern(" R ")
                .pattern(" R ")
                .define('P', CoreElements.STEEL_INGOT.get().getPlateItem())
                .define('H', CoreTags.Items.HAMMERS)
                .define('R', CoreElements.STEEL_INGOT.get().getRodItem())
                .unlockedBy("has_any", criterion)
                .save(consumer, GM.getResource("tools/wrench"));
    }
}
