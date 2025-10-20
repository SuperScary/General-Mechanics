package general.mechanics.api.entity;

import general.mechanics.hooks.WrenchHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Used for any block that can be disassembled with a wrench.
 */
public interface DisassemblyHandler {

    /**
     * Allows disassembly with wrench. Called by {@link WrenchHooks#onPlayerUseBlock(Player, Level, InteractionHand, BlockHitResult)}
     * @param player    {@link Player} the player
     * @param level     {@link Level} the level
     * @param hitResult {@link BlockHitResult} hit result of the interaction
     * @param stack     {@link ItemStack} used. Already checked to contain {@link general.mechanics.registries.CoreItems#WRENCH}
     * @return {@link InteractionResult}
     */
    InteractionResult disassemble (Player player, Level level, BlockHitResult hitResult, ItemStack stack, @Nullable ItemStack existingData);

}
