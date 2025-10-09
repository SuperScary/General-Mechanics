package fluxmachines.core.api.upgrade;

import fluxmachines.core.api.entity.block.BaseEntityBlock;
import fluxmachines.core.api.item.base.BaseItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class UpgradeBase extends BaseItem {

    private boolean hasBeenApplied = false;

    public UpgradeBase (Item.Properties properties) {
        super(properties);
    }

    public void functionalUpgrade (BaseEntityBlock<?> block, BlockState state) {
        if (block instanceof Upgradeable) {

        } else throw new IllegalStateException("Cannot apply an upgrade to a non-upgradeable block.");
    }

    public boolean setApplied (boolean hasBeenApplied) {
        return this.hasBeenApplied = hasBeenApplied;
    }

    public boolean hasBeenApplied () {
        return this.hasBeenApplied;
    }

    @Override
    public void appendHoverText (ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown())  {
            tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc"));
        } else {
            tooltipComponents.add(Component.translatable("gui.fluxmachines.press_shift"));
        }
    }

}
