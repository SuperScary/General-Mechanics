package general.mechanics.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import general.mechanics.api.recipe.CoreRecipe;
import general.mechanics.registries.CoreRecipeCategories;
import general.mechanics.registries.CoreRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

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
    public @NotNull ItemStack assemble(@NotNull CrushingRecipe.CrushingRecipeInput crushingRecipeInput) {
        return output.copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CoreRecipes.CRUSHING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.CRUSHING_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return CoreRecipeCategories.CRUSHING.get();
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

    public static class Serializer {

        public static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(CrushingRecipe::input),
                ItemStack.CODEC.fieldOf("result").forGetter(CrushingRecipe::output)
        ).apply(inst, CrushingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CrushingRecipe::input,
                        ItemStack.STREAM_CODEC, CrushingRecipe::output,
                        CrushingRecipe::new
                );

        public static final RecipeSerializer<CrushingRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

}
