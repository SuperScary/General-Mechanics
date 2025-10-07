package dimensional.core.gui.menu;

import dimensional.core.block.MatterFabricatorBlock;
import dimensional.core.entity.block.MatterFabricatorBlockEntity;
import dimensional.core.gui.GuiPower;
import dimensional.core.gui.menu.base.BaseMenu;
import dimensional.core.gui.util.OutputSlot;
import dimensional.core.registries.CoreBlocks;
import dimensional.core.registries.CoreMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MatterFabricatorMenu extends BaseMenu<MatterFabricatorBlock, MatterFabricatorBlockEntity> implements GuiPower {

    public MatterFabricatorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MatterFabricatorMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(CoreMenus.MATTER_FABRICATOR_MENU.get(), containerId, inventory, CoreBlocks.MATTER_FABRICATOR.block(), (MatterFabricatorBlockEntity) blockEntity);
    }

    @Override
    public int defineSlotCount () {
        return 4;
    }

    @Override
    public void addSlots () {

        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 20 + getUpgradeableMoveFactor(), 35));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 38 + getUpgradeableMoveFactor(), 35));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 56 + getUpgradeableMoveFactor(), 35));

        this.addSlot(new OutputSlot(this.blockEntity.getInventory(), getNextIndex(), 116 + getUpgradeableMoveFactor(), 35));
    }

    @Override
    public int getPower () {
        return blockEntity.getEnergyStorage().getEnergyStored();
    }

}