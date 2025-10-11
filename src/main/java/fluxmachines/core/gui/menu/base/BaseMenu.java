package fluxmachines.core.gui.menu.base;

import fluxmachines.core.api.entity.Crafter;
import fluxmachines.core.api.entity.block.BaseBlockEntity;
import fluxmachines.core.api.entity.block.BaseEntityBlock;
import fluxmachines.core.api.entity.block.BasePoweredBlockEntity;
import fluxmachines.core.api.upgrade.Upgradeable;
import fluxmachines.core.gui.util.QuickMoveStack;
import fluxmachines.core.gui.util.UpgradeSlot;
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

public abstract class BaseMenu<B extends BaseEntityBlock<?>, T extends BaseBlockEntity> extends AbstractContainerMenu {

    public final B block;
    public final T blockEntity;
    private final Level level;
    public ContainerData data;

    public BaseMenu(MenuType<?> type, int containerId, Inventory inventory, B block, T blockEntity) {
        super(type, containerId);
        this.block = block;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();

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
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 0, 176 + 2, 7 + 10, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 1, 176 + 2, 7 + 28, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 2, 176 + 2, 7 + 46, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 3, 176 + 2, 7 + 64, this.blockEntity));
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
     * Checks if a blockentity can be upgraded.
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
                private int[] data = new int[6]; // 0: progress, 1: crafting state, 2: enabled state, 3: export, 4: import, 5: redstone mode

                @Override
                public int get(int index) {
                    if (inventory.player.level().isClientSide()) {
                        return data[index];
                    } else {
                        int value = (int) switch (index) {
                            case 0 -> crafter.getProgress();
                            case 1 -> crafter.isCrafting() ? 1 : 0;
                            case 2 -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isEnabled() ? 1 : 0) : 1;
                            case 3 -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isExportEnabled() ? 1 : 0) : 1;
                            case 4 -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.isImportEnabled() ? 1 : 0) : 1;
                            case 5 -> (blockEntity instanceof BasePoweredBlockEntity entity) ? (entity.getRedstoneMode().id()) : BasePoweredBlockEntity.RedstoneMode.IGNORED.id();
                            default -> 0;
                        };
                        data[index] = value;
                        return value;
                    }
                }

                @Override
                public void set(int index, int value) {
                    data[index] = value;
                    // Handle enabled state changes on server side
                    if (index == 2 && !inventory.player.level().isClientSide() && blockEntity instanceof BasePoweredBlockEntity entity) {
                        entity.setEnabled(value == 1);
                    }

                    if (index == 3 && !inventory.player.level().isClientSide() && blockEntity instanceof BasePoweredBlockEntity entity) {
                        entity.setExportEnabled(value == 1);
                    }

                    if (index == 4 && !inventory.player.level().isClientSide() && blockEntity instanceof BasePoweredBlockEntity entity) {
                        entity.setImportEnabled(value == 1);
                    }

                    if (index == 5 && !inventory.player.level().isClientSide() && blockEntity instanceof BasePoweredBlockEntity entity) {
                        entity.setRedstoneMode(value);
                    }
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
                private int[] data = new int[1]; // 0: enabled state

                @Override
                public int get(int index) {
                    if (inventory.player.level().isClientSide()) {
                        return data[index];
                    } else {
                        int value = (int) switch (index) {
                            case 0 -> poweredEntity.isEnabled() ? 1 : 0;
                            default -> 0;
                        };
                        data[index] = value;
                        return value;
                    }
                }

                @Override
                public void set(int index, int value) {
                    data[index] = value;
                    // Handle enabled state changes on server side
                    if (index == 0 && !inventory.player.level().isClientSide()) {
                        poweredEntity.setEnabled(value == 1);
                    }
                }

                @Override
                public int getCount() {
                    return 1;
                }
            };
            addDataSlots(this.data);
        }
    }

    /**
     * Gets the synchronized progress from the container data.
     * This method should be used in GUIs to get real-time progress updates.
     * @return the current progress as an integer
     */
    public int getSyncedProgress() {
        if (data != null && data.getCount() >= 1) {
            return data.get(0);
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
            return data.get(1) == 1;
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
                return data.get(2) == 1; // For crafters, enabled state is at index 2
            } else if (data.getCount() >= 1) {
                return data.get(0) == 1; // For non-crafters, enabled state is at index 0
            }
        }

        if (blockEntity instanceof fluxmachines.core.api.entity.block.BasePoweredBlockEntity poweredEntity) {
            return poweredEntity.isEnabled();
        }
        return true; // Default to enabled
    }

}