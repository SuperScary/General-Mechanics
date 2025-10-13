package general.mechanics.gui.renderers;

import general.mechanics.api.energy.CoreEnergyStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EnergyDisplayTooltipArea extends BarRenderer {
    private final CoreEnergyStorage energy;

    public EnergyDisplayTooltipArea(int xMin, int yMin, CoreEnergyStorage energy) {
        this(xMin, yMin, energy, 8, 64);
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, CoreEnergyStorage energy, int width, int height) {
        super(xMin, yMin, width, height);
        this.energy = energy;
    }

    public List<Component> getTooltips () {
        return List.of(Component.literal(energy.getEnergyStored() + " / " + energy.getMaxEnergyStored() + " FE"));
    }

    @Override
    public void render (GuiGraphics guiGraphics) {
        int stored = (int) (getHeight() * (energy.getEnergyStored() / (float) energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(getXPos(), getYPos() + (getHeight() - stored), getXPos() + getWidth(), getYPos() + getHeight(), Color.BRIGHT_RED.getArgb(), Color.RED.getArgb());
    }

    public void render (GuiGraphics guiGraphics, int x, int y) {
        int stored = (int) (getHeight() * (energy.getEnergyStored() / (float) energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(x, y + (getHeight() - stored), x + getWidth(), y + getHeight(), Color.BRIGHT_RED.getArgb(), Color.RED.getArgb());
    }

}
