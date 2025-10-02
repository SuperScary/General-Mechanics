package dimensional.core.datagen.tags;

import dimensional.core.DimensionalCore;
import dimensional.core.api.util.IDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static dimensional.core.registries.CoreItems.*;

public class ModItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, DimensionalCore.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Items.INGOTS)
                .add(DRAKIUM_INGOT.asItem());

        this.tag(Tags.Items.RAW_MATERIALS)
                .add(RAW_DRAKIUM_ORE.asItem());

        this.tag(Tags.Items.NUGGETS)
                .add(DRAKIUM_NUGGET.asItem());

    }

    @Override
    public @NotNull String getName () {
        return DimensionalCore.NAME + " ItemTags";
    }
}
