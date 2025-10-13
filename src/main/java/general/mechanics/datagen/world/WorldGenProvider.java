package general.mechanics.datagen.world;

import general.mechanics.GM;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.world.modifiers.CoreBiomeModifiers;
import general.mechanics.world.modifiers.CoreConfiguredFeatures;
import general.mechanics.world.modifiers.CorePlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider implements IDataProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, CoreConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, CorePlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CoreBiomeModifiers::bootstrap);

    public WorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(GM.MODID));
    }

    @Override
    public @NotNull String getName () {
        return GM.NAME +  " World Gen";
    }

}
