package general.mechanics.registries;

import com.google.common.base.Preconditions;
import general.mechanics.GM;
import general.mechanics.api.electrical.capacitors.CapacitorItem;
import general.mechanics.api.electrical.capacitors.CapacitorType;
import general.mechanics.api.electrical.ics.IntegratedCircuitItem;
import general.mechanics.api.electrical.ics.IntegratedCircuitType;
import general.mechanics.api.electrical.resistors.ResistorItem;
import general.mechanics.api.electrical.resistors.ResistorType;
import general.mechanics.api.electrical.transformers.TransformerItem;
import general.mechanics.api.electrical.transformers.TransformerType;
import general.mechanics.api.electrical.transistor.TransistorItem;
import general.mechanics.api.electrical.transistor.TransistorType;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.*;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.api.item.plastic.ColoredPlasticItem;
import general.mechanics.api.item.plastic.PlasticType;
import general.mechanics.api.item.tools.*;
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
    // Capacitors
    public static final ItemDefinition<CapacitorItem> CAPACITOR_PF_100 = item(CapacitorType.PF_100.getDisplayValue() + " Capacitor", "capacitor_pf_100", (properties) -> new CapacitorItem(properties, CapacitorType.PF_100));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_PF_220 = item(CapacitorType.PF_220.getDisplayValue() + " Capacitor", "capacitor_pf_220", (properties) -> new CapacitorItem(properties, CapacitorType.PF_220));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_PF_470 = item(CapacitorType.PF_470.getDisplayValue() + " Capacitor", "capacitor_pf_470", (properties) -> new CapacitorItem(properties, CapacitorType.PF_470));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_NF_1   = item(CapacitorType.NF_1.getDisplayValue() + " Capacitor",   "capacitor_nf_1",   (properties) -> new CapacitorItem(properties, CapacitorType.NF_1));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_NF_10  = item(CapacitorType.NF_10.getDisplayValue() + " Capacitor",  "capacitor_nf_10",  (properties) -> new CapacitorItem(properties, CapacitorType.NF_10));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_NF_100 = item(CapacitorType.NF_100.getDisplayValue() + " Capacitor", "capacitor_nf_100", (properties) -> new CapacitorItem(properties, CapacitorType.NF_100));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_1   = item(CapacitorType.UF_1.getDisplayValue() + " Capacitor",   "capacitor_uf_1",   (properties) -> new CapacitorItem(properties, CapacitorType.UF_1));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_4_7 = item(CapacitorType.UF_4_7.getDisplayValue() + " Capacitor", "capacitor_uf_4_7", (properties) -> new CapacitorItem(properties, CapacitorType.UF_4_7));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_10  = item(CapacitorType.UF_10.getDisplayValue() + " Capacitor",  "capacitor_uf_10",  (properties) -> new CapacitorItem(properties, CapacitorType.UF_10));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_47  = item(CapacitorType.UF_47.getDisplayValue() + " Capacitor",  "capacitor_uf_47",  (properties) -> new CapacitorItem(properties, CapacitorType.UF_47));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_100 = item(CapacitorType.UF_100.getDisplayValue() + " Capacitor", "capacitor_uf_100", (properties) -> new CapacitorItem(properties, CapacitorType.UF_100));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_220 = item(CapacitorType.UF_220.getDisplayValue() + " Capacitor", "capacitor_uf_220", (properties) -> new CapacitorItem(properties, CapacitorType.UF_220));
    public static final ItemDefinition<CapacitorItem> CAPACITOR_UF_470 = item(CapacitorType.UF_470.getDisplayValue() + " Capacitor", "capacitor_uf_470", (properties) -> new CapacitorItem(properties, CapacitorType.UF_470));

    // Resistors
    public static final ItemDefinition<ResistorItem> RESISTOR_10 = item(ResistorType.OHM_10.getDisplayValue() + " Resistor", "resistor_10", (properties) -> new ResistorItem(properties, ResistorType.OHM_10));
    public static final ItemDefinition<ResistorItem> RESISTOR_100 = item(ResistorType.OHM_100.getDisplayValue() + " Resistor", "resistor_100", (properties) -> new ResistorItem(properties, ResistorType.OHM_100));
    public static final ItemDefinition<ResistorItem> RESISTOR_220 = item(ResistorType.OHM_220.getDisplayValue() + " Resistor", "resistor_220", (properties) -> new ResistorItem(properties, ResistorType.OHM_220));
    public static final ItemDefinition<ResistorItem> RESISTOR_330 = item(ResistorType.OHM_330.getDisplayValue() + " Resistor", "resistor_330", (properties) -> new ResistorItem(properties, ResistorType.OHM_330));
    public static final ItemDefinition<ResistorItem> RESISTOR_470 = item(ResistorType.OHM_470.getDisplayValue() + " Resistor", "resistor_470", (properties) -> new ResistorItem(properties, ResistorType.OHM_470));
    public static final ItemDefinition<ResistorItem> RESISTOR_1K = item(ResistorType.OHM_1K.getDisplayValue() + " Resistor", "resistor_1k", (properties) -> new ResistorItem(properties, ResistorType.OHM_1K));
    public static final ItemDefinition<ResistorItem> RESISTOR_4_7K = item(ResistorType.OHM_4_7K.getDisplayValue() + " Resistor", "resistor_4_7k", (properties) -> new ResistorItem(properties, ResistorType.OHM_4_7K));
    public static final ItemDefinition<ResistorItem> RESISTOR_10K = item(ResistorType.OHM_10K.getDisplayValue() + " Resistor", "resistor_10k", (properties) -> new ResistorItem(properties, ResistorType.OHM_10K));
    public static final ItemDefinition<ResistorItem> RESISTOR_47K = item(ResistorType.OHM_47K.getDisplayValue() + " Resistor", "resistor_47k", (properties) -> new ResistorItem(properties, ResistorType.OHM_47K));
    public static final ItemDefinition<ResistorItem> RESISTOR_100K = item(ResistorType.OHM_100K.getDisplayValue() + " Resistor", "resistor_100k", (properties) -> new ResistorItem(properties, ResistorType.OHM_100K));

    // Transistors
    public static final ItemDefinition<TransistorItem> TRANSISTOR_NPN_BJT = item(TransistorType.NPN_BJT.getDisplayName(), "transistor_npn_bjt", (properties) -> new TransistorItem(properties, TransistorType.NPN_BJT));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_PNP_BJT = item(TransistorType.PNP_BJT.getDisplayName(), "transistor_pnp_bjt", (properties) -> new TransistorItem(properties, TransistorType.PNP_BJT));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_N_CHANNEL_MOSFET = item(TransistorType.N_CHANNEL_MOSFET.getDisplayName(), "transistor_n_channel_mosfet", (properties) -> new TransistorItem(properties, TransistorType.N_CHANNEL_MOSFET));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_P_CHANNEL_MOSFET = item(TransistorType.P_CHANNEL_MOSFET.getDisplayName(), "transistor_p_channel_mosfet", (properties) -> new TransistorItem(properties, TransistorType.P_CHANNEL_MOSFET));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_N_CHANNEL_JFET = item(TransistorType.N_CHANNEL_JFET.getDisplayName(), "transistor_n_channel_jfet", (properties) -> new TransistorItem(properties, TransistorType.N_CHANNEL_JFET));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_P_CHANNEL_JFET = item(TransistorType.P_CHANNEL_JFET.getDisplayName(), "transistor_p_channel_jfet", (properties) -> new TransistorItem(properties, TransistorType.P_CHANNEL_JFET));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_DARLINGTON_NPN = item(TransistorType.DARLINGTON_NPN.getDisplayName(), "transistor_darlington_npn", (properties) -> new TransistorItem(properties, TransistorType.DARLINGTON_NPN));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_DARLINGTON_PNP = item(TransistorType.DARLINGTON_PNP.getDisplayName(), "transistor_darlington_pnp", (properties) -> new TransistorItem(properties, TransistorType.DARLINGTON_PNP));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_IGBT = item(TransistorType.IGBT.getDisplayName(), "transistor_igbt", (properties) -> new TransistorItem(properties, TransistorType.IGBT));
    public static final ItemDefinition<TransistorItem> TRANSISTOR_PHOTO = item(TransistorType.PHOTO_TRANSISTOR.getDisplayName(), "transistor_photo", (properties) -> new TransistorItem(properties, TransistorType.PHOTO_TRANSISTOR));

    // Transformers
    public static final ItemDefinition<TransformerItem> TRANSFORMER_STEP_UP = item(TransformerType.STEP_UP.getDisplayName(), "transformer_step_up", (properties) -> new TransformerItem(properties, TransformerType.STEP_UP));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_STEP_DOWN = item(TransformerType.STEP_DOWN.getDisplayName(), "transformer_step_down", (properties) -> new TransformerItem(properties, TransformerType.STEP_DOWN));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_ISOLATION = item(TransformerType.ISOLATION.getDisplayName(), "transformer_isolation", (properties) -> new TransformerItem(properties, TransformerType.ISOLATION));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_CENTER_TAPPED = item(TransformerType.CENTER_TAPPED.getDisplayName(), "transformer_center_tapped", (properties) -> new TransformerItem(properties, TransformerType.CENTER_TAPPED));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_AUTOTRANSFORMER = item(TransformerType.AUTOTRANSFORMER.getDisplayName(), "transformer_autotransformer", (properties) -> new TransformerItem(properties, TransformerType.AUTOTRANSFORMER));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_TOROIDAL = item(TransformerType.TOROIDAL.getDisplayName(), "transformer_toroidal", (properties) -> new TransformerItem(properties, TransformerType.TOROIDAL));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_FLYBACK = item(TransformerType.FLYBACK.getDisplayName(), "transformer_flyback", (properties) -> new TransformerItem(properties, TransformerType.FLYBACK));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_AUDIO = item(TransformerType.AUDIO.getDisplayName(), "transformer_audio", (properties) -> new TransformerItem(properties, TransformerType.AUDIO));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_PULSE = item(TransformerType.PULSE.getDisplayName(), "transformer_pulse", (properties) -> new TransformerItem(properties, TransformerType.PULSE));
    public static final ItemDefinition<TransformerItem> TRANSFORMER_CURRENT = item(TransformerType.CURRENT.getDisplayName(), "transformer_current", (properties) -> new TransformerItem(properties, TransformerType.CURRENT));

    // Integrated Circuits
    public static final ItemDefinition<IntegratedCircuitItem> IC_7400_ITEM = item(IntegratedCircuitType.IC_7400.getDisplayName(), "ic_7400_nand", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7400));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7402_ITEM = item(IntegratedCircuitType.IC_7402.getDisplayName(), "ic_7402_nor", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7402));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7404_ITEM = item(IntegratedCircuitType.IC_7404.getDisplayName(), "ic_7404_inverter", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7404));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7408_ITEM = item(IntegratedCircuitType.IC_7408.getDisplayName(), "ic_7408_and", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7408));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7432_ITEM = item(IntegratedCircuitType.IC_7432.getDisplayName(), "ic_7432_or", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7432));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7486_ITEM = item(IntegratedCircuitType.IC_7486.getDisplayName(), "ic_7486_xor", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7486));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7474_ITEM = item(IntegratedCircuitType.IC_7474.getDisplayName(), "ic_7474_d_flip_flop", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7474));
    public static final ItemDefinition<IntegratedCircuitItem> IC_74138_ITEM = item(IntegratedCircuitType.IC_74138.getDisplayName(), "ic_74138_decoder", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_74138));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7447_ITEM = item(IntegratedCircuitType.IC_7447.getDisplayName(), "ic_7447_bcd_decoder", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7447));
    public static final ItemDefinition<IntegratedCircuitItem> IC_4017_ITEM = item(IntegratedCircuitType.IC_4017.getDisplayName(), "ic_4017_counter", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_4017));
    public static final ItemDefinition<IntegratedCircuitItem> IC_555_ITEM = item(IntegratedCircuitType.IC_555.getDisplayName(), "ic_555_timer", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_555));
    public static final ItemDefinition<IntegratedCircuitItem> IC_556_ITEM = item(IntegratedCircuitType.IC_556.getDisplayName(), "ic_556_dual_timer", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_556));
    public static final ItemDefinition<IntegratedCircuitItem> IC_4040_ITEM = item(IntegratedCircuitType.IC_4040.getDisplayName(), "ic_4040_counter", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_4040));
    public static final ItemDefinition<IntegratedCircuitItem> IC_4093_ITEM = item(IntegratedCircuitType.IC_4093.getDisplayName(), "ic_4093_schmitt_trigger", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_4093));
    public static final ItemDefinition<IntegratedCircuitItem> IC_741_OPAMP_ITEM = item(IntegratedCircuitType.IC_741_OPAMP.getDisplayName(), "ic_741_op_amp", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_741_OPAMP));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM324_ITEM = item(IntegratedCircuitType.IC_LM324.getDisplayName(), "ic_lm324_quad_op_amp", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM324));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM358_ITEM = item(IntegratedCircuitType.IC_LM358.getDisplayName(), "ic_lm358_dual_op_amp", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM358));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM339_ITEM = item(IntegratedCircuitType.IC_LM339.getDisplayName(), "ic_lm339_comparator", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM339));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM393_ITEM = item(IntegratedCircuitType.IC_LM393.getDisplayName(), "ic_lm393_comparator", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM393));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM386_ITEM = item(IntegratedCircuitType.IC_LM386.getDisplayName(), "ic_lm386_audio_amp", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM386));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7805_ITEM = item(IntegratedCircuitType.IC_7805.getDisplayName(), "ic_lm7805_regulator", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7805));
    public static final ItemDefinition<IntegratedCircuitItem> IC_7812_ITEM = item(IntegratedCircuitType.IC_7812.getDisplayName(), "ic_lm7812_regulator", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_7812));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM317_ITEM = item(IntegratedCircuitType.IC_LM317.getDisplayName(), "ic_lm317_regulator", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM317));
    public static final ItemDefinition<IntegratedCircuitItem> IC_L293D_ITEM = item(IntegratedCircuitType.IC_L293D.getDisplayName(), "ic_l293d_motor_driver", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_L293D));
    public static final ItemDefinition<IntegratedCircuitItem> IC_ULN2003_ITEM = item(IntegratedCircuitType.IC_ULN2003.getDisplayName(), "ic_uln2003_driver_array", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_ULN2003));
    public static final ItemDefinition<IntegratedCircuitItem> IC_MAX232_ITEM = item(IntegratedCircuitType.IC_MAX232.getDisplayName(), "ic_max232_level_shifter", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_MAX232));
    public static final ItemDefinition<IntegratedCircuitItem> IC_LM75_ITEM = item(IntegratedCircuitType.IC_LM75.getDisplayName(), "ic_lm75_temperature_sensor", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_LM75));
    public static final ItemDefinition<IntegratedCircuitItem> IC_NE567_ITEM = item(IntegratedCircuitType.IC_NE567.getDisplayName(), "ic_ne567_tone_decoder", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_NE567));
    public static final ItemDefinition<IntegratedCircuitItem> IC_24C02_ITEM = item(IntegratedCircuitType.IC_24C02.getDisplayName(), "ic_24c02_eeprom", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_24C02));
    public static final ItemDefinition<IntegratedCircuitItem> IC_ATMEGA328P_ITEM = item(IntegratedCircuitType.IC_ATMEGA328P.getDisplayName(), "ic_atmega328p_microcontroller", (properties) -> new IntegratedCircuitItem(properties, IntegratedCircuitType.IC_ATMEGA328P));

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

    // Tools
    public static final ItemDefinition<WrenchItem> WRENCH = item("Wrench", WrenchItem::new);
    public static final ItemDefinition<FlatheadScrewdriverItem> FLATHEAD_SCREWDRIVER = item("Flathead Screwdriver", FlatheadScrewdriverItem::new);
    public static final ItemDefinition<PhillipsScrewdriverItem> PHILLIPS_SCREWDRIVER = item("Phillips Screwdriver", PhillipsScrewdriverItem::new);
    public static final ItemDefinition<HammerItem> HAMMER = item("Hammer", HammerItem::new);
    public static final ItemDefinition<SocketDriverItem> SOCKET_DRIVER = item("Socket Driver", SocketDriverItem::new);
    public static final ItemDefinition<WireCuttersItem> WIRE_CUTTERS = item("Wire Cutters", WireCuttersItem::new);
    public static final ItemDefinition<SawItem> SAW = item("Saw", SawItem::new);
    public static final ItemDefinition<FileItem> FILE = item("File", FileItem::new);

    // Misc
    public static final ItemDefinition<BaseItem> BOLT = item("Bolt", BaseItem::new);

    public static List<ItemDefinition<?>> getItems() {
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

    /**
     * Get all colored variants for a specific plastic type
     */
    public static List<BaseItem> getColoredPlasticsForType(PlasticType plasticType) {
        List<BaseItem> coloredVariants = new ArrayList<>();
        for (var item : ITEMS) {
            if (item.get() instanceof ColoredPlasticItem colored) {
                if (colored.getParentPlastic().getPlasticType() == plasticType) {
                    coloredVariants.add(colored);
                    if (!coloredVariants.contains(colored.getParentPlastic()))
                        coloredVariants.add(colored.getParentPlastic());
                }
            }
        }
        return coloredVariants;
    }

    static <T extends Item> ItemDefinition<T> item(String name, Function<Item.Properties, T> factory) {
        String resourceFriendly = name.toLowerCase().replace(' ', '_');
        return item(name, GM.getResource(resourceFriendly), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item(final String name, String resourceName, Function<Item.Properties, T> factory) {
        return item(name, GM.getResource(resourceName), factory, CoreTab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item(String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
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

    /**
     * Formats a color name by capitalizing each word and replacing underscores with spaces.
     */
    private static String formatColorName(String colorName) {
        String[] words = colorName.split("_");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formatted.append(" ");
            }
            formatted.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1));
        }
        return formatted.toString();
    }

    static <T extends PlasticTypeItem> ItemDefinition<T> plasticType(String name, Function<Item.Properties, T> factory) {
        // Create the main plastic type item
        ItemDefinition<T> plasticTypeDef = item(name, factory);

        // Register all colored plastic variants
        for (DyeColor color : PlasticType.getAllColors()) {
            String coloredName = formatColorName(color.getName()) + " " + name;
            String coloredResourceName = color.getName().toLowerCase() + "_" + name.toLowerCase().replace(' ', '_');
            itemColoredPlastic(coloredName, GM.getResource(coloredResourceName), (Item.Properties properties) -> {
                T plasticTypeItem = plasticTypeDef.get();
                return plasticTypeItem.getColoredVariant(color);
            }, CoreTab.MAIN);
        }

        return plasticTypeDef;
    }

}
