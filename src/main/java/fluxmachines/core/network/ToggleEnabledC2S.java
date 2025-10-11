package fluxmachines.core.network;

import fluxmachines.core.FluxMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ToggleEnabledC2S(BlockPos pos, boolean setTo) implements CustomPacketPayload {

    public static final Type<ToggleEnabledC2S> TYPE = new Type<>(FluxMachines.getResource("toggle_enabled_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ToggleEnabledC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleEnabledC2S::pos,
                    ByteBufCodecs.BOOL, ToggleEnabledC2S::setTo,
                    ToggleEnabledC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type () {
        return TYPE;
    }

}
