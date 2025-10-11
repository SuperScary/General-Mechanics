package fluxmachines.core.gui.menu;

import fluxmachines.core.block.MatterFabricatorBlock;
import fluxmachines.core.entity.block.MatterFabricatorBlockEntity;
import fluxmachines.core.gui.GuiPower;
import fluxmachines.core.gui.menu.base.BaseMenu;
import fluxmachines.core.gui.util.OutputSlot;
import fluxmachines.core.registries.CoreBlocks;
import fluxmachines.core.registries.CoreMenus;
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

        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), 0, 20, 35));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), 1, 38, 35));
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), 2, 56, 35));

        this.addSlot(new OutputSlot(this.blockEntity.getInventory(), 3, 116, 35));
    }

    @Override
    public int getPower () {
        return blockEntity.getEnergyStorage().getEnergyStored();
    }

}