package general.mechanics.client;

import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.block.plastic.ColoredPlasticBlock;
import general.mechanics.api.block.plastic.PlasticTypeBlock;
import general.mechanics.api.item.element.metallic.*;
import general.mechanics.api.item.plastic.ColoredPlasticItem;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ClientColors {

    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (var item : CoreItems.getItems()) {
            if (item.get() instanceof ColoredPlasticItem coloredPlastic) {
                event.register(ColoredPlasticItem::getColor, coloredPlastic);
            } else if (item.get() instanceof PlasticTypeItem plasticTypeItem) {
                event.register(PlasticTypeItem::getColor, plasticTypeItem);
            }
        }

        for (var block : CoreBlocks.getAllColoredPlasticBlocks()) {
            event.register(ColoredPlasticBlock::getColorForItemStack, block);
        }

        for (var block : CoreBlocks.getAllPlasticTypeBlocks()) {
            event.register(PlasticTypeBlock::getColorForItemStack, block);
        }

        for (var block : CoreBlocks.getOreBlocks()) {
            event.register(OreBlock::getColorForItemStack, block);
        }
    }

    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        for (var block : CoreBlocks.getAllColoredPlasticBlocks()) {
            event.register(ColoredPlasticBlock::getColor, block);
        }

        for (var block : CoreBlocks.getAllPlasticTypeBlocks()) {
            event.register(PlasticTypeBlock::getColor, block);
        }

        for (var block : CoreBlocks.getOreBlocks()) {
            event.register(OreBlock::getColor, block);
        }
    }

    public static void registerElementColors(RegisterColorHandlersEvent.Item event) {
        for (var itemDef : CoreElements.getElements()) {
            var item = itemDef.get();
            if (item instanceof ElementItem) {
                event.register(ElementItem::getColor, item);
            } else if (item instanceof ElementRawItem) {
                event.register(ElementRawItem::getColor, item);
            } else if (item instanceof ElementNuggetItem) {
                event.register(ElementNuggetItem::getColor, item);
            } else if (item instanceof ElementDustItem) {
                event.register(ElementDustItem::getColor, item);
            } else if (item instanceof ElementPlateItem) {
                event.register(ElementPlateItem::getColor, item);
            } else if (item instanceof ElementPileItem) {
                event.register(ElementPileItem::getColor, item);
            } else if (item instanceof ElementRodItem) {
                event.register(ElementRodItem::getColor, item);
            }
        }
    }

}
