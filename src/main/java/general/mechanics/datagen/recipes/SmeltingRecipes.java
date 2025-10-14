package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreElements;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreBlocks.*;
import static general.mechanics.registries.CoreItems.*;

public class SmeltingRecipes extends CoreRecipeProvider {

    private static final int DEFAULT_SMELTING_TIME = 200;

    public SmeltingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return GM.NAME + " Smelting Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(CoreElements.VANADIUM_INGOT.get().getRawItem().asItem()), RecipeCategory.BREWING, CoreElements.VANADIUM_INGOT, 0.6f, DEFAULT_SMELTING_TIME)
                .unlockedBy("has_raw_vanadium", has(CoreElements.VANADIUM_INGOT.get().getRawItem().asItem()))
                .save(consumer, GM.getResource("smelting/vanadium_ingot_from_raw_vanadium_ore"));
    }

}
