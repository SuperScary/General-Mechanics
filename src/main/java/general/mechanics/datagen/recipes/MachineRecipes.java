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
import net.minecraft.world.item.DyeColor;
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

        // Use white raw plastic as the default for these recipes
        var whiteRawPlastic = POLYETHYLENE.get().getColoredVariant(DyeColor.WHITE);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/test"), whiteRawPlastic, Blocks.COBBLESTONE, whiteRawPlastic);
    }

}
