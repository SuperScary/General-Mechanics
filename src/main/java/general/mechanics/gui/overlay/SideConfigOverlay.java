package general.mechanics.gui.overlay;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.math.Axis;
import general.mechanics.GM;
import general.mechanics.gui.screen.base.BaseScreen;
import general.mechanics.gui.util.IconButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.glfw.GLFW;

public class SideConfigOverlay extends AbstractWidget {

    private final BaseScreen<?> parent;
    private final BlockPos bePos;
    private boolean visible = false;

    // layout
    private int panelX, panelY, panelW = 176, panelH = 140;

    private final ResourceLocation background = GM.getResource("textures/gui/config_menu.png");
    private final ResourceLocation close = GM.getResource("textures/gui/elements/close_button.png");

    private IconButton closeButton = null;

    public SideConfigOverlay(BaseScreen<?> parent, BlockPos pos) {
        super(0, 0, 0, 0, Component.empty());
        this.parent = parent;
        this.bePos = pos;
        recalcLayout();
    }

    public void setVisible(boolean v) { visible = v; }
    public boolean isVisible() { return visible; }

    private void recalcLayout() {
        panelX = parent.getGuiLeft(); // + parent.getImageWidth() + 6; // right of vanilla bg
        panelY = parent.getGuiTop();
        setX(panelX); setY(panelY); setWidth(panelW); setHeight(panelH);
    }

    @Override
    protected void renderWidget(GuiGraphics g, int mouseX, int mouseY, float pt) {
    }

    private void closeScreen() {
        this.setVisible(false);
    }

    public void renderOnTop(GuiGraphics g, int mouseX, int mouseY, float pt) {
        if (!visible) return;

        var pose = g.pose();
        pose.pushPose();
        // Dim entire screen
        g.fill(RenderType.guiOverlay(), 0, 0, parent.width, parent.height, 0x88000000);

        pose.translate(0, 0, 10000);
        g.blit(background, panelX, panelY, 0, 0, panelW, panelH);
        renderNeighborhood(g);

        int rowY = panelY + panelH - 12 - 6;
        g.drawString(parent.getFont(), "[ESC] close", panelX + 6, rowY, 0xAAAAAA, false);
        pose.popPose();
    }

    /** Render center block and its 6 neighbors as 3D models within the GUI */
    private void renderNeighborhood(GuiGraphics graphics) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        BlockState center = level.getBlockState(bePos);
        BlockRenderDispatcher brd = mc.getBlockRenderer();

        // a small isometric cluster area
        int cx = panelX + panelW / 2;
        int cy = panelY + 72;
        int scale = 22;

        // lighting for 3D-ish block rendering
        Lighting.setupFor3DItems();

        // Render order: neighbors (back) then center (front)
        renderBlockAt(graphics, brd, level, bePos.north(), cx, cy - 36, scale);
        renderBlockAt(graphics, brd, level, bePos.south(), cx, cy + 36, scale);
        renderBlockAt(graphics, brd, level, bePos.west(),  cx - 36, cy, scale);
        renderBlockAt(graphics, brd, level, bePos.east(),  cx + 36, cy, scale);
        renderBlockAt(graphics, brd, level, bePos.above(), cx - 24, cy - 24, scale - 2);
        renderBlockAt(graphics, brd, level, bePos.below(), cx + 24, cy + 24, scale - 2);

        // Center block last (on top)
        renderBlockAt(graphics, brd, level, bePos, cx, cy, scale + 2);
    }

    /** Renders a single BlockState into GUI using the block model */
    private void renderBlockAt(GuiGraphics g, BlockRenderDispatcher brd, Level level, BlockPos pos, int x, int y, int px) {
        BlockState state = level.getBlockState(pos);

        var pose = g.pose();
        pose.pushPose();
        pose.translate(x, y, 300);   // Z a bit high to be over panel
        pose.scale(px, px, px);      // scale to pixels
        pose.translate(-0.5f, -0.75f, 0f); // center the cube; tweak Y for nicer layout
        pose.mulPose(Axis.XP.rotationDegrees(30f));
        pose.mulPose(Axis.YP.rotationDegrees(45f));

        // Render the model
        // Note: gui buffer source is a bit special in 1.21; use GuiGraphics buffer:
        brd.renderSingleBlock(state, pose, g.bufferSource(), 0x00F000F0, OverlayTexture.NO_OVERLAY);

        // flush the one-off block
        g.flush();

        pose.popPose();
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (!visible) return false;

        // If click inside our panel area, consume; else also consume to keep modal
        if (x >= panelX && x <= panelX + panelW && y >= panelY && y <= panelY + panelH) {
            // Map clicks to faces (example: simple face buttons)
            Direction clicked = hitFace((int)x, (int)y);
            if (clicked != null) {
                cycleSide(clicked);
            }
            return true;
        }
        // Click outside closes the overlay (or keep modal)
        this.visible = false;
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public boolean keyPressed(int key, int scancode, int mods) {
        if (!visible) return false;
        if (key == GLFW.GLFW_KEY_ESCAPE) { visible = false; return true; }
        return true; // modal: eat keys
    }

    private Direction hitFace(int mx, int my) {
        // Implement small clickable squares around center corresponding to UP/DOWN/N/E/S/W
        // Return Direction or null
        return null;
    }

    private void cycleSide(Direction dir) {
        // read current mode for this side (from menu data or cached BE sync),
        // compute next, send C→S update, optionally play a click sound
        // PacketDistributor.sendToServer(new SetSideModeC2S(bePos, dir, nextMode.id()));
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

}
