package general.mechanics.api.item.base;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CircuitItem extends BaseItem {

    public CircuitItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }
}
