package dimensional.core.gui.menu.base;

import dimensional.core.api.entity.block.BaseBlockEntity;
import dimensional.core.api.entity.block.BaseEntityBlock;
import dimensional.core.gui.util.QuickMoveStack;
import dimensional.core.gui.util.UpgradeSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

    public BaseMenu(MenuType<?> type, int containerId, Inventory inventory, B block, T blockEntity) {
        super(type, containerId);
        this.block = block;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.upgradeableMoveFactor = isUpgradeable() ? -14 : 0;

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

        this.addSlot(new UpgradeSlot(this.block, this.blockEntity.getInventory(), getNextIndex(), 182 + getUpgradeableMoveFactor(), 5));
        this.addSlot(new UpgradeSlot(this.block, this.blockEntity.getInventory(), getNextIndex(), 182 + getUpgradeableMoveFactor(), 23));
        this.addSlot(new UpgradeSlot(this.block, this.blockEntity.getInventory(), getNextIndex(), 182 + getUpgradeableMoveFactor(), 41));
        this.addSlot(new UpgradeSlot(this.block, this.blockEntity.getInventory(), getNextIndex(), 182 + getUpgradeableMoveFactor(), 59));
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
        return upgradeableMoveFactor;
    }

    /**
     * Checks if a blockentity can be upgraded.
     * @return true is upgradeable.
     */
    public boolean isUpgradeable() {
        return false; //blockEntity instanceof Upgradeable;
    }

}
