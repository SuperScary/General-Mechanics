package fluxmachines.core.registries;

import com.google.common.base.Preconditions;
import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.item.ItemDefinition;
import fluxmachines.core.api.item.base.*;
import fluxmachines.core.api.item.plastic.PlasticItem;
import fluxmachines.core.api.item.plastic.RawPlasticItem;
import fluxmachines.core.tab.CoreTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
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

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FluxMachines.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    // DRAKIUM
    public static final ItemDefinition<BaseItem> RAW_DRAKIUM_ORE = item("Raw Drakium Ore", BaseItem::new);
    public static final ItemDefinition<BaseItem> DRAKIUM_INGOT = item("Drakium Ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> DRAKIUM_NUGGET = item("Drakium Nugget", BaseItem::new);

    // Vanadium
    public static final ItemDefinition<BaseItem> RAW_VANADIUM_ORE = item("Raw Vanadium Ore", BaseItem::new);
    public static final ItemDefinition<BaseItem> VANADIUM_INGOT = item("Vanadium Ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> VANADIUM_NUGGET = item("Vanadium Nugget", BaseItem::new);

    // Wire
    public static final ItemDefinition<BaseItem> WIRE_SPOOL = item("Wire Spool", "spool", BaseItem::new);
    public static final ItemDefinition<WireSpoolItem> COPPER_WIRE_SPOOL = item("Copper Wire Spool", WireSpoolItem::new);
    public static final ItemDefinition<WireSpoolItem> REDSTONE_WIRE_SPOOL = item("Redstone Wire Spool", WireSpoolItem::new);

    // Electrical Components
    public static final ItemDefinition<ElectricalComponent> CAPACITOR = item("Capacitor", ElectricalComponent::new);
    public static final ItemDefinition<ElectricalComponent> RESISTOR = item("Resistor", ElectricalComponent::new);
    public static final ItemDefinition<ElectricalComponent> TRANSISTOR = item("Transistor", ElectricalComponent::new);
    public static final ItemDefinition<ElectricalComponent> TRANSFORMER = item("Transformer", ElectricalComponent::new);
    public static final ItemDefinition<ElectricalComponent> IC = item("Integrated Circuit (IC)", "ic", ElectricalComponent::new);

    // Circuits
    public static final ItemDefinition<CircuitItem> BASIC_CIRCUIT = item("Basic Circuit", CircuitItem::new);

    // Upgrades
    //public static final ItemDefinition<CircuitItem> UPGRADE_BASE = item("Upgrade Base", CircuitItem::new);

    // Plastic
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC = item("Raw Plastic", (properties) -> new RawPlasticItem(properties, DyeColor.WHITE));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_ORANGE = item("Orange Raw Plastic", "raw_plastic_orange", (properties) -> new RawPlasticItem(properties, DyeColor.ORANGE));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_MAGENTA = item("Magenta Raw Plastic", "raw_plastic_magenta", (properties) -> new RawPlasticItem(properties, DyeColor.MAGENTA));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_LIGHT_BLUE = item("Light Blue Raw Plastic", "raw_plastic_light_blue", (properties) -> new RawPlasticItem(properties, DyeColor.LIGHT_BLUE));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_YELLOW = item("Yellow Raw Plastic", "raw_plastic_yellow", (properties) -> new RawPlasticItem(properties, DyeColor.YELLOW));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_LIME = item("Lime Raw Plastic", "raw_plastic_lime", (properties) -> new RawPlasticItem(properties, DyeColor.LIME));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_PINK = item("Pink Raw Plastic", "raw_plastic_pink", (properties) -> new RawPlasticItem(properties, DyeColor.PINK));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_GRAY = item("Gray Raw Plastic", "raw_plastic_gray", (properties) -> new RawPlasticItem(properties, DyeColor.GRAY));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_LIGHT_GRAY = item("Light Gray Raw Plastic", "raw_plastic_light_gray", (properties) -> new RawPlasticItem(properties, DyeColor.LIGHT_GRAY));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_CYAN = item("Cyan Raw Plastic", "raw_plastic_cyan", (properties) -> new RawPlasticItem(properties, DyeColor.CYAN));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_PURPLE = item("Purple Raw Plastic", "raw_plastic_purple", (properties) -> new RawPlasticItem(properties, DyeColor.PURPLE));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_BLUE = item("Blue Raw Plastic", "raw_plastic_blue", (properties) -> new RawPlasticItem(properties, DyeColor.BLUE));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_BROWN = item("Brown Raw Plastic", "raw_plastic_brown", (properties) -> new RawPlasticItem(properties, DyeColor.BROWN));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_GREEN = item("Green Raw Plastic", "raw_plastic_green", (properties) -> new RawPlasticItem(properties, DyeColor.GREEN));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_RED = item("Red Raw Plastic", "raw_plastic_red", (properties) -> new RawPlasticItem(properties, DyeColor.RED));
    public static final ItemDefinition<RawPlasticItem> RAW_PLASTIC_BLACK = item("Black Raw Plastic", "raw_plastic_black", (properties) -> new RawPlasticItem(properties, DyeColor.BLACK));

    public static final ItemDefinition<PlasticItem> PLASTIC = item("Plastic", (properties) -> new PlasticItem(properties, DyeColor.WHITE));
    public static final ItemDefinition<PlasticItem> PLASTIC_ORANGE = item("Orange Plastic", "plastic_orange", (properties) -> new PlasticItem(properties, DyeColor.ORANGE));
    public static final ItemDefinition<PlasticItem> PLASTIC_MAGENTA = item("Magenta Plastic", "plastic_magenta", (properties) -> new PlasticItem(properties, DyeColor.MAGENTA));
    public static final ItemDefinition<PlasticItem> PLASTIC_LIGHT_BLUE = item("Light Blue Plastic", "plastic_light_blue", (properties) -> new PlasticItem(properties, DyeColor.LIGHT_BLUE));
    public static final ItemDefinition<PlasticItem> PLASTIC_YELLOW = item("Yellow Plastic", "plastic_yellow", (properties) -> new PlasticItem(properties, DyeColor.YELLOW));
    public static final ItemDefinition<PlasticItem> PLASTIC_LIME = item("Lime Plastic", "plastic_lime", (properties) -> new PlasticItem(properties, DyeColor.LIME));
    public static final ItemDefinition<PlasticItem> PLASTIC_PINK = item("Pink Plastic", "plastic_pink", (properties) -> new PlasticItem(properties, DyeColor.PINK));
    public static final ItemDefinition<PlasticItem> PLASTIC_GRAY = item("Gray Plastic", "plastic_gray", (properties) -> new PlasticItem(properties, DyeColor.GRAY));
    public static final ItemDefinition<PlasticItem> PLASTIC_LIGHT_GRAY = item("Light Gray Plastic", "plastic_light_gray", (properties) -> new PlasticItem(properties, DyeColor.LIGHT_GRAY));
    public static final ItemDefinition<PlasticItem> PLASTIC_CYAN = item("Cyan Plastic", "plastic_cyan", (properties) -> new PlasticItem(properties, DyeColor.CYAN));
    public static final ItemDefinition<PlasticItem> PLASTIC_PURPLE = item("Purple Plastic", "plastic_purple", (properties) -> new PlasticItem(properties, DyeColor.PURPLE));
    public static final ItemDefinition<PlasticItem> PLASTIC_BLUE = item("Blue Plastic", "plastic_blue", (properties) -> new PlasticItem(properties, DyeColor.BLUE));
    public static final ItemDefinition<PlasticItem> PLASTIC_BROWN = item("Brown Plastic", "plastic_brown", (properties) -> new PlasticItem(properties, DyeColor.BROWN));
    public static final ItemDefinition<PlasticItem> PLASTIC_GREEN = item("Green Plastic", "plastic_green", (properties) -> new PlasticItem(properties, DyeColor.GREEN));
    public static final ItemDefinition<PlasticItem> PLASTIC_RED = item("Red Plastic", "plastic_red", (properties) -> new PlasticItem(properties, DyeColor.RED));
    public static final ItemDefinition<PlasticItem> PLASTIC_BLACK = item("Black Plastic", "plastic_black", (properties) -> new PlasticItem(properties, DyeColor.BLACK));

    public static final ItemDefinition<Wrench> WRENCH = item("Wrench", Wrench::new);

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, FluxMachines.getResource(resourceFriendly), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (final String name, String resourceName, Function<Item.Properties, T> factory) {
        return item(name, FluxMachines.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(FluxMachines.MODID), "Can only register items in " + FluxMachines.MODID);
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
