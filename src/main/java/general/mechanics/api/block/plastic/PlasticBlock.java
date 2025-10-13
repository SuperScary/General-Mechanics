package general.mechanics.api.block.plastic;

import general.mechanics.GM;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.registries.CoreSounds;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class PlasticBlock extends BaseBlock {

    private static final ArrayList<PlasticBlock> PLASTIC_BLOCKS = new ArrayList<>();

    private final DyeColor color;

    public PlasticBlock(Properties properties, DyeColor color) {
        super(properties
                .sound(CoreSounds.PLASTIC_BLOCK));
        this.color = color;
        PLASTIC_BLOCKS.add(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static int getColorForBlock(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int tintIndex) {
        Block block = state.getBlock();
        if (block instanceof PlasticBlock item) {
            return item.getColor().getTextureDiffuseColor();
        }
        return -1;
    }

    public static int getColorForItemStack(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof PlasticBlock plasticBlock) {
            return plasticBlock.getColor().getTextureDiffuseColor();
        }

        return -1;
    }

    public static void getRecipeFrom(PlasticBlock item, PlasticItem creator, RecipeOutput consumer, Criterion<InventoryChangeTrigger.TriggerInstance> criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, item, 2)
                .pattern("SS")
                .pattern("SS")
                .define('S', creator)
                .unlockedBy("has_plastic", criterion)
                .save(consumer, GM.getResource("crafting/plastic/" + item.getRegistryName().getPath() + "_from_" + creator.getRegistryName().getPath()));
    }

    public static ArrayList<PlasticBlock> getPlasticBlocks() {
        return PLASTIC_BLOCKS;
    }

}
