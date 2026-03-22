package general.mechanics.network;

import general.mechanics.GM;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SetSideModeC2S(BlockPos pos, int direction, int mode) implements CustomPacketPayload {

    public static final Type<SetSideModeC2S> TYPE = new Type<>(GM.getResource("set_side_mode_c2s"));

    public static final StreamCodec<FriendlyByteBuf, SetSideModeC2S> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetSideModeC2S::pos,
                    ByteBufCodecs.INT, SetSideModeC2S::direction,
                    ByteBufCodecs.INT, SetSideModeC2S::mode,
                    SetSideModeC2S::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
