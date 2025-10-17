package general.mechanics.multiblocks;

import general.mechanics.api.multiblock.Layout;
import general.mechanics.api.multiblock.base.Multiblock;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EmptyMachineMultiblock extends Multiblock {

    public EmptyMachineMultiblock() {
        super("Empty Machine");
    }

    @Override
    public InteractionResult onInteract(Level level, BlockPos anchorPos, Direction facing, boolean mirrored, Player player, InteractionHand hand, ItemStack itemInHand, BlockPos hitPos) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        return super.onInteract(level, anchorPos, facing, mirrored, player, hand, itemInHand, hitPos);
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public Layout createLayout() {
        return Layout.builder()
                .layer("CCC", "CCC", "CCC")
                .layer("CCC", "CCC", "CCC")
                .layer("CCC", "CCC", "CCC")
                .key('C', CoreBlocks.MACHINE_FRAME_0)
                .build();
    }
}
