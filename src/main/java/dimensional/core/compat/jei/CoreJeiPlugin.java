package dimensional.core.compat.jei;

import dimensional.core.DimensionalCore;
import dimensional.core.compat.jei.categories.MatterFabricatorCategory;
import dimensional.core.gui.screen.MatterFabricatorScreen;
import dimensional.core.registries.CoreBlocks;
import dimensional.core.registries.CoreRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class CoreJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return DimensionalCore.getResource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MatterFabricatorCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        var fabricationRecipeList = recipeManager.getAllRecipesFor(CoreRecipes.FABRICATION_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MatterFabricatorCategory.TYPE, fabricationRecipeList);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MatterFabricatorScreen.class, 80, 35, 22, 15, MatterFabricatorCategory.TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CoreBlocks.MATTER_FABRICATOR), MatterFabricatorCategory.TYPE);
    }
}
