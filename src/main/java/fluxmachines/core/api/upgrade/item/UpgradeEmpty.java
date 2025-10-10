package fluxmachines.core.api.upgrade.item;

import fluxmachines.core.api.upgrade.UpgradeBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class UpgradeEmpty extends UpgradeBase {

    public UpgradeEmpty (Item.Properties properties, String description) {
        super(properties, description);
    }

    @Override
    public void appendHoverText (ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    }
}
