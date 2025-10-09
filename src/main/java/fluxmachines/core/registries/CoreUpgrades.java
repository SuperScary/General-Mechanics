package fluxmachines.core.registries;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.item.ItemDefinition;
import fluxmachines.core.api.upgrade.UpgradeBase;
import fluxmachines.core.api.upgrade.item.SpeedUpgrade;
import fluxmachines.core.api.upgrade.item.UpgradeEmpty;
import fluxmachines.core.tab.CoreTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CoreUpgrades {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FluxMachines.MODID);

    private static final List<ItemDefinition<?>> UPGRADES = new ArrayList<>();
    private static final List<Pair<ItemDefinition<?>, UpgradeFunction<?, ?>>> FUNCTIONS = new ArrayList<>();

    public static final ItemDefinition<UpgradeBase> EMPTY = create("upgrade_base", UpgradeEmpty::new, null);
    public static final ItemDefinition<UpgradeBase> SPEED = create("speed_upgrade", UpgradeBase::new, SpeedUpgrade::new);
    public static final ItemDefinition<UpgradeBase> CAPACITY = create("capacity_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> EFFICIENCY = create("efficiency_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> OVERCLOCK = create("overclock_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> THERMAL_BUFFER = create("thermal_buffer_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> AUTO_EJECTOR = create("auto_ejector_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> INPUT_EXPANDER = create("input_expander_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> MULTI_PROCESSOR_UNIT = create("mpu_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> SILENCING_COIL = create("silencing_coil_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> NANITE_INJECTOR = create("nanite_injector_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> PRECISION_GEARBOX = create("precision_gearbox_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> REDSTONE_INTERFACE = create("redstone_interface_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> ECO_DRIVE = create("eco_drive_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> VOID_MOD = create("void_mod_upgrade", UpgradeBase::new, null);
    public static final ItemDefinition<UpgradeBase> REPLICATION_NODE = create("replication_node_upgrade", UpgradeBase::new, null);

    public static List<ItemDefinition<?>> getUpgrades () {
        return Collections.unmodifiableList(UPGRADES);
    }

    public static List<Pair<ItemDefinition<?>, UpgradeFunction<?, ?>>> getFunctions () {
        return Collections.unmodifiableList(FUNCTIONS);
    }

    /**
     * Returns a list of registered upgrades for a given block.
     * @param block the block to check. This should be the main block and linked to a {@link net.minecraft.world.level.block.entity.BlockEntity}
     * @return an {@link ImmutableList} of upgrade pairs.
     */
    public static ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> getCompatibleUpgrades (Block block) {
        var id = BuiltInRegistries.BLOCK.getKey(block);

        var upgradeMap = CoreRegistries.UPGRADE_MAP_REGISTRY.get(id);
        return upgradeMap != null && !upgradeMap.upgrades().isEmpty() ? upgradeMap.upgrades() : ImmutableList.of();
    }

    public static <T extends UpgradeBase, I> ItemDefinition<T> create (String id, Function<Item.Properties, T> factory, @Nullable UpgradeFunctionBuilder<T, I> caller) {
        return create(id, FluxMachines.getResource(id), factory, caller);
    }

    public static <T extends UpgradeBase, I> ItemDefinition<T> create (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable UpgradeFunctionBuilder<T, I> caller) {
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));
        CoreTab.add(definition);

        UPGRADES.add(definition);
        if (caller != null) FUNCTIONS.add(new Pair<>(definition, caller.build()));
        return definition;
    }

    @FunctionalInterface
    public interface UpgradeFunctionBuilder<T extends UpgradeBase, I> {
        UpgradeFunction<T, I> build ();
    }

    public interface UpgradeFunction<T extends UpgradeBase, I> {
        void create (T base, I valueType);
        void work (I attribute);
    }

}
