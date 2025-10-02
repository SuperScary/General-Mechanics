package dimensional.core.multiblocks;

import dimensional.core.api.multiblock.Layout;
import dimensional.core.api.multiblock.base.Multiblock;
import dimensional.core.registries.CoreBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DrakiumCrafterMultiblock extends Multiblock {

    public DrakiumCrafterMultiblock() {
        super("Drakium Crafter");
    }

    @Override
    public InteractionResult onInteract(Level level, BlockPos anchorPos, Direction facing, boolean mirrored,
                                        Player player, InteractionHand hand, ItemStack itemInHand, BlockPos hitPos) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        System.out.println("Drakium Crafter Multiblock");
        return super.onInteract(level, anchorPos, facing, mirrored, player, hand, itemInHand, hitPos);
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public Layout createLayout() {
        return Layout.builder()
                // Bottom layer (2x2)
                .layer("CC", "CC")
                // Top layer (2x2)
                .layer("CC", "CC")
                .key('C', CoreBlocks.DRAKIUM_BLOCK)
                .build();
    }
}
