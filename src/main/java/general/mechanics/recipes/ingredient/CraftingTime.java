package general.mechanics.recipes.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the crafting duration of a recipe in ticks. This record ensures that
 * the duration cannot be negative.
 * <p>
 * Provides serialization and deserialization methods for handling crafting time,
 * as well as codec support for data validation and transformation.
 * <p>
 * The crafting time is primarily used in defining recipes that require a specific
 * duration to process or complete an operation.
 *
 * @param ticks The duration of the crafting process in game ticks. Must be non-negative.
 */
public record CraftingTime(float ticks) {

    public static final Codec<CraftingTime> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.FLOAT.fieldOf("time").forGetter(CraftingTime::ticks)
    ).apply(inst, CraftingTime::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CraftingTime> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buf, CraftingTime value) {
            buf.writeFloat(value.ticks());
        }

        @Override
        public @NotNull CraftingTime decode(@NotNull RegistryFriendlyByteBuf buf) {
            float ticks = buf.readFloat();
            return new CraftingTime(ticks);
        }
    };

    public CraftingTime {
        if (ticks < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
    }

}
