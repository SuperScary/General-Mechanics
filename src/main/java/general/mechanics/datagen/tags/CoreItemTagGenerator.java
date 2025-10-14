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

        this.tag(CoreTags.Items.RAW_PLASTIC)
                .add(RAW_PLASTIC.asItem())
                .add(RAW_PLASTIC_ORANGE.asItem())
                .add(RAW_PLASTIC_MAGENTA.asItem())
                .add(RAW_PLASTIC_LIGHT_BLUE.asItem())
                .add(RAW_PLASTIC_YELLOW.asItem())
                .add(RAW_PLASTIC_LIME.asItem())
                .add(RAW_PLASTIC_PINK.asItem())
                .add(RAW_PLASTIC_GRAY.asItem())
                .add(RAW_PLASTIC_LIGHT_GRAY.asItem())
                .add(RAW_PLASTIC_CYAN.asItem())
                .add(RAW_PLASTIC_PURPLE.asItem())
                .add(RAW_PLASTIC_BLUE.asItem())
                .add(RAW_PLASTIC_BROWN.asItem())
                .add(RAW_PLASTIC_GREEN.asItem())
                .add(RAW_PLASTIC_RED.asItem())
                .add(RAW_PLASTIC_BLACK.asItem());

        this.tag(CoreTags.Items.PLASTIC)
                .add(PLASTIC.asItem())
                .add(PLASTIC_ORANGE.asItem())
                .add(PLASTIC_MAGENTA.asItem())
                .add(PLASTIC_LIGHT_BLUE.asItem())
                .add(PLASTIC_YELLOW.asItem())
                .add(PLASTIC_LIME.asItem())
                .add(PLASTIC_PINK.asItem())
                .add(PLASTIC_GRAY.asItem())
                .add(PLASTIC_LIGHT_GRAY.asItem())
                .add(PLASTIC_CYAN.asItem())
                .add(PLASTIC_PURPLE.asItem())
                .add(PLASTIC_BLUE.asItem())
                .add(PLASTIC_BROWN.asItem())
                .add(PLASTIC_GREEN.asItem())
                .add(PLASTIC_RED.asItem())
                .add(PLASTIC_BLACK.asItem());

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
