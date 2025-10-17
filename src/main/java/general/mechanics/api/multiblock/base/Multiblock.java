package general.mechanics.api.multiblock.base;

import general.mechanics.GM;
import general.mechanics.api.multiblock.Layout;
import general.mechanics.api.multiblock.MultiblockDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for multiblocks that provides common functionality and lifecycle methods.
 * Extend this class to create custom multiblocks with their own behavior.
 */
public abstract class Multiblock {

    protected MultiblockDefinition definition;
    protected final String name;
    /**
     * Exact count requirements for specific blocks present within the validated structure region.
     * If present, validation will require exactly the specified number to be matched.
     */
    protected Map<Block, Integer> requiredExactBlocks;
    /**
     * Minimum count requirements for specific blocks present within the validated structure region.
     * If present, validation will require at least the specified number to be matched (more is allowed).
     */
    protected Map<Block, Integer> requiredMinimumBlocks;

    public Multiblock(String name) {
        this.name = name;
        this.definition = null; // Will be created lazily when needed
        this.requiredExactBlocks = new HashMap<>();
        this.requiredMinimumBlocks = new HashMap<>();
    }

    /**
     * Gets the resource location for this multiblock.
     * Override this to provide a custom resource location.
     */
    protected ResourceLocation getResourceLocation() {
        String resourceName = name.toLowerCase().replace(' ', '_');
        return GM.getResource(resourceName);
    }

    /**
     * Gets the multiblock definition for this multiblock.
     */
    public MultiblockDefinition getDefinition() {
        if (definition == null) {
            // Create the definition with the layout
            this.definition = new MultiblockDefinition(
                    getResourceLocation(),
                    createLayout(),
                    () -> this
            );
        }
        return definition;
    }

    // Abstract methods that subclasses must implement

    /**
     * Creates the layout for this multiblock.
     * This method must be implemented by subclasses.
     */
    public abstract Layout createLayout();

    /**
     * Adds or updates an exact count requirement for a specific block.
     */
    public Multiblock requireExact(Block block, int count) {
        if (count < 0) throw new IllegalArgumentException("count must be >= 0");
        this.requiredExactBlocks.put(block, count);
        return this;
    }

    /**
     * Adds or updates a minimum count requirement for a specific block. More than this number is allowed.
     */
    public Multiblock requireAtLeast(Block block, int minCount) {
        if (minCount < 0) throw new IllegalArgumentException("minCount must be >= 0");
        this.requiredMinimumBlocks.put(block, minCount);
        return this;
    }

    /**
     * Returns an unmodifiable view of exact block count requirements.
     */
    public Map<Block, Integer> getRequiredExactBlocks() {
        return Collections.unmodifiableMap(this.requiredExactBlocks);
    }

    /**
     * Returns an unmodifiable view of minimum block count requirements.
     */
    public Map<Block, Integer> getRequiredMinimumBlocks() {
        return Collections.unmodifiableMap(this.requiredMinimumBlocks);
    }

    // Overridable methods with default implementations

    /**
     * Called when the multiblock is created/formed.
     * Override this to add custom creation behavior.
     */
    public void onCreate(Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        // Default: do nothing
    }

    /**
     * Called when the multiblock is destroyed.
     * Override this to add custom destruction behavior.
     */
    public void onDestroy(Level level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        // Default: do nothing
    }

    /**
     * Called every server tick while the multiblock is active.
     * Override this to add custom ticking behavior.
     */
    public void onTick(ServerLevel level, BlockPos anchorPos, Direction facing, boolean mirrored) {
        // Default: do nothing
    }

    /**
     * Called when a block within the multiblock structure is changed.
     * Override this to react to block changes within the multiblock.
     */
    public void onBlockChange(Level level, BlockPos anchorPos, Direction facing, boolean mirrored, BlockPos changedPos) {
        // Default: do nothing
    }
    
    /**
     * Called when a player interacts with the multiblock (right-clicks on it).
     * Override this to add custom interaction behavior.
     * 
     * @param level The level where the interaction occurred
     * @param anchorPos The anchor position of the multiblock
     * @param facing The facing direction of the multiblock
     * @param mirrored Whether the multiblock is mirrored
     * @param player The player who interacted with the multiblock
     * @param hand The hand the player used to interact
     * @param itemInHand The item the player was holding
     * @param hitPos The specific position that was clicked within the multiblock
     * @return The result of the interaction
     */
    public InteractionResult onInteract(Level level, BlockPos anchorPos, Direction facing, boolean mirrored, Player player, InteractionHand hand, ItemStack itemInHand, BlockPos hitPos) {
        // Default: do nothing
        return InteractionResult.PASS;
    }

    /**
     * Returns whether this multiblock should be ticked.
     * Override this to enable/disable ticking.
     */
    public boolean shouldTick() {
        return false; // Default: don't tick
    }

    /**
     * Gets the display name of this multiblock.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + name + "]";
    }
}
