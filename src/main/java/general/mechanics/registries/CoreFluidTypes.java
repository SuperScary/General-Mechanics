package general.mechanics.registries;

import general.mechanics.GM;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CoreFluidTypes {

    public static final ResourceLocation WATER_STILL = GM.getMinecraftResource("block/water_still");
    public static final ResourceLocation WATER_FLOWING = GM.getMinecraftResource("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = GM.getMinecraftResource("block/water_overlay");

    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, GM.MODID);

    // Fluid types are now registered via CoreFluids.registerFluid

    static Supplier<FluidType> register(String name, FluidType type) {
        return REGISTRY.register(name, () -> type);
    }

}
