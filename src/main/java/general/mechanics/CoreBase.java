package general.mechanics;

import general.mechanics.api.multiblock.MultiblockManager;
import general.mechanics.api.multiblock.feedback.MultiblockFeedbackHandler;
import general.mechanics.hooks.WrenchHooks;
import general.mechanics.registries.*;
import general.mechanics.tab.CoreTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public abstract class CoreBase implements GM {

    static GM INSTANCE;

    public CoreBase(ModContainer container, IEventBus modEventBus) {
        if (INSTANCE != null) throw new IllegalStateException("Already initialized!");
        INSTANCE = this;

        modEventBus.addListener(CoreTab::initExternal);

        register(modEventBus);

        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB)
                CoreTab.init(BuiltInRegistries.CREATIVE_MODE_TAB);
        });

        modEventBus.addListener(this::registerRegistries);


        MultiblockManager.init(modEventBus);
        NeoForge.EVENT_BUS.register(MultiblockFeedbackHandler.class);
        NeoForge.EVENT_BUS.addListener(WrenchHooks::onPlayerUseBlockEvent);

    }

    private void registerRegistries(NewRegistryEvent event) {
        CoreRegistries.registerRegistries(event);
    }

    private void register(IEventBus modEventBus) {
        CoreComponents.REGISTRY.register(modEventBus);
        CoreBlocks.REGISTRY.register(modEventBus);
        CoreItems.REGISTRY.register(modEventBus);
        CoreBlockEntities.REGISTRY.register(modEventBus);
        CoreMultiblocks.REGISTRY.register(modEventBus);
        CoreSounds.REGISTRY.register(modEventBus);
        CoreRecipes.SERIALIZER_REGISTRY.register(modEventBus);
        CoreRecipes.RECIPE_REGISTRY.register(modEventBus);
        CoreMenus.REGISTRY.register(modEventBus);
        CoreUpgrades.REGISTRY.register(modEventBus);
        CoreUpgradeRegistry.REGISTRY.register(modEventBus);
    }

    @Override
    public Collection<ServerPlayer> getPlayers() {
        var server = getCurrentServer();

        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
