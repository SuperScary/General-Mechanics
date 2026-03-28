package general.mechanics.registries;

import java.util.function.BiFunction;

import general.mechanics.GM;
import general.mechanics.api.capability.heat.IHeater;
import general.mechanics.api.capability.AutomationAccess;
import general.mechanics.api.component.io.ISidedEnergyAccess;
import general.mechanics.api.component.io.ISidedFluidAccess;
import general.mechanics.api.component.io.ISidedItemAccess;
import general.mechanics.api.energy.PoweredBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
        registerSidedItemAccess(event);
        registerSidedFluidAccess(event);
        registerPoweredEnergy(event);

        // Heating Element
        event.registerBlockEntity(HEATER, CoreBlockEntities.HEATING_ELEMENT.get(), (be, _) -> be);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> void registerForImplementors(RegisterCapabilitiesEvent event, Class<?> iface, BlockCapability<T, Direction> capability, BiFunction<Object, Direction, T> provider) {
        for (var type : CoreBlockEntities.getImplementorsOf(iface)) {
            event.registerBlockEntity(capability, (BlockEntityType) type, provider::apply);
        }
    }

    private static void registerSidedItemAccess(RegisterCapabilitiesEvent event) {
        registerForImplementors(event, ISidedItemAccess.class, Capabilities.Item.BLOCK, (be, direction) -> {
            var sided = ((ISidedItemAccess) be).getSidedItemIO();
            if (!AutomationAccess.allow(direction)) {
                return sided.forAutomationWithoutSide();
            }
            return sided.forSide(direction);
        });
    }

    private static void registerPoweredEnergy(RegisterCapabilitiesEvent event) {
        registerForImplementors(event, PoweredBlock.class, Capabilities.Energy.BLOCK, (be, direction) -> {
            if (be instanceof ISidedEnergyAccess sided) {
                var io = sided.getSidedEnergyIO();
                if (!AutomationAccess.allow(direction)) {
                    return io.forAutomationWithoutSide();
                }
                return io.forSide(direction);
            }

            return ((PoweredBlock) be).getEnergyStorage();
        });
    }

    private static void registerSidedFluidAccess(RegisterCapabilitiesEvent event) {
        registerForImplementors(event, ISidedFluidAccess.class, Capabilities.Fluid.BLOCK, (be, direction) -> {
            var sided = ((ISidedFluidAccess) be).getSidedFluidIO();
            if (!AutomationAccess.allow(direction)) {
                return sided.forAutomationWithoutSide();
            }
            return sided.forSide(direction);
        });
    }

}
