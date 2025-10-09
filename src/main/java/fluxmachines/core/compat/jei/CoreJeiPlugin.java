package fluxmachines.core.compat.jei;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.compat.jei.categories.MatterFabricatorCategory;
import fluxmachines.core.gui.screen.MatterFabricatorScreen;
import fluxmachines.core.gui.screen.base.BaseScreen;
import fluxmachines.core.registries.CoreBlocks;
import fluxmachines.core.registries.CoreRecipes;
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

@JeiPlugin
public class CoreJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return FluxMachines.getResource("jei_plugin");
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

        screenAdapter(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CoreBlocks.MATTER_FABRICATOR), MatterFabricatorCategory.TYPE);
    }

    /**
     * Moves JEI stuff out of the way if the side panel is open.
     * Since all menus in FluxMachines use the BaseScreen, this should always work.
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
