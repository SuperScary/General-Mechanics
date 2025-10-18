package general.mechanics.api.item.element.metallic;

import general.mechanics.api.item.base.BaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementPileItem extends BaseItem {

    private final ElementItem parentElement;

    public ElementPileItem(ElementItem element) {
        super(element.getProperties());
        this.parentElement = element;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        //tooltipComponents.add(Component.translatable("element.atomic_number", getAtomicNumber()));
        tooltipComponents.add(Component.literal(String.format("§e" + parentElement.getAtomicSymbol())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public ElementItem getParentElement() {
        return parentElement;
    }

    public static int getColor(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof ElementPileItem rawItem) {
            ElementItem parent = rawItem.getParentElement();
            return parent.getTint();
        }
        return -1;
    }
}