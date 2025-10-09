package fluxmachines.core.api.upgrade;

import fluxmachines.core.api.entity.block.BasePoweredBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class DeferredUpgradeMap<T extends BasePoweredBlockEntity> implements Supplier<UpgradeMap<T>> {

    private final Class<? extends BasePoweredBlockEntity> entity;
    private final DeferredHolder<UpgradeMap<?>, UpgradeMap<T>> holder;

    public DeferredUpgradeMap (Class<? extends BasePoweredBlockEntity> entity, DeferredHolder<UpgradeMap<?>, UpgradeMap<T>> holder) {
        this.entity = entity;
        this.holder = holder;
    }

    public Class<? extends BasePoweredBlockEntity> getEntity () {
        return entity;
    }

    @Override
    public UpgradeMap<T> get () {
        return holder.get();
    }

}
