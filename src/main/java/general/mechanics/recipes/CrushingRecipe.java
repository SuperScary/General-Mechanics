package general.mechanics.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import general.mechanics.api.recipe.CoreRecipe;
import general.mechanics.registries.CoreRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CrushingRecipe(Ingredient input, ItemStack output) implements CoreRecipe<CrushingRecipe.CrushingRecipeInput> {

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(input);
    }

    @Override
    public boolean matches(@NotNull CrushingRecipe.CrushingRecipeInput in, @NotNull Level level) {
        if (level.isClientSide()) return false;
        return input.test(in.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CrushingRecipe.CrushingRecipeInput crushingRecipeInput, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CoreRecipes.CRUSHING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.CRUSHING_RECIPE_TYPE.get();
    }

    public record CrushingRecipeInput(ItemStack input) implements RecipeInput {

        @Override
        public @NotNull ItemStack getItem(int slot) {
            return input;
        }

        @Override
        public int size() {
            return 1;
        }
    }

    public static class Serializer implements RecipeSerializer<CrushingRecipe> {

        public static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CrushingRecipe::input),
                ItemStack.CODEC.fieldOf("result").forGetter(CrushingRecipe::output)
        ).apply(inst, CrushingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CrushingRecipe::input,
                        ItemStack.STREAM_CODEC, CrushingRecipe::output,
                        CrushingRecipe::new
                );

        @Override
        public @NotNull MapCodec<CrushingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
