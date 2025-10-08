package fluxmachines.core.hooks;

import com.google.common.base.Preconditions;
import fluxmachines.core.api.entity.IWrenchable;
import fluxmachines.core.api.entity.block.BaseBlockEntity;
import fluxmachines.core.api.tags.CoreTags;
import fluxmachines.core.util.ItemHelper;
import fluxmachines.core.util.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class WrenchHooks {

    private static final ThreadLocal<Boolean> IS_DISASSEMBLING = new ThreadLocal<>();

    private WrenchHooks () {

    }

    public static boolean isDisassembling () {
        return Boolean.TRUE.equals(IS_DISASSEMBLING.get());
    }

    public static void onPlayerUseBlockEvent (PlayerInteractEvent.RightClickBlock event) {
        Preconditions.checkArgument(!isDisassembling());
        if (event.isCanceled()) return;
        var result = onPlayerUseBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        if (result != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }

    public static InteractionResult onPlayerUseBlock (Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isSpectator() || hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        var itemStack = player.getItemInHand(hand);

        // disassemble
        if (Utils.alternateUseMode(player) && canDisassemble(itemStack)) {
            var be = level.getBlockEntity(hitResult.getBlockPos());
            if (be instanceof IWrenchable baseBlockEntity) {
                IS_DISASSEMBLING.set(true);
                try {
                    var result = baseBlockEntity.disassemble(player, level, hitResult, itemStack, null);
                    if (result.consumesAction()) {
                        //SoundHelper.fire(level, player, be.getBlockPos(), DECONSTRUCT);
                        if (itemStack.is(CoreTags.Items.WRENCHES)) {
                            ItemHelper.damageStack(itemStack, level, player);
                        }
                    }
                    return result;
                } finally {
                    IS_DISASSEMBLING.remove();
                }
            }
        }
        // rotate
        else if (!Utils.alternateUseMode(player) && canRotate(itemStack)) {
            IS_DISASSEMBLING.set(true);
            try {
                var be = level.getBlockEntity(hitResult.getBlockPos());
                var pos = hitResult.getBlockPos();
                var state = level.getBlockState(pos);
                var clickedFace = hitResult.getDirection();
                if (be instanceof BaseBlockEntity baseBlockEntity) {
                    var result = baseBlockEntity.rotateOnAxis(level, hitResult, state, clickedFace);
                    if (result.consumesAction()) {
                        //SoundHelper.fire(level, player, pos, ROTATE);
                        ItemHelper.damageStack(itemStack, level, player);
                    }
                    return result;
                }
            } finally {
                IS_DISASSEMBLING.remove();
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean canDisassemble (ItemStack tool) {
        return tool.is(CoreTags.Items.WRENCHES);
    }

    public static boolean canRotate (ItemStack tool) {
        return tool.is(CoreTags.Items.WRENCHES);
    }

}