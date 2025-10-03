package dimensional.core.registries;

import com.google.common.base.Preconditions;
import dimensional.core.DimensionalCore;
import dimensional.core.api.item.ItemDefinition;
import dimensional.core.api.item.base.BaseItem;
import dimensional.core.api.item.base.CircuitItem;
import dimensional.core.api.item.base.WireSpoolItem;
import dimensional.core.tab.CoreTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Core items are items that are shared across all child mods.
 * If your item is specific to a child mod, it should be registered in that mod's item registry.
 */
public class CoreItems {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(DimensionalCore.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    // DRAKIUM
    public static final ItemDefinition<BaseItem> RAW_DRAKIUM_ORE = item("Raw Drakium Ore", BaseItem::new);
    public static final ItemDefinition<BaseItem> DRAKIUM_INGOT = item("Drakium Ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> DRAKIUM_NUGGET = item("Drakium Nugget", BaseItem::new);

    // Vanadium
    public static final ItemDefinition<BaseItem> RAW_VANADIUM_ORE = item("Raw Vanadium Ore", BaseItem::new);
    public static final ItemDefinition<BaseItem> VANADIUM_INGOT = item("Vanadium Ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> VANADIUM_NUGGET = item("Vanadium Nugget", BaseItem::new);

    // Circuits
    public static final ItemDefinition<CircuitItem> BASIC_CIRCUIT = item("Basic Circuit", CircuitItem::new);

    // Wire
    public static final ItemDefinition<BaseItem> WIRE_SPOOL = item("Wire Spool", "spool", BaseItem::new);
    public static final ItemDefinition<WireSpoolItem> COPPER_WIRE_SPOOL = item("Copper Wire Spool", WireSpoolItem::new);
    public static final ItemDefinition<WireSpoolItem> REDSTONE_WIRE_SPOOL = item("Redstone Wire Spool", WireSpoolItem::new);

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, DimensionalCore.getResource(resourceFriendly), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (final String name, String resourceName, Function<Item.Properties, T> factory) {
        return item(name, DimensionalCore.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(DimensionalCore.MODID), "Can only register items in " + DimensionalCore.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.MAIN)) {
            CoreTab.add(definition);
        } else if (group != null) {
            CoreTab.addExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

}
