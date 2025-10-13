package general.mechanics.registries;

import general.mechanics.GM;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = GM.MODID)
public class CoreCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Matter Fabricator
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CoreBlockEntities.MATTER_FABRICATOR.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, CoreBlockEntities.MATTER_FABRICATOR.get(), (o, direction) -> o.getEnergyStorage());
    }

}
