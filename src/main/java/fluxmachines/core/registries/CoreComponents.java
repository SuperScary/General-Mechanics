package fluxmachines.core.registries;

import com.mojang.serialization.Codec;
import fluxmachines.core.FluxMachines;
import fluxmachines.core.components.InventoryComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public final class CoreComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, FluxMachines.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_STORED = register("energy_stored", builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_MAX = register("energy_max", builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<InventoryComponent>> INVENTORY = register("inventory_comp", builder -> builder.persistent(InventoryComponent.CODEC));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register (String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return REGISTRY.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
