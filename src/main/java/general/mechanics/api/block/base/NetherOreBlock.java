package general.mechanics.api.block.base;

import general.mechanics.api.item.element.ElementType;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a specialized subclass of DropExperienceBlock, designed to model ore blocks
 * found in the Nether. This class extends the functionality of a standard drop-experience
 * block and acts as a wrapper for an existing {@link OreBlock} instance.
 * <p>
 * The NetherOreBlock inherits the behavior and properties of its parent OreBlock. It is
 * constructed using the parent OreBlock's experience drop range and block state properties.
 * This allows for consistent behavior and appearance with its parent block, while being
 * specifically tailored for the Nether environment.
 */
public class NetherOreBlock extends DropExperienceBlock {

    @Getter
    private final ElementType type;

    public NetherOreBlock(OreBlock parent) {
        super(parent.getXpRange(), Blocks.NETHER_GOLD_ORE.properties());
        this.type = parent.getType();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(String.format("§e" + type.getSymbol())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public ResourceLocation getRegistryName() {
        var id = BuiltInRegistries.BLOCK.getKey(this);
        return id != BuiltInRegistries.BLOCK.getDefaultKey() ? id : null;
    }

    public static int getColor(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int tintIndex) {
        Block block = state.getBlock();

        if (block instanceof NetherOreBlock ore && tintIndex == 1) {
            return ore.getType().getTintColor();
        }
        return -1;
    }

    public static int getColorForItemStack(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof NetherOreBlock ore && index == 1) {
            return ore.getType().getTintColor();
        }

        return -1;
    }

}
