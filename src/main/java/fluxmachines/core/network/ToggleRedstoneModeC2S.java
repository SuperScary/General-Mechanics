package fluxmachines.core.network;

import fluxmachines.core.FluxMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ToggleRedstoneModeC2S(BlockPos pos, int setTo) implements CustomPacketPayload {

    public static final Type<ToggleRedstoneModeC2S> TYPE = new Type<>(FluxMachines.getResource("toggle_redstone_mode_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ToggleRedstoneModeC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleRedstoneModeC2S::pos,
                    ByteBufCodecs.INT, ToggleRedstoneModeC2S::setTo,
                    ToggleRedstoneModeC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type () {
        return TYPE;
    }

}
