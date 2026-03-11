package general.mechanics.gui.component.button;

import general.mechanics.gui.component.TabComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SideConfigButton extends TabComponent<Button> {

    public SideConfigButton(int positionX, int positionY, int width, int height, Runnable onPress) {
        super(positionX, positionY, "gui.gm.side_config", Button.builder(Component.translatable("gui.gm.side_config"), button -> onPress.run())
                .bounds(positionX, positionY, width, height)
                .build());
    }
}
