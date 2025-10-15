package general.mechanics.registries;

import com.google.common.base.Preconditions;
import general.mechanics.GM;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.*;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.api.item.plastic.ColoredPlasticItem;
import general.mechanics.api.item.plastic.PlasticType;
import general.mechanics.tab.CoreTab;
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

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GM.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

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

    // Plastic Types
    public static final ItemDefinition<PlasticTypeItem> POLYETHYLENE = plasticType("Polyethylene", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYETHYLENE));
    public static final ItemDefinition<PlasticTypeItem> POLYPROPYLENE = plasticType("Polypropylene", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYPROPYLENE));
    public static final ItemDefinition<PlasticTypeItem> POLYSTYRENE = plasticType("Polystyrene", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYSTYRENE));
    public static final ItemDefinition<PlasticTypeItem> POLYVINYL_CHLORIDE = plasticType("Polyvinyl Chloride", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYVINYL_CHLORIDE));
    public static final ItemDefinition<PlasticTypeItem> POLYETHYLENE_TEREPHTHALATE = plasticType("Polyethylene Terephthalate", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYETHYLENE_TEREPHTHALATE));
    public static final ItemDefinition<PlasticTypeItem> ACRYLONITRILE_BUTADIENE_STYRENE = plasticType("Acrylonitrile Butadiene Styrene", (properties) -> new PlasticTypeItem(properties, PlasticType.ACRYLONITRILE_BUTADIENE_STYRENE));
    public static final ItemDefinition<PlasticTypeItem> POLYCARBONATE = plasticType("Polycarbonate", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYCARBONATE));
    public static final ItemDefinition<PlasticTypeItem> NYLON = plasticType("Nylon", (properties) -> new PlasticTypeItem(properties, PlasticType.NYLON));
    public static final ItemDefinition<PlasticTypeItem> POLYURETHANE = plasticType("Polyurethane", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYURETHANE));
    public static final ItemDefinition<PlasticTypeItem> POLYTETRAFLUOROETHYLENE = plasticType("Polytetrafluoroethylene", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYTETRAFLUOROETHYLENE));
    public static final ItemDefinition<PlasticTypeItem> POLYETHERETHERKETONE = plasticType("Polyetheretherketone", (properties) -> new PlasticTypeItem(properties, PlasticType.POLYETHERETHERKETONE));

    public static final ItemDefinition<Wrench> WRENCH = item("Wrench", Wrench::new);

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    public static List<ColoredPlasticItem> getAllColoredPlastics() {
        List<ColoredPlasticItem> allColored = new ArrayList<>();
        for (var item : ITEMS) {
            if (item.get() instanceof ColoredPlasticItem colored) {
                allColored.add(colored);
            }
        }
        return allColored;
    }

    static <T extends Item> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, GM.getResource(resourceFriendly), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (final String name, String resourceName, Function<Item.Properties, T> factory) {
        return item(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.MAIN)) {
            CoreTab.add(definition);
        } else if (group != null) {
            CoreTab.addExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    // Plastic Type Registration Methods
    static ItemDefinition<ColoredPlasticItem> itemColoredPlastic(String name, String resourceName, Function<Item.Properties, ColoredPlasticItem> factory) {
        return itemColoredPlastic(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static ItemDefinition<ColoredPlasticItem> itemColoredPlastic(String name, ResourceLocation id, Function<Item.Properties, ColoredPlasticItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.MAIN)) {
            CoreTab.add(definition);
        } else if (group != null) {
            CoreTab.addExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static <T extends PlasticTypeItem> ItemDefinition<T> plasticType(String name, Function<Item.Properties, T> factory) {
        // Create the main plastic type item
        ItemDefinition<T> plasticTypeDef = item(name, factory);
        
        // Register all colored plastic variants
        for (DyeColor color : PlasticType.getAllColors()) {
            String coloredName = color.getName().substring(0, 1).toUpperCase() + color.getName().substring(1) + " " + name;
            String coloredResourceName = color.getName().toLowerCase() + "_" + name.toLowerCase().replace(' ', '_');
            itemColoredPlastic(coloredName, GM.getResource(coloredResourceName), (Item.Properties properties) -> {
                T plasticTypeItem = plasticTypeDef.get();
                return plasticTypeItem.getColoredVariant(color);
            }, null);
        }
        
        return plasticTypeDef;
    }

}
