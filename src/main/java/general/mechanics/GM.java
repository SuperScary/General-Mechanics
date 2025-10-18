package general.mechanics;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Collection;

public interface GM {

    String NAME = "General Mechanics";
    String MODID = "gm";

    Logger LOGGER = LoggerFactory.getLogger(NAME);

    static GM getInstance() {
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

    static Path gameDirectory() {
        return FMLPaths.GAMEDIR.get();
    }

    static Path configDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    static Path modsDirectory() {
        return FMLPaths.MODSDIR.get();
    }

    Collection<ServerPlayer> getPlayers();

    Level getClientLevel();

    MinecraftServer getCurrentServer();

}
