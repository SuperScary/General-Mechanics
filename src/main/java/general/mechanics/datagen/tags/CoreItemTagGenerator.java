package general.mechanics.datagen.tags;

import general.mechanics.GM;
import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.tags.CoreTags;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreElements;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static general.mechanics.registries.CoreItems.*;

public class CoreItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public CoreItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, GM.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Items.INGOTS)
                .add(CoreElements.VANADIUM_INGOT.asItem());

        this.tag(Tags.Items.RAW_MATERIALS)
                .add(CoreElements.VANADIUM_INGOT.get().getRawItem().asItem().asItem());

        this.tag(Tags.Items.NUGGETS)
                .add(CoreElements.VANADIUM_INGOT.get().getNuggetItem().asItem().asItem());

        // Add all colored plastics to the tag
        for (var plastic : CoreItems.getAllColoredPlastics()) {
            this.tag(CoreTags.Items.PLASTIC).add(plastic.asItem());
        }

        for (var block : PlasticBlock.getPlasticBlocks()) {
            this.tag(CoreTags.Items.PLASTIC_BLOCKS)
                    .add(block.asItem());
        }

        this.tag(CoreTags.Items.WRENCHES)
                .add(WRENCH.asItem());

    }

    @Override
    public @NotNull String getName () {
        return GM.NAME + " ItemTags";
    }
}
