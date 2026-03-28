package general.mechanics.registries;

import general.mechanics.GM;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CoreFluidTypes {

    public static final Identifier WATER_STILL = GM.getMinecraftResource("block/water_still");
    public static final Identifier WATER_FLOWING = GM.getMinecraftResource("block/water_flow");
    public static final Identifier WATER_OVERLAY = GM.getMinecraftResource("block/water_overlay");

    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, GM.MODID);

    static Supplier<FluidType> register(String name, FluidType type) {
        return REGISTRY.register(name, () -> type);
    }

}
