package general.mechanics.api.electrical.transistor;

import general.mechanics.api.item.base.ElectricalComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransistorItem extends ElectricalComponent {

    private final TransistorType transistorType;

    public TransistorItem(Properties properties, TransistorType resistor) {
        super(properties);
        this.transistorType = resistor;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(String.format("§e§o" + getTransistorType().getCategory() + " | " + getTransistorType().getSymbol())));
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc"));
        } else {
            tooltipComponents.add(Component.translatable("gui.gm.press_shift"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public TransistorType getTransistorType() {
        return transistorType;
    }

}
