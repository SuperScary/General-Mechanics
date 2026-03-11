package general.mechanics.gui.component.button;

import general.mechanics.GM;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.network.ToggleRedstoneModeC2S;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public class RedstoneButton extends TabButtonComponent {

    private static final ResourceLocation REDSTONE_HIGH = GM.getMinecraftResource("textures/item/redstone.png");
    private static final ResourceLocation REDSTONE_LOW = GM.getResource("textures/gui/elements/redstone_low.png");
    private static final ResourceLocation REDSTONE_DISABLED = GM.getMinecraftResource("textures/item/gunpowder.png");

    private final Supplier<BasePoweredBlockEntity> entity;

    public RedstoneButton(int positionX, int positionY, Supplier<BasePoweredBlockEntity> entity) {
        super(positionX, positionY, "gui.gm.redstone_mode", REDSTONE_HIGH);
        this.entity = entity;
        setOnPress(this::toggleRedstoneMode);
        refresh();
    }

    public void refresh() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        getBuilder().setIcon(switch (poweredEntity.getRedstoneMode()) {
            case LOW -> REDSTONE_LOW;
            case HIGH -> REDSTONE_HIGH;
            case IGNORED -> REDSTONE_DISABLED;
        });

        getBuilder().setTooltip(Tooltip.create(Component.translatable("gui.gm.redstone_mode", Component.translatable("gui.gm.redstone_mode." +
                switch (poweredEntity.getRedstoneMode()) {
                    case LOW -> BasePoweredBlockEntity.RedstoneMode.LOW.name().toLowerCase();
                    case HIGH -> BasePoweredBlockEntity.RedstoneMode.HIGH.name().toLowerCase();
                    case IGNORED -> BasePoweredBlockEntity.RedstoneMode.IGNORED.name().toLowerCase();
                }
        ))));
    }

    private void toggleRedstoneMode() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        poweredEntity.setRedstoneMode(poweredEntity.getRedstoneMode().next().id());
        PacketDistributor.sendToServer(new ToggleRedstoneModeC2S(poweredEntity.getBlockPos(), poweredEntity.getRedstoneMode().id()));
        refresh();
    }

}
