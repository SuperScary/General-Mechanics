package general.mechanics.datagen.recipes;

import general.mechanics.GM;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CoreFluidTagGenerator extends FluidTagsProvider implements IDataProvider {

    public CoreFluidTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, GM.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(FluidTags.WATER)
                .add(CoreFluids.CRUDE_OIL.getSource().get())
                .add(CoreFluids.CRUDE_OIL.getFlowing().get())
                .add(CoreFluids.DIESEL.getSource().get())
                .add(CoreFluids.DIESEL.getFlowing().get());
    }

    @Override
    public @Nonnull String getName() {
        return GM.NAME + " Fluid Tags";
    }
}
