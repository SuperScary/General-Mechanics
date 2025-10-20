package general.mechanics.api.fluid;

import lombok.Getter;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
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

    public BaseFluid(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
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
                return new Vector3f(fog);
            }
        };
    }
}
