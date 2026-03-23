package general.mechanics.api.component.io;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.EnumMap;

public final class SidedFluidIOComponent {

    private final BasePoweredBlockEntity be;
    private final IFluidHandler backing;

    private final EnumMap<Direction, IFluidHandler> cached = new EnumMap<>(Direction.class);
    private IFluidHandler cachedUnsidedAutomation;

    public SidedFluidIOComponent(BasePoweredBlockEntity be, IFluidHandler backing) {
        this.be = be;
        this.backing = backing;
    }

    public IFluidHandler forSide(Direction side) {
        return cached.computeIfAbsent(side, s -> new SidedFluidHandler(be, s, backing));
    }

    public IFluidHandler forAutomationWithoutSide() {
        if (cachedUnsidedAutomation == null) {
            cachedUnsidedAutomation = new SidedFluidHandler(be, null, backing);
        }
        return cachedUnsidedAutomation;
    }

    private static final class SidedFluidHandler implements IFluidHandler {

        private final BasePoweredBlockEntity be;
        private final Direction side;
        private final IFluidHandler backing;

        private SidedFluidHandler(BasePoweredBlockEntity be, Direction side, IFluidHandler backing) {
            this.be = be;
            this.side = side;
            this.backing = backing;
        }

        private boolean canFillOnSide() {
            if (side == null) return false;
            return be.getSideMode(IoType.FLUIDS, side) == BasePoweredBlockEntity.SideMode.INPUT;
        }

        private boolean canDrainOnSide() {
            if (side == null) return false;
            return be.getSideMode(IoType.FLUIDS, side) == BasePoweredBlockEntity.SideMode.OUTPUT;
        }

        @Override
        public int getTanks() {
            return backing.getTanks();
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return backing.getFluidInTank(tank);
        }

        @Override
        public int getTankCapacity(int tank) {
            return backing.getTankCapacity(tank);
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            if (!canFillOnSide()) return false;
            return backing.isFluidValid(tank, stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!canFillOnSide()) return 0;
            return backing.fill(resource, action);
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (!canDrainOnSide()) return FluidStack.EMPTY;
            return backing.drain(resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (!canDrainOnSide()) return FluidStack.EMPTY;
            return backing.drain(maxDrain, action);
        }
    }
}
