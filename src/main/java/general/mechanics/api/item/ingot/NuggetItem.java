package general.mechanics.api.item.ingot;

import general.mechanics.api.item.base.BaseItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NuggetItem extends BaseItem {

    private final IngotItem parentIngot;

    public NuggetItem(IngotItem ingot) {
        super(ingot.getProperties());
        this.parentIngot = ingot;
    }

    public IngotItem getParentIngot() {
        return parentIngot;
    }

    public static int getColor(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof NuggetItem nuggetItem) {
            IngotItem parent = nuggetItem.getParentIngot();
            if (parent instanceof general.mechanics.api.item.element.metallic.ElementItem elementItem) {
                return elementItem.getTint();
            }
        }

        return -1;
    }

}
