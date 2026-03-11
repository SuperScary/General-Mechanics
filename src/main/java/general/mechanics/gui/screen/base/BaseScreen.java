package general.mechanics.gui.screen.base;

import com.mojang.blaze3d.systems.RenderSystem;
import general.mechanics.GM;
import general.mechanics.api.energy.CoreEnergyStorage;
import general.mechanics.api.energy.PoweredBlock;
import general.mechanics.api.entity.Crafter;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.gui.GuiFluid;
import general.mechanics.gui.GuiPower;
import general.mechanics.gui.menu.base.BaseMenu;
import general.mechanics.gui.component.MachineSettingsTab;
import general.mechanics.gui.renderers.EnergyDisplayTooltipArea;
import general.mechanics.gui.renderers.FluidTankRenderer;
import general.mechanics.gui.renderers.ProgressDisplayTooltipArea;
import general.mechanics.gui.util.IconButtonNoBG;
import general.mechanics.util.MouseUtil;
import lombok.Getter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseMenu<?, ?>> extends AbstractContainerScreen<T> {

    private final int guiOffset;

    private final int energyLeft;
    private final int energyWidth;
    private final int energyTop;
    private final int energyHeight;

    private final MachineSettingsTab machineSettingsTab;

    private EnergyDisplayTooltipArea energyInfoArea;
    private ProgressDisplayTooltipArea progressDisplayTooltipArea;

    private IconButtonNoBG lockedButton = null;

    private final ResourceLocation upgradeSlotGui = GM.getResource("textures/gui/elements/upgrade_slot.png");
    private final ResourceLocation progressArrow = GM.getResource("textures/gui/elements/arrow.png");
    private final ResourceLocation progressBar = GM.getResource("textures/gui/elements/progress_bar.png");
    private final ResourceLocation machineActive = GM.getResource("textures/gui/elements/machine_active.png");
    private final ResourceLocation machineInactive = GM.getResource("textures/gui/elements/machine_inactive.png");
    private final ResourceLocation machineError = GM.getResource("textures/gui/elements/machine_warning.png");
    private final ResourceLocation infoIcon = GM.getResource("textures/gui/elements/info.png");
    private final ResourceLocation lockedIcon = GM.getResource("textures/gui/elements/locked.png");
    private final ResourceLocation unlockedIcon = GM.getResource("textures/gui/elements/unlocked.png");

    @Getter
    protected int imageWidth;

    private boolean locked = false; // TODO: Testing only

    public BaseScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.guiOffset = 0;
        this.energyLeft = 158;
        this.energyWidth = 8;
        this.energyTop = 9;
        this.energyHeight = 64;
        this.machineSettingsTab = new MachineSettingsTab(this, guiOffset);
    }

    @Override
    public void init() {
        super.init();
        if (isPoweredMenu()) assignEnergyInfoArea();
        assignProgressBarArea();
        this.lockedButton = new IconButtonNoBG(this.leftPos - 17, this.topPos + 16, 20, 20, unlockedIcon, 0, 0, 16, 16, 16, 16, 0, 0.75f, button -> toggleLocked());
        machineSettingsTab.init();
        addRenderableWidget(lockedButton);
    }

    @Override
    public void onClose() {
        super.onClose();
        machineSettingsTab.onClose();
    }

    private void toggleLocked() {
        locked = !locked;
        lockedButton.setIcon(locked ? lockedIcon : unlockedIcon);
        lockedButton.setTooltip(!locked ? Tooltip.create(Component.translatable("gui.gm.unlocked")) : Tooltip.create(Component.translatable("gui.gm.locked")));
    }

    /**
     * Returns the gui texture to render.
     *
     * @return {@link ResourceLocation} of a gui texture.
     */
    public abstract ResourceLocation getGuiTexture();

    /**
     * Renders the main background for the screen.
     *
     * @param guiGraphics {@link GuiGraphics}
     * @param v           {@link Float}
     * @param mouseX      Mouse Position X
     * @param mouseY      Mouse Position Y
     */
    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getGuiTexture());
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        machineSettingsTab.render(guiGraphics, mouseX, mouseY, x, y);

        // render the main texture after the side tab. Doesn't really matter the order it is rendered in.
        guiGraphics.blit(getGuiTexture(), x, y, 0, 0, imageWidth, imageHeight);

        guiGraphics.blit(infoIcon, x - 16, y, 0, 0, 16, 16, 16, 16);

        renderMachineStatus(guiGraphics, v, mouseX, mouseY);
        renderArrow(guiGraphics, x, y);
        renderEnergyArea(guiGraphics);
        renderProgressBar(guiGraphics, x, y);
        addAdditionalScreenElements(guiGraphics, v, mouseX, mouseY);
    }

    protected void renderMachineStatus(GuiGraphics graphics, float v, int mouseX, int mouseY) {
        if (!showMachineStatus()) return;

        final int x = leftPos + 5;
        final int y = topPos + 5;

        float scale = 0.35f;

        var pose = graphics.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(scale, scale, 1.0f);

        var machineState = ((BasePoweredBlockEntity) menu.blockEntity).getMachineState();
        ResourceLocation texture = switch (machineState) {
            case ACTIVE -> machineActive;
            case INACTIVE -> machineInactive;
            case ERROR -> machineError;
        };

        graphics.blit(texture, 0, 0, 0, 0, 16, 16, 16, 16);

        pose.popPose();

        if (MouseUtil.isMouseOver(mouseX, mouseY, x, y, (int) (16 * scale), (int) (16 * scale))) {
            graphics.renderTooltip(this.font, Component.translatable("gui.gm.status", Component.translatable("gui.gm.status." + machineState.name().toLowerCase())), mouseX, mouseY);
        }
    }

    /**
     * Handles rendering the labels for objects.
     *
     * @param graphics {@link GuiGraphics}
     * @param mouseX   Mouse Position X
     * @param mouseY   Mouse Position Y
     */
    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        renderTitles(graphics, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (isConfigurable() && !machineSettingsTab.isSideTabOpen()) renderOptionsAreaTooltips(graphics, mouseX, mouseY, x, y);
        if (isPoweredMenu()) renderEnergyAreaTooltips(graphics, mouseX, mouseY, x, y);
        if (menu.isUpgradeable()) renderUpgradeSlotTooltips(graphics, mouseX, mouseY, x, y);
        renderProgressAreaTooltips(graphics, mouseX, mouseY, x, y);
    }

    public void renderTitles(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2) - modifiedWidth(), titleLabelY, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - modifiedWidth(), this.inventoryLabelY, 4210752, false);
    }

    /**
     * Main render method.
     *
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX      Mouse Position X
     * @param mouseY      Mouse Position Y
     * @param delta       Delta movement.
     */
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * Checks for mouse clicks for opening the side tab.
     *
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param button Mouse button pressed
     * @return true if the mouse is clicked in a defined area or the super method.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (machineSettingsTab.mouseClicked(mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Currently for rendering custom objects onto the settings pane
     *
     * @param graphics {@link GuiGraphics}
     * @param mouseX   Mouse Position X
     * @param mouseY   Mouse Position Y
     * @param x        X position
     * @param y        Y position
     */
    public abstract void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y);

    /**
     * Render additional elements to the screen without overriding the parent.
     *
     * @param guiGraphics {@link GuiGraphics}
     * @param v           {@link Float}
     * @param mouseX      Mouse Position X
     * @param mouseY      Mouse Position Y
     */
    public void addAdditionalScreenElements(@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
    }

    /**
     * Renders the energy info onto the screen.
     *
     * @param guiGraphics {@link GuiGraphics}
     */
    public void renderEnergyArea(GuiGraphics guiGraphics) {
        if (isPoweredMenu()) {
            int left = leftPos + energyLeft + guiOffset;
            int top = topPos + energyTop;
            energyInfoArea.render(guiGraphics, left, top);
        }
    }

    /**
     * Renders the progress arrow for progress crafting.
     *
     * @param graphics {@link GuiGraphics}
     * @param posX     X position of the arrow
     * @param posY     Y position of the arrow
     */
    public void renderArrow(GuiGraphics graphics, int posX, int posY) {
        if (menu.blockEntity instanceof Crafter<?>) {
            int progress = menu.getSyncedProgress();
            int maxProgress = 176;
            int arrowSize = 26;

            int scaledProgress = progress != 0 ? progress * arrowSize / maxProgress : 0;

            if (scaledProgress >= 0) {
                graphics.blit(progressArrow, posX + 79, posY + 35, 0, 0, scaledProgress, 17, 24, 17);
            }
        }
    }

    public void renderProgressBar(GuiGraphics graphics, int posX, int posY) {
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
     *
     * @return 15 offset if the image width is 203
     */
    public int modifiedWidth() {
        return imageWidth == 203 ? 15 : 0;
    }

    /**
     * Creates the energy info area
     */
    private void assignEnergyInfoArea() {
        this.energyInfoArea = new EnergyDisplayTooltipArea(energyLeft, energyTop, getEnergyStorage(), energyWidth, energyHeight);
    }

    private void assignProgressBarArea() {
        this.progressDisplayTooltipArea = new ProgressDisplayTooltipArea(138, 31, 3, 22);
    }

    /**
     * Renders the settings area tooltip
     *
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX      Mouse Position X
     * @param mouseY      Mouse Position Y
     * @param x           X position
     * @param y           Y position
     */
    private void renderOptionsAreaTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y, 0, 0, 12, 176)) {
            guiGraphics.renderTooltip(this.font, getOptionsTooltips(), Optional.empty(), mouseX - x + (guiOffset / 2), mouseY - y);
        }
    }

    private void renderProgressAreaTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        int left = leftPos + (139 - guiOffset / 2) + guiOffset;
        int top = topPos + 32;

        if (isMouseAboveArea(mouseX, mouseY, left, top, 0, 0, 5, 22)) {
            guiGraphics.renderTooltip(this.font, progressDisplayTooltipArea.getTooltips(), Optional.empty(), mouseX - x + (guiOffset / 2), mouseY - y);
        }
    }

    /**
     * Renders the energy area tooltip
     *
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX      Mouse Position X
     * @param mouseY      Mouse Position Y
     * @param x           X position
     * @param y           Y position
     */
    private void renderEnergyAreaTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, energyLeft, energyTop, energyWidth, energyHeight)) {
            guiGraphics.renderTooltip(this.font, getEnergyTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private void renderUpgradeSlotTooltips(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if (!menu.isUpgradeable() || !menu.blockEntity.isSettingsPanelOpen()) {
            return;
        }

        // Find upgrade slots and render their tooltips
        for (var slot : menu.slots) {
            if (slot instanceof general.mechanics.gui.util.UpgradeSlot upgradeSlot && upgradeSlot.isActive()) {
                if (isMouseAboveArea(mouseX, mouseY, x, y, slot.x - 1, slot.y - 1, 18, 18)) {
                    graphics.renderTooltip(this.font, upgradeSlot.getTooltip(), Optional.empty(), mouseX - x, mouseY - y);
                    break; // Only show one tooltip at a time
                }
            }
        }
    }

    /**
     * Renders the fluid area tooltip
     *
     * @param guiGraphics Graphics renderer
     * @param pMouseX     mouse position x
     * @param pMouseY     mouse position y
     * @param x           position x
     * @param y           position y
     * @param stack       {@link FluidStack}
     * @param offsetX     offset x
     * @param offsetY     offset y
     * @param renderer    {@link FluidTankRenderer}
     */
    @SuppressWarnings("SameParameterValue")
    protected void renderFluidTooltipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer.getWidth(), renderer.getHeight())) {
            guiGraphics.renderTooltip(font, renderer.getTooltip(stack, TooltipFlag.NORMAL), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public boolean gmIsMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, width, height);
    }

    public <W extends net.minecraft.client.gui.components.Renderable & net.minecraft.client.gui.components.events.GuiEventListener & net.minecraft.client.gui.narration.NarratableEntry> W gmAddRenderableWidget(W widget) {
        return this.addRenderableWidget(widget);
    }

    public void gmRemoveWidget(net.minecraft.client.gui.components.events.GuiEventListener widget) {
        this.removeWidget(widget);
    }

    public BaseMenu<?, ?> gmGetMenu() {
        return this.menu;
    }

    public List<Component> getEnergyTooltips() {
        GuiPower power = (GuiPower) menu;
        DecimalFormat format = new DecimalFormat("#,###");
        return List.of(Component.literal(format.format(power.getPower()) + " / " + format.format(getEnergyStorage().getMaxEnergyStored()) + " FE"));
    }

    public List<Component> getOptionsTooltips() {
        return List.of(Component.translatable("gui.gm.settings.left"));
    }

    public CoreEnergyStorage getEnergyStorage() {
        if (menu.blockEntity instanceof PoweredBlock entity) {
            return entity.getEnergyStorage();
        }
        return null;
    }

    public boolean isPoweredMenu() {
        return menu instanceof GuiPower;
    }

    public boolean isFluidMenu() {
        return menu instanceof GuiFluid;
    }

    public boolean isConfigurable() {
        return true;
    }

    @SuppressWarnings("unused")
    public EnergyDisplayTooltipArea getEnergyInfoArea() {
        return energyInfoArea;
    }

    public ProgressDisplayTooltipArea getProgressBarArea() {
        return progressDisplayTooltipArea;
    }

    public boolean isSettingsPanelOpen() {
        return machineSettingsTab.isSettingsPanelOpen();
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public boolean showMachineStatus() {
        return true;
    }

    public Font getFont() {
        return font;
    }

}
