package dimensional.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class CoreClient extends CoreBase {

    public CoreClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
