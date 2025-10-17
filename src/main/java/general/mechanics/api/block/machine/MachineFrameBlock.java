package general.mechanics.api.block.machine;

import general.mechanics.api.block.base.BaseBlock;
import general.mechanics.api.item.plastic.PlasticType;
import general.mechanics.util.RomanNumeral;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineFrameBlock extends BaseBlock {

    // Connectivity to adjacent MachineFrame blocks in each cardinal direction
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private final PlasticType plasticType;
    private final int level;

    public MachineFrameBlock(Properties properties, PlasticType plasticType) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH, false)
                        .setValue(SOUTH, false)
                        .setValue(EAST, false)
                        .setValue(WEST, false)
                        .setValue(UP, false)
                        .setValue(DOWN, false)
        );

        this.plasticType = plasticType;
        this.level = plasticType.ordinal() + 1;
    }

    public MachineFrameBlock(PlasticType plasticType) {
        this(Properties.ofFullCopy(Blocks.IRON_BLOCK), plasticType);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockGetter level = context.getLevel();
        return this.defaultBlockState()
                .setValue(NORTH, isSame(level, pos.relative(Direction.NORTH)))
                .setValue(SOUTH, isSame(level, pos.relative(Direction.SOUTH)))
                .setValue(EAST, isSame(level, pos.relative(Direction.EAST)))
                .setValue(WEST, isSame(level, pos.relative(Direction.WEST)))
                .setValue(UP, isSame(level, pos.relative(Direction.UP)))
                .setValue(DOWN, isSame(level, pos.relative(Direction.DOWN)));
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        return switch (direction) {
            case NORTH -> state.setValue(NORTH, isSame(level, neighborPos));
            case SOUTH -> state.setValue(SOUTH, isSame(level, neighborPos));
            case EAST -> state.setValue(EAST, isSame(level, neighborPos));
            case WEST -> state.setValue(WEST, isSame(level, neighborPos));
            case UP -> state.setValue(UP, isSame(level, neighborPos));
            case DOWN -> state.setValue(DOWN, isSame(level, neighborPos));
        };
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(getPlasticType().getAbbreviation()));
        tooltipComponents.add(Component.literal("§e" + getPlasticType().getFormula()));
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.literal("§oLevel: " + RomanNumeral.toRoman(level)));
        } else {
            tooltipComponents.add(Component.translatable("gui.gm.press_shift"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private boolean isSame(BlockGetter level, BlockPos pos) {
        BlockState st = level.getBlockState(pos);
        return st.getBlock() instanceof MachineFrameBlock;
    }

    public PlasticType getPlasticType() {
        return plasticType;
    }

    public int getLevel() {
        return level;
    }
}
