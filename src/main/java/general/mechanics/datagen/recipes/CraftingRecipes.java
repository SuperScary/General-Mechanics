package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.api.item.plastic.RawPlasticItem;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreElements;
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
import static net.minecraft.world.item.Items.CRAFTER;
import static net.minecraft.world.item.Items.REDSTONE;

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
    }

    private void block(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MACHINE_FRAME, 1)
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
                .define('F', MACHINE_FRAME)
                .define('U', CRAFTER)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .unlockedBy("has_plastic", has(CoreTags.Items.PLASTIC_BLOCKS))
                .unlockedBy("has_crafter", has(CRAFTER))
                .unlockedBy("has_machine_frame", has(MACHINE_FRAME))
                .save(consumer, GM.getResource("crafting/matter_fabricator_from_plastic"));
    }

    protected void color (RecipeOutput consumer) {
        // TODO: Update recipe generation for new colored plastic system
        // These recipes need to be updated to work with ColoredPlasticItem and ColoredRawPlasticItem
        
        // Lazily creating all recipes for raw plastic item dyes.
        // for (var rawPlastic : CoreItems.getAllColoredRawPlastics()) {
        //     RawPlasticItem.getRecipeFrom(rawPlastic, consumer, has(CoreTags.Items.RAW_PLASTIC));
        // }

        // Lazily creating all recipes for plastic blocks.
        // for (int i = 0; i < PlasticBlock.getPlasticBlocks().size(); i++) {
        //     var plasticBlock = PlasticBlock.getPlasticBlocks().get(i);
        //     // Use the new colored plastic system
        //     var coloredPlastics = CoreItems.getAllColoredPlastics();
        //     if (i < coloredPlastics.size()) {
        //         PlasticBlock.getRecipeFrom(plasticBlock, coloredPlastics.get(i), consumer, has(plasticBlock));
        //     }
        // }
    }

    protected void tools (RecipeOutput consumer) {
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

    protected void misc (RecipeOutput consumer) {

        // Vanadium
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VANADIUM_BLOCK, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', CoreElements.VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .save(consumer, GM.getResource("crafting/vanadium_block_from_ingot"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CoreElements.VANADIUM_INGOT, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', CoreElements.VANADIUM_INGOT.get().getNuggetItem().asItem())
                .unlockedBy("has_vanadium_nugget", has(CoreElements.VANADIUM_INGOT.get().getNuggetItem().asItem()))
                .save(consumer, GM.getResource("crafting/vanadium_ingot_from_nugget"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CoreElements.VANADIUM_INGOT.get().getNuggetItem().asItem(), 9)
                .requires(CoreElements.VANADIUM_INGOT)
                .unlockedBy("has_vanadium_ingot", has(CoreElements.VANADIUM_INGOT))
                .save(consumer, GM.getResource("crafting/vanadium_nugget_from_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CoreElements.VANADIUM_INGOT, 9)
                .requires(VANADIUM_BLOCK)
                .unlockedBy("has_vanadium_block", has(VANADIUM_BLOCK))
                .save(consumer, GM.getResource("crafting/vanadium_ingot_from_block"));
    }
}
