package general.mechanics.api.component.io;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.EnumMap;

public final class SidedEnergyIOComponent {

    private final BasePoweredBlockEntity be;
    private final IEnergyStorage backing;

    private final EnumMap<Direction, IEnergyStorage> cached = new EnumMap<>(Direction.class);
    private IEnergyStorage cachedUnsidedAutomation;

    public SidedEnergyIOComponent(BasePoweredBlockEntity be, IEnergyStorage backing) {
        this.be = be;
        this.backing = backing;
    }

    public IEnergyStorage forSide(Direction side) {
        return cached.computeIfAbsent(side, s -> new SidedEnergyStorage(be, s, backing));
    }

    public IEnergyStorage forAutomationWithoutSide() {
        if (cachedUnsidedAutomation == null) {
            cachedUnsidedAutomation = new SidedEnergyStorage(be, null, backing);
        }
        return cachedUnsidedAutomation;
    }

    private static final class SidedEnergyStorage implements IEnergyStorage {

        private final BasePoweredBlockEntity be;
        private final Direction side;
        private final IEnergyStorage backing;

        private SidedEnergyStorage(BasePoweredBlockEntity be, Direction side, IEnergyStorage backing) {
            this.be = be;
            this.side = side;
            this.backing = backing;
        }

        private boolean canReceiveOnSide() {
            if (side == null) return false;
            return be.getSideMode(IoType.ENERGY, side) == BasePoweredBlockEntity.SideMode.INPUT;
        }

        private boolean canExtractOnSide() {
            if (side == null) return false;
            return be.getSideMode(IoType.ENERGY, side) == BasePoweredBlockEntity.SideMode.OUTPUT;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (!canReceiveOnSide()) return 0;
            return backing.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!canExtractOnSide()) return 0;
            return backing.extractEnergy(maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() {
            return backing.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return backing.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            if (!backing.canExtract()) return false;
            return canExtractOnSide();
        }

        @Override
        public boolean canReceive() {
            if (!backing.canReceive()) return false;
            return canReceiveOnSide();
        }
    }
}
