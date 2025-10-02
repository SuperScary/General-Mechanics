package dimensional.core.registries;

import dimensional.core.DimensionalCore;
import dimensional.core.api.block.BlockDefinition;
import dimensional.core.api.block.base.BaseBlock;
import dimensional.core.api.block.base.DecorativeBlock;
import dimensional.core.api.block.base.OreBlock;
import dimensional.core.api.item.ItemDefinition;
import dimensional.core.api.item.base.BaseBlockItem;
import dimensional.core.tab.CoreTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Block definitions are blocks that are shared across all child mods.
 * If your block is specific to a child mod, it should be registered in that mod's block registry.
 */
public class CoreBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(DimensionalCore.MODID);

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();

    // ORES
    public static final BlockDefinition<DecorativeBlock> DRAKIUM_BLOCK = reg("Drakium Block", DecorativeBlock::new);
    public static final BlockDefinition<DecorativeBlock> DRAKIUM_BLOCK_RAW = reg("Block of Raw Drakium", "raw_drakium_block", DecorativeBlock::new);
    public static final BlockDefinition<OreBlock> DRAKIUM_ORE = reg("Drakium Ore", () -> new OreBlock(1, 3, OreBlock.Type.STONE));
    public static final BlockDefinition<OreBlock> DEEPSLATE_DRAKIUM_ORE = reg("Deepslate Drakium Ore", () -> new OreBlock(1, 3, OreBlock.Type.DEEPSLATE));
    public static final BlockDefinition<OreBlock> END_STONE_DRAKIUM_ORE = reg("End Stone Drakium Ore", () -> new OreBlock(1, 3, OreBlock.Type.END));
    public static final BlockDefinition<OreBlock> NETHER_DRAKIUM_ORE = reg("Nether Drakium Ore", () -> new OreBlock(1, 3, OreBlock.Type.NETHER));

    public static List<BlockDefinition<?>> getBlocks () {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, final Supplier<T> supplier) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return reg(name, DimensionalCore.getResource(resourceFriendly), supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, String resourceName, final Supplier<T> supplier) {
        return reg(name, DimensionalCore.getResource(resourceName), supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier, @Nullable BiFunction<Block, Item.Properties, BlockItem> itemFactory, boolean addToTab) {
        var deferredBlock = REGISTRY.register(id.getPath(), supplier);
        var deferredItem = CoreItems.REGISTRY.register(id.getPath(), () -> {
            var block = deferredBlock.get();
            var itemProperties = new Item.Properties();
            if (itemFactory != null) {
                var item = itemFactory.apply(block, itemProperties);
                if (item == null) {
                    throw new IllegalArgumentException("BlockItem factory for " + id + " returned null.");
                }
                return item;
            } else if (block instanceof BaseBlock) {
                return new BaseBlockItem(block, itemProperties);
            } else {
                return new BlockItem(block, itemProperties);
            }
        });
        var itemDef = new ItemDefinition<>(name, deferredItem);
        if (addToTab) CoreTab.add(itemDef);
        BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, itemDef);
        BLOCKS.add(definition);
        return definition;
    }

}
