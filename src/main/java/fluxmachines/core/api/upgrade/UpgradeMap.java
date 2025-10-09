package fluxmachines.core.api.upgrade;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fluxmachines.core.api.entity.DeferredBlockEntityType;
import fluxmachines.core.api.item.ItemDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;

public record UpgradeMap<T extends BlockEntity>(DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
}
