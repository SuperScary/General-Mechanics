package general.mechanics.recipes.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an ingredient along with a specified count. This record ensures that the count is always greater than 0.
 * It provides methods for serialization and deserialization using codecs.
 * <p>
 * The ingredient is handled as part of various recipes while maintaining a specified quantity.
 */
public record CountedIngredient(Ingredient ingredient, int count) {
    public CountedIngredient {
        if (count <= 0) {
            throw new IllegalArgumentException("Ingredient count must be > 0");
        }
    }

    public static final Codec<CountedIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CountedIngredient::ingredient),
            Codec.INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count)
    ).apply(inst, CountedIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CountedIngredient> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buf, CountedIngredient value) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, value.ingredient());
            buf.writeVarInt(value.count());
        }

        @Override
        public @NotNull CountedIngredient decode(@NotNull RegistryFriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            int count = buf.readVarInt();
            return new CountedIngredient(ingredient, count);
        }
    };
}
