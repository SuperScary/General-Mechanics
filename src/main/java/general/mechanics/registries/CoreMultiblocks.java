package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.multiblock.MultiblockDefinition;
import general.mechanics.api.multiblock.base.Multiblock;
import general.mechanics.multiblocks.DrakiumCrafterMultiblock;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CoreMultiblocks {

    public static final DeferredRegister<MultiblockDefinition> REGISTRY = DeferredRegister.create(CoreRegistries.MULTIBLOCK_DEFINITIONS, GM.MODID);

    public static final DeferredHolder<MultiblockDefinition, MultiblockDefinition> DRAKIUM_CRAFTER =
            reg("Drakium Crafter", DrakiumCrafterMultiblock::new);

    /**
     * Registers a multiblock with the default resource name (name converted to lowercase with underscores).
     */
    public static <T extends Multiblock> DeferredHolder<MultiblockDefinition, MultiblockDefinition> reg(final String name, final Supplier<T> supplier) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return reg(name, GM.getResource(resourceFriendly), supplier);
    }

    /**
     * Registers a multiblock with a custom resource name.
     */
    public static <T extends Multiblock> DeferredHolder<MultiblockDefinition, MultiblockDefinition> reg(final String name, String resourceName, final Supplier<T> supplier) {
        return reg(name, GM.getResource(resourceName), supplier);
    }

    /**
     * Registers a multiblock with a custom resource location.
     */
    public static <T extends Multiblock> DeferredHolder<MultiblockDefinition, MultiblockDefinition> reg(final String name, ResourceLocation id, final Supplier<T> supplier) {
        return REGISTRY.register(id.getPath(), () -> {
            T multiblockObject = supplier.get();
            return new MultiblockDefinition(id, multiblockObject.createLayout(), () -> multiblockObject);
        });
    }

}
