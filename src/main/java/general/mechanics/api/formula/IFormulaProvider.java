package general.mechanics.api.formula;

import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public interface IFormulaProvider {

    @NotNull
    ItemLike getItem();

    @NotNull
    String getFormula();

    default Formula getType() {
        return new Formula(getItem(), getFormula());
    }

}
