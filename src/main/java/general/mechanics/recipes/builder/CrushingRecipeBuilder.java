package general.mechanics.recipes.builder;

import general.mechanics.recipes.CrushingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class CrushingRecipeBuilder {

    public static void build(RecipeOutput out, ResourceLocation id, Ingredient input, ItemLike result) {
        out.accept(id, new CrushingRecipe(input, new ItemStack(result.asItem())), null);
    }

    public static void build(RecipeOutput out, ResourceLocation id, Ingredient inputs, ItemStack result) {
        out.accept(id, new CrushingRecipe(inputs, result), null);
    }

    public static void build(RecipeOutput out, ResourceLocation id, TagKey<Item> tag, ItemLike result) {
        out.accept(id, new CrushingRecipe(Ingredient.of(tag), new ItemStack(result.asItem())), null);
    }

}
