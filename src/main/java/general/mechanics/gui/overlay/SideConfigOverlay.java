package general.mechanics.gui.overlay;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.api.gui.MachineUiState;
import general.mechanics.gui.component.button.CloseTabButton;
import general.mechanics.gui.component.button.SideModeFaceButtonComponent;
import general.mechanics.gui.component.screen.TerminalWidget;
import general.mechanics.gui.screen.base.BaseScreen;
import general.mechanics.network.SetSideModeC2S;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class SideConfigOverlay extends AbstractWidget {

    private final BaseScreen<?> parent;
    private final BlockPos bePos;
    private final Supplier<MachineUiState> state;

    @Getter
    private boolean visible = false;

    @Getter
    private final TerminalWidget terminal;

    private final CloseTabButton closeButton;

    private final SideModeFaceButtonComponent upButton;
    private final SideModeFaceButtonComponent leftButton;
    private final SideModeFaceButtonComponent frontButton;
    private final SideModeFaceButtonComponent rightButton;
    private final SideModeFaceButtonComponent downButton;
    private final SideModeFaceButtonComponent backButton;

    public SideConfigOverlay(BaseScreen<?> parent, Supplier<MachineUiState> state, BlockPos pos) {
        super(0, 0, 0, 0, Component.empty());
        this.parent = parent;
        this.state = state;
        this.bePos = pos;

        this.terminal = new TerminalWidget(0, 0, TerminalWidget.Preset.TERMINAL_64x64);
        this.closeButton = new CloseTabButton(0, 0, this::closeScreen);

        this.upButton = new SideModeFaceButtonComponent(0, 0, Direction.UP, () -> getMode(Direction.UP), () -> cycleSide(Direction.UP));
        this.leftButton = new SideModeFaceButtonComponent(0, 0, Direction.WEST, () -> getMode(getLocalLeft()), () -> cycleSide(getLocalLeft()));
        this.frontButton = new SideModeFaceButtonComponent(0, 0, Direction.NORTH, () -> getMode(getLocalFront()), () -> cycleSide(getLocalFront()));
        this.rightButton = new SideModeFaceButtonComponent(0, 0, Direction.EAST, () -> getMode(getLocalRight()), () -> cycleSide(getLocalRight()));
        this.downButton = new SideModeFaceButtonComponent(0, 0, Direction.DOWN, () -> getMode(Direction.DOWN), () -> cycleSide(Direction.DOWN));
        this.backButton = new SideModeFaceButtonComponent(0, 0, Direction.SOUTH, () -> getMode(getLocalBack()), () -> cycleSide(getLocalBack()));

        terminal.addComponent(closeButton, 0, 0, TerminalWidget.ClampMode.OUTER);
        terminal.addComponent(upButton, 0, 0, TerminalWidget.ClampMode.INNER);
        terminal.addComponent(leftButton, 0, 0, TerminalWidget.ClampMode.INNER);
        terminal.addComponent(frontButton, 0, 0, TerminalWidget.ClampMode.INNER);
        terminal.addComponent(rightButton, 0, 0, TerminalWidget.ClampMode.INNER);
        terminal.addComponent(downButton, 0, 0, TerminalWidget.ClampMode.INNER);
        terminal.addComponent(backButton, 0, 0, TerminalWidget.ClampMode.INNER);

        anchorToParent();
        layoutChildren();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        terminal.setBlocksSlotRenderingFlag(visible);
        if (visible) {
            anchorToParent();
            layoutChildren();
        }
    }

    private void anchorToParent() {
        terminal.setX(parent.getGuiLeft());
        terminal.setY(parent.getGuiTop());
    }

    private void syncBoundsToTerminal() {
        setX(terminal.getX());
        setY(terminal.getY());
        setWidth(terminal.getWidth());
        setHeight(terminal.getHeight());
    }

    private void layoutChildren() {
        syncBoundsToTerminal();
        int size = 16;
        int gap = 1;

        int cx = terminal.getInnerX() + terminal.getInnerWidth() / 2 - size / 2;
        int cy = terminal.getInnerY() + terminal.getInnerHeight() / 2 - size / 2;

        terminal.setComponentOffset(upButton, cx - terminal.getX(), (cy - (size + gap)) - terminal.getY());
        terminal.setComponentOffset(leftButton, (cx - (size + gap)) - terminal.getX(), cy - terminal.getY());
        terminal.setComponentOffset(frontButton, cx - terminal.getX(), cy - terminal.getY());
        terminal.setComponentOffset(rightButton, (cx + (size + gap)) - terminal.getX(), cy - terminal.getY());
        terminal.setComponentOffset(downButton, cx - terminal.getX(), (cy + (size + gap)) - terminal.getY());
        terminal.setComponentOffset(backButton, (cx + (size + gap)) - terminal.getX(), (cy + (size + gap)) - terminal.getY());

        int closeX = terminal.getX() + terminal.getWidth() - 12;
        int closeY = terminal.getY() + 4;
        terminal.setComponentOffset(closeButton, closeX - terminal.getX(), closeY - terminal.getY());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float pt) {
    }

    private void closeScreen() {
        this.setVisible(false);
    }

    private Direction getFacing() {
        var level = Minecraft.getInstance().level;
        if (level == null) return Direction.NORTH;

        var state = level.getBlockState(bePos);
        if (state.hasProperty(BlockStateProperties.FACING)) {
            var facing = state.getValue(BlockStateProperties.FACING);
            if (facing.getAxis().isHorizontal()) return facing;
        }
        return Direction.NORTH;
    }

    private Direction getLocalFront() {
        return getFacing();
    }

    private Direction getLocalBack() {
        return getFacing().getOpposite();
    }

    private Direction getLocalLeft() {
        return getFacing().getClockWise();
    }

    private Direction getLocalRight() {
        return getFacing().getCounterClockWise();
    }

    public void renderOnTop(GuiGraphics g, int mouseX, int mouseY, float pt) {
        if (!visible) return;

        var pose = g.pose();
        pose.pushPose();
        // Dim entire screen
        g.fill(0, 0, parent.width, parent.height, 0x88000000);
        pose.translate(0, 0, 200);

        RenderSystem.disableDepthTest();
        terminal.render(g, mouseX, mouseY, pt);
        RenderSystem.enableDepthTest();

        //int rowY = terminal.getY() + terminal.getHeight() - 12 - 6;
        //g.drawString(parent.getFont(), "[ESC] close", terminal.getX() + 6, rowY, 0xAAAAAA, false);
        pose.popPose();
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (!visible) return false;
        if (terminal.mouseClicked(x, y, button)) return true;
        if (terminal.isMouseOver(x, y)) return true;
        closeScreen();
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!visible) return false;
        if (terminal.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            layoutChildren();
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!visible) return false;
        terminal.mouseReleased(mouseX, mouseY, button);
        return true;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public boolean keyPressed(int key, int scancode, int mods) {
        if (!visible) return false;
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            closeScreen();
            return true;
        }
        return true; // modal: eat keys
    }

    private void cycleSide(Direction dir) {
        var next = getMode(dir).next();
        PacketDistributor.sendToServer(new SetSideModeC2S(bePos, dir.get3DDataValue(), next.id()));

        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    private BasePoweredBlockEntity.SideMode getMode(Direction dir) {
        var ui = state.get();
        if (ui == null) return BasePoweredBlockEntity.SideMode.IGNORED;
        return ui.sideMode(dir);
    }

}
