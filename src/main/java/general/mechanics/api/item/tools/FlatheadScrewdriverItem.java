package general.mechanics.api.item.tools;

import general.mechanics.GM;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.registries.CoreElements;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

public class FlatheadScrewdriverItem extends ToolItem {

    public FlatheadScrewdriverItem(Properties properties) {
        super(properties, 143);
    }

    @Override
    public void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this, 1)
                .pattern("HPI")
                .pattern("PRP")
                .pattern("RPF")
                .define('H', CoreTags.Items.HAMMERS)
                .define('P', CoreTags.Items.PLASTIC)
                .define('I', CoreElements.STAINLESS_STEEL_INGOT.get().getPlateItem())
                .define('R', CoreElements.STEEL_INGOT.get().getRodItem())
                .define('F', CoreTags.Items.FILES)
                .unlockedBy("has_any", criterion)
                .save(consumer, GM.getResource("tools/flathead_screwdriver"));
    }
}
