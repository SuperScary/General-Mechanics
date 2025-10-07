package dimensional.core.gui.screen.base;

import com.mojang.blaze3d.systems.RenderSystem;
import dimensional.core.DimensionalCore;
import dimensional.core.api.energy.CoreEnergyStorage;
import dimensional.core.api.energy.PoweredBlock;
import dimensional.core.api.entity.Crafter;
import dimensional.core.gui.GuiPower;
import dimensional.core.gui.menu.base.BaseMenu;
import dimensional.core.gui.renderers.EnergyDisplayTooltipArea;
import dimensional.core.gui.renderers.FluidTankRenderer;
import dimensional.core.gui.renderers.ProgressDisplayTooltipArea;
import dimensional.core.util.MouseUtil;
import dimensional.core.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseMenu<?, ?>> extends AbstractContainerScreen<T> {

    private final int guiOffset;

    private final int energyLeft;
    private final int energyWidth;
    private final int energyTop;
    private final int energyHeight;
    private boolean isSideTabOpen;

    private boolean settingsPanelOpen;
    public static int settingsPanelX;
    public static int settingsPanelY;
    public static int settingsPanelXHalf;

    private EnergyDisplayTooltipArea energyInfoArea;
    private ProgressDisplayTooltipArea progressDisplayTooltipArea;
    //private UpgradeSlotTooltipArea upgradeSlotTooltipArea;

    private final ResourceLocation sideTabClosed = DimensionalCore.getResource("textures/gui/elements/side_tab_closed.png");
    private final ResourceLocation sideTabSelected = DimensionalCore.getResource("textures/gui/elements/side_tab_selected.png");
    private final ResourceLocation sideTabOpen = DimensionalCore.getResource("textures/gui/elements/side_tab_open.png");
    private final ResourceLocation upgradeSlotGui = DimensionalCore.getResource("textures/gui/elements/upgrade_slot.png");
    private final ResourceLocation arrowGui = DimensionalCore.getResource("textures/gui/elements/arrow.png");
    private final ResourceLocation progressBar = DimensionalCore.getResource("textures/gui/elements/progress_bar.png");

    protected int imageWidth;

    public BaseScreen (T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = menu.isUpgradeable() ? 203 : 176;
        this.guiOffset = menu.isUpgradeable() ? Utils.guiScaleOffset() : 0;
        this.energyLeft = 158 - guiOffset / 2;
        this.energyWidth = 8;
        this.energyTop = 9;
        this.energyHeight = 64;
        this.isSideTabOpen = false;
        this.settingsPanelOpen = false;
    }

    @Override
    public void init () {
        super.init();
        if (isPoweredMenu()) {
            assignEnergyInfoArea();
        }

        if (getMenu().isUpgradeable()) {
            assignUpgradeSlotTooltipArea();
        }

        assignProgressBarArea();

        settingsPanelX = ((width - imageWidth) / 2) + imageWidth - 14;
        settingsPanelY = ((height - imageHeight) / 2) + imageHeight - 84;
        settingsPanelXHalf = settingsPanelX + (settingsPanelX / 2);
    }

    /**
     * Returns the gui texture to render.
     * @return {@link ResourceLocation} of a gui texture.
     */
    public abstract ResourceLocation getGuiTexture ();

    /**
     * Renders the main background for the screen.
     * @param guiGraphics {@link GuiGraphics}
     * @param v {@link Float}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
    @Override
    protected void renderBg (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getGuiTexture());
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (isConfigurable() && Minecraft.getInstance().screen == this) {
            if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y, 0, 0, 12, 176) && !isSideTabOpen) {
                guiGraphics.blit(sideTabSelected, x , y, 0, 0, 256, 256);
            } else if (!isSideTabOpen) {
                guiGraphics.blit(sideTabClosed, x, y, 0, 0, 256, 256);
            }
        }

        if (isConfigurable()) toggleSideTab(guiGraphics, mouseX, mouseY, x, y);

        // render main texture after the side tab. Doesn't really matter the order it is rendered in.
        guiGraphics.blit(getGuiTexture(), x, y, 0, 0, imageWidth, imageHeight);

        renderArrow(guiGraphics, x, y);
        renderEnergyArea(guiGraphics);
        renderProgressBar(guiGraphics, x, y);
        addAdditionalScreenElements(guiGraphics, v, mouseX, mouseY);
    }

    /**
     * Handles rendering the labels for objects.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
    @Override
    protected void renderLabels (@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        renderTitles(graphics, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (isConfigurable() && !isSideTabOpen) {
            renderOptionsAreaTooltips(graphics, mouseX, mouseY, x, y);
        }

        if (isPoweredMenu()) {
            renderEnergyAreaTooltips(graphics, mouseX, mouseY, x, y);
        }

        if (menu.isUpgradeable()) {
            renderUpgradeSlotTooltips(graphics, mouseX, mouseY, x, y);
        }

        renderProgressAreaTooltips(graphics, mouseX, mouseY, x, y);
    }

    public void renderTitles (GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2) - modifiedWidth(), titleLabelY, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - modifiedWidth(), this.inventoryLabelY, 4210752, false);
    }

    /**
     * Main render method.
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param delta Delta movement.
     */
    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * Checks for mouse clicks for opening the side tab.
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param button Mouse button pressed
     * @return true if the mouse is clicked in a defined area or the super method.
     */
    @Override
    public boolean mouseClicked (double mouseX, double mouseY, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if (isMouseAboveArea((int) mouseX, (int) mouseY, x + imageWidth + guiOffset, y, 0, 0, 12, imageHeight) && !isSideTabOpen && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            isSideTabOpen = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Use for mouse release on the gui
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param button Mouse button pressed
     * @return true if the mouse button is released.
     */
    @Deprecated(forRemoval = true)
    @Override
    public boolean mouseReleased (double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }
        return false;
    }

    /**
     * Toggles the side settings tab menu.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x Position X
     * @param y Position Y
     */
    public void toggleSideTab (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if (!isSideTabOpen) return;
        settingsPanelOpen = true;
        menu.blockEntity.setSettingsPanelOpen(true);
        graphics.blit(sideTabOpen, x, y, 0, 0, 256, 256);

        addTabElements(graphics, mouseX, mouseY, x, y);

        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y, 0, 0, 256, 256)) {
            // TODO: DO SOMETHING
        } else {
            isSideTabOpen = false;
            settingsPanelOpen = false;
            menu.blockEntity.setSettingsPanelOpen(false);
        }
    }

    /**
     * Internal for rendering the settings tabs elements.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x Position X
     * @param y Position Y
     */
    private void addTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        graphics.drawCenteredString(this.font, Component.translatable("gui.dimensionalcore.gui.settings"), settingsPanelX + Math.abs(guiOffset), settingsPanelY + 6, 0xFFFFFF);
        addAdditionalTabElements(graphics, mouseX, mouseY, x, y);
    }

    /**
     * Currently for rendering custom objects onto the settings pane
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    public abstract void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y);

    /**
     * Render additional elements to the screen without overriding the parent.
     * @param guiGraphics {@link GuiGraphics}
     * @param v {@link Float}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
    public void addAdditionalScreenElements (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {

    }

    /**
     * Renders the energy info onto the screen.
     * @param guiGraphics {@link GuiGraphics}
     */
    public void renderEnergyArea (GuiGraphics guiGraphics) {
        if (isPoweredMenu()) {
            int left = leftPos + energyLeft + guiOffset;
            int top = topPos + energyTop;
            energyInfoArea.render(guiGraphics, left, top);
        }
    }

    /**
     * Renders the progress arrow for progress crafting.
     * @param graphics {@link GuiGraphics}
     * @param posX X position of the arrow
     * @param posY Y position of the arrow
     */
    public void renderArrow (GuiGraphics graphics, int posX, int posY) {
        if (menu.blockEntity instanceof Crafter<?>) {
            int progress = menu.getSyncedProgress();
            int maxProgress = 176;
            int arrowSize = 26;
            
            int scaledProgress = progress != 0 ? progress * arrowSize / maxProgress : 0;
            
            if (scaledProgress >= 0) {
                graphics.blit(arrowGui, posX + 79, posY + 35, 0, 0, scaledProgress, 17, 24, 17);
            }
        }
    }

    public void renderProgressBar (GuiGraphics graphics, int posX, int posY) {
        if (menu.blockEntity instanceof Crafter<?>) {
            graphics.blit(progressBar, posX + 138, posY + 31, 0, 0, 5, 24, 5, 24);

            int left = leftPos + (139 - guiOffset / 2) + guiOffset;
            int top = topPos + 32;
            int progress = menu.getSyncedProgress();
            float prog = progress != 0 ? (progress * 22f) / 176 + 1 : 0; // Add 1 or else it technically never reaches the top.

            progressDisplayTooltipArea.render(graphics, (int) prog, left, top);
        }
    }

    /**
     * For centering the text labels on the main gui window.
     * @return 15 offset if the image width is 203
     */
    protected int modifiedWidth () {
        return imageWidth == 203 ? 15 : 0;
    }

    /**
     * Creates the energy info area
     */
    private void assignEnergyInfoArea () {
        this.energyInfoArea = new EnergyDisplayTooltipArea(energyLeft, energyTop, getEnergyStorage(), energyWidth, energyHeight);
    }

    private void assignUpgradeSlotTooltipArea () {
        //this.upgradeSlotTooltipArea = new UpgradeSlotTooltipArea(182, 5, 16, 16, 4, 2, menu.block);
    }

    private void assignProgressBarArea() {
        this.progressDisplayTooltipArea = new ProgressDisplayTooltipArea(138, 31, 3, 22);
    }

    /**
     * Renders the settings area tooltip
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    private void renderOptionsAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y, 0, 0, 12, 176)) {
            guiGraphics.renderTooltip(this.font, getOptionsTooltips(), Optional.empty(), mouseX - x + (guiOffset / 2), mouseY - y);
        }
    }

    private void renderProgressAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        int left = leftPos + (139 - guiOffset / 2) + guiOffset;
        int top = topPos + 32;

        if (isMouseAboveArea(mouseX, mouseY, left, top, 0, 0, 5, 22)) {
            guiGraphics.renderTooltip(this.font, progressDisplayTooltipArea.getTooltips(), Optional.empty(), mouseX - x + (guiOffset / 2), mouseY - y);
        }
    }

    /**
     * Renders the energy area tooltip
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    private void renderEnergyAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, energyLeft, energyTop, energyWidth, energyHeight)) {
            guiGraphics.renderTooltip(this.font, getEnergyTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private void renderUpgradeSlotTooltips (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        //upgradeSlotTooltipArea.render(this.font, graphics, mouseX, mouseY, x, y);
    }

    /**
     * Renders the fluid area tooltip
     * @param guiGraphics Graphics renderer
     * @param pMouseX mouse position x
     * @param pMouseY mouse position y
     * @param x position x
     * @param y position y
     * @param stack {@link FluidStack}
     * @param offsetX offset x
     * @param offsetY offset y
     * @param renderer {@link FluidTankRenderer}
     */
    @SuppressWarnings("SameParameterValue")
    protected void renderFluidTooltipArea (GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer.getWidth(), renderer.getHeight())) {
            guiGraphics.renderTooltip(font, renderer.getTooltip(stack, TooltipFlag.NORMAL), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected boolean isMouseAboveArea (int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public List<Component> getEnergyTooltips () {
        GuiPower power = (GuiPower) menu;
        DecimalFormat format = new DecimalFormat("#,###");
        return List.of(Component.literal(format.format(power.getPower()) + " / " + format.format(getEnergyStorage().getMaxEnergyStored()) + " FE"));
    }

    public List<Component> getOptionsTooltips () {
        return List.of(Component.translatable("gui.dimensionalcore.gui.settings.left"));
    }

    public CoreEnergyStorage getEnergyStorage () {
        if (menu.blockEntity instanceof PoweredBlock entity) {
            return entity.getEnergyStorage();
        }
        return null;
    }

    public boolean isPoweredMenu () {
        return menu instanceof GuiPower;
    }

    /*public boolean isFluidMenu () {
        return menu instanceof GuiFluid;
    }*/

    public boolean isConfigurable () {
        return true;
    }

    @SuppressWarnings("unused")
    public EnergyDisplayTooltipArea getEnergyInfoArea () {
        return energyInfoArea;
    }

    public ProgressDisplayTooltipArea getProgressBarArea() {
        return progressDisplayTooltipArea;
    }

    public ResourceLocation getUpgradeSlotGui () {
        return upgradeSlotGui;
    }

    public boolean isSettingsPanelOpen () {
        return settingsPanelOpen;
    }

}
