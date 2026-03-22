package general.mechanics.gui.menu.base;

import general.mechanics.api.entity.Crafter;
import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.entity.block.BaseEntityBlock;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.api.energy.CoreEnergyStorage;
import general.mechanics.api.energy.PoweredBlock;
import general.mechanics.api.gui.IMachineMenuData;
import general.mechanics.api.gui.MachineUiState;
import general.mechanics.api.upgrade.Upgradeable;
import general.mechanics.gui.util.QuickMoveStack;
import general.mechanics.gui.util.UpgradeSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMenu<B extends BaseEntityBlock<?>, T extends BaseBlockEntity> extends AbstractContainerMenu implements IMachineMenuData {

    protected static final int DATA_PROGRESS = 0;
    protected static final int DATA_CRAFTING_STATE = 1;
    protected static final int DATA_ENABLED_STATE = 2;
    protected static final int DATA_EXPORT_STATE = 3;
    protected static final int DATA_IMPORT_STATE = 4;
    protected static final int DATA_REDSTONE_MODE = 5;
    protected static final int DATA_ENERGY_STORED = 6;
    protected static final int DATA_ENERGY_CAPACITY = 7;
    protected static final int DATA_SIDE_MODES = 8;

    protected static final int DATA_POWERED_ENABLED_STATE = 0;
    protected static final int DATA_POWERED_ENERGY_STORED = 1;
    protected static final int DATA_POWERED_ENERGY_CAPACITY = 2;
    protected static final int DATA_POWERED_SIDE_MODES = 3;

    public final B block;
    public final T blockEntity;
    private final Level level;
    public ContainerData data;

    private boolean settingsPanelOpen;

    public BaseMenu(MenuType<?> type, int containerId, Inventory inventory, B block, T blockEntity) {
        super(type, containerId);
        this.block = block;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.settingsPanelOpen = false;

        // Initialize container data for syncing progress if the block entity is a crafter
        initializeContainerData(inventory);

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addSlots();
        addUpgradeSlots();
    }

    @SuppressWarnings({"unchecked", "unused"})
    public BaseMenu(MenuType<?> type, int containerId, Inventory inventory, B block, FriendlyByteBuf extraData) {
        this(type, containerId, inventory, block, (T) inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    /**
     * Define the number of slots in the menu
     */
    public abstract int defineSlotCount ();

    /**
     * Add the slots to the menu
     */
    public abstract void addSlots ();

    public void addUpgradeSlots () {
        if (!isUpgradeable()) {
            return;
        }

        Upgradeable upgradeable = (Upgradeable) this.blockEntity;
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 0, 176 + 2, 7 + 10, this.blockEntity, this::isSettingsPanelOpen));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 1, 176 + 2, 7 + 28, this.blockEntity, this::isSettingsPanelOpen));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 2, 176 + 2, 7 + 46, this.blockEntity, this::isSettingsPanelOpen));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 3, 176 + 2, 7 + 64, this.blockEntity, this::isSettingsPanelOpen));
    }

    @Override
    public @NotNull ItemStack quickMoveStack (@NotNull Player player, int index) {
        return new QuickMoveStack(this, defineSlotCount(), player, index).quickMoveStack();
    }

    @Override
    public boolean stillValid (@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, block);
    }

    /**
     * Adds the player inventory.
     * @param playerInventory the players inventory.
     */
    public void addPlayerInventory (Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, (8 + col * 18), 84 + row * 18));
            }
        }
    }

    /**
     * Adds the player hotbar.
     * @param playerInventory the players inventory.
     */
    public void addPlayerHotbar (Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, (8 + i * 18), 142));
        }
    }

    /**
     * Checks if a block entity can be upgraded.
     * @return true is upgradeable.
     */
    public boolean isUpgradeable() {
        return blockEntity instanceof Upgradeable;
    }

    /**
     * Initializes container data for syncing progress and other data from server to client.
     * This method automatically sets up data synchronization for crafting block entities and powered block entities.
     */
    protected void initializeContainerData(Inventory inventory) {
        if (blockEntity instanceof Crafter<?> crafter) {
            this.data = new ContainerData() {
                private final int[] data = new int[9];

                @Override
                public int get(int index) {
                    if (inventory.player.level().isClientSide()) {
                        return data[index];
                    } else {
                        int value = (int) switch (index) {
                            case DATA_PROGRESS -> crafter.getProgress();
                            case DATA_CRAFTING_STATE -> crafter.isCrafting() ? 1 : 0;
                            case DATA_ENABLED_STATE -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isEnabled() ? 1 : 0) : 1;
                            case DATA_EXPORT_STATE -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isExportEnabled() ? 1 : 0) : 1;
                            case DATA_IMPORT_STATE -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isImportEnabled() ? 1 : 0) : 1;
                            case DATA_REDSTONE_MODE -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.getRedstoneMode().id()) : BasePoweredBlockEntity.RedstoneMode.IGNORED.id();
                            case DATA_ENERGY_STORED -> (blockEntity instanceof PoweredBlock powered) ? powered.getEnergyStorage().getEnergyStored() : 0;
                            case DATA_ENERGY_CAPACITY -> (blockEntity instanceof PoweredBlock powered) ? powered.getEnergyStorage().getMaxEnergyStored() : 0;
                            case DATA_SIDE_MODES -> getPackedSideModes();
                            default -> 0;
                        };
                        data[index] = value;
                        return value;
                    }
                }

                @Override
                public void set(int index, int value) {
                    data[index] = value;
                }

                @Override
                public int getCount() {
                    return data.length;
                }
            };
            addDataSlots(this.data);
        } else if (blockEntity instanceof BasePoweredBlockEntity poweredEntity) {
            // For non-crafter powered entities, only sync enabled state
            this.data = new ContainerData() {
                private final int[] data = new int[4];

                @Override
                public int get(int index) {
                    if (inventory.player.level().isClientSide()) {
                        return data[index];
                    } else {
                        int value = switch (index) {
                            case DATA_POWERED_ENABLED_STATE -> poweredEntity.isEnabled() ? 1 : 0;
                            case DATA_POWERED_ENERGY_STORED -> poweredEntity.getEnergyStorage().getEnergyStored();
                            case DATA_POWERED_ENERGY_CAPACITY -> poweredEntity.getEnergyStorage().getMaxEnergyStored();
                            case DATA_POWERED_SIDE_MODES -> getPackedSideModes();
                            default -> 0;
                        };
                        data[index] = value;
                        return value;
                    }
                }

                @Override
                public void set(int index, int value) {
                    data[index] = value;
                }

                @Override
                public int getCount() {
                    return 4;
                }
            };
            addDataSlots(this.data);
        }
    }

    public boolean isSettingsPanelOpen() {
        return settingsPanelOpen;
    }

    public void setSettingsPanelOpen(boolean settingsPanelOpen) {
        this.settingsPanelOpen = settingsPanelOpen;
    }

    private int getPackedSideModes() {
        if (blockEntity instanceof BasePoweredBlockEntity be) {
            int packed = 0;
            for (var dir : net.minecraft.core.Direction.values()) {
                int id = be.getSideMode(dir).id() & 0b11;
                packed |= (id << (dir.ordinal() * 2));
            }
            return packed;
        }
        return 0;
    }

    /**
     * Gets the synchronized progress from the container data.
     * This method should be used in GUIs to get real-time progress updates.
     * @return the current progress as an integer
     */
    public int getSyncedProgress() {
        if (data != null && data.getCount() >= 1) {
            return data.get(DATA_PROGRESS);
        }

        if (blockEntity instanceof Crafter<?> crafter) {
            return (int) crafter.getProgress();
        }
        return 0;
    }

    /**
     * Gets the synchronized crafting state from the container data.
     * This method should be used in GUIs to get real-time crafting state updates.
     * @return true if the block entity is currently crafting
     */
    public boolean getSyncedCraftingState() {
        if (data != null && data.getCount() >= 2) {
            return data.get(DATA_CRAFTING_STATE) == 1;
        }

        if (blockEntity instanceof Crafter<?> crafter) {
            return crafter.isCrafting();
        }
        return false;
    }

    /**
     * Gets the synchronized enabled state from the container data.
     * This method should be used in GUIs to get real-time enabled state updates.
     * @return true if the block entity is currently enabled
     */
    public boolean getSyncedEnabledState() {
        if (data != null) {
            if (blockEntity instanceof Crafter<?> && data.getCount() >= 3) {
                return data.get(DATA_ENABLED_STATE) == 1; // For crafters, the enabled state is at index 2
            } else if (data.getCount() >= 1) {
                return data.get(DATA_POWERED_ENABLED_STATE) == 1; // For non-crafters, the enabled state is at index 0
            }
        }

        if (blockEntity instanceof general.mechanics.api.entity.block.BasePoweredBlockEntity poweredEntity) {
            return poweredEntity.isEnabled();
        }
        return true; // Default to enabled
    }

    public boolean getSyncedExportState() {
        if (data != null && blockEntity instanceof Crafter<?> && data.getCount() >= 4) {
            return data.get(DATA_EXPORT_STATE) == 1;
        }

        if (blockEntity instanceof BasePoweredBlockEntity poweredEntity) {
            return poweredEntity.isExportEnabled();
        }

        return true;
    }

    public boolean getSyncedImportState() {
        if (data != null && blockEntity instanceof Crafter<?> && data.getCount() >= 5) {
            return data.get(DATA_IMPORT_STATE) == 1;
        }

        if (blockEntity instanceof BasePoweredBlockEntity poweredEntity) {
            return poweredEntity.isImportEnabled();
        }

        return true;
    }

    public int getSyncedRedstoneModeId() {
        if (data != null && blockEntity instanceof Crafter<?> && data.getCount() >= 6) {
            return data.get(DATA_REDSTONE_MODE);
        }

        if (blockEntity instanceof BasePoweredBlockEntity poweredEntity) {
            return poweredEntity.getRedstoneMode().id();
        }

        return BasePoweredBlockEntity.RedstoneMode.IGNORED.id();
    }

    public boolean isPowered() {
        return blockEntity instanceof PoweredBlock;
    }

    @Nullable
    public CoreEnergyStorage getEnergyStorage() {
        if (blockEntity instanceof PoweredBlock powered) {
            return powered.getEnergyStorage();
        }
        return null;
    }

    public int getPowerStored() {
        if (data != null) {
            if (blockEntity instanceof Crafter<?> && data.getCount() > DATA_ENERGY_STORED) {
                return data.get(DATA_ENERGY_STORED);
            }
            if (!(blockEntity instanceof Crafter<?>) && data.getCount() > DATA_POWERED_ENERGY_STORED) {
                return data.get(DATA_POWERED_ENERGY_STORED);
            }
        }

        var storage = getEnergyStorage();
        return storage != null ? storage.getEnergyStored() : 0;
    }

    public int getPowerCapacity() {
        if (data != null) {
            if (blockEntity instanceof Crafter<?> && data.getCount() > DATA_ENERGY_CAPACITY) {
                return data.get(DATA_ENERGY_CAPACITY);
            }
            if (!(blockEntity instanceof Crafter<?>) && data.getCount() > DATA_POWERED_ENERGY_CAPACITY) {
                return data.get(DATA_POWERED_ENERGY_CAPACITY);
            }
        }

        var storage = getEnergyStorage();
        return storage != null ? storage.getMaxEnergyStored() : 0;
    }

    @Override
    public MachineUiState getUiState() {
        boolean hasPower = isPowered();

        boolean hasCrafting = blockEntity instanceof Crafter<?>;
        int progress = getSyncedProgress();
        int maxProgress = 176;
        boolean crafting = getSyncedCraftingState();
        if (blockEntity instanceof Crafter<?> crafter) {
            maxProgress = crafter.getMaxProgress();
        }

        int packedSideModes = 0;
        if (data != null) {
            if (blockEntity instanceof Crafter<?> && data.getCount() > DATA_SIDE_MODES) {
                packedSideModes = data.get(DATA_SIDE_MODES);
            } else if (!(blockEntity instanceof Crafter<?>) && data.getCount() > DATA_POWERED_SIDE_MODES) {
                packedSideModes = data.get(DATA_POWERED_SIDE_MODES);
            }
        }

        return new MachineUiState(
                hasPower,
                getPowerStored(),
                getPowerCapacity(),
                getSyncedEnabledState(),
                getSyncedExportState(),
                getSyncedImportState(),
                BasePoweredBlockEntity.RedstoneMode.fromId(getSyncedRedstoneModeId()),
                hasCrafting,
                progress,
                maxProgress,
                crafting,
                packedSideModes
        );
    }
}