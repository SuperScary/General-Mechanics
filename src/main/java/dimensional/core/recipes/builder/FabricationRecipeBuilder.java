package dimensional.core.recipes.builder;

import dimensional.core.recipes.FabricationRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public class FabricationRecipeBuilder {

    public static void build(RecipeOutput out, ResourceLocation id, Ingredient input, ItemLike result) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    public static void build(RecipeOutput out, ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack result) {
        out.accept(id, new FabricationRecipe(inputs, result), null);
    }

    /**
     * Varargs items → each becomes its own Ingredient (require all).
     */
    public static void build(RecipeOutput out, ResourceLocation id, ItemLike result, ItemLike... inputs) {
        NonNullList<Ingredient> list = NonNullList.create();
        Arrays.stream(inputs).forEach(i -> list.add(Ingredient.of(i)));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }

    /**
     * Single tag as an ingredient (require any item in the tag).
     */
    public static void build(RecipeOutput out, ResourceLocation id, TagKey<Item> tag, ItemLike result) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.of(tag));
        out.accept(id, new FabricationRecipe(list, new ItemStack(result.asItem())), null);
    }
}
