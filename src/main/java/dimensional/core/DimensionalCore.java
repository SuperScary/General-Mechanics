package dimensional.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface DimensionalCore {

    String NAME = "Dimensional Core";
    String MODID = "dimensionalcore";

    Logger LOGGER = LoggerFactory.getLogger(NAME);

    static DimensionalCore getInstance() {
        return CoreBase.INSTANCE;
    }

    static ResourceLocation getResource(String name) {
        return custom(MODID, name);
    }

    static ResourceLocation getMinecraftResource(String name) {
        return ResourceLocation.withDefaultNamespace(name);
    }

    static ResourceLocation custom(String id, String name) {
        return ResourceLocation.fromNamespaceAndPath(id, name);
    }

    Collection<ServerPlayer> getPlayers();

    Level getClientLevel();

    MinecraftServer getCurrentServer();

}
