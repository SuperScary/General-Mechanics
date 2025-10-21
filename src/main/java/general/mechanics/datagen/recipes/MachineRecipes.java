package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.recipes.builder.CrushingRecipeBuilder;
import general.mechanics.recipes.builder.FabricationRecipeBuilder;
import general.mechanics.recipes.builder.FluidMixingRecipeBuilder;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreItems.POLYETHYLENE;

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
        crushingRecipes(consumer);
        fluidMixingRecipes(consumer);
    }

    protected void refabricateRecipes (RecipeOutput consumer) {
        // Use white raw plastic as the default for these recipes
        var whiteRawPlastic = POLYETHYLENE.get().getColoredVariant(DyeColor.WHITE);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/test"), whiteRawPlastic, Blocks.COBBLESTONE, whiteRawPlastic);
    }

    protected void crushingRecipes (RecipeOutput consumer) {
        for (var item : CoreElements.getElements()) {
            if (item.get() instanceof ElementItem element) {
                CrushingRecipeBuilder.build(consumer, GM.getResource("crushing/" + element.getDustItem().getRegistryName().getPath() + "_from_" + element.getRegistryName().getPath()), Ingredient.of(element), new ItemStack(element.getDustItem(), 2));
                CrushingRecipeBuilder.build(consumer, GM.getResource("crushing/" + element.getPileItem().getRegistryName().getPath() + "_from_" + element.getDustItem().getRegistryName().getPath()), Ingredient.of(element.getDustItem()), new ItemStack(element.getPileItem(), 6));
            }
        }
    }

    protected void fluidMixingRecipes (RecipeOutput consumer) {
        FluidMixingRecipeBuilder.build(consumer, GM.getResource("fluid_mixing/ammonia_from_hydrogen_and_nitrogen"), List.of(SizedFluidIngredient.of(CoreFluids.NITROGEN.getStack(500)), SizedFluidIngredient.of(CoreFluids.HYDROGEN.getStack(500))), SizedFluidIngredient.of(CoreFluids.AMMONIA.getStack()).getFluids()[0]);
    }

}
