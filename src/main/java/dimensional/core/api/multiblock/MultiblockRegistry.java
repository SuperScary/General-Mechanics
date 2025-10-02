package dimensional.core.api.multiblock;

import dimensional.core.DimensionalCore;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * A simple thread-safe registry for multiblock definitions keyed by ResourceLocation.
 * This is a custom registry (not a game registry type) intended for runtime lookup.
 */
public final class MultiblockRegistry {

    private MultiblockRegistry() {}

    public static final ResourceKey<Registry<MultiblockDefinition>> REGISTRY_KEY = ResourceKey.createRegistryKey(DimensionalCore.getResource("multiblock"));
    public static final Registry<MultiblockDefinition> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(true)
            .defaultKey(DimensionalCore.getResource("empty"))
            .create();

}
