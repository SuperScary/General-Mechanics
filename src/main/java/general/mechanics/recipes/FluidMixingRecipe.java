package general.mechanics.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import general.mechanics.registries.CoreRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record FluidMixingRecipe(List<SizedFluidIngredient> inputs, FluidStack result) implements Recipe<FluidMixingRecipe.MixingInput> {

    @Override
    public boolean matches(@NotNull MixingInput in, @NotNull Level level) {
        if (level.isClientSide()) return false;
        List<FluidStack> have = new ArrayList<>(3);
        if (!in.getFluid(0).isEmpty()) have.add(in.getFluid(0));
        if (!in.getFluid(1).isEmpty()) have.add(in.getFluid(1));
        if (!in.getFluid(2).isEmpty()) have.add(in.getFluid(2));
        if (have.size() < inputs.size()) return false;

        List<FluidStack> used = new ArrayList<>();
        for (SizedFluidIngredient req : inputs) {
            boolean matched = false;
            for (FluidStack candidate : have) {
                if (used.contains(candidate)) continue;
                if (req.test(candidate)) { used.add(candidate); matched = true; break; }
            }
            if (!matched) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MixingInput in, HolderLookup.@NotNull Provider p) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider p) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CoreRecipes.FLUID_MIXING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.FLUID_MIXING_RECIPE_TYPE.get();
    }

    public record MixingInput(@Nullable FluidStack a, @Nullable FluidStack b, @Nullable FluidStack c) implements RecipeInput {
        @Override
        public @NotNull ItemStack getItem(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return 0;
        }

        public FluidStack getFluid(int idx) {
            return switch (idx) {
                case 0 -> a == null ? FluidStack.EMPTY : a;
                case 1 -> b == null ? FluidStack.EMPTY : b;
                case 2 -> c == null ? FluidStack.EMPTY : c;
                default -> FluidStack.EMPTY;
            };
        }
    }

    // ----- Serializer (JSON + network) -----
    public static class Serializer implements RecipeSerializer<FluidMixingRecipe> {

        public static final MapCodec<FluidMixingRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        SizedFluidIngredient.NESTED_CODEC.listOf()
                                .flatXmap(list -> (!list.isEmpty() && list.size() <= 3) ? DataResult.success(list) : DataResult.error(() -> "FluidMixingRecipe requires 1..3 inputs"),DataResult::success)
                                .fieldOf("inputs").forGetter(FluidMixingRecipe::inputs),
                        FluidStack.CODEC.fieldOf("result").forGetter(FluidMixingRecipe::result)
                ).apply(inst, FluidMixingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FluidMixingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        SizedFluidIngredient.STREAM_CODEC.apply(ByteBufCodecs.list(3)), FluidMixingRecipe::inputs,
                        FluidStack.STREAM_CODEC, FluidMixingRecipe::result,
                        FluidMixingRecipe::new
                );

        @Override
        public @NotNull MapCodec<FluidMixingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FluidMixingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
