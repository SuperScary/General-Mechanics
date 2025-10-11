package fluxmachines.core.network;

import fluxmachines.core.FluxMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ToggleImportC2S(BlockPos pos, boolean setTo) implements CustomPacketPayload {

    public static final Type<ToggleImportC2S> TYPE = new Type<>(FluxMachines.getResource("toggle_import_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ToggleImportC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleImportC2S::pos,
                    ByteBufCodecs.BOOL, ToggleImportC2S::setTo,
                    ToggleImportC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type () {
        return TYPE;
    }

}
