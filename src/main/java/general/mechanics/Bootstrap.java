package general.mechanics;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(GM.MODID)
public class Bootstrap {

    public Bootstrap(ModContainer container, IEventBus modEventBus) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new CoreClient(container, modEventBus);
            case DEDICATED_SERVER -> new CoreServer(container, modEventBus);
        }
    }

}
