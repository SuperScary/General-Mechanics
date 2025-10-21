package general.mechanics.api.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class BaseFluid extends FluidType implements IClientFluidExtensions {

    @Getter
    private final ResourceLocation stillTexture;

    @Getter
    private final ResourceLocation flowingTexture;

    @Getter
    private final ResourceLocation overlayTexture;

    @Getter
    private final int tintColor;

    @Getter
    private final Vector3f fogColor;

    @Getter
    @Setter
    private boolean isAcidic;

    @Getter
    @Setter
    private boolean isBasic;

    @Getter
    @Setter
    private long temp; // kelvin

    public BaseFluid(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, boolean isAcidic, boolean isBasic, long temp) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
        this.isAcidic = isAcidic;
        this.isBasic = isBasic;
        this.temp = temp;
    }

    @Override
    public IClientFluidTypeExtensions getFluidTypeExtensions() {
        var still = stillTexture;
        var flowing = flowingTexture;
        var overlay = overlayTexture;
        var tint = tintColor;
        var fog = new Vector3f(fogColor);

        return new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return flowing;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return overlay;
            }

            @Override
            public int getTintColor() {
                return tint;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(@NotNull Camera cam, float pt, @NotNull ClientLevel lvl, int renderDistance, float darken, @NotNull Vector3f original) {
                return fog;
            }

            @Override
            public void modifyFogRender(@NotNull Camera camera, FogRenderer.@NotNull FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, @NotNull FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }

        };
    }
}
