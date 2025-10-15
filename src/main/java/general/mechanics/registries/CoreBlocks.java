package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.block.base.DecorativeBlock;
import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.block.ice.Ice7Block;
import general.mechanics.api.block.ice.IceBlock;
import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.BaseBlockItem;
import general.mechanics.block.MatterFabricatorBlock;
import general.mechanics.tab.CoreTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GM.MODID);

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();

    //################################
    //||            Ores            ||
    //################################
    // Vanadium
    public static final BlockDefinition<DecorativeBlock> VANADIUM_BLOCK = reg("Vanadium Block", DecorativeBlock::new);
    //public static final BlockDefinition<DecorativeBlock> VANADIUM_BLOCK_RAW = reg("Block of Raw Vanadium", "raw_vanadium_block", DecorativeBlock::new);
    public static final BlockDefinition<OreBlock> VANADIUM_ORE = reg("Vanadium Ore", () -> new OreBlock(1, 3, OreBlock.Type.STONE.getProperties().explosionResistance(500).strength(25)));
    public static final BlockDefinition<OreBlock> DEEPSLATE_VANADIUM_ORE = reg("Deepslate Vanadium Ore", () -> new OreBlock(1, 3, OreBlock.Type.DEEPSLATE.getProperties().explosionResistance(500).strength(25)));

    // Plastic
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK = reg("Plastic Block", "plastic_block", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.WHITE));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_ORANGE = reg("Orange Plastic Block", "plastic_block_orange", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.ORANGE));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_MAGENTA = reg("Magenta Plastic Block", "plastic_block_magenta", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.MAGENTA));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_LIGHT_BLUE = reg("Light Blue Plastic Block", "plastic_block_light_blue", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.LIGHT_BLUE));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_YELLOW = reg("Yellow Plastic Block", "plastic_block_yellow", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.YELLOW));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_LIME = reg("Lime Plastic Block", "plastic_block_lime", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.LIME));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_PINK = reg("Pink Plastic Block", "plastic_block_pink", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.PINK));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_GRAY = reg("Gray Plastic Block", "plastic_block_gray", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.GRAY));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_LIGHT_GRAY = reg("Light Gray Plastic Block", "plastic_block_light_gray", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.LIGHT_GRAY));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_CYAN = reg("Cyan Plastic Block", "plastic_block_cyan", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.CYAN));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_PURPLE = reg("Purple Plastic Block", "plastic_block_purple", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.PURPLE));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_BLUE = reg("Blue Plastic Block", "plastic_block_blue", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.BLUE));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_BROWN = reg("Brown Plastic Block", "plastic_block_brown", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.BROWN));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_GREEN = reg("Green Plastic Block", "plastic_block_green", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.GREEN));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_RED = reg("Red Plastic Block", "plastic_block_red", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.RED));
    public static final BlockDefinition<PlasticBlock> PLASTIC_BLOCK_BLACK = reg("Black Plastic Block", "plastic_block_black", () -> new PlasticBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), DyeColor.BLACK));

    // Ice
    public static final BlockDefinition<IceBlock> ICE2 = reg("Ice II", "ice_2", IceBlock::new);
    public static final BlockDefinition<IceBlock> ICE3 = reg("Ice III", "ice_3", IceBlock::new);
    public static final BlockDefinition<IceBlock> ICE4 = reg("Ice IV", "ice_4", IceBlock::new);
    public static final BlockDefinition<IceBlock> ICE5 = reg("Ice V", "ice_5", IceBlock::new);
    public static final BlockDefinition<IceBlock> ICE6 = reg("Ice VI", "ice_6", IceBlock::new);
    public static final BlockDefinition<Ice7Block> ICE7 = reg("Ice VII", "ice_7", () -> new Ice7Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE)));

    // Crafting Machines
    public static final BlockDefinition<MatterFabricatorBlock> MATTER_FABRICATOR = reg("Matter Fabricator", MatterFabricatorBlock::new);

    // Machine block
    public static final BlockDefinition<DecorativeBlock> MACHINE_FRAME = reg("Machine Frame", () -> new DecorativeBlock(BlockBehaviour.Properties.ofFullCopy(PLASTIC_BLOCK.block())));

    public static List<BlockDefinition<?>> getBlocks () {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, final Supplier<T> supplier) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return reg(name, GM.getResource(resourceFriendly), supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, String resourceName, final Supplier<T> supplier) {
        return reg(name, GM.getResource(resourceName), supplier, null, true);
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
