package general.mechanics.recipes.builder;

import general.mechanics.recipes.FabricationRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public final class FabricationRecipeBuilder {

    public static void build(RecipeOutput out, ResourceLocation id, Ingredient input, ItemLike result) {
        NonNullList<FabricationRecipe.CountedIngredient> list = NonNullList.create();
        list.add(new FabricationRecipe.CountedIngredient(input, 1));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    public static void build(RecipeOutput out, ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack result) {
        NonNullList<FabricationRecipe.CountedIngredient> list = NonNullList.create();
        for (Ingredient ing : inputs) {
            list.add(new FabricationRecipe.CountedIngredient(ing, 1));
        }
        out.accept(id, new FabricationRecipe(list, result), null);
    }

    public static void buildCounted(RecipeOutput out, ResourceLocation id, NonNullList<FabricationRecipe.CountedIngredient> inputs, ItemStack result) {
        out.accept(id, new FabricationRecipe(inputs, result), null);
    }

    /**
     * Varargs items → each become its own Ingredient (require all).
     */
    public static void build(RecipeOutput out, ResourceLocation id, ItemLike result, ItemLike... inputs) {
        NonNullList<FabricationRecipe.CountedIngredient> list = NonNullList.create();
        Arrays.stream(inputs).forEach(i -> list.add(new FabricationRecipe.CountedIngredient(Ingredient.of(i), 1)));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    /**
     * Single tag as an ingredient (require any item in the tag).
     */
    public static void build(RecipeOutput out, ResourceLocation id, TagKey<Item> tag, ItemLike result) {
        NonNullList<FabricationRecipe.CountedIngredient> list = NonNullList.create();
        list.add(new FabricationRecipe.CountedIngredient(Ingredient.of(tag), 1));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    public static void build(RecipeOutput out, ResourceLocation id, ItemStack result, FabricationRecipe.CountedIngredient... inputs) {
        NonNullList<FabricationRecipe.CountedIngredient> list = NonNullList.create();
        list.addAll(Arrays.asList(inputs));
        out.accept(id, new FabricationRecipe(list, result), null);
    }
}
