package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreBlocks.*;
import static general.mechanics.registries.CoreItems.*;
import static net.minecraft.world.item.Items.*;

public class CraftingRecipes extends CoreRecipeProvider {

    public CraftingRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName() {
        return GM.NAME + " Crafting Recipes";
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        block(consumer);
        machine(consumer);
        color(consumer);
        tools(consumer);
        misc(consumer);

        for (var item : CoreElements.getElements()) {
            if (item.get() instanceof ElementItem element) {
                element.getRecipes(consumer, has(element::getDustItem));
            }
        }
    }

    private void block(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MACHINE_FRAME_0, 1)
                .pattern("IPI")
                .pattern("PRP")
                .pattern("IPI")
                .define('P', CoreTags.Items.PLASTIC)
                .define('I', CoreElements.VANADIUM_INGOT)
                .define('R', REDSTONE)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .unlockedBy("has_plastic", has(CoreTags.Items.PLASTIC))
                .unlockedBy("has_redstone", has(REDSTONE))
                .save(consumer, GM.getResource("crafting/machine_frame_from_plastic"));
    }

    protected void machine(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MATTER_FABRICATOR, 1)
                .pattern("PPP")
                .pattern("VUV")
                .pattern("VFV")
                .define('P', CoreTags.Items.PLASTIC_BLOCKS)
                .define('V', CoreElements.VANADIUM_INGOT)
                .define('F', MACHINE_FRAME_0)
                .define('U', CRAFTER)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .unlockedBy("has_plastic", has(CoreTags.Items.PLASTIC_BLOCKS))
                .unlockedBy("has_crafter", has(CRAFTER))
                .unlockedBy("has_machine_frame", has(MACHINE_FRAME_0))
                .save(consumer, GM.getResource("crafting/matter_fabricator_from_plastic"));
    }

    protected void color(RecipeOutput consumer) {
        // Lazily creating all recipes for raw plastic item dyes.
        /*for (var rawPlastic : CoreItems.getAllColoredRawPlastics()) {
            RawPlasticItem.getRecipeFrom(rawPlastic, consumer, has(CoreTags.Items.RAW_PLASTIC));
        }*/

        // Lazily creating all recipes for plastic blocks.
        var coloredPlasticBlocks = CoreBlocks.getAllColoredPlasticBlocks();
        var coloredPlastics = CoreItems.getAllColoredPlastics();
        for (int i = 0; i < coloredPlasticBlocks.size() && i < coloredPlastics.size(); i++) {
            var plasticBlock = coloredPlasticBlocks.get(i);
            var plastic = coloredPlastics.get(i);
            // TODO: Implement recipe generation for new plastic block system
            // This will need to be updated to work with the new ColoredPlasticBlock system
        }
    }

    protected void tools(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WRENCH, 1)
                .pattern(" V ")
                .pattern(" PV")
                .pattern("P  ")
                .define('P', POLYETHYLENE.get().getColoredVariant(DyeColor.BLACK))
                .define('V', CoreElements.VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .unlockedBy("has_plastic_black", has(POLYETHYLENE.get().getColoredVariant(DyeColor.BLACK)))
                .save(consumer, GM.getResource("crafting/wrench_from_plastic"));
    }

    protected void misc(RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CoreElements.STAINLESS_STEEL_INGOT.get().getDustItem(), 1)
                .requires(CoreElements.IRON_INGOT.get().getDustItem())
                .requires(CoreElements.CHROMIUM_INGOT.get().getDustItem())
                .requires(CoreElements.NICKEL_INGOT.get().getDustItem())
                .unlockedBy("has_iron_dust", has(CoreElements.IRON_INGOT.get().getDustItem()))
                .unlockedBy("has_chromium_dust", has(CoreElements.CHROMIUM_INGOT.get().getDustItem()))
                .unlockedBy("has_nickel_dust", has(CoreElements.NICKEL_INGOT.get().getDustItem()))
                .save(consumer, GM.getResource("crafting/stainless_steel_dust_from_dusts"));
    }
}
