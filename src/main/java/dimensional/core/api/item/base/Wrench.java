package dimensional.core.api.item.base;

import net.minecraft.world.item.ItemStack;

public class Wrench extends BaseItem {

    public Wrench (Properties properties) {
        super(properties.durability(143).setNoRepair());
    }

    @Override
    public boolean isDamageable (ItemStack stack) {
        return true;
    }

}
