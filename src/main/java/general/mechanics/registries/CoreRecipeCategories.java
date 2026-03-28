package general.mechanics.registries;

import general.mechanics.GM;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CoreRecipeCategories {

    public static final DeferredRegister<RecipeBookCategory> REGISTRY = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, GM.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> CRUSHING = register("crushing");
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> MATTER_FABRICATOR = register("matter_fabricator");
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> FLUID_MIXING = register("fluid_mixing");

    private static DeferredHolder<RecipeBookCategory, RecipeBookCategory> register(String id) {
        return REGISTRY.register(id, RecipeBookCategory::new);
    }

}
