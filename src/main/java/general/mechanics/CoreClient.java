package general.mechanics;

import general.mechanics.client.ClientColors;
import general.mechanics.client.ClientItemDecorators;
import general.mechanics.gui.screen.MatterFabricatorScreen;
import general.mechanics.registries.CoreMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class CoreClient extends CoreBase {

    public CoreClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);

        modEventBus.addListener(ClientColors::registerItemColors);
        modEventBus.addListener(ClientColors::registerBlockColors);
        modEventBus.addListener(ClientColors::registerElementColors);
        modEventBus.addListener(ClientItemDecorators::registerDecorators);
        modEventBus.addListener(this::registerMenuScreens);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CoreMenus.MATTER_FABRICATOR_MENU.get(), MatterFabricatorScreen::new);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
