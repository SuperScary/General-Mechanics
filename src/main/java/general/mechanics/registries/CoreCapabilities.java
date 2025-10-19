package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.capability.heat.IHeater;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = GM.MODID)
public class CoreCapabilities {

    // Custom block capability for heaters (no sided context)
    public static final BlockCapability<IHeater, Void> HEATER = BlockCapability.create(GM.getResource("heater"), IHeater.class, Void.class);

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Matter Fabricator
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CoreBlockEntities.MATTER_FABRICATOR.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, CoreBlockEntities.MATTER_FABRICATOR.get(), (o, direction) -> o.getEnergyStorage());

        // Heating Element
        event.registerBlockEntity(HEATER, CoreBlockEntities.HEATING_ELEMENT.get(), (be, ctx) -> be);
    }

}
