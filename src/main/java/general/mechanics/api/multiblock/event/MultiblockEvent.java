package general.mechanics.api.multiblock.event;

import general.mechanics.api.multiblock.MultiblockDefinition;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

/**
 * Base event for multiblock-related actions.
 */
public abstract class MultiblockEvent extends Event {

    @Getter
    private final MultiblockDefinition multiblock;

    @Getter
    private final Level level;

    @Getter
    private final BlockPos anchorPos;

    @Getter
    private final Direction facing;

    @Getter
    private final boolean mirrored;
    
    public MultiblockEvent(MultiblockDefinition multiblock, Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        this.multiblock = multiblock;
        this.level = level;
        this.anchorPos = anchorPos;
        this.facing = facing;
        this.mirrored = mirrored;
    }
}
