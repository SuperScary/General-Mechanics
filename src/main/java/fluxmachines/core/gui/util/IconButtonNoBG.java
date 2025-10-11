package fluxmachines.core.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class IconButtonNoBG extends Button {

    private ResourceLocation texture;
    private int u, v, iconW, iconH, texW, texH;
    private int hoverVOffset;
    private float scale = 1f;

    public IconButtonNoBG(int x, int y, int width, int height, ResourceLocation texture, int u, int v, int iconW, int iconH, int texW, int texH, int hoverVOffset, OnPress onPress) {
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

    public IconButtonNoBG(int x, int y, int width, int height, ResourceLocation texture, int u, int v, int iconW, int iconH, int texW, int texH, int hoverVOffset, float scale, OnPress onPress) {
        this(x, y, width, height, texture, u, v, iconW, iconH, texW, texH, hoverVOffset, onPress);
        this.scale = scale;
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
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float pt) {
        Minecraft minecraft = Minecraft.getInstance();
        graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        //graphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.getFGColor();
        this.renderString(graphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);

        float drawW = iconW * scale;
        float drawH = iconH * scale;

        float cx = getX() + (this.width  - drawW) / 2f;
        float cy = getY() + (this.height - drawH) / 2f;

        var pose = graphics.pose();
        pose.pushPose();
        pose.translate(cx, cy, 0);   // top-left of scaled icon
        pose.scale(scale, scale, 1f); // scale around that top-left

        int vv = this.isHoveredOrFocused() ? v + hoverVOffset : v;
        if (texture != null) graphics.blit(texture, 0, 0, u, vv, iconW, iconH, texW, texH);
        pose.popPose();
    }

}
