package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.recipes.CrushingRecipe;
import general.mechanics.recipes.FabricationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CoreRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, GM.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, GM.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FabricationRecipe>> FABRICATION_SERIALIZER = SERIALIZER_REGISTRY.register("fabrication", FabricationRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FabricationRecipe>> FABRICATION_RECIPE_TYPE = RECIPE_REGISTRY.register("fabrication", () -> RecipeType.simple(GM.getResource("fabrication")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrushingRecipe>> CRUSHING_SERIALIZER = SERIALIZER_REGISTRY.register("crushing", CrushingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrushingRecipe>> CRUSHING_RECIPE_TYPE = RECIPE_REGISTRY.register("crushing", () -> RecipeType.simple(GM.getResource("crushing")));

}
