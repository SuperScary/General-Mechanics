package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.block.base.DecorativeBlock;
import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.block.ice.Ice7Block;
import general.mechanics.api.block.ice.IceBlock;
import general.mechanics.api.block.machine.MachineFrameBlock;
import general.mechanics.api.block.plastic.PlasticTypeBlock;
import general.mechanics.api.block.plastic.ColoredPlasticBlock;
import general.mechanics.api.item.plastic.PlasticType;
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

    // Plastic Blocks
    public static final BlockDefinition<PlasticTypeBlock> POLYETHYLENE_BLOCK = plasticTypeBlock("Polyethylene Block", PlasticType.POLYETHYLENE);
    public static final BlockDefinition<PlasticTypeBlock> POLYPROPYLENE_BLOCK = plasticTypeBlock("Polypropylene Block", PlasticType.POLYPROPYLENE);
    public static final BlockDefinition<PlasticTypeBlock> POLYSTYRENE_BLOCK = plasticTypeBlock("Polystyrene Block", PlasticType.POLYSTYRENE);
    public static final BlockDefinition<PlasticTypeBlock> POLYVINYL_CHLORIDE_BLOCK = plasticTypeBlock("Polyvinyl Chloride Block", PlasticType.POLYVINYL_CHLORIDE);
    public static final BlockDefinition<PlasticTypeBlock> POLYETHYLENE_TEREPHTHALATE_BLOCK = plasticTypeBlock("Polyethylene Terephthalate Block", PlasticType.POLYETHYLENE_TEREPHTHALATE);
    public static final BlockDefinition<PlasticTypeBlock> ACRYLONITRILE_BUTADIENE_STYRENE_BLOCK = plasticTypeBlock("Acrylonitrile Butadiene Styrene Block", PlasticType.ACRYLONITRILE_BUTADIENE_STYRENE);
    public static final BlockDefinition<PlasticTypeBlock> POLYCARBONATE_BLOCK = plasticTypeBlock("Polycarbonate Block", PlasticType.POLYCARBONATE);
    public static final BlockDefinition<PlasticTypeBlock> NYLON_BLOCK = plasticTypeBlock("Nylon Block", PlasticType.NYLON);
    public static final BlockDefinition<PlasticTypeBlock> POLYURETHANE_BLOCK = plasticTypeBlock("Polyurethane Block", PlasticType.POLYURETHANE);
    public static final BlockDefinition<PlasticTypeBlock> POLYTETRAFLUOROETHYLENE_BLOCK = plasticTypeBlock("Polytetrafluoroethylene Block", PlasticType.POLYTETRAFLUOROETHYLENE);
    public static final BlockDefinition<PlasticTypeBlock> POLYETHERETHERKETONE_BLOCK = plasticTypeBlock("Polyetheretherketone Block", PlasticType.POLYETHERETHERKETONE);

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
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_0 = reg("Polyethylene Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYETHYLENE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_1 = reg("Polypropylene Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYPROPYLENE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_2 = reg("Polystyrene Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYSTYRENE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_3 = reg("Polyvinyl Chloride Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYVINYL_CHLORIDE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_4 = reg("Polyethylene Terephthalate Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYETHYLENE_TEREPHTHALATE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_5 = reg("Acrylonitrile Butadiene Styrene Machine Frame", () -> new MachineFrameBlock(PlasticType.ACRYLONITRILE_BUTADIENE_STYRENE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_6 = reg("Polycarbonate Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYCARBONATE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_7 = reg("Nylon Machine Frame", () -> new MachineFrameBlock(PlasticType.NYLON));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_8 = reg("Polyurethane Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYURETHANE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_9 = reg("Polytetrafluoroethylene Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYTETRAFLUOROETHYLENE));
    public static final BlockDefinition<MachineFrameBlock> MACHINE_FRAME_10 = reg("Polyetheretherketone Machine Frame", () -> new MachineFrameBlock(PlasticType.POLYETHERETHERKETONE));

    /**
     * Formats a color name by capitalizing each word and replacing underscores with spaces.
     * Example: "light_blue" -> "Light Blue"
     */
    private static String formatColorName(String colorName) {
        String[] words = colorName.split("_");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formatted.append(" ");
            }
            formatted.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1));
        }
        return formatted.toString();
    }

    static <T extends PlasticTypeBlock> BlockDefinition<T> plasticTypeBlock(String name, PlasticType plasticType) {
        // Create the main plastic type block
        BlockDefinition<T> plasticTypeDef = reg(name, () -> (T) new PlasticTypeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), plasticType));
        
        // Register all colored plastic block variants
        for (DyeColor color : PlasticType.getAllColors()) {
            String coloredName = formatColorName(color.getName()) + " " + name;
            String coloredResourceName = color.getName().toLowerCase() + "_" + name.toLowerCase().replace(' ', '_');
            reg(coloredName, GM.getResource(coloredResourceName), () -> {
                T plasticTypeBlock = plasticTypeDef.block();
                return (Block) plasticTypeBlock.getColoredVariant(color);
            }, null, true);
        }
        
        return plasticTypeDef;
    }

    /**
     * Get all colored plastic blocks for a specific plastic type
     */
    public static List<ColoredPlasticBlock> getColoredPlasticBlocksForType(PlasticType plasticType) {
        List<ColoredPlasticBlock> coloredVariants = new ArrayList<>();
        for (var block : BLOCKS) {
            if (block.block() instanceof ColoredPlasticBlock colored) {
                if (colored.getPlasticType() == plasticType) {
                    coloredVariants.add(colored);
                }
            }
        }
        return coloredVariants;
    }

    /**
     * Get all colored plastic blocks
     */
    public static List<ColoredPlasticBlock> getAllColoredPlasticBlocks() {
        List<ColoredPlasticBlock> allColored = new ArrayList<>();
        for (var block : BLOCKS) {
            if (block.block() instanceof ColoredPlasticBlock colored) {
                allColored.add(colored);
            }
        }
        return allColored;
    }

    public static List<PlasticTypeBlock> getAllPlasticTypeBlocks() {
        List<PlasticTypeBlock> allPlastic = new ArrayList<>();
        for (var block : BLOCKS) {
            if (block.block() instanceof PlasticTypeBlock plastic) {
                allPlastic.add(plastic);
            }
        }
        return allPlastic;
    }

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
