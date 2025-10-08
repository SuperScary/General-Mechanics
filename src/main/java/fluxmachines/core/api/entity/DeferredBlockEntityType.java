package fluxmachines.core.api.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public final class DeferredBlockEntityType<T extends BlockEntity> implements Supplier<BlockEntityType<T>> {

    private final Class<T> blockEntityClass;

    private final DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> holder;

    public DeferredBlockEntityType (Class<T> blockEntityClass, DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> holder) {
        this.blockEntityClass = blockEntityClass;
        this.holder = holder;
    }

    public Class<T> getBlockEntityClass () {
        return blockEntityClass;
    }

    @Override
    public BlockEntityType<T> get () {
        return holder.get();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public T getBlockEntity (BlockGetter level, BlockPos pos) {
        BlockEntity block = level.getBlockEntity(pos);
        return (T) (block != null && block.getType() == holder.get() ? block : null);
    }

}
