package general.mechanics.compat.jei.categories;

import general.mechanics.GM;
import general.mechanics.api.gui.MachineUiState;
import general.mechanics.gui.renderers.EnergyDisplayTooltipArea;
import general.mechanics.recipes.FabricationRecipe;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.util.MouseUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatterFabricatorCategory implements IRecipeCategory<FabricationRecipe> {

    public static final ResourceLocation UID = GM.getResource("refabrication");
    public static final ResourceLocation TEXTURE = GM.getResource("textures/gui/matter_fabricator.png");
    private final ResourceLocation progressArrow = GM.getResource("textures/gui/elements/arrow.png");

    public static final RecipeType<FabricationRecipe> TYPE =
            new RecipeType<>(UID, FabricationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MatterFabricatorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(CoreBlocks.MATTER_FABRICATOR));
    }

    @Override
    public @NotNull RecipeType<FabricationRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.gm.matter_fabricator");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public void draw(@NotNull FabricationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        background.draw(graphics);

        var time = recipe.craftingTime().ticks();
        var energy = recipe.powerIngredient().fePerTick();

        new EnergyDisplayTooltipArea(0, 0, () -> (int) energy, () -> (int) energy).render(graphics, 158, 9);

        int arrowSize = 26;

        long gameTime = Minecraft.getInstance().level != null
                ? Minecraft.getInstance().level.getGameTime()
                : 0;

        float progress = time > 0 ? (gameTime % (long) time) : 0;
        int scaledProgress = time > 0 ? (int) ((progress / time) * arrowSize) : 0;

        graphics.blit(progressArrow, 79, 35, 0, 0, scaledProgress, 17, 26, 17);
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, @NotNull FabricationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        IRecipeCategory.super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);

        // Energy Bar Tooltip
        var energy = recipe.powerIngredient().fePerTick();
        if (energy > 0 && MouseUtil.isMouseOver(mouseX, mouseY, 158, 9, 8, 64)) {
            DecimalFormat format = new DecimalFormat("#,###");
            tooltip.add(Component.literal(format.format(energy)+ " FE/t"));
        }

        var time = recipe.craftingTime().ticks();
        if (time > 0 && MouseUtil.isMouseOver(mouseX, mouseY, 80, 35, 22, 15)) {
            tooltip.add(Component.translatable("gui.gm.seconds", time / 20f));
        }
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, FabricationRecipe recipe, @NotNull IFocusGroup focuses) {
        final int baseX = 20, baseY = 35, step = 18, cols = 3;

        var ingredients = recipe.inputItems();
        int count = Math.min(ingredients.size(), 3);

        for (int i = 0; i < count; i++) {
            var ing = ingredients.get(i);
            if (ing.ingredient().isEmpty()) continue;

            int col = i % cols;
            int x = baseX + col * step;
            int y = baseY;

            List<ItemStack> displayed = new ArrayList<>();
            for (ItemStack stack : ing.ingredient().getItems()) {
                if (stack.isEmpty()) continue;
                ItemStack copy = stack.copy();
                copy.setCount(ing.count());
                displayed.add(copy);
            }

            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .addItemStacks(displayed);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35)
                .addItemStack(recipe.getResultItem(null));
    }
}