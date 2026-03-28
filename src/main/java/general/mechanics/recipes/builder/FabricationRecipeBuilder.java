package general.mechanics.recipes.builder;

import general.mechanics.recipes.FabricationRecipe;
import general.mechanics.recipes.ingredient.CountedIngredient;
import general.mechanics.recipes.ingredient.CraftingTime;
import general.mechanics.recipes.ingredient.PowerIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public final class FabricationRecipeBuilder {

    public static void build(RecipeOutput out, Identifier id, Ingredient input, ItemLike result) {
        NonNullList<CountedIngredient> list = NonNullList.create();
        list.add(new CountedIngredient(input, 1));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    public static void build(RecipeOutput out, Identifier id, float craftTime, float fePerTick, NonNullList<Ingredient> inputs, ItemStack result) {
        NonNullList<CountedIngredient> list = NonNullList.create();
        for (Ingredient ing : inputs) {
            list.add(new CountedIngredient(ing, 1));
        }
        out.accept(id, new FabricationRecipe(list, result, new CraftingTime(craftTime), new PowerIngredient(fePerTick)), null);
    }

    public static void buildCounted(RecipeOutput out, Identifier id, NonNullList<CountedIngredient> inputs, ItemStack result) {
        out.accept(id, new FabricationRecipe(inputs, result), null);
    }

    /**
     * Varargs items → each become its own Ingredient (require all).
     */
    public static void build(RecipeOutput out, Identifier id, float craftTime, float fePerTick, ItemLike result, ItemLike... inputs) {
        NonNullList<CountedIngredient> list = NonNullList.create();
        Arrays.stream(inputs).forEach(i -> list.add(new CountedIngredient(Ingredient.of(i), 1)));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem()), new CraftingTime(craftTime), new PowerIngredient(fePerTick)), null);
    }

    /**
     * Single tag as an ingredient (require any item in the tag).
     */
    public static void build(RecipeOutput out, Identifier id, float craftTime, float fePerTick, TagKey<Item> tag, ItemLike result) {
        NonNullList<CountedIngredient> list = NonNullList.create();
        list.add(new CountedIngredient(Ingredient.of(tag), 1));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem()), new CraftingTime(craftTime), new PowerIngredient(fePerTick)), null);
    }

    public static void build(RecipeOutput out, Identifier id, float craftTime, float fePerTick, ItemStack result, CountedIngredient... inputs) {
        NonNullList<CountedIngredient> list = NonNullList.create();
        list.addAll(Arrays.asList(inputs));
        out.accept(id, new FabricationRecipe(list, result, new CraftingTime(craftTime), new PowerIngredient(fePerTick)), null);
    }
}
