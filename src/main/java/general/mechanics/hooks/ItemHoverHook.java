package general.mechanics.hooks;

import general.mechanics.registries.CoreFormulas;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ItemHoverHook {

    private ItemHoverHook() {}

    public static void onItemHover(ItemTooltipEvent event) {
        var item = event.getItemStack();
        if (item.isEmpty()) return;
        var component = CoreFormulas.getFormulaForItem(item.getItem());
        if (component == null) return;
        event.getToolTip().add(component);
    }

}
