package general.mechanics.api.block.base;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBlock extends Block {

    protected BaseBlock (Properties properties) {
        super(properties);
    }

    public void addToCreativeTab (CreativeModeTab.Output output) {
        output.accept(this);
    }

    @Override
    public @NotNull String toString () {
        String regName = this.getRegistryName() != null ? this.getRegistryName().getPath() : "unregistered";
        return this.getClass().getSimpleName() + "[" + regName + "]";
    }

    @Nullable
    public Identifier getRegistryName () {
        var id = BuiltInRegistries.BLOCK.getKey(this);
        return id != BuiltInRegistries.BLOCK.getDefaultKey() ? id : null;
    }

}
