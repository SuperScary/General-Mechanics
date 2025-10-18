package general.mechanics.registries;

import com.google.common.base.Preconditions;
import general.mechanics.GM;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.element.ElementType;
import general.mechanics.api.item.element.metallic.*;
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
    public static final ItemDefinition<ElementItem> LITHIUM_INGOT = elementIngot(ElementType.LITHIUM);
    public static final ItemDefinition<ElementItem> SODIUM_INGOT = elementIngot(ElementType.SODIUM);
    public static final ItemDefinition<ElementItem> POTASSIUM_INGOT = elementIngot(ElementType.POTASSIUM);
    public static final ItemDefinition<ElementItem> RUBIDIUM_INGOT = elementIngot(ElementType.RUBIDIUM);
    public static final ItemDefinition<ElementItem> CESIUM_INGOT = elementIngot(ElementType.CESIUM);
    public static final ItemDefinition<ElementItem> FRANCIUM_INGOT = elementIngot(ElementType.FRANCIUM);
    
    // Alkaline Earth Metals
    public static final ItemDefinition<ElementItem> BERYLLIUM_INGOT = elementIngot(ElementType.BERYLLIUM);
    public static final ItemDefinition<ElementItem> MAGNESIUM_INGOT = elementIngot(ElementType.MAGNESIUM);
    public static final ItemDefinition<ElementItem> CALCIUM_INGOT = elementIngot(ElementType.CALCIUM);
    public static final ItemDefinition<ElementItem> STRONTIUM_INGOT = elementIngot(ElementType.STRONTIUM);
    public static final ItemDefinition<ElementItem> BARIUM_INGOT = elementIngot(ElementType.BARIUM);
    public static final ItemDefinition<ElementItem> RADIUM_INGOT = elementIngot(ElementType.RADIUM);
    
    // Transition Metals - Period 4
    public static final ItemDefinition<ElementItem> SCANDIUM_INGOT = elementIngot(ElementType.SCANDIUM);
    public static final ItemDefinition<ElementItem> TITANIUM_INGOT = elementIngot(ElementType.TITANIUM);
    public static final ItemDefinition<ElementItem> VANADIUM_INGOT = elementIngot(ElementType.VANADIUM);
    public static final ItemDefinition<ElementItem> CHROMIUM_INGOT = elementIngot(ElementType.CHROMIUM);
    public static final ItemDefinition<ElementItem> MANGANESE_INGOT = elementIngot(ElementType.MANGANESE);
    public static final ItemDefinition<ElementItem> IRON_INGOT = elementIngot(ElementType.IRON);
    public static final ItemDefinition<ElementItem> COBALT_INGOT = elementIngot(ElementType.COBALT);
    public static final ItemDefinition<ElementItem> NICKEL_INGOT = elementIngot(ElementType.NICKEL);
    public static final ItemDefinition<ElementItem> COPPER_INGOT = elementIngot(ElementType.COPPER);
    public static final ItemDefinition<ElementItem> ZINC_INGOT = elementIngot(ElementType.ZINC);
    
    // Transition Metals - Period 5
    public static final ItemDefinition<ElementItem> YTTRIUM_INGOT = elementIngot(ElementType.YTTRIUM);
    public static final ItemDefinition<ElementItem> ZIRCONIUM_INGOT = elementIngot(ElementType.ZIRCONIUM);
    public static final ItemDefinition<ElementItem> NIOBIUM_INGOT = elementIngot(ElementType.NIOBIUM);
    public static final ItemDefinition<ElementItem> MOLYBDENUM_INGOT = elementIngot(ElementType.MOLYBDENUM);
    public static final ItemDefinition<ElementItem> TECHNETIUM_INGOT = elementIngot(ElementType.TECHNETIUM);
    public static final ItemDefinition<ElementItem> RUTHENIUM_INGOT = elementIngot(ElementType.RUTHENIUM);
    public static final ItemDefinition<ElementItem> RHODIUM_INGOT = elementIngot(ElementType.RHODIUM);
    public static final ItemDefinition<ElementItem> PALLADIUM_INGOT = elementIngot(ElementType.PALLADIUM);
    public static final ItemDefinition<ElementItem> SILVER_INGOT = elementIngot(ElementType.SILVER);
    public static final ItemDefinition<ElementItem> CADMIUM_INGOT = elementIngot(ElementType.CADMIUM);
    
    // Transition Metals - Period 6
    public static final ItemDefinition<ElementItem> LANTHANUM_INGOT = elementIngot(ElementType.LANTHANUM);
    public static final ItemDefinition<ElementItem> HAFNIUM_INGOT = elementIngot(ElementType.HAFNIUM);
    public static final ItemDefinition<ElementItem> TANTALUM_INGOT = elementIngot(ElementType.TANTALUM);
    public static final ItemDefinition<ElementItem> TUNGSTEN_INGOT = elementIngot(ElementType.TUNGSTEN);
    public static final ItemDefinition<ElementItem> RHENIUM_INGOT = elementIngot(ElementType.RHENIUM);
    public static final ItemDefinition<ElementItem> OSMIUM_INGOT = elementIngot(ElementType.OSMIUM);
    public static final ItemDefinition<ElementItem> IRIDIUM_INGOT = elementIngot(ElementType.IRIDIUM);
    public static final ItemDefinition<ElementItem> PLATINUM_INGOT = elementIngot(ElementType.PLATINUM);
    public static final ItemDefinition<ElementItem> GOLD_INGOT = elementIngot(ElementType.GOLD);
    public static final ItemDefinition<ElementItem> MERCURY_INGOT = elementIngot(ElementType.MERCURY);
    
    // Transition Metals - Period 7
    public static final ItemDefinition<ElementItem> ACTINIUM_INGOT = elementIngot(ElementType.ACTINIUM);
    public static final ItemDefinition<ElementItem> RUTHERFORDIUM_INGOT = elementIngot(ElementType.RUTHERFORDIUM);
    public static final ItemDefinition<ElementItem> DUBNIUM_INGOT = elementIngot(ElementType.DUBNIUM);
    public static final ItemDefinition<ElementItem> SEABORGIUM_INGOT = elementIngot(ElementType.SEABORGIUM);
    public static final ItemDefinition<ElementItem> BOHRIUM_INGOT = elementIngot(ElementType.BOHRIUM);
    public static final ItemDefinition<ElementItem> HASSIUM_INGOT = elementIngot(ElementType.HASSIUM);
    public static final ItemDefinition<ElementItem> MEITNERIUM_INGOT = elementIngot(ElementType.MEITNERIUM);
    public static final ItemDefinition<ElementItem> DARMSTADTIUM_INGOT = elementIngot(ElementType.DARMSTADTIUM);
    public static final ItemDefinition<ElementItem> ROENTGENIUM_INGOT = elementIngot(ElementType.ROENTGENIUM);
    public static final ItemDefinition<ElementItem> COPERNICIUM_INGOT = elementIngot(ElementType.COPERNICIUM);
    
    // Lanthanides (Rare Earth Metals)
    public static final ItemDefinition<ElementItem> CERIUM_INGOT = elementIngot(ElementType.CERIUM);
    public static final ItemDefinition<ElementItem> PRASEODYMIUM_INGOT = elementIngot(ElementType.PRASEODYMIUM);
    public static final ItemDefinition<ElementItem> NEODYMIUM_INGOT = elementIngot(ElementType.NEODYMIUM);
    public static final ItemDefinition<ElementItem> PROMETHIUM_INGOT = elementIngot(ElementType.PROMETHIUM);
    public static final ItemDefinition<ElementItem> SAMARIUM_INGOT = elementIngot(ElementType.SAMARIUM);
    public static final ItemDefinition<ElementItem> EUROPIUM_INGOT = elementIngot(ElementType.EUROPIUM);
    public static final ItemDefinition<ElementItem> GADOLINIUM_INGOT = elementIngot(ElementType.GADOLINIUM);
    public static final ItemDefinition<ElementItem> TERBIUM_INGOT = elementIngot(ElementType.TERBIUM);
    public static final ItemDefinition<ElementItem> DYSPROSIUM_INGOT = elementIngot(ElementType.DYSPROSIUM);
    public static final ItemDefinition<ElementItem> HOLMIUM_INGOT = elementIngot(ElementType.HOLMIUM);
    public static final ItemDefinition<ElementItem> ERBIUM_INGOT = elementIngot(ElementType.ERBIUM);
    public static final ItemDefinition<ElementItem> THULIUM_INGOT = elementIngot(ElementType.THULIUM);
    public static final ItemDefinition<ElementItem> YTTERBIUM_INGOT = elementIngot(ElementType.YTTERBIUM);
    public static final ItemDefinition<ElementItem> LUTETIUM_INGOT = elementIngot(ElementType.LUTETIUM);
    
    // Actinides
    public static final ItemDefinition<ElementItem> THORIUM_INGOT = elementIngot(ElementType.THORIUM);
    public static final ItemDefinition<ElementItem> PROTACTINIUM_INGOT = elementIngot(ElementType.PROTACTINIUM);
    public static final ItemDefinition<ElementItem> URANIUM_INGOT = elementIngot(ElementType.URANIUM);
    public static final ItemDefinition<ElementItem> NEPTUNIUM_INGOT = elementIngot(ElementType.NEPTUNIUM);
    public static final ItemDefinition<ElementItem> PLUTONIUM_INGOT = elementIngot(ElementType.PLUTONIUM);
    public static final ItemDefinition<ElementItem> AMERICIUM_INGOT = elementIngot(ElementType.AMERICIUM);
    public static final ItemDefinition<ElementItem> CURIUM_INGOT = elementIngot(ElementType.CURIUM);
    public static final ItemDefinition<ElementItem> BERKELIUM_INGOT = elementIngot(ElementType.BERKELIUM);
    public static final ItemDefinition<ElementItem> CALIFORNIUM_INGOT = elementIngot(ElementType.CALIFORNIUM);
    public static final ItemDefinition<ElementItem> EINSTEINIUM_INGOT = elementIngot(ElementType.EINSTEINIUM);
    public static final ItemDefinition<ElementItem> FERMIUM_INGOT = elementIngot(ElementType.FERMIUM);
    public static final ItemDefinition<ElementItem> MENDELEVIUM_INGOT = elementIngot(ElementType.MENDELEVIUM);
    public static final ItemDefinition<ElementItem> NOBELIUM_INGOT = elementIngot(ElementType.NOBELIUM);
    public static final ItemDefinition<ElementItem> LAWRENCIUM_INGOT = elementIngot(ElementType.LAWRENCIUM);
    
    // Post-Transition Metals
    public static final ItemDefinition<ElementItem> ALUMINUM_INGOT = elementIngot(ElementType.ALUMINUM);
    public static final ItemDefinition<ElementItem> GALLIUM_INGOT = elementIngot(ElementType.GALLIUM);
    public static final ItemDefinition<ElementItem> INDIUM_INGOT = elementIngot(ElementType.INDIUM);
    public static final ItemDefinition<ElementItem> TIN_INGOT = elementIngot(ElementType.TIN);
    public static final ItemDefinition<ElementItem> THALLIUM_INGOT = elementIngot(ElementType.THALLIUM);
    public static final ItemDefinition<ElementItem> LEAD_INGOT = elementIngot(ElementType.LEAD);
    public static final ItemDefinition<ElementItem> BISMUTH_INGOT = elementIngot(ElementType.BISMUTH);
    public static final ItemDefinition<ElementItem> NIHONIUM_INGOT = elementIngot(ElementType.NIHONIUM);
    public static final ItemDefinition<ElementItem> FLEROVIUM_INGOT = elementIngot(ElementType.FLEROVIUM);
    public static final ItemDefinition<ElementItem> MOSCOVIUM_INGOT = elementIngot(ElementType.MOSCOVIUM);
    public static final ItemDefinition<ElementItem> LIVERMORIUM_INGOT = elementIngot(ElementType.LIVERMORIUM);

    /**
     * Creates an element ingot with automatic naming and all associated items (raw, nugget)
     * @param element the element to create an ingot for
     * @return the ItemDefinition for the element ingot
     */
    public static ItemDefinition<ElementItem> elementIngot(ElementType element) {
        String name = element.getDisplayName() + " Ingot";
        return ingot(name, (properties) -> new ElementItem(properties, element));
    }

    static <T extends ElementItem> ItemDefinition<T> ingot(String name, Function<Item.Properties, T> factory) {
        // Create the main element item
        ItemDefinition<T> elementDef = item(name, factory);

        // Register the raw item
        String rawName = "Raw " + name.replace(" Ingot", "");
        String rawResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_raw");
        itemElementRaw(rawName, rawResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getRawItem();
        });
        
        // Register the nugget item
        String nuggetName = name.replace(" Ingot", " Nugget");
        String nuggetResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_nugget");
        itemElementNugget(nuggetName, nuggetResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getNuggetItem();
        });
        
        // Register the dust item
        String dustName = name.replace(" Ingot", " Dust");
        String dustResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_dust");
        itemDust(dustName, dustResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getDustItem();
        });
        
        // Register the plate item
        String plateName = name.replace(" Ingot", " Plate");
        String plateResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_plate");
        itemPlate(plateName, plateResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getPlateItem();
        });

        // Register the pile item
        String pileName = name.replace(" Ingot", " Pile");
        String pileResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_pile");
        itemPile(pileName, pileResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getPileItem();
        });

        // Register the rod item
        String rodName = name.replace(" Ingot", " Rod");
        String rodResourceName = name.toLowerCase().replace(' ', '_').replace("_ingot", "_rod");
        itemRod(rodName, rodResourceName, (Item.Properties properties) -> {
            T elementItem = elementDef.get();
            return elementItem.getRodItem();
        });
        
        return elementDef;
    }

    public static List<ItemDefinition<?>> getElements() {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends ElementItem> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, GM.getResource(resourceFriendly), factory, CoreTab.ELEMENTS);
    }

    static <T extends ElementItem> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementDustItem> itemDust (String name, String resourceName, Function<Item.Properties, ElementDustItem> factory) {
        return itemDust(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementDustItem> itemDust (String name, ResourceLocation id, Function<Item.Properties, ElementDustItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementPlateItem> itemPlate (String name, String resourceName, Function<Item.Properties, ElementPlateItem> factory) {
        return itemPlate(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementPlateItem> itemPlate (String name, ResourceLocation id, Function<Item.Properties, ElementPlateItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementPileItem> itemPile (String name, String resourceName, Function<Item.Properties, ElementPileItem> factory) {
        return itemPile(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementPileItem> itemPile (String name, ResourceLocation id, Function<Item.Properties, ElementPileItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementRodItem> itemRod (String name, String resourceName, Function<Item.Properties, ElementRodItem> factory) {
        return itemRod(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementRodItem> itemRod (String name, ResourceLocation id, Function<Item.Properties, ElementRodItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementRawItem> itemElementRaw (String name, String resourceName, Function<Item.Properties, ElementRawItem> factory) {
        return itemElementRaw(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementRawItem> itemElementRaw (String name, ResourceLocation id, Function<Item.Properties, ElementRawItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static ItemDefinition<ElementNuggetItem> itemElementNugget (String name, String resourceName, Function<Item.Properties, ElementNuggetItem> factory) {
        return itemElementNugget(name, GM.getResource(resourceName), factory, CoreTab.ELEMENTS);
    }

    static ItemDefinition<ElementNuggetItem> itemElementNugget (String name, ResourceLocation id, Function<Item.Properties, ElementNuggetItem> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(GM.MODID), "Can only register items in " + GM.MODID);
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, CoreTab.ELEMENTS)) {
            CoreTab.addElements(definition);
        } else if (group != null) {
            //CoreTab.addElementsElementsExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

}
