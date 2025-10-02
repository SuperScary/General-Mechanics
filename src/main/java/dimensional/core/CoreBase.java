package dimensional.core;

import dimensional.core.api.multiblock.MultiblockManager;
import dimensional.core.api.multiblock.MultiblockRegistry;
import dimensional.core.api.multiblock.feedback.MultiblockFeedbackHandler;
import dimensional.core.registries.CoreBlocks;
import dimensional.core.registries.CoreItems;
import dimensional.core.registries.CoreMultiblocks;
import dimensional.core.tab.CoreTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public abstract class CoreBase implements DimensionalCore {

    static DimensionalCore INSTANCE;

    public CoreBase(ModContainer container, IEventBus modEventBus) {
        if (INSTANCE != null) throw new IllegalStateException("Already initialized!");
        INSTANCE = this;

        modEventBus.addListener(CoreTab::initExternal);

        register(modEventBus);

        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) CoreTab.init(BuiltInRegistries.CREATIVE_MODE_TAB);
        });

        modEventBus.addListener(this::registerRegistries);
        
        // Initialize multiblock system
        MultiblockManager.init(modEventBus);
        // Register feedback handler on NeoForge event bus since our custom events are not mod bus events
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(MultiblockFeedbackHandler.class);

    }

    private void registerRegistries (NewRegistryEvent event) {
           event.register(MultiblockRegistry.REGISTRY);
    }

    private void register(IEventBus modEventBus) {
        CoreBlocks.REGISTRY.register(modEventBus);
        CoreItems.REGISTRY.register(modEventBus);
        CoreMultiblocks.REGISTRY.register(modEventBus);
    }

    @Override
    public Collection<ServerPlayer> getPlayers () {
        var server = getCurrentServer();

        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer () {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
