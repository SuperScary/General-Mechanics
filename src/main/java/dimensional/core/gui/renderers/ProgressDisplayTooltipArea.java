package dimensional.core.gui.renderers;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ProgressDisplayTooltipArea extends BarRenderer {

    private int progress = 0;

    public ProgressDisplayTooltipArea(int xMin, int yMin) {
        this(xMin, yMin, 3, 22);
    }

    public ProgressDisplayTooltipArea(int xMin, int yMin, int width, int height) {
        super(xMin, yMin, width, height);
    }

    public List<Component> getTooltips () {
        return List.of(Component.literal(progress * 100 / 22 + "%"));
    }

    @Override
    public void render(GuiGraphics guiGraphics) {
    }

    public void render (GuiGraphics guiGraphics, int amount) {
        render(guiGraphics, amount, 0, 0);
    }

    public void render (GuiGraphics guiGraphics, int amount, int x, int y) {
        this.progress = amount;
        guiGraphics.fillGradient(x, y + (getHeight() - amount), x + getWidth(), y + getHeight(), Color.GREEN.getArgb(), Color.BRIGHT_GREEN.getArgb());
    }

}
