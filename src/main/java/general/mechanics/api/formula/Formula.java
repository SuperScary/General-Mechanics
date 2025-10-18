package general.mechanics.api.formula;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public record Formula(@NotNull ItemLike itemLike, @NotNull String formula) {

    public Component getFormulaComponent(boolean colored, boolean italic) {
        String color = colored ? "§e" : "";
        String italicized = italic ? "§o" : "";
        String formula = color + italicized + this.formula;
        return Component.literal(formula);
    }

    public Component getFormulaComponent() {
        return getFormulaComponent(true, false);
    }

}
