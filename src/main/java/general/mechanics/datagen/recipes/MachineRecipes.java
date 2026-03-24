package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.recipes.FabricationRecipe;
import general.mechanics.recipes.builder.CrushingRecipeBuilder;
import general.mechanics.recipes.builder.FabricationRecipeBuilder;
import general.mechanics.recipes.builder.FluidMixingRecipeBuilder;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreFluids;
import general.mechanics.registries.CoreItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreItems.POLYETHERETHERKETONE;
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
        dyedPlasticRecipes(consumer);
        crushingRecipes(consumer);
        fluidMixingRecipes(consumer);
    }

    protected void refabricateRecipes (RecipeOutput consumer) {
        // Use white raw plastic as the default for these recipes
        var whiteRawPlastic = POLYETHYLENE.get().getColoredVariant(DyeColor.WHITE);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_leaves"), ItemTags.LEAVES, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_saplings"), ItemTags.SAPLINGS, whiteRawPlastic);
        FabricationRecipeBuilder.build(consumer, GM.getResource("fabrication/raw_plastic_from_flowers"), ItemTags.FLOWERS, whiteRawPlastic);
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

    protected void dyedPlasticRecipes (RecipeOutput consumer) {
        Set<PlasticTypeItem> plasticTypes = new HashSet<>();
        for (var plastic : CoreItems.getAllColoredPlastics()) {
            plasticTypes.add(plastic.getParentPlastic());
        }

        for (var plasticType : plasticTypes) {
            for (var color : DyeColor.values()) {
                var coloredOut = plasticType.getColoredVariant(color);
                if (coloredOut == null) continue;

                String plasticId = plasticType.getPlasticType().name().toLowerCase();
                var id = GM.getResource("fabrication/dye_plastic/" + plasticId + "/" + color.getName() + "_" + plasticId + "_from_dye");

                FabricationRecipeBuilder.build(consumer, id, new ItemStack(coloredOut, 2),
                        new FabricationRecipe.CountedIngredient(Ingredient.of(plasticType), 1),
                        new FabricationRecipe.CountedIngredient(Ingredient.of(plasticType), 1),
                        new FabricationRecipe.CountedIngredient(Ingredient.of(dyeTag(color)), 4));
            }
        }
    }

    private static TagKey<Item> dyeTag(DyeColor color) {
        return switch (color) {
            case WHITE -> Tags.Items.DYES_WHITE;
            case ORANGE -> Tags.Items.DYES_ORANGE;
            case MAGENTA -> Tags.Items.DYES_MAGENTA;
            case LIGHT_BLUE -> Tags.Items.DYES_LIGHT_BLUE;
            case YELLOW -> Tags.Items.DYES_YELLOW;
            case LIME -> Tags.Items.DYES_LIME;
            case PINK -> Tags.Items.DYES_PINK;
            case GRAY -> Tags.Items.DYES_GRAY;
            case LIGHT_GRAY -> Tags.Items.DYES_LIGHT_GRAY;
            case CYAN -> Tags.Items.DYES_CYAN;
            case PURPLE -> Tags.Items.DYES_PURPLE;
            case BLUE -> Tags.Items.DYES_BLUE;
            case BROWN -> Tags.Items.DYES_BROWN;
            case GREEN -> Tags.Items.DYES_GREEN;
            case RED -> Tags.Items.DYES_RED;
            case BLACK -> Tags.Items.DYES_BLACK;
        };
    }

}
