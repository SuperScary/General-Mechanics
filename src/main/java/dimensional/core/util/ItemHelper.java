package dimensional.core.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;

public final class ItemHelper {

    public static void damageStack (ItemStack stack, Level level, Player player) {
        damageStack(stack, 1, level, player);
    }

    public static void damageStack (ItemStack stack, int amount, Level level, Player player) {
        var item = stack.getItem();
        if (item.isDamageable(stack)) {
            item.setDamage(stack, item.getDamage(stack) + amount);
            if (item.getDamage(stack) >= item.getMaxDamage(stack)) {
                destroy(stack, level, player);
            }
        }
    }

    public static void destroy (ItemStack stack, Level level, Player player) {
        destroy(stack, level, player, true);
    }

    public static void destroy (ItemStack stack, Level level, Player player, boolean playBreakSound) {
        stack.shrink(1);
    }

    public static void giveOrDropBucket (Player player, ItemStack filledBucket) {
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getCapability(Capabilities.FluidHandler.ITEM) != null) {
            if (heldItem.getCount() == 1) {
                player.setItemInHand(InteractionHand.MAIN_HAND, filledBucket);
            } else {
                player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
                player.setItemInHand(player.getUsedItemHand(), new ItemStack(heldItem.getItem(), heldItem.getCount() - 1));

                if (!player.getInventory().add(filledBucket)) {
                    player.drop(filledBucket.copy(), false);
                }
            }
        }
    }

    public static void giveOrDrop (Level level, Player player, ItemStack stack) {
        if (!level.isClientSide() && !player.getInventory().add(stack) ) {
            player.drop(stack, false);
        }
    }

}
