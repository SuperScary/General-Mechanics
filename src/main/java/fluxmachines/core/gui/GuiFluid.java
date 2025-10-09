package fluxmachines.core.gui;

import fluxmachines.core.gui.renderers.FluidTankRenderer;
import net.neoforged.neoforge.fluids.FluidStack;

public interface GuiFluid {

    FluidStack getFluidStack();

    FluidTankRenderer getFluidTankRenderer();

}
