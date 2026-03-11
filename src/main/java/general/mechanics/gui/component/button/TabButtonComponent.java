package general.mechanics.gui.component.button;

import general.mechanics.gui.component.TabComponent;
import general.mechanics.gui.util.IconButton;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public abstract class TabButtonComponent extends TabComponent<IconButton> {

    private final AtomicReference<Runnable> onPress;

    protected TabButtonComponent(int positionX, int positionY, @Nullable String tooltip, ResourceLocation icon) {
        this(positionX, positionY, tooltip, icon, new AtomicReference<>(() -> {
        }));
    }

    protected TabButtonComponent(int positionX, int positionY, @Nullable String tooltip, ResourceLocation icon, Runnable onPress) {
        this(positionX, positionY, tooltip, icon);
        setOnPress(onPress);
    }

    private TabButtonComponent(int positionX, int positionY, @Nullable String tooltip, ResourceLocation icon, AtomicReference<Runnable> onPressRef) {
        super(positionX, positionY, tooltip, new IconButton(positionX, positionY, BUTTON_WIDTH, BUTTON_HEIGHT, icon, 0, 0, 16, 16, 16, 16, 0, button -> onPressRef.get().run()));
        this.onPress = onPressRef;
    }

    protected final void setOnPress(Runnable onPress) {
        this.onPress.set(onPress);
    }

}
