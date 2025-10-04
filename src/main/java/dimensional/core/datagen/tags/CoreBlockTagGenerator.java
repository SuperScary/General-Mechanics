package dimensional.core.datagen.tags;

import dimensional.core.DimensionalCore;
import dimensional.core.api.block.plastic.PlasticBlock;
import dimensional.core.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static dimensional.core.registries.CoreBlocks.*;

public class CoreBlockTagGenerator extends BlockTagsProvider implements IDataProvider {

    public CoreBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DimensionalCore.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(DRAKIUM_ORE.block())
                .add(DEEPSLATE_DRAKIUM_ORE.block())
                .add(END_STONE_DRAKIUM_ORE.block())
                .add(NETHER_DRAKIUM_ORE.block())
                .add(DRAKIUM_BLOCK.block())
                .add(DRAKIUM_BLOCK_RAW.block())
                .add(VANADIUM_ORE.block())
                .add(VANADIUM_BLOCK.block())
                .add(DEEPSLATE_VANADIUM_ORE.block())
                .add(ICE2.block())
                .add(ICE3.block())
                .add(ICE4.block())
                .add(ICE5.block())
                .add(ICE6.block())
                .add(ICE7.block());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ICE2.block())
                .add(ICE3.block())
                .add(ICE4.block())
                .add(ICE5.block())
                .add(ICE6.block())
                .add(ICE7.block());

        for (var block : PlasticBlock.getPlasticBlocks()) {
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(block);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
        }

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(DRAKIUM_ORE.block())
                .add(DEEPSLATE_DRAKIUM_ORE.block())
                .add(END_STONE_DRAKIUM_ORE.block())
                .add(NETHER_DRAKIUM_ORE.block())
                .add(DRAKIUM_BLOCK.block())
                .add(DRAKIUM_BLOCK_RAW.block())
                .add(VANADIUM_ORE.block())
                .add(VANADIUM_BLOCK.block())
                .add(DEEPSLATE_VANADIUM_ORE.block());

        this.tag(Tags.Blocks.ORES)
                .add(DRAKIUM_ORE.block())
                .add(END_STONE_DRAKIUM_ORE.block())
                .add(DEEPSLATE_DRAKIUM_ORE.block())
                .add(NETHER_DRAKIUM_ORE.block())
                .add(VANADIUM_ORE.block())
                .add(VANADIUM_BLOCK.block())
                .add(DEEPSLATE_VANADIUM_ORE.block());

        this.tag(BlockTags.ICE)
                .add(ICE2.block())
                .add(ICE3.block())
                .add(ICE4.block())
                .add(ICE5.block())
                .add(ICE6.block())
                .add(ICE7.block());
    }
}
