package general.mechanics.network;

import general.mechanics.GM;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ToggleExportC2S(BlockPos pos, boolean setTo) implements CustomPacketPayload {

    public static final Type<ToggleExportC2S> TYPE = new Type<>(GM.getResource("toggle_export_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ToggleExportC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleExportC2S::pos,
                    ByteBufCodecs.BOOL, ToggleExportC2S::setTo,
                    ToggleExportC2S::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type () {
        return TYPE;
    }

}
