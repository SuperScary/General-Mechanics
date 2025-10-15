package general.mechanics.api.item.plastic;

import general.mechanics.api.item.base.BaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlasticTypeItem extends BaseItem {

    private final PlasticType plasticType;
    private final List<ColoredPlasticItem> coloredVariants;
    private final Properties properties;

    public PlasticTypeItem(Properties properties, PlasticType plasticType) {
        super(properties);
        this.properties = properties;
        this.plasticType = plasticType;
        this.coloredVariants = new ArrayList<>();
        
        // Create all colored variants
        for (DyeColor color : PlasticType.getAllColors()) {
            coloredVariants.add(new ColoredPlasticItem(this, color));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("§o" + plasticType.getAbbreviation()));
        tooltipComponents.add(Component.literal(String.format("§e" + plasticType.getFormula())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public PlasticType getPlasticType() {
        return plasticType;
    }

    public List<ColoredPlasticItem> getColoredVariants() {
        return coloredVariants;
    }

    public Properties getProperties() {
        return properties;
    }

    public ColoredPlasticItem getColoredVariant(DyeColor color) {
        return coloredVariants.stream()
                .filter(variant -> variant.getColor() == color)
                .findFirst()
                .orElse(null);
    }
}
