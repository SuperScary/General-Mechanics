package general.mechanics.api.item.tools;

import general.mechanics.GM;
import general.mechanics.registries.CoreElements;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class FileItem extends ToolItem {

    public FileItem(Properties properties) {
        super(properties, 143);
    }

    @Override
    public void registerCraftingRecipe(RecipeOutput consumer, Criterion<?> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this, 1)
                .pattern("P")
                .pattern("P")
                .pattern("S")
                .define('P', CoreElements.STEEL_INGOT.get().getPlateItem())
                .define('S', Items.STICK)
                .unlockedBy("has_any", criterion)
                .save(consumer, GM.getResource("tools/file"));
    }
}
