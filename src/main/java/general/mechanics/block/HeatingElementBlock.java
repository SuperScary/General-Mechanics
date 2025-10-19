package general.mechanics.block;

import com.mojang.serialization.MapCodec;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.entity.block.BaseEntityBlock;
import general.mechanics.entity.block.HeatingElementBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatingElementBlock extends BaseEntityBlock<HeatingElementBlockEntity> {

    public HeatingElementBlock(Properties properties) {
        this();
    }

    public HeatingElementBlock() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    @Override
    public MapCodec<BaseBlock> getCodec() {
        return simpleCodec(HeatingElementBlock::new);
    }

    @Override
    protected void tick (@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.tick(state, level, pos, random);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return ((lvl, pos, blockState, type) -> {
            if (type instanceof BaseBlockEntity)
                ((BaseBlockEntity) type).tick(lvl, pos, blockState);
        });
    }
}
