package fluxmachines.core.datagen.recipes;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.item.plastic.PlasticItem;
import fluxmachines.core.recipes.builder.FabricationRecipeBuilder;
import fluxmachines.core.registries.CoreItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static fluxmachines.core.registries.CoreItems.*;

public class MachineRecipes extends CoreRecipeProvider {

    public MachineRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return FluxMachines.NAME + " Machine Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {
        refabricateRecipes(consumer);
    }

    protected void refabricateRecipes (RecipeOutput consumer) {

        FabricationRecipeBuilder.build(consumer, FluxMachines.getResource("fabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, FluxMachines.getResource("fabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, FluxMachines.getResource("fabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, CoreItems.RAW_PLASTIC.get());
        FabricationRecipeBuilder.build(consumer, FluxMachines.getResource("fabrication/test"), RAW_PLASTIC.get(), Blocks.COBBLESTONE, RAW_PLASTIC.get());

        for (var rawPlastic : PlasticItem.getPlasticItems()) {
            //RefabricationRecipeBuilder.build(consumer, DimensionalCore.getResource("refabrication/" + rawPlastic.getRegistryName().getPath() + "_from_"));
        }
    }

}
