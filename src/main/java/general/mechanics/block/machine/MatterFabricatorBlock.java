package general.mechanics.block.machine;

import com.mojang.serialization.MapCodec;
import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.entity.block.BaseEntityBlock;
import general.mechanics.api.util.data.PropertyHelper;
import general.mechanics.entity.block.MatterFabricatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MatterFabricatorBlock extends BaseEntityBlock<MatterFabricatorBlockEntity> {

    public MatterFabricatorBlock() {
        super(Blocks.IRON_BLOCK.properties());
    }

    public MatterFabricatorBlock(Properties properties) {
        this();
    }

    @Override
    public MapCodec<BaseBlock> getCodec () {
        return simpleCodec(MatterFabricatorBlock::new);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MatterFabricatorBlockEntity blockEntity) {
                player.openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.gm.matter_fabricator")), pos);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
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

    @Override
    public int getLightEmission (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return PropertyHelper.of(BlockStateProperties.POWERED, state).getValue() ? 16 : 8;
    }

    @Override
    public boolean hasDynamicLightEmission (@NotNull BlockState state) {
        return true;
    }

    @Override
    protected boolean useShapeForLightOcclusion (@NotNull BlockState state) {
        return true;
    }

    @Override
    public void animateTick (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!PropertyHelper.of(BlockStateProperties.POWERED, state).getValue() || !this.getBlockEntity(level, pos).isEnabled() || !this.getBlockEntity(level, pos).redstoneAllowsRunning()) {
            return;
        }

        double xPos = (double) pos.getX() + 0.5;
        double yPos = pos.getY();
        double zPos = (double) pos.getZ() + 0.5;

        if (random.nextDouble() < 0.1) {
            level.playLocalSound(xPos, yPos, zPos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction direction = PropertyHelper.of(BlockStateProperties.FACING, state).getValue();
        Direction.Axis axis = direction.getAxis();

        double defaultOffset = random.nextDouble() * 0.6 - 0.3;
        double xOffsets = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52 : defaultOffset;
        double yOffsets = random.nextDouble() * 6.0 / 8.0;
        double zOffsets = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52 : defaultOffset;

        // No longer doing this,
        /*if (level.getBlockEntity(pos) instanceof MatterFabricatorBlockEntity entity && !entity.getInventory().getStackInSlot(0).isEmpty()) {
            var stack = entity.getInventory().getStackInSlot(0);
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), xPos + xOffsets, yPos + yOffsets, zPos + zOffsets, 0d, 0d, 0d);
        }*/

    }

}
