package fluxmachines.core.gui.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {

    private ResourceLocation texture;
    private int u, v, iconW, iconH, texW, texH;
    private int hoverVOffset;

    public IconButton(int x, int y, int width, int height,
                      ResourceLocation texture,
                      int u, int v, int iconW, int iconH,
                      int texW, int texH, int hoverVOffset,
                      OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.iconW = iconW;
        this.iconH = iconH;
        this.texW = texW;
        this.texH = texH;
        this.hoverVOffset = hoverVOffset;
    }

    public void setIcon(ResourceLocation newTexture, int newU, int newV,
                        int newIconW, int newIconH, int newTexW, int newTexH, int newHoverVOffset) {
        this.texture = newTexture;
        this.u = newU;
        this.v = newV;
        this.iconW = newIconW;
        this.iconH = newIconH;
        this.texW = newTexW;
        this.texH = newTexH;
        this.hoverVOffset = newHoverVOffset;
    }

    public void setIcon(ResourceLocation newTexture) {
        setIcon(newTexture, u, v, iconW, iconH, texW, texH, hoverVOffset);
    }

    @Override
    protected void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // Draw vanilla button background & hover/active state
        super.renderWidget(gfx, mouseX, mouseY, partialTick);

        // Center the icon inside the button
        int cx = getX() + (this.width - iconW) / 2;
        int cy = getY() + (this.height - iconH) / 2;

        int vv = this.isHoveredOrFocused() ? v + hoverVOffset : v; // use hover frame if provided
        gfx.blit(texture, cx, cy, u, vv, iconW, iconH, texW, texH);
    }

}
