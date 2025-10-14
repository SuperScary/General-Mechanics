package general.mechanics.api.item.element.metallic;

import general.mechanics.api.item.base.BaseItem;
import general.mechanics.api.item.element.Element;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementItem extends BaseItem {

    public static final Properties ELEMENT_PROPERTIES;
    private final Element element;
    private final ElementDustItem dust;
    private final ElementPlateItem plate;
    private final ElementNuggetItem nugget;
    private final ElementRawItem raw;
    private final Properties properties;

    public ElementItem(Properties properties, Element element) {
        super(properties);
        this.properties = properties;
        this.element = element;
        this.dust = new ElementDustItem(this);
        this.plate = new ElementPlateItem(this);
        this.nugget = new ElementNuggetItem(this);
        this.raw = new ElementRawItem(this);
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

    public ElementDustItem getDustItem() {
        return dust;
    }

    public ElementPlateItem getPlateItem() {
        return plate;
    }

    public ElementNuggetItem getNuggetItem() {
        return nugget;
    }

    public ElementRawItem getRawItem() {
        return raw;
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

    public Properties getProperties() {
        return properties;
    }

    static {
        ELEMENT_PROPERTIES = new Properties();
    }

}
