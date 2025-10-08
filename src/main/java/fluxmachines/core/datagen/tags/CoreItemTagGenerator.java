package fluxmachines.core.datagen.tags;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.block.plastic.PlasticBlock;
import fluxmachines.core.api.tags.CoreTags;
import fluxmachines.core.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static fluxmachines.core.registries.CoreItems.*;

public class CoreItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public CoreItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Items.INGOTS)
                .add(DRAKIUM_INGOT.asItem())
                .add(VANADIUM_INGOT.asItem());

        this.tag(Tags.Items.RAW_MATERIALS)
                .add(RAW_DRAKIUM_ORE.asItem())
                .add(RAW_VANADIUM_ORE.asItem());

        this.tag(Tags.Items.NUGGETS)
                .add(DRAKIUM_NUGGET.asItem())
                .add(VANADIUM_NUGGET.asItem());

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
        return FluxMachines.NAME + " ItemTags";
    }
}
