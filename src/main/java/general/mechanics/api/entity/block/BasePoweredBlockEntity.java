package general.mechanics.api.entity.block;

import general.mechanics.api.attributes.Attribute;
import general.mechanics.api.energy.CoreEnergyStorage;
import general.mechanics.api.energy.PoweredBlock;
import general.mechanics.api.upgrade.Upgradeable;
import general.mechanics.api.util.ContentDropper;
import general.mechanics.api.util.data.Keys;
import general.mechanics.api.util.data.PropertyComponent;
import general.mechanics.registries.CoreComponents;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class BasePoweredBlockEntity extends BaseBlockEntity implements PoweredBlock, Upgradeable {

    private final CoreEnergyStorage energyStorage;
    private final ItemStackHandler upgradeInventory = new ItemStackHandler(4);

    @Getter
    private boolean enabled;
    private boolean autoImport;
    private boolean autoExport;
    @Getter
    private RedstoneMode redstoneMode;

    public BasePoweredBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive) {
        this(type, pos, blockState, capacity, maxReceive, Attribute.Builder.of(Keys.POWER, 0));
    }

    public BasePoweredBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive, Attribute.IntValue current) {
        super(type, pos, blockState);
        this.energyStorage = new CoreEnergyStorage(capacity.get(), maxReceive.get(), maxReceive.get(), current.getAsInt());
        this.enabled = true;
        this.autoExport = false;
        this.autoImport = false;
        this.redstoneMode = RedstoneMode.IGNORED;
    }

    @Override
    public void saveClientData(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveClientData(tag, registries);
        tag.put(Keys.POWER, energyStorage.serializeNBT(registries));
        tag.put(Keys.INVENTORY_UPGRADE, upgradeInventory.serializeNBT(registries));
        tag.putBoolean(Keys.ENABLED, enabled);
        tag.putBoolean(Keys.AUTO_EXPORT, autoExport);
        tag.putBoolean(Keys.AUTO_IMPORT, autoImport);
        tag.putInt(Keys.REDSTONE_MODE, redstoneMode.id());
    }

    @Override
    public void loadClientData(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        energyStorage.deserializeNBT(registries, IntTag.valueOf(tag.getInt(Keys.POWER)));
        if (tag.contains(Keys.INVENTORY_UPGRADE)) {
            upgradeInventory.deserializeNBT(registries, tag.getCompound(Keys.INVENTORY_UPGRADE));
        }
        if (tag.contains(Keys.ENABLED)) enabled = tag.getBoolean(Keys.ENABLED);
        if (tag.contains(Keys.AUTO_IMPORT)) autoImport = tag.getBoolean(Keys.AUTO_IMPORT);
        if (tag.contains(Keys.AUTO_EXPORT)) autoExport = tag.getBoolean(Keys.AUTO_EXPORT);
        if (tag.contains(Keys.REDSTONE_MODE)) redstoneMode = RedstoneMode.fromId(tag.getInt(Keys.REDSTONE_MODE));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean(Keys.ENABLED, enabled);
        tag.putBoolean(Keys.AUTO_EXPORT, autoExport);
        tag.putBoolean(Keys.AUTO_IMPORT, autoImport);
        tag.putInt(Keys.REDSTONE_MODE, redstoneMode.id());
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        enabled = tag.getBoolean(Keys.ENABLED);
        autoExport = tag.getBoolean(Keys.AUTO_EXPORT);
        autoImport = tag.getBoolean(Keys.AUTO_IMPORT);
        redstoneMode = RedstoneMode.fromId(tag.getInt(Keys.REDSTONE_MODE));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        var tag = super.getUpdateTag(registries);
        tag.putBoolean(Keys.ENABLED, enabled);
        tag.putBoolean(Keys.AUTO_IMPORT, autoImport);
        tag.putBoolean(Keys.AUTO_EXPORT, autoExport);
        tag.putInt(Keys.REDSTONE_MODE, redstoneMode.id());
        return tag;
    }

    @Override
    public CoreEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public void updateBlockState(BlockState state) {
        assert level != null;
        level.setBlockAndUpdate(getBlockPos(), state);
    }

    @Override
    public void drops(ItemStackHandler inventory) {
        var container = new SimpleContainer(upgradeInventory.getSlots());
        for (int i = 0; i < upgradeInventory.getSlots(); i++) {
            if (upgradeInventory.getStackInSlot(i).is(Items.AIR)) {
                container.setItem(i, ItemStack.EMPTY);
            } else {
                container.setItem(i, inventory.getStackInSlot(i));
            }
        }
        assert level != null;
        ContentDropper.spawnDrops(level, worldPosition, container);
        super.drops(inventory);
    }

    @Override
    public InteractionResult disassemble(Player player, Level level, BlockHitResult hitResult, ItemStack stack, @Nullable ItemStack existingData) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = (BaseEntityBlock<?>) state.getBlock();
        var itemstack = getEither(existingData, new ItemStack(block));
        if (level instanceof ServerLevel) {
            itemstack.set(CoreComponents.ENERGY_STORED, getEnergyStorage().getEnergyStored());
            itemstack.set(CoreComponents.ENERGY_MAX, getEnergyStorage().getMaxEnergyStored());
        }
        return super.disassemble(player, level, hitResult, stack, itemstack);
    }

    @Override
    public void setData(ItemStack stack) {
        if (stack.has(CoreComponents.ENERGY_STORED) && stack.has(CoreComponents.ENERGY_MAX)) {
            var stored = stack.get(CoreComponents.ENERGY_STORED);
            var max = stack.get(CoreComponents.ENERGY_MAX);
            getEnergyStorage().setStored(stored);
            getEnergyStorage().setMaxStorage(max);
        }

        super.setData(stack);
    }

    @Override
    public ItemStackHandler getUpgradeInventory() {
        return upgradeInventory;
    }

    /**
     * Determines the current machine state based on various conditions.
     * This method should be overridden by subclasses for specific state logic.
     *
     * @return the current machine state
     */
    public MachineState getMachineState() {
        if (!enabled) {
            return MachineState.INACTIVE;
        }
        if (getEnergyStorage().getEnergyStored() <= 0) {
            return MachineState.ERROR;
        }
        return MachineState.INACTIVE;
    }

    /**
     * Sets the enabled state of the machine
     *
     * @param enabled true to enable, false to disable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        setChanged();

        if (getLevel() instanceof ServerLevel sl) {
            sl.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    /**
     * Toggles the enabled state of the machine
     */
    public void toggleEnabled() {
        setEnabled(!enabled);
    }

    public boolean isExportEnabled() {
        return autoExport;
    }

    public void setExportEnabled(boolean bool) {
        this.autoExport = bool;
        setChanged();

        if (getLevel() instanceof ServerLevel sl) {
            sl.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void toggleExport() {
        setExportEnabled(!autoExport);
    }

    public boolean isImportEnabled() {
        return autoImport;
    }

    public void setImportEnabled(boolean bool) {
        this.autoImport = bool;

        if (getLevel() instanceof ServerLevel sl) {
            sl.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void toggleImport() {
        setImportEnabled(!autoImport);
    }

    public void setRedstoneMode(int i) {
        this.redstoneMode = RedstoneMode.fromId(i);
    }

    public boolean redstoneAllowsRunning() {
        if (level == null) return false;
        boolean powered = level.hasNeighborSignal(worldPosition); // true if any side gives power
        return switch (redstoneMode) {
            case IGNORED -> true;
            case HIGH -> powered;
            case LOW -> !powered;
        };
    }

    public enum MachineState {
        ACTIVE,
        INACTIVE,
        ERROR
    }

    public enum RedstoneMode {
        HIGH(0),
        LOW(1),
        IGNORED(2);

        private final int id;

        RedstoneMode(int id) {
            this.id = id;
        }

        public int id() {
            return id;
        }

        // Fast lookup table
        private static final RedstoneMode[] BY_ID;

        static {
            var vals = values();
            int max = 0;
            for (var v : vals) max = Math.max(max, v.id);
            BY_ID = new RedstoneMode[max + 1];
            for (var v : vals) BY_ID[v.id] = v;
        }

        public static RedstoneMode fromId(int id) {
            return (id >= 0 && id < BY_ID.length && BY_ID[id] != null) ? BY_ID[id] : IGNORED;
        }

        public RedstoneMode next() {
            return fromId((id + 1) % BY_ID.length);
        }

        public RedstoneMode prev() {
            return fromId((id - 1 + BY_ID.length) % BY_ID.length);
        }
    }

}
