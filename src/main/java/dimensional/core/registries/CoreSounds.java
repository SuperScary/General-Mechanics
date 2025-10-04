package dimensional.core.registries;

import dimensional.core.DimensionalCore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CoreSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DimensionalCore.MODID);

    public static final Supplier<SoundEvent> PLASTIC_BLOCK_PLACE = register("plastic_block_place");
    public static final Supplier<SoundEvent> PLASTIC_BLOCK_BREAK = register("plastic_block_break");

    public static final DeferredSoundType PLASTIC_BLOCK = new DeferredSoundType(1f, 1f,
            PLASTIC_BLOCK_BREAK, PLASTIC_BLOCK_BREAK, PLASTIC_BLOCK_PLACE, PLASTIC_BLOCK_BREAK, PLASTIC_BLOCK_BREAK);

    private static Supplier<SoundEvent> register (String name) {
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(DimensionalCore.getResource(name)));
    }

}
