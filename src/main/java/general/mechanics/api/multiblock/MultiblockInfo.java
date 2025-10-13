package general.mechanics.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * Information about an active multiblock.
 */
public record MultiblockInfo(MultiblockDefinition definition, BlockPos anchorPos, Direction facing, boolean mirrored) {
}
