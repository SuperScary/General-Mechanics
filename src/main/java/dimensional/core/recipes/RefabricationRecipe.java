package dimensional.core.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dimensional.core.api.recipe.CoreRecipe;
import dimensional.core.registries.CoreRecipes;
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

public record RefabricationRecipe(NonNullList<Ingredient> inputItems, ItemStack output) implements CoreRecipe<RefabricationRecipe.RefabricationRecipeInput> {

    private static final int MAX_INGREDIENTS = 3;

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(@NotNull RefabricationRecipeInput in, @NotNull Level level) {
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
    public @NotNull ItemStack assemble(@NotNull RefabricationRecipeInput refabricationRecipeInput, HolderLookup.@NotNull Provider provider) {
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
        return CoreRecipes.REFABRICATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.REFABRICATION_RECIPE_TYPE.get();
    }

    public record RefabricationRecipeInput(ItemStack... slots) implements RecipeInput {

        public static RefabricationRecipeInput of(Container container) {
            int size = container.getContainerSize();
            ItemStack[] arr = new ItemStack[size];
            for (int i = 0; i < size; i++) arr[i] = container.getItem(i);
            return new RefabricationRecipeInput(arr);
        }

        public static RefabricationRecipeInput of(List<ItemStack> stacks) {
            ItemStack[] arr = stacks.toArray(new ItemStack[0]);
            return new RefabricationRecipeInput(arr);
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

    public static class RefabricationRecipeSerializer implements RecipeSerializer<RefabricationRecipe> {

        public static final MapCodec<RefabricationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").xmap(
                        list -> {
                            if (list.size() > MAX_INGREDIENTS) {
                                throw new IllegalArgumentException("Refabrication recipe supports at most " + MAX_INGREDIENTS + " ingredients");
                            }
                            NonNullList<Ingredient> nn = NonNullList.create();
                            nn.addAll(list);
                            return nn;
                        },
                        List::copyOf
                ).forGetter(RefabricationRecipe::inputItems),
                ItemStack.CODEC.fieldOf("result").forGetter(RefabricationRecipe::output)
        ).apply(inst, RefabricationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RefabricationRecipe> SIMPLE_STREAM_CODEC =
                new StreamCodec<>() {
                    @Override
                    public void encode(RegistryFriendlyByteBuf buf, RefabricationRecipe recipe) {
                        NonNullList<Ingredient> ingredients = recipe.inputItems();
                        buf.writeByte(ingredients.size());
                        for (Ingredient ing : ingredients) {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                    }

                    @Override
                    public @NotNull RefabricationRecipe decode(RegistryFriendlyByteBuf buf) {
                        int size = buf.readUnsignedByte();
                        if (size < 0 || size > MAX_INGREDIENTS)
                            throw new IllegalArgumentException("Invalid ingredient count: " + size);
                        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
                        for (int i = 0; i < size; i++) {
                            ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                        }
                        ItemStack out = ItemStack.STREAM_CODEC.decode(buf);
                        return new RefabricationRecipe(ingredients, out);
                    }
                };

        @Override
        public @NotNull MapCodec<RefabricationRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, RefabricationRecipe> streamCodec() {
            return SIMPLE_STREAM_CODEC;
        }
    }

}
