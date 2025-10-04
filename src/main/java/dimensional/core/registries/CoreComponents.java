package dimensional.core.registries;

import com.mojang.serialization.Codec;
import dimensional.core.DimensionalCore;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class CoreComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, DimensionalCore.MODID);

    public static final Supplier<DataComponentType<Integer>> PLASTIC_COLOR = REGISTRY.register(
            "plastic_color",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)          // store as an RGB int, e.g. 0xRRGGBB
                    .build()
    );

    private CoreComponents() {
    }
}
