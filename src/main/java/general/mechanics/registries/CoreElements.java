package general.mechanics.registries;

import com.google.common.base.Preconditions;
import general.mechanics.GM;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.element.Element;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.api.item.ingot.NuggetItem;
import general.mechanics.api.item.ingot.RawItem;
import general.mechanics.tab.CoreTab;
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

public class CoreElements {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GM.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    // Alkali Metals
    public static final ItemDefinition<ElementItem> LITHIUM_INGOT = elementIngot(Element.LITHIUM);
    public static final ItemDefinition<ElementItem> SODIUM_INGOT = elementIngot(Element.SODIUM);
    public static final ItemDefinition<ElementItem> POTASSIUM_INGOT = elementIngot(Element.POTASSIUM);
    public static final ItemDefinition<ElementItem> RUBIDIUM_INGOT = elementIngot(Element.RUBIDIUM);
    public static final ItemDefinition<ElementItem> CESIUM_INGOT = elementIngot(Element.CESIUM);
    public static final ItemDefinition<ElementItem> FRANCIUM_INGOT = elementIngot(Element.FRANCIUM);
    
    // Alkaline Earth Metals
    public static final ItemDefinition<ElementItem> BERYLLIUM_INGOT = elementIngot(Element.BERYLLIUM);
    public static final ItemDefinition<ElementItem> MAGNESIUM_INGOT = elementIngot(Element.MAGNESIUM);
    public static final ItemDefinition<ElementItem> CALCIUM_INGOT = elementIngot(Element.CALCIUM);
    public static final ItemDefinition<ElementItem> STRONTIUM_INGOT = elementIngot(Element.STRONTIUM);
    public static final ItemDefinition<ElementItem> BARIUM_INGOT = elementIngot(Element.BARIUM);
    public static final ItemDefinition<ElementItem> RADIUM_INGOT = elementIngot(Element.RADIUM);
    
    // Transition Metals - Period 4
    public static final ItemDefinition<ElementItem> SCANDIUM_INGOT = elementIngot(Element.SCANDIUM);
    public static final ItemDefinition<ElementItem> TITANIUM_INGOT = elementIngot(Element.TITANIUM);
    public static final ItemDefinition<ElementItem> VANADIUM_INGOT = elementIngot(Element.VANADIUM);
    public static final ItemDefinition<ElementItem> CHROMIUM_INGOT = elementIngot(Element.CHROMIUM);
    public static final ItemDefinition<ElementItem> MANGANESE_INGOT = elementIngot(Element.MANGANESE);
    public static final ItemDefinition<ElementItem> IRON_INGOT = elementIngot(Element.IRON);
    public static final ItemDefinition<ElementItem> COBALT_INGOT = elementIngot(Element.COBALT);
    public static final ItemDefinition<ElementItem> NICKEL_INGOT = elementIngot(Element.NICKEL);
    public static final ItemDefinition<ElementItem> COPPER_INGOT = elementIngot(Element.COPPER);
    public static final ItemDefinition<ElementItem> ZINC_INGOT = elementIngot(Element.ZINC);
    
    // Transition Metals - Period 5
    public static final ItemDefinition<ElementItem> YTTRIUM_INGOT = elementIngot(Element.YTTRIUM);
    public static final ItemDefinition<ElementItem> ZIRCONIUM_INGOT = elementIngot(Element.ZIRCONIUM);
    public static final ItemDefinition<ElementItem> NIOBIUM_INGOT = elementIngot(Element.NIOBIUM);
    public static final ItemDefinition<ElementItem> MOLYBDENUM_INGOT = elementIngot(Element.MOLYBDENUM);
    public static final ItemDefinition<ElementItem> TECHNETIUM_INGOT = elementIngot(Element.TECHNETIUM);
    public static final ItemDefinition<ElementItem> RUTHENIUM_INGOT = elementIngot(Element.RUTHENIUM);
    public static final ItemDefinition<ElementItem> RHODIUM_INGOT = elementIngot(Element.RHODIUM);
    public static final ItemDefinition<ElementItem> PALLADIUM_INGOT = elementIngot(Element.PALLADIUM);
    public static final ItemDefinition<ElementItem> SILVER_INGOT = elementIngot(Element.SILVER);
    public static final ItemDefinition<ElementItem> CADMIUM_INGOT = elementIngot(Element.CADMIUM);
    
    // Transition Metals - Period 6
    public static final ItemDefinition<ElementItem> LANTHANUM_INGOT = elementIngot(Element.LANTHANUM);
    public static final ItemDefinition<ElementItem> HAFNIUM_INGOT = elementIngot(Element.HAFNIUM);
    public static final ItemDefinition<ElementItem> TANTALUM_INGOT = elementIngot(Element.TANTALUM);
    public static final ItemDefinition<ElementItem> TUNGSTEN_INGOT = elementIngot(Element.TUNGSTEN);
    public static final ItemDefinition<ElementItem> RHENIUM_INGOT = elementIngot(Element.RHENIUM);
    public static final ItemDefinition<ElementItem> OSMIUM_INGOT = elementIngot(Element.OSMIUM);
    public static final ItemDefinition<ElementItem> IRIDIUM_INGOT = elementIngot(Element.IRIDIUM);
    public static final ItemDefinition<ElementItem> PLATINUM_INGOT = elementIngot(Element.PLATINUM);
    public static final ItemDefinition<ElementItem> GOLD_INGOT = elementIngot(Element.GOLD);
    public static final ItemDefinition<ElementItem> MERCURY_INGOT = elementIngot(Element.MERCURY);
    
    // Transition Metals - Period 7
    public static final ItemDefinition<ElementItem> ACTINIUM_INGOT = elementIngot(Element.ACTINIUM);
    public static final ItemDefinition<ElementItem> RUTHERFORDIUM_INGOT = elementIngot(Element.RUTHERFORDIUM);
    public static final ItemDefinition<ElementItem> DUBNIUM_INGOT = elementIngot(Element.DUBNIUM);
    public static final ItemDefinition<ElementItem> SEABORGIUM_INGOT = elementIngot(Element.SEABORGIUM);
    public static final ItemDefinition<ElementItem> BOHRIUM_INGOT = elementIngot(Element.BOHRIUM);
    public static final ItemDefinition<ElementItem> HASSIUM_INGOT = elementIngot(Element.HASSIUM);
    public static final ItemDefinition<ElementItem> MEITNERIUM_INGOT = elementIngot(Element.MEITNERIUM);
    public static final ItemDefinition<ElementItem> DARMSTADTIUM_INGOT = elementIngot(Element.DARMSTADTIUM);
    public static final ItemDefinition<ElementItem> ROENTGENIUM_INGOT = elementIngot(Element.ROENTGENIUM);
    public static final ItemDefinition<ElementItem> COPERNICIUM_INGOT = elementIngot(Element.COPERNICIUM);
    
    // Lanthanides (Rare Earth Metals)
    public static final ItemDefinition<ElementItem> CERIUM_INGOT = elementIngot(Element.CERIUM);
    public static final ItemDefinition<ElementItem> PRASEODYMIUM_INGOT = elementIngot(Element.PRASEODYMIUM);
    public static final ItemDefinition<ElementItem> NEODYMIUM_INGOT = elementIngot(Element.NEODYMIUM);
    public static final ItemDefinition<ElementItem> PROMETHIUM_INGOT = elementIngot(Element.PROMETHIUM);
    public static final ItemDefinition<ElementItem> SAMARIUM_INGOT = elementIngot(Element.SAMARIUM);
    public static final ItemDefinition<ElementItem> EUROPIUM_INGOT = elementIngot(Element.EUROPIUM);
    public static final ItemDefinition<ElementItem> GADOLINIUM_INGOT = elementIngot(Element.GADOLINIUM);
    public static final ItemDefinition<ElementItem> TERBIUM_INGOT = elementIngot(Element.TERBIUM);
    public static final ItemDefinition<ElementItem> DYSPROSIUM_INGOT = elementIngot(Element.DYSPROSIUM);
    public static final ItemDefinition<ElementItem> HOLMIUM_INGOT = elementIngot(Element.HOLMIUM);
    public static final ItemDefinition<ElementItem> ERBIUM_INGOT = elementIngot(Element.ERBIUM);
    public static final ItemDefinition<ElementItem> THULIUM_INGOT = elementIngot(Element.THULIUM);
    public static final ItemDefinition<ElementItem> YTTERBIUM_INGOT = elementIngot(Element.YTTERBIUM);
    public static final ItemDefinition<ElementItem> LUTETIUM_INGOT = elementIngot(Element.LUTETIUM);
    
    // Actinides
    public static final ItemDefinition<ElementItem> THORIUM_INGOT = elementIngot(Element.THORIUM);
    public static final ItemDefinition<ElementItem> PROTACTINIUM_INGOT = elementIngot(Element.PROTACTINIUM);
    public static final ItemDefinition<ElementItem> URANIUM_INGOT = elementIngot(Element.URANIUM);
    public static final ItemDefinition<ElementItem> NEPTUNIUM_INGOT = elementIngot(Element.NEPTUNIUM);
    public static final ItemDefinition<ElementItem> PLUTONIUM_INGOT = elementIngot(Element.PLUTONIUM);
    public static final ItemDefinition<ElementItem> AMERICIUM_INGOT = elementIngot(Element.AMERICIUM);
    public static final ItemDefinition<ElementItem> CURIUM_INGOT = elementIngot(Element.CURIUM);
    public static final ItemDefinition<ElementItem> BERKELIUM_INGOT = elementIngot(Element.BERKELIUM);
    public static final ItemDefinition<ElementItem> CALIFORNIUM_INGOT = elementIngot(Element.CALIFORNIUM);
    public static final ItemDefinition<ElementItem> EINSTEINIUM_INGOT = elementIngot(Element.EINSTEINIUM);
    public static final ItemDefinition<ElementItem> FERMIUM_INGOT = elementIngot(Element.FERMIUM);
    public static final ItemDefinition<ElementItem> MENDELEVIUM_INGOT = elementIngot(Element.MENDELEVIUM);
    public static final ItemDefinition<ElementItem> NOBELIUM_INGOT = elementIngot(Element.NOBELIUM);
    public static final ItemDefinition<ElementItem> LAWRENCIUM_INGOT = elementIngot(Element.LAWRENCIUM);
    
    // Post-Transition Metals
    public static final ItemDefinition<ElementItem> ALUMINUM_INGOT = elementIngot(Element.ALUMINUM);
    public static final ItemDefinition<ElementItem> GALLIUM_INGOT = elementIngot(Element.GALLIUM);
    public static final ItemDefinition<ElementItem> INDIUM_INGOT = elementIngot(Element.INDIUM);
    public static final ItemDefinition<ElementItem> TIN_INGOT = elementIngot(Element.TIN);
    public static final ItemDefinition<ElementItem> THALLIUM_INGOT = elementIngot(Element.THALLIUM);
    public static final ItemDefinition<ElementItem> LEAD_INGOT = elementIngot(Element.LEAD);
    public static final ItemDefinition<ElementItem> BISMUTH_INGOT = elementIngot(Element.BISMUTH);
    public static final ItemDefinition<ElementItem> NIHONIUM_INGOT = elementIngot(Element.NIHONIUM);
    public static final ItemDefinition<ElementItem> FLEROVIUM_INGOT = elementIngot(Element.FLEROVIUM);
    public static final ItemDefinition<ElementItem> MOSCOVIUM_INGOT = elementIngot(Element.MOSCOVIUM);
    public static final ItemDefinition<ElementItem> LIVERMORIUM_INGOT = elementIngot(Element.LIVERMORIUM);

    /**
     * Creates an element ingot with automatic naming and all associated items (raw, nugget)
     * @param element the element to create an ingot for
     * @return the ItemDefinition for the element ingot
     */
    public static ItemDefinition<ElementItem> elementIngot(Element element) {
        String name = element.getDisplayName() + " Ingot";
        return ingot(name, (properties) -> new ElementItem(properties, element));
    }

    static <T extends ElementItem> ItemDefinition<T> ingot(String name, Function<Item.Properties, T> factory) {
        // Create the main element item
        ItemDefinition<T> elementDef = item(name, factory);
        
        // Register the raw item
        String rawName = "Raw " + name.replace(" Ingot", "");
        String rawResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_raw");
        itemRaw(rawName, rawResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getRawItem();
        });
        
        // Register the nugget item
        String nuggetName = name.replace(" Ingot", " Nugget");
        String nuggetResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_nugget");
        itemNugget(nuggetName, nuggetResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getNuggetItem();
        });
        
        return elementDef;
    }

    public static List<ItemDefinition<?>> getElements() {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends ElementItem> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, GM.getResource(resourceFriendly), factory, CoreTab.MAIN);
    }

    static <T extends ElementItem> ItemDefinition<T> item (final String name, String resourceName, Function<Item.Properties, T> factory) {
        return item(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static <T extends ElementItem> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
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

    static ItemDefinition<RawItem> itemRaw (String name, String resourceName, Function<Item.Properties, RawItem> factory) {
        return itemRaw(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static ItemDefinition<RawItem> itemRaw (String name, ResourceLocation id, Function<Item.Properties, RawItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
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

    static ItemDefinition<NuggetItem> itemNugget (String name, String resourceName, Function<Item.Properties, NuggetItem> factory) {
        return itemNugget(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static ItemDefinition<NuggetItem> itemNugget (String name, ResourceLocation id, Function<Item.Properties, NuggetItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
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

}
