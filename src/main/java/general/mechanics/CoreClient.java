package general.mechanics;

import general.mechanics.api.block.plastic.PlasticBlock;
import general.mechanics.api.item.plastic.PlasticItem;
import general.mechanics.api.item.plastic.RawPlasticItem;
import general.mechanics.gui.screen.MatterFabricatorScreen;
import general.mechanics.registries.CoreMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static general.mechanics.registries.CoreBlocks.*;
import static general.mechanics.registries.CoreItems.*;

public class CoreClient extends CoreBase {

    public CoreClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
        modEventBus.addListener(this::registerItemColors);
        modEventBus.addListener(this::registerBlockColors);
        modEventBus.addListener(this::registerMenuScreens);
    }

    public void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(RawPlasticItem::getColorForItemStack, RAW_PLASTIC, RAW_PLASTIC_ORANGE, RAW_PLASTIC_MAGENTA,
                RAW_PLASTIC_LIGHT_BLUE, RAW_PLASTIC_YELLOW, RAW_PLASTIC_LIME, RAW_PLASTIC_PINK, RAW_PLASTIC_GRAY,
                RAW_PLASTIC_LIGHT_GRAY, RAW_PLASTIC_CYAN, RAW_PLASTIC_PURPLE, RAW_PLASTIC_BLUE, RAW_PLASTIC_BROWN,
                RAW_PLASTIC_GREEN, RAW_PLASTIC_RED, RAW_PLASTIC_BLACK);

        event.register(PlasticItem::getColorForItemStack, PLASTIC, PLASTIC_ORANGE, PLASTIC_MAGENTA,
                PLASTIC_LIGHT_BLUE, PLASTIC_YELLOW, PLASTIC_LIME, PLASTIC_PINK, PLASTIC_GRAY,
                PLASTIC_LIGHT_GRAY, PLASTIC_CYAN, PLASTIC_PURPLE, PLASTIC_BLUE, PLASTIC_BROWN,
                PLASTIC_GREEN, PLASTIC_RED, PLASTIC_BLACK);

        event.register(PlasticBlock::getColorForItemStack, PLASTIC_BLOCK.block(), PLASTIC_BLOCK_ORANGE.block(), PLASTIC_BLOCK_MAGENTA.block(),
                PLASTIC_BLOCK_LIGHT_BLUE.block(), PLASTIC_BLOCK_YELLOW.block(), PLASTIC_BLOCK_LIME.block(), PLASTIC_BLOCK_PINK.block(), PLASTIC_BLOCK_GRAY.block(),
                PLASTIC_BLOCK_LIGHT_GRAY.block(), PLASTIC_BLOCK_CYAN.block(), PLASTIC_BLOCK_PURPLE.block(), PLASTIC_BLOCK_BLUE.block(), PLASTIC_BLOCK_BROWN.block(),
                PLASTIC_BLOCK_GREEN.block(), PLASTIC_BLOCK_RED.block(), PLASTIC_BLOCK_BLACK.block());
    }

    public void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(PlasticBlock::getColorForBlock, PLASTIC_BLOCK.block(), PLASTIC_BLOCK_ORANGE.block(), PLASTIC_BLOCK_MAGENTA.block(),
                PLASTIC_BLOCK_LIGHT_BLUE.block(), PLASTIC_BLOCK_YELLOW.block(), PLASTIC_BLOCK_LIME.block(), PLASTIC_BLOCK_PINK.block(), PLASTIC_BLOCK_GRAY.block(),
                PLASTIC_BLOCK_LIGHT_GRAY.block(), PLASTIC_BLOCK_CYAN.block(), PLASTIC_BLOCK_PURPLE.block(), PLASTIC_BLOCK_BLUE.block(), PLASTIC_BLOCK_BROWN.block(),
                PLASTIC_BLOCK_GREEN.block(), PLASTIC_BLOCK_RED.block(), PLASTIC_BLOCK_BLACK.block());
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CoreMenus.MATTER_FABRICATOR_MENU.get(), MatterFabricatorScreen::new);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
