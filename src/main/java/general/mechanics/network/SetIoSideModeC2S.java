package general.mechanics.network;

import general.mechanics.GM;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SetIoSideModeC2S(BlockPos pos, int ioType, int direction, int mode) implements CustomPacketPayload {

    public static final Type<SetIoSideModeC2S> TYPE = new Type<>(GM.getResource("set_io_side_mode_c2s"));

    public static final StreamCodec<FriendlyByteBuf, SetIoSideModeC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetIoSideModeC2S::pos,
                    ByteBufCodecs.INT, SetIoSideModeC2S::ioType,
                    ByteBufCodecs.INT, SetIoSideModeC2S::direction,
                    ByteBufCodecs.INT, SetIoSideModeC2S::mode,
                    SetIoSideModeC2S::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
