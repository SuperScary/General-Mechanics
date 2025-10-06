package dimensional.core.datagen.recipes;

import dimensional.core.DimensionalCore;
import dimensional.core.api.item.plastic.PlasticItem;
import dimensional.core.api.item.plastic.RawPlasticItem;
import dimensional.core.recipes.RefabricationRecipe;
import dimensional.core.recipes.builder.RefabricationRecipeBuilder;
import dimensional.core.registries.CoreItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static dimensional.core.registries.CoreBlocks.DRAKIUM_BLOCK;
import static dimensional.core.registries.CoreBlocks.DRAKIUM_BLOCK_RAW;
import static dimensional.core.registries.CoreItems.*;

public class MachineRecipes extends CoreRecipeProvider {

    public MachineRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return DimensionalCore.NAME + " Machine Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {
        refabricateRecipes(consumer);
    }

    protected void refabricateRecipes (RecipeOutput consumer) {

        RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, CoreItems.RAW_PLASTIC.get());
        RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, CoreItems.RAW_PLASTIC.get());
        RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, CoreItems.RAW_PLASTIC.get());
        RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/test"), RAW_PLASTIC.get(), Blocks.COBBLESTONE, RAW_PLASTIC.get());

        for (var rawPlastic : PlasticItem.getPlasticItems()) {
            //RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/" + rawPlastic.getRegistryName().getPath() + "_from_"));
        }
    }

}
