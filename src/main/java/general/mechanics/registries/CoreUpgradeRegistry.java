package general.mechanics.registries;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import general.mechanics.GM;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.entity.DeferredBlockEntityType;
import general.mechanics.api.entity.block.BasePoweredBlockEntity;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.upgrade.DeferredUpgradeMap;
import general.mechanics.api.upgrade.UpgradeBase;
import general.mechanics.api.upgrade.UpgradeMap;
import general.mechanics.api.upgrade.Upgradeable;
import general.mechanics.entity.block.MatterFabricatorBlockEntity;
import lombok.Getter;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static general.mechanics.registries.CoreUpgrades.*;

public class CoreUpgradeRegistry {
    // MAXIMUM ALLOWED IN EACH SLOT
    public static final int MAX_UPGRADES = 8;

    /**
     * A list of all registered upgrade maps.
     */
    public static final List<DeferredUpgradeMap<?>> UPGRADE_MAPS = new ArrayList<>();

    public static final DeferredRegister<UpgradeMap<?>> REGISTRY = DeferredRegister.create(CoreRegistries.UPGRADE_MAP_REGISTRY, GM.MODID);

    public static final DeferredUpgradeMap<MatterFabricatorBlockEntity> MATTER_FABRICATOR_UPGRADES = build(CoreBlocks.MATTER_FABRICATOR, MatterFabricatorBlockEntity.class, CoreBlockEntities.MATTER_FABRICATOR, UpgradePairs.POWERED_CRAFTER.getUpgrades());
    //public static final DeferredUpgradeMap<CoalGeneratorBlockEntity> COAL_GENERATOR_BLOCK_ENTITY_UPGRADES = build(FMBlocks.COAL_GENERATOR, CoalGeneratorBlockEntity.class, FMBlockEntities.COAL_GENERATOR, UpgradePairs.POWER_GENERATOR.getUpgrades());

    private static <T extends BasePoweredBlockEntity> DeferredUpgradeMap<T> build (BlockDefinition<?> id, Class<? extends BasePoweredBlockEntity> cl, DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
        return build(id.getRegistryFriendlyName(), cl, entity, upgrades);
    }

    private static <T extends BasePoweredBlockEntity> DeferredUpgradeMap<T> build (String id, Class<? extends BasePoweredBlockEntity> cl, DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
        Preconditions.checkArgument(Upgradeable.class.isAssignableFrom(cl), "Cannot register upgrades to an object that is not Upgradeable.");

        var deferred = REGISTRY.register(id, () -> new UpgradeMap<>(entity, upgrades));
        var supplier = new DeferredUpgradeMap<>(cl, deferred);
        UPGRADE_MAPS.add(supplier);
        return supplier;
    }

    @Getter
    public enum UpgradePairs {
        POWERED_CRAFTER (ImmutableList.of(
                make(SPEED),
                make(CAPACITY),
                make(EFFICIENCY),
                make(OVERCLOCK, 1),
                make(AUTO_EJECTOR, 1),
                make(SILENCING_COIL, 1),
                make(REDSTONE_INTERFACE, 1),
                make(ECO_DRIVE, 4),
                make(VOID_MOD, 1),
                make(REPLICATION_NODE)
        )),
        POWER_GENERATOR (ImmutableList.of(
                make(SPEED),
                make(EFFICIENCY),
                make(OVERCLOCK),
                make(SILENCING_COIL, 1),
                make(REDSTONE_INTERFACE, 1),
                make(ECO_DRIVE, 4),
                make(VOID_MOD, 1)
        ));

        private final ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades;
        UpgradePairs (ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
            this.upgrades = upgrades;
        }

        private static Pair<ItemDefinition<UpgradeBase>, Integer> make (ItemDefinition<UpgradeBase> base, Integer maximum) {
            return new Pair<>(base, maximum);
        }

        private static Pair<ItemDefinition<UpgradeBase>, Integer> make (ItemDefinition<UpgradeBase> base) {
            return make(base, MAX_UPGRADES);
        }
    }

}
