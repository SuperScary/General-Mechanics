package general.mechanics.api.electrical.transformers;

import general.mechanics.api.item.base.ElectricalComponent;
import lombok.Getter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class TransformerItem extends ElectricalComponent {

    private final TransformerType transformerType;

    public TransformerItem(Properties properties, TransformerType resistor) {
        super(properties);
        this.transformerType = resistor;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc"));
        } else {
            tooltipComponents.add(Component.translatable("gui.gm.press_shift"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
