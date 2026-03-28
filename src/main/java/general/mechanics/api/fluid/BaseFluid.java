package general.mechanics.api.fluid;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.jspecify.annotations.NonNull;

public class BaseFluid extends FluidType implements IClientFluidExtensions {

    @Getter
    private final Identifier stillTexture;

    @Getter
    private final Identifier flowingTexture;

    @Getter
    private final Identifier overlayTexture;

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

    public BaseFluid(Properties properties, Identifier stillTexture, Identifier flowingTexture, Identifier overlayTexture, int tintColor, Vector3f fogColor, boolean isAcidic, boolean isBasic, long temp) {
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
    public int getTemperature() {
        return Math.toIntExact(this.temp);
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
            public @NotNull Identifier getStillTexture() {
                return still;
            }

            @Override
            public @NotNull Identifier getFlowingTexture() {
                return flowing;
            }

            @Override
            public Identifier getOverlayTexture() {
                return overlay;
            }

            @Override
            public int getTintColor() {
                return tint;
            }

            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, @NonNull Vector4f fluidFogColor) {

            }

            /*@Override
            public void modifyFogRender(@NonNull Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, @NonNull FogData fogData) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }*/

        };
    }
}
