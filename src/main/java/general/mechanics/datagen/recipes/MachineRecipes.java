package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.recipes.builder.FabricationRecipeBuilder;
import general.mechanics.registries.CoreItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreItems.*;

public class MachineRecipes extends CoreRecipeProvider {

    public MachineRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return GM.NAME + " Machine Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {
        refabricateRecipes(consumer);
    }

    protected void refabricateRecipes (RecipeOutput consumer) {

        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/test"), RAW_PLASTIC.get(), Blocks.COBBLESTONE, RAW_PLASTIC.get());

        for (var rawPlastic : PlasticItem.getPlasticItems()) {
            //RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/" + rawPlastic.getRegistryName().getPath() + "_from_"));
        }
    }

}
