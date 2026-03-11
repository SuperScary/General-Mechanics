package general.mechanics.gui.component.button;

import general.mechanics.GM;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.network.ToggleExportC2S;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public class AutoExportButton extends TabButtonComponent {

    private static final ResourceLocation EXPORT_ON = GM.getResource("textures/gui/elements/export_on.png");
    private static final ResourceLocation EXPORT_OFF = GM.getResource("textures/gui/elements/export_off.png");

    private final Supplier<BasePoweredBlockEntity> entity;

    public AutoExportButton(int positionX, int positionY, Supplier<BasePoweredBlockEntity> entity) {
        super(positionX, positionY, "gui.gm.auto_export", EXPORT_OFF);
        this.entity = entity;
        setOnPress(this::toggle);
        getBuilder().setTooltip(Tooltip.create(Component.translatable("gui.gm.auto_export")));
        refresh();
    }

    public void refresh() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        getBuilder().setIcon(poweredEntity.isExportEnabled() ? EXPORT_ON : EXPORT_OFF);
    }

    private void toggle() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        poweredEntity.toggleExport();
        PacketDistributor.sendToServer(new ToggleExportC2S(poweredEntity.getBlockPos(), poweredEntity.isExportEnabled()));
        refresh();
    }
}
