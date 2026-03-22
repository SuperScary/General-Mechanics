package general.mechanics.api.component.io;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.api.util.Range;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.EnumMap;

public final class SidedItemIOComponent {

    public interface SlotRanges {
        Range getInputSlots();
        Range getOutputSlots();
    }

    private final BasePoweredBlockEntity be;
    private final IItemHandler inventory;
    private final SlotRanges ranges;

    private final EnumMap<Direction, IItemHandler> cached = new EnumMap<>(Direction.class);
    private IItemHandler cachedUnsidedAutomation;

    public SidedItemIOComponent(BasePoweredBlockEntity be, IItemHandler inventory, SlotRanges ranges) {
        this.be = be;
        this.inventory = inventory;
        this.ranges = ranges;
    }

    public IItemHandler forSide(Direction side) {
        return cached.computeIfAbsent(side, this::createHandler);
    }

    public IItemHandler forAutomationWithoutSide() {
        if (cachedUnsidedAutomation == null) {
            cachedUnsidedAutomation = new SidedHandler(be, null, inventory, new int[0], new int[0]);
        }
        return cachedUnsidedAutomation;
    }

    private IItemHandler createHandler(Direction side) {
        var input = ranges.getInputSlots();
        var output = ranges.getOutputSlots();
        int[] inputSlots = input != null ? input.toArray() : new int[0];
        int[] outputSlots = output != null ? output.toArray() : new int[0];
        return new SidedHandler(be, side, inventory, inputSlots, outputSlots);
    }

    private static boolean contains(int[] arr, int value) {
        for (int v : arr) if (v == value) return true;
        return false;
    }

    private static final class SidedHandler implements IItemHandler {

        private final BasePoweredBlockEntity be;
        private final Direction side;
        private final IItemHandler backing;
        private final int[] inputSlots;
        private final int[] outputSlots;

        private SidedHandler(BasePoweredBlockEntity be, Direction side, IItemHandler backing, int[] inputSlots, int[] outputSlots) {
            this.be = be;
            this.side = side;
            this.backing = backing;
            this.inputSlots = inputSlots;
            this.outputSlots = outputSlots;
        }

        private boolean canInsert(int slot) {
            if (side == null) return false;
            if (!be.isImportEnabled()) return false;
            if (be.getSideMode(side) != BasePoweredBlockEntity.SideMode.INPUT) return false;
            return contains(inputSlots, slot);
        }

        private boolean canExtract(int slot) {
            if (side == null) return false;
            if (!be.isExportEnabled()) return false;
            if (be.getSideMode(side) != BasePoweredBlockEntity.SideMode.OUTPUT) return false;
            return contains(outputSlots, slot);
        }

        @Override
        public int getSlots() {
            return backing.getSlots();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return backing.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (!canInsert(slot)) return stack;
            return backing.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!canExtract(slot)) return ItemStack.EMPTY;
            return backing.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return backing.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (!canInsert(slot)) return false;
            return backing.isItemValid(slot, stack);
        }
    }
}
