package fluxmachines.core.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fluxmachines.core.api.recipe.CoreRecipe;
import fluxmachines.core.registries.CoreRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record FabricationRecipe(NonNullList<Ingredient> inputItems, ItemStack output) implements CoreRecipe<FabricationRecipe.FabricationRecipeInput> {

    private static final int MAX_INGREDIENTS = 3;

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(@NotNull FabricationRecipe.FabricationRecipeInput in, @NotNull Level level) {
        if (level.isClientSide()) return false;

        List<ItemStack> provided = new ArrayList<>();
        for (int i = 0; i < in.size(); i++) {
            ItemStack s = in.getItem(i);
            if (!s.isEmpty()) provided.add(s);
        }

        int needed = 0;
        for (Ingredient ing : inputItems) {
            if (!ing.isEmpty()) needed++;
        }
        if (provided.size() != needed) return false;

        List<ItemStack> pool = new ArrayList<>(provided);
        for (Ingredient req : inputItems) {
            if (req.isEmpty()) continue;
            boolean matchedOne = false;

            Iterator<ItemStack> it = pool.iterator();
            while (it.hasNext()) {
                ItemStack candidate = it.next();
                if (req.test(candidate)) {
                    it.remove();
                    matchedOne = true;
                    break;
                }
            }
            if (!matchedOne) return false;
        }

        return pool.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FabricationRecipe.FabricationRecipeInput fabricationRecipeInput, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        int needed = 0;
        for (Ingredient ing : inputItems) if (!ing.isEmpty()) needed++;
        return width * height >= needed;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CoreRecipes.FABRICATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.FABRICATION_RECIPE_TYPE.get();
    }

    public record FabricationRecipeInput(ItemStack... slots) implements RecipeInput {

        public static FabricationRecipeInput of(Container container) {
            int size = container.getContainerSize();
            ItemStack[] arr = new ItemStack[size];
            for (int i = 0; i < size; i++) arr[i] = container.getItem(i);
            return new FabricationRecipeInput(arr);
        }

        public static FabricationRecipeInput of(List<ItemStack> stacks) {
            ItemStack[] arr = stacks.toArray(new ItemStack[0]);
            return new FabricationRecipeInput(arr);
        }

        @Override
        public @NotNull ItemStack getItem(int slot) {
            return (slot >= 0 && slot < slots.length) ? slots[slot] : ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return slots.length;
        }
    }

    public static class FabricationRecipeSerializer implements RecipeSerializer<FabricationRecipe> {

        public static final MapCodec<FabricationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").xmap(
                        list -> {
                            if (list.size() > MAX_INGREDIENTS) {
                                throw new IllegalArgumentException("Fabrication recipe supports at most " + MAX_INGREDIENTS + " ingredients");
                            }
                            NonNullList<Ingredient> nn = NonNullList.create();
                            nn.addAll(list);
                            return nn;
                        },
                        List::copyOf
                ).forGetter(FabricationRecipe::inputItems),
                ItemStack.CODEC.fieldOf("result").forGetter(FabricationRecipe::output)
        ).apply(inst, FabricationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FabricationRecipe> SIMPLE_STREAM_CODEC =
                new StreamCodec<>() {
                    @Override
                    public void encode(RegistryFriendlyByteBuf buf, FabricationRecipe recipe) {
                        NonNullList<Ingredient> ingredients = recipe.inputItems();
                        buf.writeByte(ingredients.size());
                        for (Ingredient ing : ingredients) {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                    }

                    @Override
                    public @NotNull FabricationRecipe decode(RegistryFriendlyByteBuf buf) {
                        int size = buf.readUnsignedByte();
                        if (size < 0 || size > MAX_INGREDIENTS)
                            throw new IllegalArgumentException("Invalid ingredient count: " + size);
                        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
                        for (int i = 0; i < size; i++) {
                            ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                        }
                        ItemStack out = ItemStack.STREAM_CODEC.decode(buf);
                        return new FabricationRecipe(ingredients, out);
                    }
                };

        @Override
        public @NotNull MapCodec<FabricationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FabricationRecipe> streamCodec() {
            return SIMPLE_STREAM_CODEC;
        }
    }

}
