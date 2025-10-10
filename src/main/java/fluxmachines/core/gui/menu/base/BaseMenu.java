package fluxmachines.core.gui.menu.base;

import fluxmachines.core.api.entity.Crafter;
import fluxmachines.core.api.entity.block.BaseBlockEntity;
import fluxmachines.core.api.entity.block.BaseEntityBlock;
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
    private int index = 0;
    private final int upgradeableMoveFactor;
    public ContainerData data;

    public BaseMenu(MenuType<?> type, int containerId, Inventory inventory, B block, T blockEntity) {
        super(type, containerId);
        this.block = block;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.upgradeableMoveFactor = isUpgradeable() ? -14 : 0;

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

    /**
     * Using {@link BaseMenu#getUpgradeableMoveFactor()} is basically pointless since it will only ever be -14.
     * However, for readability and maintainability I'm using it. 182 is the x location on the texture, but it will always
     * subtract 14.
     */
    public void addUpgradeSlots () {
        if (!isUpgradeable()) {
            return;
        }

        Upgradeable upgradeable = (Upgradeable) this.blockEntity;
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 0, 176 + getUpgradeableMoveFactor() + 2, 7 + 10, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 1, 176 + getUpgradeableMoveFactor() + 2, 7 + 28, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 2, 176 + getUpgradeableMoveFactor() + 2, 7 + 46, this.blockEntity));
        this.addSlot(new UpgradeSlot(this.block, upgradeable.getUpgradeInventory(), 3, 176 + getUpgradeableMoveFactor() + 2, 7 + 64, this.blockEntity));
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
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, (8 + getUpgradeableMoveFactor() + col * 18), 84 + row * 18));
            }
        }
    }

    /**
     * Adds the player hotbar.
     * @param playerInventory the players inventory.
     */
    public void addPlayerHotbar (Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, (8 + getUpgradeableMoveFactor() + i * 18), 142));
        }
    }

    /**
     * Gets the next index for a slot.
     * @return next slot
     */
    public int getNextIndex () {
        return index++;
    }

    /**
     * Represents the offset of the x location when a menu is upgradeable.
     * @return -14 or 0
     */
    public int getUpgradeableMoveFactor () {
        return 0;
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
     * This method automatically sets up data synchronization for crafting block entities.
     */
    protected void initializeContainerData(Inventory inventory) {
        if (blockEntity instanceof Crafter<?> crafter) {
            this.data = new ContainerData() {
                private int[] data = new int[2];
                
                @Override
                public int get(int index) {
                    if (inventory.player.level().isClientSide()) {
                        return data[index];
                    } else {
                        int value = (int) switch (index) {
                            case 0 -> crafter.getProgress();
                            case 1 -> crafter.isCrafting() ? 1 : 0;
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
                    return 2;
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

}
