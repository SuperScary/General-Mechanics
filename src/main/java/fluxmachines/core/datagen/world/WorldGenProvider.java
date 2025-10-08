package fluxmachines.core.datagen.world;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.util.IDataProvider;
import fluxmachines.core.world.modifiers.CoreBiomeModifiers;
import fluxmachines.core.world.modifiers.CoreConfiguredFeatures;
import fluxmachines.core.world.modifiers.CorePlacedFeatures;
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
        super(output, registries, BUILDER, Set.of(FluxMachines.MODID));
    }

    @Override
    public @NotNull String getName () {
        return FluxMachines.NAME +  " World Gen";
    }

}
