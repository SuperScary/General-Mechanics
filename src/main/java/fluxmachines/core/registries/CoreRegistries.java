package fluxmachines.core.registries;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.multiblock.MultiblockDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class CoreRegistries {

    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();

    public static final ResourceKey<Registry<MultiblockDefinition>> MULTIBLOCKS = createRegistryKey("multiblock");
    //public static final ResourceKey<Registry<UpgradeMap<?>>> UPGRADE_MAPS = createRegistryKey("upgrade_map");
    //public static final ResourceKey<Registry<DeferredCoolant<?>>> DEFERRED_COOLANT = createRegistryKey("deferred_coolant");

    public static final Registry<MultiblockDefinition> MULTIBLOCK_DEFINITIONS = create(MULTIBLOCKS);
    //public static final Registry<UpgradeMap<?>> UPGRADE_MAP_REGISTRY = create(UPGRADE_MAPS);

    private static <T> Registry<T> create (ResourceKey<? extends Registry<T>> key) {
        var reg = new RegistryBuilder<>(key).sync(true).defaultKey(FluxMachines.getResource("empty")).create();
        REGISTRIES.add(reg);
        return reg;
    }

    private static <T> ResourceKey<Registry<T>> createRegistryKey (String name) {
        return ResourceKey.createRegistryKey(FluxMachines.getResource(name));
    }

    public static void registerRegistries (NewRegistryEvent event) {
        for (var registry : REGISTRIES) {
            event.register(registry);
        }
    }

}

