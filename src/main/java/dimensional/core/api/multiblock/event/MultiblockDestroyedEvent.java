package dimensional.core.api.multiblock.event;

import dimensional.core.api.multiblock.MultiblockDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

/**
 * Fired when a multiblock is destroyed or becomes invalid.
 * This event can be used to clean up effects, remove rewards, or notify players.
 */
public class MultiblockDestroyedEvent extends MultiblockEvent {
    
    public MultiblockDestroyedEvent(MultiblockDefinition multiblock, Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        super(multiblock, level, anchorPos, facing, mirrored);
    }
}
