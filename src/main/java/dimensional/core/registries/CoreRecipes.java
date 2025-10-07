package dimensional.core.registries;

import dimensional.core.DimensionalCore;
import dimensional.core.recipes.FabricationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CoreRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, DimensionalCore.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, DimensionalCore.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FabricationRecipe>> FABRICATION_SERIALIZER = SERIALIZER_REGISTRY.register("fabrication", FabricationRecipe.FabricationRecipeSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FabricationRecipe>> FABRICATION_RECIPE_TYPE = RECIPE_REGISTRY.register("fabrication", () -> RecipeType.simple(DimensionalCore.getResource("fabrication")));

}
