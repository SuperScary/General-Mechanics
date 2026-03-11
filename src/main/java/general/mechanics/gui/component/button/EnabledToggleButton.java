package general.mechanics.gui.component.button;

import general.mechanics.GM;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.gui.component.TabComponent;
import general.mechanics.gui.util.IconButton;
import general.mechanics.network.ToggleEnabledC2S;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class EnabledToggleButton extends TabComponent<IconButton> {

    private static final ResourceLocation ENABLED_ON = GM.getResource("textures/gui/elements/enabled.png");
    private static final ResourceLocation DISABLED_ON = GM.getResource("textures/gui/elements/disabled.png");

    private final Supplier<BasePoweredBlockEntity> entity;

    public EnabledToggleButton(int positionX, int positionY, Supplier<BasePoweredBlockEntity> entity) {
        this(positionX, positionY, entity, new AtomicReference<>(() -> {
        }));
        this.onPress.set(this::toggle);
        refresh();
    }

    private final AtomicReference<Runnable> onPress;

    private EnabledToggleButton(int positionX, int positionY, Supplier<BasePoweredBlockEntity> entity, AtomicReference<Runnable> onPressRef) {
        super(positionX, positionY, "gui.gm.enabled", new IconButton(positionX, positionY, BUTTON_WIDTH, BUTTON_HEIGHT, ENABLED_ON, 0, 0, 16, 16, 16, 16, 0, 0.9f, button -> onPressRef.get().run()));
        this.entity = entity;
        this.onPress = onPressRef;
    }

    public void refresh() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        getBuilder().setIcon(poweredEntity.isEnabled() ? ENABLED_ON : DISABLED_ON);
        getBuilder().setTooltip(poweredEntity.isEnabled()
                ? Tooltip.create(Component.translatable("gui.gm.enabled"))
                : Tooltip.create(Component.translatable("gui.gm.disabled")));
    }

    private void toggle() {
        BasePoweredBlockEntity poweredEntity = entity.get();
        if (poweredEntity == null) {
            return;
        }

        poweredEntity.toggleEnabled();
        PacketDistributor.sendToServer(new ToggleEnabledC2S(poweredEntity.getBlockPos(), poweredEntity.isEnabled()));
        refresh();
    }
}
