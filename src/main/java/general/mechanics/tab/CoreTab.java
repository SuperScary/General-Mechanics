package general.mechanics.tab;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import general.mechanics.GM;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.BaseBlockItem;
import general.mechanics.api.item.base.BaseItem;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreFluids;
import general.mechanics.registries.CoreItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;

public class CoreTab {

    public static final ResourceKey<CreativeModeTab> MAIN = ResourceKey.create(Registries.CREATIVE_MODE_TAB, GM.getResource("main"));
    public static final ResourceKey<CreativeModeTab> ELEMENTS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, GM.getResource("elements"));
    public static final ResourceKey<CreativeModeTab> FLUIDS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, GM.getResource("fluids"));

    private static final Multimap<ResourceKey<CreativeModeTab>, ItemDefinition<?>> externalItemDefs = HashMultimap.create();
    private static final List<ItemDefinition<?>> itemDefs = new ArrayList<>();
    private static final List<ItemDefinition<?>> elementDefs = new ArrayList<>();
    private static final List<ItemDefinition<?>> fluidDefs = new ArrayList<>();

    public static void init (Registry<CreativeModeTab> registry) {
        var mainTab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + GM.MODID))
                .icon(CoreItems.WRENCH::stack)
                .displayItems(CoreTab::buildDisplayItems)
                .build();
        Registry.register(registry, MAIN, mainTab);

        var elementsTab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + GM.MODID + ".elements"))
                .icon(CoreElements.EINSTEINIUM_INGOT::stack)
                .displayItems(CoreTab::buildElementDisplayItems)
                .build();
        Registry.register(registry, ELEMENTS, elementsTab);

        var fluidsTab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + GM.MODID + ".fluids"))
                .icon(CoreFluids.CRUDE_OIL.getBucket()::stack)
                .displayItems(CoreTab::buildFluidDisplayItems)
                .build();
        Registry.register(registry, FLUIDS, fluidsTab);
    }

    public static void initExternal (BuildCreativeModeTabContentsEvent contents) {
        for (var itemDefinition : externalItemDefs.get(contents.getTabKey())) {
            contents.accept(itemDefinition);
        }
    }

    public static void add (ItemDefinition<?> itemDef) {
        itemDefs.add(itemDef);
    }

    public static void addElements (ItemDefinition<?> itemDef) {
        elementDefs.add(itemDef);
    }

    public static void addFluids (ItemDefinition<?> itemDef) {
        fluidDefs.add(itemDef);
    }

    public static void addExternal (ResourceKey<CreativeModeTab> tab, ItemDefinition<?> itemDef) {
        externalItemDefs.put(tab, itemDef);
    }

    private static void buildDisplayItems (CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        for (var itemDef : itemDefs) {
            var item = itemDef.asItem();
            if (item instanceof BaseBlockItem baseItem && baseItem.getBlock() instanceof BaseBlock baseBlock) {
                baseBlock.addToCreativeTab(output);
            } else if (item instanceof BaseItem baseItem) {
                baseItem.addToCreativeTab(output);
            } else if (!(item instanceof BaseBlockItem base && base.getBlock() instanceof LiquidBlock || item instanceof BucketItem)){
                output.accept(itemDef);
            }
        }
    }

    private static void buildElementDisplayItems (CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        for (var itemDef : elementDefs) {
            var item = itemDef.get();
            if (item instanceof BaseBlockItem baseItem && baseItem.getBlock() instanceof BaseBlock baseBlock) {
                baseBlock.addToCreativeTab(output);
            } else if (item instanceof BaseItem baseItem) {
                baseItem.addToCreativeTab(output);
            } else {
                output.accept(itemDef);
            }
        }
    }

    private static void buildFluidDisplayItems (CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        for (var itemDef : fluidDefs) {
            var item = itemDef.get();
            if (item instanceof BaseBlockItem baseItem && baseItem.getBlock() instanceof BaseBlock baseBlock) {
                baseBlock.addToCreativeTab(output);
            } else if (item instanceof BaseItem baseItem) {
                baseItem.addToCreativeTab(output);
            } else {
                output.accept(itemDef);
            }
        }
    }

}
