package general.mechanics.api.upgrade;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import general.mechanics.api.entity.DeferredBlockEntityType;
import general.mechanics.api.item.ItemDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;

public record UpgradeMap<T extends BlockEntity>(DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
}
