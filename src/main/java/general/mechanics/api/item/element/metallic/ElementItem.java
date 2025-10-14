package general.mechanics.api.item.element.metallic;

import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.item.element.Element;
import general.mechanics.api.item.ingot.IngotItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ElementItem extends IngotItem {

    public static final Properties ELEMENT_PROPERTIES;
    private final Element element;

    public ElementItem(Properties properties, Element element) {
        super(properties);
        this.element = element;
    }

    public ElementItem(Element element) {
        this(ELEMENT_PROPERTIES, element);
    }

    public int getAtomicNumber() {
        return element.getAtomicNumber();
    }

    public String getAtomicSymbol() {
        return element.getSymbol();
    }

    public int getTint() {
        return element.getTintColor();
    }
    
    public Element getElement() {
        return element;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        //tooltipComponents.add(Component.translatable("element.atomic_number", getAtomicNumber()));
        tooltipComponents.add(Component.literal(String.format("§e" + getAtomicSymbol())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static int getColor(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof ElementItem element) {
            return element.getTint();
        }

        return -1;
    }

    static {
        ELEMENT_PROPERTIES = new Properties();
    }

}
