package general.mechanics.api.item.ingot;

import general.mechanics.api.item.base.BaseItem;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RawItem extends BaseItem {

    @Getter
    private final IngotItem parentIngot;

    public RawItem(IngotItem ingot) {
        super(ingot.getProperties());
        this.parentIngot = ingot;
    }

    public static int getColor(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof RawItem rawItem) {
            IngotItem parent = rawItem.getParentIngot();
            if (parent instanceof IngotItem elementItem) {
                return elementItem.getTint();
            }
        }

        return -1;
    }

}
