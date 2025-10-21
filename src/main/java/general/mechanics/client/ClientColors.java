package general.mechanics.client;

import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.block.plastic.ColoredPlasticBlock;
import general.mechanics.api.block.plastic.PlasticTypeBlock;
import general.mechanics.api.fluid.BaseFluid;
import general.mechanics.api.item.element.metallic.*;
import general.mechanics.api.item.plastic.ColoredPlasticItem;
import general.mechanics.api.item.plastic.PlasticTypeItem;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreFluids;
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

        // Fluid bucket item tinting: apply fluid color to overlay layer (layer1)
        for (var fluid : CoreFluids.getFluids()) {
            var type = fluid.getType().get();
            event.register((stack, index) -> index == 1 ? ((BaseFluid) type).getTintColor() : -1, fluid.getBucket().get());
        }

        // Fluid block item tinting: use the fluid type tint color on layer0
        for (var fluid : CoreFluids.getFluids()) {
            var type = fluid.getType().get();
            event.register((stack, index) -> index == 0 ? ((BaseFluid) type).getTintColor() : -1, fluid.getBlock().block());
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
            switch (item) {
                case ElementItem ignored -> event.register(ElementItem::getColor, item);
                case ElementRawItem ignored -> event.register(ElementRawItem::getColor, item);
                case ElementNuggetItem ignored -> event.register(ElementNuggetItem::getColor, item);
                case ElementDustItem ignored -> event.register(ElementDustItem::getColor, item);
                case ElementPlateItem ignored -> event.register(ElementPlateItem::getColor, item);
                case ElementPileItem ignored -> event.register(ElementPileItem::getColor, item);
                case ElementRodItem ignored -> event.register(ElementRodItem::getColor, item);
                default -> {
                }
            }
        }
    }

}
