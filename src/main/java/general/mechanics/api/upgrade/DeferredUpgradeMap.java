package general.mechanics.api.upgrade;

import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public record DeferredUpgradeMap<T extends BasePoweredBlockEntity>(Class<? extends BasePoweredBlockEntity> entity, DeferredHolder<UpgradeMap<?>, UpgradeMap<T>> holder) implements Supplier<UpgradeMap<T>> {

    @Override
    public UpgradeMap<T> get() {
        return holder.get();
    }

}
