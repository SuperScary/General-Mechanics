package general.mechanics;

import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.item.element.metallic.*;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.api.item.plastic.RawPlasticItem;
import general.mechanics.gui.screen.MatterFabricatorScreen;
import general.mechanics.registries.CoreElements;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class CoreClient extends CoreBase {

    public CoreClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
        modEventBus.addListener(this::registerItemColors);
        modEventBus.addListener(this::registerBlockColors);
        modEventBus.addListener(this::registerElementColors);
        modEventBus.addListener(this::registerMenuScreens);
    }

    public void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (var item : CoreItems.getItems()) {
            if (item.get() instanceof RawPlasticItem plastic) {
                event.register(RawPlasticItem::getColorForItemStack, plastic);
            }

            if (item.get() instanceof PlasticItem plastic) {
                event.register(PlasticItem::getColorForItemStack, plastic);
            }
        }

        for (var block : PlasticBlock.getPlasticBlocks()) {
            event.register(PlasticBlock::getColorForItemStack, block);
        }
    }

    public void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        for (var block : PlasticBlock.getPlasticBlocks()) {
            event.register(PlasticBlock::getColorForBlock, block);
        }
    }

    public void registerElementColors(RegisterColorHandlersEvent.Item event) {
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
            }
        }
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CoreMenus.MATTER_FABRICATOR_MENU.get(), MatterFabricatorScreen::new);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
