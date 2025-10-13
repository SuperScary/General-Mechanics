package general.mechanics.api.upgrade;

import general.mechanics.api.entity.block.BaseEntityBlock;
import general.mechanics.api.item.base.BaseItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class UpgradeBase extends BaseItem {

    private boolean hasBeenApplied = false;

    private final String description;

    public UpgradeBase (Item.Properties properties, String description) {
        super(properties);
        this.description = description;
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
            tooltipComponents.add(Component.translatable("gui.gm.press_shift"));
        }
    }

    public String getEnglishDescription () {
        return this.description;
    }

}
