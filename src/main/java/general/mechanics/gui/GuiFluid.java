package general.mechanics.gui;

import general.mechanics.gui.renderers.FluidTankRenderer;
import net.neoforged.neoforge.fluids.FluidStack;

public interface GuiFluid {

    FluidStack getFluidStack();

    FluidTankRenderer getFluidTankRenderer();

}
