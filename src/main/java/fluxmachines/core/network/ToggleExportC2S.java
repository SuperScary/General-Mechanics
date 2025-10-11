package fluxmachines.core.network;

import fluxmachines.core.FluxMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ToggleExportC2S(BlockPos pos, boolean setTo) implements CustomPacketPayload {

    public static final Type<ToggleExportC2S> TYPE = new Type<>(FluxMachines.getResource("toggle_export_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ToggleExportC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleExportC2S::pos,
                    ByteBufCodecs.BOOL, ToggleExportC2S::setTo,
                    ToggleExportC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type () {
        return TYPE;
    }

}
