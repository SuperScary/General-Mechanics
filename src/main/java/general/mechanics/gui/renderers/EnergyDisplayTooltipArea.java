package general.mechanics.gui.renderers;

import general.mechanics.api.energy.CoreEnergyStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.IntSupplier;

public class EnergyDisplayTooltipArea extends BarRenderer {
    private final IntSupplier energyStored;
    private final IntSupplier energyCapacity;

    public EnergyDisplayTooltipArea(int xMin, int yMin, CoreEnergyStorage energy) {
        this(xMin, yMin, energy, 8, 64);
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, CoreEnergyStorage energy, int width, int height) {
        super(xMin, yMin, width, height);
        this.energyStored = energy::getEnergyStored;
        this.energyCapacity = energy::getMaxEnergyStored;
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, IntSupplier energyStored, IntSupplier energyCapacity) {
        this(xMin, yMin, energyStored, energyCapacity, 8, 64);
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, IntSupplier energyStored, IntSupplier energyCapacity, int width, int height) {
        super(xMin, yMin, width, height);
        this.energyStored = energyStored;
        this.energyCapacity = energyCapacity;
    }

    public List<Component> getTooltips () {
        int stored = energyStored.getAsInt();
        int cap = energyCapacity.getAsInt();
        return List.of(Component.literal(stored + " / " + cap + " FE"));
    }

    @Override
    public void render (GuiGraphics guiGraphics) {
        int cap = energyCapacity.getAsInt();
        if (cap <= 0) return;
        int storedPx = (int) (getHeight() * (energyStored.getAsInt() / (float) cap));
        guiGraphics.fillGradient(getXPos(), getYPos() + (getHeight() - storedPx), getXPos() + getWidth(), getYPos() + getHeight(), Color.BRIGHT_RED.getArgb(), Color.RED.getArgb());
    }

    public void render (GuiGraphics guiGraphics, int x, int y) {
        int cap = energyCapacity.getAsInt();
        if (cap <= 0) return;
        int storedPx = (int) (getHeight() * (energyStored.getAsInt() / (float) cap));
        guiGraphics.fillGradient(x, y + (getHeight() - storedPx), x + getWidth(), y + getHeight(), Color.BRIGHT_RED.getArgb(), Color.RED.getArgb());
    }

}
