package general.mechanics.tab;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import general.mechanics.GM;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.item.base.BaseBlockItem;
import general.mechanics.api.item.base.BaseItem;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;

public class CoreTab {

    public static final ResourceKey<CreativeModeTab> MAIN = ResourceKey.create(Registries.CREATIVE_MODE_TAB, GM.getResource("main"));

    private static final Multimap<ResourceKey<CreativeModeTab>, ItemDefinition<?>> externalItemDefs = HashMultimap.create();
    private static final List<ItemDefinition<?>> itemDefs = new ArrayList<>();

    public static void init (Registry<CreativeModeTab> registry) {
        var tab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + GM.MODID))
                .icon(CoreBlocks.DRAKIUM_BLOCK::stack)
                .displayItems(CoreTab::buildDisplayItems)
                .build();
        Registry.register(registry, MAIN, tab);
    }

    public static void initExternal (BuildCreativeModeTabContentsEvent contents) {
        for (var itemDefinition : externalItemDefs.get(contents.getTabKey())) {
            contents.accept(itemDefinition);
        }
    }

    public static void add (ItemDefinition<?> itemDef) {
        itemDefs.add(itemDef);
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
            } else {
                output.accept(itemDef);
            }
        }
    }

}
