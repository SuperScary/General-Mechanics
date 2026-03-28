package general.mechanics.recipes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import general.mechanics.api.recipe.CoreRecipe;
import general.mechanics.recipes.ingredient.CraftingTime;
import general.mechanics.recipes.ingredient.CountedIngredient;
import general.mechanics.recipes.ingredient.PowerIngredient;
import general.mechanics.registries.CoreRecipeCategories;
import general.mechanics.registries.CoreRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record FabricationRecipe(NonNullList<CountedIngredient> inputItems, ItemStack output, CraftingTime craftingTime, PowerIngredient powerIngredient) implements CoreRecipe<FabricationRecipe.FabricationRecipeInput> {

    private static final int MAX_INGREDIENTS = 3;

    public static final CraftingTime DEFAULT_CRAFTING_TIME = new CraftingTime(176);
    public static final PowerIngredient DEFAULT_POWER = new PowerIngredient(8);

    public FabricationRecipe(NonNullList<CountedIngredient> inputItems, ItemStack output) {
        this(inputItems, output, DEFAULT_CRAFTING_TIME, DEFAULT_POWER);
    }

    private static final Codec<CountedIngredient> COUNTED_INGREDIENT_OR_INGREDIENT_CODEC = Codec.either(Ingredient.CODEC, CountedIngredient.CODEC)
            .xmap(either -> either.map(ing -> new CountedIngredient(ing, 1), ci -> ci),
            ci -> ci.count() == 1 ? Either.left(ci.ingredient()) : Either.right(ci)
    );

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        for (CountedIngredient counted : inputItems) {
            list.add(counted.ingredient());
        }
        return list;
    }

    @Override
    public boolean matches(@NotNull FabricationRecipe.FabricationRecipeInput in, @NotNull Level level) {
        if (level.isClientSide()) return false;

        List<ItemStack> provided = new ArrayList<>();
        for (int i = 0; i < in.size(); i++) {
            ItemStack s = in.getItem(i);
            if (!s.isEmpty()) provided.add(s);
        }

        List<CountedIngredient> required = new ArrayList<>();
        for (CountedIngredient counted : inputItems) {
            if (!counted.ingredient().isEmpty()) required.add(counted);
        }

        if (provided.size() != required.size()) return false;

        boolean[] used = new boolean[provided.size()];
        return matchesRecursive(required, 0, provided, used);
    }

    private static boolean matchesRecursive(List<CountedIngredient> required, int reqIndex, List<ItemStack> provided, boolean[] used) {
        if (reqIndex >= required.size()) return true;

        CountedIngredient req = required.get(reqIndex);
        for (int i = 0; i < provided.size(); i++) {
            if (used[i]) continue;

            ItemStack candidate = provided.get(i);
            if (candidate.getCount() < req.count()) continue;
            if (!req.ingredient().test(candidate)) continue;

            used[i] = true;
            if (matchesRecursive(required, reqIndex + 1, provided, used)) return true;
            used[i] = false;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FabricationRecipe.FabricationRecipeInput fabricationRecipeInput) {
        return output.copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    // TODO: wtf is this?
    @Override
    public String group() {
        return "";
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CoreRecipes.FABRICATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CoreRecipes.FABRICATION_RECIPE_TYPE.get();
    }

    // TODO: ngl i have no idea what this is for.
    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return CoreRecipeCategories.MATTER_FABRICATOR.get();
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

    public static class Serializer {

        private static final Codec<CraftingTime> CRAFTING_TIME_OR_FLOAT_CODEC = Codec.either(
                Codec.FLOAT,
                CraftingTime.CODEC
        ).xmap(
                either -> either.map(CraftingTime::new, t -> t),
                Either::right
        );

        private static final Codec<PowerIngredient> POWER_OR_FLOAT_CODEC = Codec.either(
                Codec.FLOAT,
                PowerIngredient.CODEC
        ).xmap(
                either -> either.map(PowerIngredient::new, p -> p),
                Either::right
        );

        public static final MapCodec<FabricationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                COUNTED_INGREDIENT_OR_INGREDIENT_CODEC.listOf().fieldOf("ingredients").xmap(
                        list -> {
                            if (list.size() > MAX_INGREDIENTS) {
                                throw new IllegalArgumentException("Fabrication recipe supports at most " + MAX_INGREDIENTS + " ingredients");
                            }
                            NonNullList<CountedIngredient> nn = NonNullList.create();
                            nn.addAll(list);
                            return nn;
                        },
                        List::copyOf
                ).forGetter(FabricationRecipe::inputItems),
                ItemStack.CODEC.fieldOf("result").forGetter(FabricationRecipe::output),
                CRAFTING_TIME_OR_FLOAT_CODEC.optionalFieldOf("crafting_time", DEFAULT_CRAFTING_TIME).forGetter(FabricationRecipe::craftingTime),
                POWER_OR_FLOAT_CODEC.optionalFieldOf("power", DEFAULT_POWER).forGetter(FabricationRecipe::powerIngredient)
        ).apply(inst, FabricationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FabricationRecipe> STREAM_CODEC =
                new StreamCodec<>() {
                    @Override
                    public void encode(RegistryFriendlyByteBuf buf, FabricationRecipe recipe) {
                        NonNullList<CountedIngredient> ingredients = recipe.inputItems();
                        buf.writeByte(ingredients.size());
                        for (CountedIngredient ing : ingredients) {
                            CountedIngredient.STREAM_CODEC.encode(buf, ing);
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                        CraftingTime.STREAM_CODEC.encode(buf, recipe.craftingTime());
                        PowerIngredient.STREAM_CODEC.encode(buf, recipe.powerIngredient());
                    }

                    @Override
                    public @NotNull FabricationRecipe decode(RegistryFriendlyByteBuf buf) {
                        int size = buf.readUnsignedByte();
                        if (size < 0 || size > MAX_INGREDIENTS)
                            throw new IllegalArgumentException("Invalid ingredient count: " + size);
                        NonNullList<CountedIngredient> ingredients = NonNullList.withSize(size, new CountedIngredient(Ingredient.EMPTY, 1));
                        for (int i = 0; i < size; i++) {
                            ingredients.set(i, CountedIngredient.STREAM_CODEC.decode(buf));
                        }
                        ItemStack out = ItemStack.STREAM_CODEC.decode(buf);
                        CraftingTime time = CraftingTime.STREAM_CODEC.decode(buf);
                        PowerIngredient power = PowerIngredient.STREAM_CODEC.decode(buf);
                        return new FabricationRecipe(ingredients, out, time, power);
                    }
                };

        public static final RecipeSerializer<FabricationRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    }

}
