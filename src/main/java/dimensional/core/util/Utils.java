package dimensional.core.util;

import dimensional.core.DimensionalCore;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.HitResult;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    public static <T> T assignIfNotNull (T value) {
        return assignIfNotNull(List.of(value)).get(0);
    }

    public static <T> List<T> assignIfNotNull (List<T> value) {
        if (value != null) {
            return value;
        }

        DimensionalCore.LOGGER.warn("Value of {} is null. Cannot be assigned,", value);
        return null;
    }

    public static ItemStack convertToItemStack (Ingredient from) {
        return Arrays.stream(from.getItems()).findFirst().get();
    }

    public static int randomInteger (int base, int max) {
        Random random = new Random();
        return random.nextInt(base, max);
    }

    public static HitResult getClientMouseOver () {
        return Minecraft.getInstance().hitResult;
    }

    public static boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }

    public static boolean alternateUseMode (Player player) {
        return player.isShiftKeyDown();
    }

    public static int getGuiScale () {
        return (int) Minecraft.getInstance().getWindow().getGuiScale();
    }

    public static int guiScaleOffset () {
        return switch (getGuiScale()) {
            case 3 -> -27;
            default -> -27;
        };
    }

}
