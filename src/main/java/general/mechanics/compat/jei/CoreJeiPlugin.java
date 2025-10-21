package general.mechanics.compat.jei;

import general.mechanics.GM;
import general.mechanics.compat.jei.categories.CrushingCategory;
import general.mechanics.compat.jei.categories.FluidMixingCategory;
import general.mechanics.compat.jei.categories.MatterFabricatorCategory;
import general.mechanics.gui.screen.MatterFabricatorScreen;
import general.mechanics.gui.screen.base.BaseScreen;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
@JeiPlugin
public class CoreJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return GM.getResource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MatterFabricatorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrushingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FluidMixingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        var fabricationRecipeList = recipeManager.getAllRecipesFor(CoreRecipes.FABRICATION_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MatterFabricatorCategory.TYPE, fabricationRecipeList);

        var crushingRecipeList = recipeManager.getAllRecipesFor(CoreRecipes.CRUSHING_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CrushingCategory.TYPE, crushingRecipeList);

        var fluidMixingRecipeList = recipeManager.getAllRecipesFor(CoreRecipes.FLUID_MIXING_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(FluidMixingCategory.TYPE, fluidMixingRecipeList);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MatterFabricatorScreen.class, -16, 0, 16, 16, MatterFabricatorCategory.TYPE);

        screenAdapter(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CoreBlocks.MATTER_FABRICATOR), MatterFabricatorCategory.TYPE);
    }

    /**
     * Moves JEI stuff out of the way if the side panel is open.
     * Since all menus in GM use the BaseScreen, this should always work.
     * @param registration Registration object
     */
    private void screenAdapter(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(BaseScreen.class, new IGuiContainerHandler<>() {
            @Override
            public @NotNull List<Rect2i> getGuiExtraAreas(@NotNull BaseScreen screen) {
                if (!screen.isSettingsPanelOpen()) return List.of();

                int x = screen.getGuiLeft() + screen.modifiedWidth();
                int y = screen.getGuiTop();
                int w = screen.getImageWidth() + screen.modifiedWidth() + 80;
                int h = screen.getImageHeight();

                return List.of(new Rect2i(x, y, w, h));
            }
        });
    }
}
