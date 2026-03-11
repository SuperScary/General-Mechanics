package general.mechanics.gui.component;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public abstract class TabComponent<T> {

    public static final int BUTTON_WIDTH = 20;
    public static final int BUTTON_HEIGHT = 20;

    @Getter
    private final int positionX;

    @Getter
    private final int positionY;

    @Getter
    @Nullable
    private final String tooltip;

    @Getter
    private final T builder;

    public TabComponent(int positionX, int positionY, @Nullable String tooltip, T builder) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.tooltip = tooltip;
        this.builder = builder;
    }

}
