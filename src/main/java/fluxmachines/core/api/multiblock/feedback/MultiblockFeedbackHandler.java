package fluxmachines.core.api.multiblock.feedback;

import fluxmachines.core.api.multiblock.event.MultiblockDestroyedEvent;
import fluxmachines.core.api.multiblock.event.MultiblockFormedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;

/**
 * Handles feedback when multiblocks are formed or destroyed.
 * Provides visual effects, sounds, and messages to players.
 */
public class MultiblockFeedbackHandler {
    
    @SubscribeEvent
    public static void onMultiblockFormed(MultiblockFormedEvent event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockPos anchorPos = event.getAnchorPos();
        
        // Spawn particles
        spawnFormationParticles(serverLevel, anchorPos);
        
        // You can add more effects here:
        // - Light up the multiblock
        // - Give experience points
        // - Trigger redstone signals
        // - etc.
    }
    
    @SubscribeEvent
    public static void onMultiblockDestroyed(MultiblockDestroyedEvent event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockPos anchorPos = event.getAnchorPos();
        
        // Spawn particles
        spawnDestructionParticles(serverLevel, anchorPos);
    }
    
    /**
     * Spawn particles when a multiblock is formed.
     */
    private static void spawnFormationParticles(ServerLevel level, BlockPos pos) {
        // Spawn happy particles (enchantment particles)
        for (int i = 0; i < 20; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 3;
            double y = pos.getY() + 0.5 + level.random.nextDouble() * 3;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 3;
            
            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1, 0, 0.1, 0, 0);
        }
        
        // Spawn some sparkle particles
        for (int i = 0; i < 10; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2;
            double y = pos.getY() + 0.5 + level.random.nextDouble() * 2;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2;
            
            level.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0, 0, 0, 0);
        }
    }
    
    /**
     * Spawn particles when a multiblock is destroyed.
     */
    private static void spawnDestructionParticles(ServerLevel level, BlockPos pos) {
        // Spawn smoke particles
        for (int i = 0; i < 15; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2;
            double y = pos.getY() + 0.5 + level.random.nextDouble() * 2;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2;
            
            level.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0, 0.1, 0, 0.1);
        }
        
        // Spawn some breaking particles
        for (int i = 0; i < 8; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 1.5;
            double y = pos.getY() + 0.5 + level.random.nextDouble() * 1.5;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 1.5;
            
            level.sendParticles(ParticleTypes.CRIT, x, y, z, 1, 0, 0, 0, 0.2);
        }
    }
}
