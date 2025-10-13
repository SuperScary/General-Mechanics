package general.mechanics.api.multiblock.event;

import general.mechanics.api.multiblock.MultiblockDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

/**
 * Fired when a multiblock is successfully formed/validated.
 * This event can be used to trigger effects, give rewards, or provide feedback to players.
 */
public class MultiblockFormedEvent extends MultiblockEvent {
    
    public MultiblockFormedEvent(MultiblockDefinition multiblock, Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        super(multiblock, level, anchorPos, facing, mirrored);
    }
}
