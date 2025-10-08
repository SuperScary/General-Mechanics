package fluxmachines.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class CoreServer extends CoreBase {

    public CoreServer(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return null;
    }
}
