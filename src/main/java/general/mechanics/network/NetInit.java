package general.mechanics.network;

import general.mechanics.GM;
import general.mechanics.api.component.io.IoType;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = GM.MODID)
public class NetInit {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var reg = event.registrar("1");
        reg.playToServer(ToggleEnabledC2S.TYPE, ToggleEnabledC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                be.setEnabled(payload.setTo());
            });
        });

        reg.playToServer(ToggleImportC2S.TYPE, ToggleImportC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                be.setImportEnabled(payload.setTo());
            });
        });

        reg.playToServer(ToggleExportC2S.TYPE, ToggleExportC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                be.setExportEnabled(payload.setTo());
            });
        });

        reg.playToServer(ToggleRedstoneModeC2S.TYPE, ToggleRedstoneModeC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                be.setRedstoneMode(payload.setTo());
            });
        });

        reg.playToServer(SetSideModeC2S.TYPE, SetSideModeC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                var dir = net.minecraft.core.Direction.from3DDataValue(payload.direction());
                var mode = BasePoweredBlockEntity.SideMode.fromId(payload.mode());
                be.setSideMode(dir, mode);
            });
        });

        reg.playToServer(SetIoSideModeC2S.TYPE, SetIoSideModeC2S.CODEC, (payload, ctx) -> {
            ServerPlayer sp = (ServerPlayer) ctx.player();

            sp.server.execute(() -> {
                ServerLevel level = sp.serverLevel();
                BlockPos pos = payload.pos();

                if (!(level.getBlockEntity(pos) instanceof BasePoweredBlockEntity be)) return;

                var type = IoType.fromId(payload.ioType());
                var dir = net.minecraft.core.Direction.from3DDataValue(payload.direction());
                var mode = BasePoweredBlockEntity.SideMode.fromId(payload.mode());
                be.setSideMode(type, dir, mode);
            });
        });
    }

}
