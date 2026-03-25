package general.mechanics.recipes.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record PowerIngredient(float fePerTick) {

    public static final Codec<PowerIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.FLOAT.fieldOf("required").forGetter(PowerIngredient::fePerTick)
    ).apply(inst, PowerIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PowerIngredient> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buf, PowerIngredient value) {
            buf.writeFloat(value.fePerTick());
        }

        @Override
        public @NotNull PowerIngredient decode(@NotNull RegistryFriendlyByteBuf buf) {
            float power = buf.readFloat();
            return new PowerIngredient(power);
        }
    };

    public PowerIngredient {
        if (fePerTick < 0) {
            throw new IllegalArgumentException("Power cannot be negative");
        }
    }

}
