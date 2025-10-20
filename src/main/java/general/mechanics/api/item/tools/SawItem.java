package general.mechanics.api.item.tools;

import general.mechanics.GM;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.registries.CoreElements;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class SawItem extends ToolItem {

    public SawItem(Properties properties) {
        super(properties, 143);
    }

    @Override
    public void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this, 1)
                .pattern("PPS")
                .pattern("FHS")
                .define('P', CoreElements.STEEL_INGOT.get().getPlateItem())
                .define('S', Items.STICK)
                .define('F', CoreTags.Items.FILES)
                .define('H', CoreTags.Items.HAMMERS)
                .unlockedBy("has_any", criterion)
                .save(consumer, GM.getResource("tools/saw"));
    }
}
