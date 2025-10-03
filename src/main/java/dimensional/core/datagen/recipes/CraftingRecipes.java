package dimensional.core.datagen.recipes;

import dimensional.core.DimensionalCore;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static dimensional.core.registries.CoreBlocks.*;
import static dimensional.core.registries.CoreItems.*;

public class CraftingRecipes extends CoreRecipeProvider {

    public CraftingRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName() {
        return DimensionalCore.NAME + " Crafting Recipes";
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        block(consumer);
        misc(consumer);
        test(consumer);
    }

    private void block(RecipeOutput consumer) {
    }

    protected void test (RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.COBBLESTONE, 1)
                .requires(COPPER_WIRE_SPOOL)
                .unlockedBy("has_copper_wire_spool", has(COPPER_WIRE_SPOOL))
                .save(consumer, DimensionalCore.getResource("crafting/test_copper_wire_spool_to_cobblestone"));
    }

    protected void misc (RecipeOutput consumer) {

        // Drakium
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DRAKIUM_BLOCK, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', DRAKIUM_INGOT)
                .unlockedBy("has_drakium_ingot", has(DRAKIUM_INGOT))
                .save(consumer, DimensionalCore.getResource("crafting/drakium_block_from_ingot"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DRAKIUM_BLOCK_RAW, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', RAW_DRAKIUM_ORE)
                .unlockedBy("has_raw_drakium", has(RAW_DRAKIUM_ORE))
                .save(consumer, DimensionalCore.getResource("crafting/raw_drakium_block_from_raw_ore"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DRAKIUM_INGOT, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', DRAKIUM_NUGGET)
                .unlockedBy("has_drakium_nugget", has(DRAKIUM_NUGGET))
                .save(consumer, DimensionalCore.getResource("crafting/drakium_ingot_from_nugget"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DRAKIUM_NUGGET, 9)
                .requires(DRAKIUM_INGOT)
                .unlockedBy("has_drakium_ingot", has(DRAKIUM_INGOT))
                .save(consumer, DimensionalCore.getResource("crafting/drakium_nugget_from_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DRAKIUM_INGOT, 9)
                .requires(DRAKIUM_BLOCK)
                .unlockedBy("has_drakium_block", has(DRAKIUM_BLOCK))
                .save(consumer, DimensionalCore.getResource("crafting/drakium_ingot_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RAW_DRAKIUM_ORE, 9)
                .requires(DRAKIUM_BLOCK_RAW)
                .unlockedBy("has_drakium_block_raw", has(DRAKIUM_BLOCK_RAW))
                .save(consumer, DimensionalCore.getResource("crafting/raw_drakium_from_raw_drakium_block"));

        // Vanadium
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VANADIUM_BLOCK, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", has(VANADIUM_INGOT))
                .save(consumer, DimensionalCore.getResource("crafting/vanadium_block_from_ingot"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VANADIUM_INGOT, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', VANADIUM_NUGGET)
                .unlockedBy("has_vanadium_nugget", has(VANADIUM_NUGGET))
                .save(consumer, DimensionalCore.getResource("crafting/vanadium_ingot_from_nugget"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, VANADIUM_NUGGET, 9)
                .requires(VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", has(VANADIUM_INGOT))
                .save(consumer, DimensionalCore.getResource("crafting/vanadium_nugget_from_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, VANADIUM_INGOT, 9)
                .requires(VANADIUM_BLOCK)
                .unlockedBy("has_vanadium_block", has(VANADIUM_BLOCK))
                .save(consumer, DimensionalCore.getResource("crafting/vanadium_ingot_from_block"));
    }
}
