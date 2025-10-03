package dimensional.core.api.block.ice;

import com.mojang.serialization.MapCodec;
import dimensional.core.api.block.base.BaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class IceBlock extends BaseBlock {

    public static final MapCodec<IceBlock> CODEC = simpleCodec(IceBlock::new);

    public @NotNull MapCodec<? extends IceBlock> codec() {
        return CODEC;
    }

    public IceBlock(Properties properties) {
        super(properties
                .pushReaction(PushReaction.DESTROY)
                .isValidSpawn((state, level, pos, type) -> false)
                .explosionResistance(0));
    }

    @Override
    protected void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 20); // 20 ticks = 1 second
        }
    }

    public boolean canMelt() {
        return true;
    }

    public static BlockState meltsInto() {
        return Blocks.WATER.defaultBlockState();
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!canMelt()) return;

        if (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos)) {
            this.melt(state, level, pos);
        }
    }

    @Override
    protected boolean skipRendering(@NotNull BlockState state, BlockState adjacentBlockState, @NotNull Direction side) {
        return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    protected void melt(BlockState state, Level level, BlockPos pos) {
        if (level.dimensionType().ultraWarm()) {
            level.removeBlock(pos, false);
        } else {
            level.setBlockAndUpdate(pos, meltsInto());
            level.neighborChanged(pos, meltsInto().getBlock(), pos);
        }

    }
}
