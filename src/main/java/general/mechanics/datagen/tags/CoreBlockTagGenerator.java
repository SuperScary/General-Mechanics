package general.mechanics.datagen.tags;

import general.mechanics.GM;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreBlocks.*;
import general.mechanics.registries.CoreBlocks;

public class CoreBlockTagGenerator extends BlockTagsProvider implements IDataProvider {

    public CoreBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, GM.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(VANADIUM_ORE.block())
                .add(VANADIUM_BLOCK.block())
                .add(DEEPSLATE_VANADIUM_ORE.block())
                .add(ICE2.block())
                .add(ICE3.block())
                .add(ICE4.block())
                .add(ICE5.block())
                .add(ICE6.block())
                .add(ICE7.block())
                .add(MACHINE_FRAME_0.block());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ICE2.block())
                .add(ICE3.block())
                .add(ICE4.block())
                .add(ICE5.block())
                .add(ICE6.block())
                .add(ICE7.block())
                .add(MACHINE_FRAME_0.block());

        for (var block : CoreBlocks.getAllColoredPlasticBlocks()) {
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(block);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            this.tag(CoreTags.Blocks.PLASTIC).add(block);
        }

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(VANADIUM_ORE.block())
                .add(VANADIUM_BLOCK.block())
                .add(DEEPSLATE_VANADIUM_ORE.block());

        this.tag(Tags.Blocks.ORES)
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
