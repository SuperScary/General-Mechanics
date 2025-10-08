package fluxmachines.core.api.multiblock.event;

import fluxmachines.core.api.multiblock.MultiblockDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

/**
 * Base event for multiblock-related actions.
 */
public abstract class MultiblockEvent extends Event {
    
    private final MultiblockDefinition multiblock;
    private final Level level;
    private final BlockPos anchorPos;
    private final Direction facing;
    private final boolean mirrored;
    
    protected MultiblockEvent(MultiblockDefinition multiblock, Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        this.multiblock = multiblock;
        this.level = level;
        this.anchorPos = anchorPos;
        this.facing = facing;
        this.mirrored = mirrored;
    }
    
    public MultiblockDefinition getMultiblock() {
        return multiblock;
    }
    
    public Level getLevel() {
        return level;
    }
    
    public BlockPos getAnchorPos() {
        return anchorPos;
    }
    
    public Direction getFacing() {
        return facing;
    }
    
    public boolean isMirrored() {
        return mirrored;
    }
}
