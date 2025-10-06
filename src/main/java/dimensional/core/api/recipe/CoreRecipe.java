package dimensional.core.api.recipe;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

public interface CoreRecipe<T extends RecipeInput> extends Recipe<T> {

    default Ingredient input () {
        return getIngredients().getFirst();
    }

}
