package dimensional.core.api.item.plastic;

import dimensional.core.api.item.base.BaseItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class PlasticItem extends BaseItem {

    public static ArrayList<PlasticItem> PLASTIC_ITEMS = new ArrayList<>();

    private final DyeColor color;

    public PlasticItem(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
        PLASTIC_ITEMS.add(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static int getColorForItemStack(ItemStack stack, int index) {
        if (stack.getItem() instanceof PlasticItem item) {
            return item.getColor().getTextureDiffuseColor();
        }
        return -1;
    }

    public static ArrayList<PlasticItem> getPlasticItems() {
        return PLASTIC_ITEMS;
    }

}
