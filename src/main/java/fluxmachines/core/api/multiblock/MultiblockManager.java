package fluxmachines.core.api.multiblock;

import fluxmachines.core.api.multiblock.event.MultiblockDestroyedEvent;
import fluxmachines.core.api.multiblock.event.MultiblockFormedEvent;
import fluxmachines.core.api.multiblock.base.Multiblock;
import fluxmachines.core.registries.CoreRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages multiblock tracking and provides feedback when multiblocks are formed or destroyed.
 * This class handles automatic detection and event firing for multiblock changes.
 */
public class MultiblockManager {
    
    private static final Map<BlockPos, MultiblockInfo> activeMultiblocks = new HashMap<>();
    private static final Map<BlockPos, Multiblock> activeMultiblockObjects = new HashMap<>();
    
    
    /**
     * Initialize the multiblock manager by registering event handlers.
     */
    public static void init(IEventBus modEventBus) {
        // Block events are on the NeoForge event bus, not the mod event bus
        // We need to listen to specific subclasses, not the abstract BlockEvent
        NeoForge.EVENT_BUS.addListener(MultiblockManager::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(MultiblockManager::onBlockPlace);

        // Register server tick event for multiblock object ticking
        NeoForge.EVENT_BUS.addListener(MultiblockManager::onServerTick);
        
        // Register player interaction event for multiblock interactions
        NeoForge.EVENT_BUS.addListener(MultiblockManager::onPlayerInteract);
        
        // Register world load event to restore multiblocks from saved data
        NeoForge.EVENT_BUS.addListener(MultiblockManager::onWorldLoad);
    }
    
    /**
     * Manually check for multiblocks at a specific position.
     * This can be called when you want to trigger a check programmatically.
     */
    public static Optional<MultiblockFormedEvent> checkForMultiblock(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel)) {
            return Optional.empty();
        }
        
        // Don't check if this position is already an active multiblock anchor
        if (activeMultiblocks.containsKey(pos)) {
            return Optional.empty();
        }
        
        // Check all registered multiblocks
        for (var definition : CoreRegistries.MULTIBLOCK_DEFINITIONS) {
            if (definition == null) continue;
            
            Optional<MultiblockValidator.ValidationResult> result = definition.findMatch(level, pos);
            if (result.isPresent() && result.get().success()) {
                MultiblockValidator.ValidationResult validation = result.get();
                
                // Check if this multiblock would conflict with existing multiblocks
                if (hasMultiblockConflict(level, pos, definition, validation.facing(), validation.mirrored())) {
                    continue; // Try the next definition
                }
                
                // Check if this multiblock is already tracked
                if (!isMultiblockActive(pos, definition, validation.facing(), validation.mirrored())) {
                    
                    // New multiblock formed!
                    MultiblockFormedEvent event = new MultiblockFormedEvent(definition, level, pos, validation.facing(), validation.mirrored());
                    MultiblockInfo info = new MultiblockInfo(definition, pos, validation.facing(), validation.mirrored());
                    activeMultiblocks.put(pos, info);
                    
                    // Save to world data
                    if (level instanceof ServerLevel serverLevel) {
                        saveMultiblockToWorldData(serverLevel, pos, info);
                    }
                    
                    // Create a multiblock object if the definition supports it
                    if (definition.hasObject()) {
                        Multiblock multiblockObject = (Multiblock) definition.createObject();
                        if (multiblockObject != null) {
                            activeMultiblockObjects.put(pos, multiblockObject);
                            multiblockObject.onCreate(level, pos, validation.facing(), validation.mirrored());
                        }
                    }
                    
                    // Fire the event
                    NeoForge.EVENT_BUS.post(event);
                    return Optional.of(event);
                }
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Handle block break events and check for multiblock formation/destruction.
     */
    private static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        
        BlockPos changedPos = event.getPos();
        
        // Check for destroyed multiblocks BEFORE the block is broken
        checkForDestroyedMultiblocksBeforeBreak(serverLevel, changedPos);
    }
    
    /**
     * Handle block place events and check for multiblock formation/destruction.
     */
    private static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        
        BlockPos changedPos = event.getPos();
        
        // Check for destroyed multiblocks first
        checkForDestroyedMultiblocks(serverLevel, changedPos);
        
        // Check for new multiblocks in the area
        checkAreaForMultiblocks(serverLevel, changedPos);
        
        // Notify multiblock objects about the block change
        notifyMultiblockObjects(serverLevel, changedPos);
    }
    
    /**
     * Handle server tick events to tick multiblock objects.
     */
    private static void onServerTick(ServerTickEvent.Post event) {
        if (event.getServer().overworld() instanceof ServerLevel serverLevel) {
            tickMultiblockObjects(serverLevel);
        }
    }
    
    /**
     * Handle player interaction events for multiblock interactions.
     */
    private static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            BlockPos hitPos = event.getPos();
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack itemInHand = player.getItemInHand(hand);
            
            // Try to handle multiblock interaction
            InteractionResult result = handlePlayerInteraction(serverLevel, hitPos, player, hand, itemInHand);
            
            // If multiblock handled the interaction, cancel the event
            if (result != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(result);
            }
        }
    }
    
    /**
     * Check if any existing multiblocks will be destroyed by the block break.
     * This checks BEFORE the block is actually broken.
     */
    private static void checkForDestroyedMultiblocksBeforeBreak(ServerLevel level, BlockPos changedPos) {
        var toRemove = new java.util.ArrayList<Map.Entry<BlockPos, MultiblockInfo>>();
        
        for (var entry : activeMultiblocks.entrySet()) {
            BlockPos anchorPos = entry.getKey();
            MultiblockInfo info = entry.getValue();
            
            // Check if the changed position affects this multiblock
            if (isPositionInMultiblock(anchorPos, info, changedPos)) {
                
                // Check if removing this block would make the multiblock invalid
                if (wouldBreakMultiblock(level, anchorPos, info, changedPos)) {
                    
                    // Destroy multiblock object if it exists
                    Multiblock multiblockObject = activeMultiblockObjects.remove(anchorPos);
                    if (multiblockObject != null) {
                        multiblockObject.onDestroy(level, anchorPos, info.facing(), info.mirrored());
                    }
                    
                    // Multiblock will be destroyed
                    MultiblockDestroyedEvent destroyEvent = new MultiblockDestroyedEvent(
                        info.definition(), level, anchorPos, info.facing(), info.mirrored()
                    );
                    NeoForge.EVENT_BUS.post(destroyEvent);
                    toRemove.add(entry);
                }
            }
        }
        
        // Remove destroyed multiblocks
        for (var entry : toRemove) {
            BlockPos pos = entry.getKey();
            activeMultiblocks.remove(pos);
            activeMultiblockObjects.remove(pos);
            
            // Remove from world data
            if (level instanceof ServerLevel serverLevel) {
                removeMultiblockFromWorldData(serverLevel, pos);
            }
        }
    }
    
    /**
     * Check if any existing multiblocks were destroyed by the block change.
     */
    private static void checkForDestroyedMultiblocks(ServerLevel level, BlockPos changedPos) {
        var toRemove = new java.util.ArrayList<Map.Entry<BlockPos, MultiblockInfo>>();
        
        for (var entry : activeMultiblocks.entrySet()) {
            BlockPos anchorPos = entry.getKey();
            MultiblockInfo info = entry.getValue();
            
            // Check if the changed position affects this multiblock
            if (isPositionInMultiblock(anchorPos, info, changedPos)) {
                // Re-validate the multiblock
                Optional<MultiblockValidator.ValidationResult> result = info.definition().findMatch(level, anchorPos);
                if (result.isEmpty() || !result.get().success()) {
                    // Multiblock was destroyed
                    MultiblockDestroyedEvent destroyEvent = new MultiblockDestroyedEvent(
                        info.definition(), level, anchorPos, info.facing(), info.mirrored()
                    );
                    NeoForge.EVENT_BUS.post(destroyEvent);
                    toRemove.add(entry);
                }
            }
        }
        
        // Remove destroyed multiblocks
        for (var entry : toRemove) {
            BlockPos pos = entry.getKey();
            activeMultiblocks.remove(pos);
            activeMultiblockObjects.remove(pos);
            
            // Remove from world data
            removeMultiblockFromWorldData(level, pos);
        }
    }
    
    /**
     * Check the area around a changed position for new multiblocks.
     * Only checks positions that could potentially be anchor points for multiblocks.
     */
    private static void checkAreaForMultiblocks(ServerLevel level, BlockPos centerPos) {
        // Only check a smaller radius to avoid interference with normal block placement
        int checkRadius = 2; // Reduced from 5 to 2
        
        for (int x = -checkRadius; x <= checkRadius; x++) {
            for (int y = -checkRadius; y <= checkRadius; y++) {
                for (int z = -checkRadius; z <= checkRadius; z++) {
                    BlockPos checkPos = centerPos.offset(x, y, z);
                    
                    // Skip if this position is already an active multiblock anchor
                    if (activeMultiblocks.containsKey(checkPos)) {
                        continue;
                    }
                    
                    // Only check if the position has a block that could be part of a multiblock
                    if (level.getBlockState(checkPos).isAir()) {
                        continue;
                    }
                    
                    checkForMultiblock(level, checkPos);
                }
            }
        }
    }
    
    /**
     * Check if a position is within a multiblock's bounds by checking the actual layout pattern.
     */
    private static boolean isPositionInMultiblock(BlockPos anchorPos, MultiblockInfo info, BlockPos checkPos) {
        Layout layout = info.definition().layout();
        
        // Get the anchor offset from the layout
        BlockPos anchorOffset = layout.anchorOffset();
        
        // Calculate the origin position (where the layout starts)
        BlockPos origin = anchorPos.subtract(anchorOffset);
        
        // Calculate the relative position from the origin
        BlockPos relativePos = checkPos.subtract(origin);
        
        // Apply inverse rotation and mirroring to get layout coordinates
        // We need to reverse the transformation that was applied during validation
        BlockPos layoutPos = RotationUtil.rotateAndMirrorInverse(
            relativePos.getX(), relativePos.getY(), relativePos.getZ(), 
            info.facing(), info.mirrored()
        );
        
        // Check if the layout position is within the layout bounds
        if (layoutPos.getX() < 0 || layoutPos.getX() >= layout.width() ||
            layoutPos.getY() < 0 || layoutPos.getY() >= layout.height() ||
            layoutPos.getZ() < 0 || layoutPos.getZ() >= layout.depth()) {
            return false;
        }
        
        // Get the character at this position in the layout
        char layoutChar = layout.getCharAt(layoutPos.getX(), layoutPos.getY(), layoutPos.getZ());
        
        // If the character is a space or the anchor character, this position is not part of the multiblock
        if (layoutChar == ' ' || (layout.anchorChar().isPresent() && layoutChar == layout.anchorChar().get())) {
            return false;
        }
        
        // This position is part of the multiblock structure
        return true;
    }
    
    /**
     * Check if any position in the given multiblock layout would conflict with existing multiblocks.
     * Returns true if there's a conflict (meaning the multiblock cannot be formed).
     */
    private static boolean hasMultiblockConflict(Level level, BlockPos anchorPos, MultiblockDefinition definition, Direction facing, boolean mirrored) {
        Layout layout = definition.layout();
        BlockPos anchorOffset = layout.anchorOffset();
        BlockPos origin = anchorPos.subtract(anchorOffset);
        
        // Check each position in the layout
        for (int y = 0; y < layout.height(); y++) {
            for (int z = 0; z < layout.depth(); z++) {
                for (int x = 0; x < layout.width(); x++) {
                    char layoutChar = layout.getCharAt(x, y, z);
                    
                    // Skip spaces and anchor characters
                    if (layoutChar == ' ' || (layout.anchorChar().isPresent() && layoutChar == layout.anchorChar().get())) {
                        continue;
                    }
                    
                    // Transform layout coordinates to world coordinates
                    BlockPos layoutPos = new BlockPos(x, y, z);
                    BlockPos worldPos = origin.offset(RotationUtil.rotateAndMirror(layoutPos.getX(), layoutPos.getY(), layoutPos.getZ(), facing, mirrored));
                    
                    // Check if this world position is already part of an existing multiblock
                    if (isPositionInAnyMultiblock(level, worldPos)) {
                        return true; // Conflict found
                    }
                }
            }
        }
        
        return false; // No conflicts
    }
    
    /**
     * Check if a position is part of any existing multiblock.
     */
    private static boolean isPositionInAnyMultiblock(Level level, BlockPos pos) {
        // Check all active multiblocks to see if this position is part of any of them
        // Note: We assume all multiblocks are in the same level since we only track by position
        for (MultiblockInfo info : activeMultiblocks.values()) {
            if (isPositionInMultiblock(info.anchorPos(), info, pos)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if breaking a block at the given position would make the multiblock invalid.
     * This simulates the block break and validates the multiblock.
     */
    private static boolean wouldBreakMultiblock(ServerLevel level, BlockPos anchorPos, MultiblockInfo info, BlockPos breakPos) {
        // Get the current block state at the position that will be broken
        var currentState = level.getBlockState(breakPos);
        
        // Temporarily set the block to air to simulate the break
        level.setBlock(breakPos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 0);
        
        try {
            // Check if the multiblock is still valid with this block removed
            Optional<MultiblockValidator.ValidationResult> result = info.definition().findMatch(level, anchorPos);
            return result.isEmpty() || !result.get().success();
        } finally {
            // Restore the original block state
            level.setBlock(breakPos, currentState, 0);
        }
    }
    
    /**
     * Check if a multiblock is already being tracked.
     */
    private static boolean isMultiblockActive(BlockPos pos, MultiblockDefinition definition, Direction facing, boolean mirrored) {
        MultiblockInfo info = activeMultiblocks.get(pos);
        return info != null && 
               info.definition().equals(definition) && 
               info.facing() == facing && 
               info.mirrored() == mirrored;
    }
    
    /**
     * Get all currently active multiblocks.
     */
    public static Map<BlockPos, MultiblockInfo> getActiveMultiblocks() {
        return new HashMap<>(activeMultiblocks);
    }
    
    /**
     * Clear all tracked multiblocks (useful for server restart, etc.).
     */
    public static void clearAll() {
        activeMultiblocks.clear();
        activeMultiblockObjects.clear();
    }
    
    /**
     * Ticks all active multiblock objects.
     */
    private static void tickMultiblockObjects(ServerLevel level) {
        for (Map.Entry<BlockPos, Multiblock> entry : activeMultiblockObjects.entrySet()) {
            BlockPos anchorPos = entry.getKey();
            Multiblock multiblockObject = entry.getValue();
            
            if (multiblockObject.shouldTick()) {
                MultiblockInfo info = activeMultiblocks.get(anchorPos);
                if (info != null) {
                    multiblockObject.onTick(level, anchorPos, info.facing(), info.mirrored());
                }
            }
        }
    }
    
    /**
     * Notifies multiblock objects about block changes.
     */
    private static void notifyMultiblockObjects(Level level, BlockPos changedPos) {
        for (Map.Entry<BlockPos, Multiblock> entry : activeMultiblockObjects.entrySet()) {
            BlockPos anchorPos = entry.getKey();
            Multiblock multiblockObject = entry.getValue();
            
            // Get the multiblock info to check bounds properly
            MultiblockInfo info = activeMultiblocks.get(anchorPos);
            if (info != null && isPositionInMultiblock(anchorPos, info, changedPos)) {
                multiblockObject.onBlockChange(level, anchorPos, info.facing(), info.mirrored(), changedPos);
            }
        }
    }
    
    /**
     * Handles player interaction with multiblocks.
     * This should be called when a player right-clicks on a block that might be part of a multiblock.
     * 
     * @param level The level where the interaction occurred
     * @param hitPos The position that was clicked
     * @param player The player who interacted
     * @param hand The hand the player used
     * @param itemInHand The item the player was holding
     * @return The result of the interaction, or PASS if no multiblock was found
     */
    public static InteractionResult handlePlayerInteraction(Level level, BlockPos hitPos, Player player, 
                                                           InteractionHand hand, ItemStack itemInHand) {
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.PASS;
        }
        
        // Check if the clicked position is part of any active multiblock
        for (Map.Entry<BlockPos, MultiblockInfo> entry : activeMultiblocks.entrySet()) {
            BlockPos anchorPos = entry.getKey();
            MultiblockInfo info = entry.getValue();
            
            // Check if the hit position is within this multiblock's bounds
            if (isPositionInMultiblock(anchorPos, info, hitPos)) {
                // Get the multiblock object
                Multiblock multiblockObject = activeMultiblockObjects.get(anchorPos);
                if (multiblockObject != null) {
                    // Call the multiblock's interaction method
                    InteractionResult result = multiblockObject.onInteract(
                        level, anchorPos, info.facing(), info.mirrored(), 
                        player, hand, itemInHand, hitPos
                    );
                    
                    // If the multiblock handled the interaction, return the result
                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }
            }
        }
        
        return InteractionResult.PASS;
    }
    
    /**
     * Handle world load event to restore multiblocks from saved data.
     */
    public static void onWorldLoad(net.neoforged.neoforge.event.level.LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            loadMultiblocksFromWorldData(level);
        }
    }
    
    /**
     * Save a multiblock to world data.
     */
    private static void saveMultiblockToWorldData(ServerLevel level, BlockPos pos, MultiblockInfo info) {
        MultiblockWorldData worldData = MultiblockWorldData.get(level);
        worldData.addMultiblock(pos, info);
    }
    
    /**
     * Remove a multiblock from world data.
     */
    private static void removeMultiblockFromWorldData(ServerLevel level, BlockPos pos) {
        MultiblockWorldData worldData = MultiblockWorldData.get(level);
        worldData.removeMultiblock(pos);
    }
    
    /**
     * Load all multiblocks from world data and validate them.
     */
    private static void loadMultiblocksFromWorldData(ServerLevel level) {
        MultiblockWorldData worldData = MultiblockWorldData.get(level);
        
        for (Map.Entry<BlockPos, MultiblockInfo> entry : worldData.getMultiblocks().entrySet()) {
            BlockPos pos = entry.getKey();
            MultiblockInfo info = entry.getValue();
            
            // Validate that the multiblock still exists in the world
            Optional<MultiblockValidator.ValidationResult> result = info.definition().findMatch(level, pos);
            if (result.isPresent() && result.get().success()) {
                // Multiblock is still valid, restore it
                activeMultiblocks.put(pos, info);
                
                // Create multiblock object if the definition supports it
                if (info.definition().hasObject()) {
                    Multiblock multiblockObject = (Multiblock) info.definition().createObject();
                    if (multiblockObject != null) {
                        activeMultiblockObjects.put(pos, multiblockObject);
                        multiblockObject.onCreate(level, pos, info.facing(), info.mirrored());
                    }
                }
                
                System.out.println("DEBUG: Restored multiblock at " + pos + " from world data");
            } else {
                // Multiblock is no longer valid, remove it from world data
                worldData.removeMultiblock(pos);
                System.out.println("DEBUG: Removed invalid multiblock at " + pos + " from world data");
            }
        }
    }
}
