package general.mechanics.gui.component.button;

import general.mechanics.GM;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.network.ToggleImportC2S;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public class AutoImportButton extends TabButtonComponent {

    private static final ResourceLocation IMPORT_ON = GM.getResource("textures/gui/elements/import_on.png");
    private static final ResourceLocation IMPORT_OFF = GM.getResource("textures/gui/elements/import_off.png");

    private final Supplier<BasePoweredBlockEntity> entity;

    public AutoImportButton(int positionX, int positionY, Supplier<BasePoweredBlockEntity> entity) {
        super(positionX, positionY, "gui.gm.auto_import", IMPORT_OFF);
        this.entity = entity;
        setOnPress(this::toggle);
        getBuilder().setTooltip(Tooltip.create(Component.translatable("gui.gm.auto_import")));
        refresh();
    }

    public void refresh() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        getBuilder().setIcon(poweredEntity.isImportEnabled() ? IMPORT_ON : IMPORT_OFF);
    }

    private void toggle() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        poweredEntity.toggleImport();
        PacketDistributor.sendToServer(new ToggleImportC2S(poweredEntity.getBlockPos(), poweredEntity.isImportEnabled()));
        refresh();
    }
}
