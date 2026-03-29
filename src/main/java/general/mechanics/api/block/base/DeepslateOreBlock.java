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
 * Represents a variant of an OreBlock that is specifically designed to mimic the appearance
 * and behavior of deepslate ores. This class extends {@code DropExperienceBlock}, allowing it
 * to drop experience when mined.
 * <p>
 * The DeepslateOreBlock leverages properties and experience ranges from its parent {@code OreBlock}
 * to maintain consistent functionality with the original ore block, but with aesthetic adjustments
 * to match the deepslate theme.
 * <p>
 * This block is typically used in cases where an ore requires a deepslate version to offer a visually
 * distinct variant when generating in deeper layers of the world.
 */
public class DeepslateOreBlock extends DropExperienceBlock {

    @Getter
    private final ElementType type;

    public DeepslateOreBlock(OreBlock parent) {
        super(parent.getXpRange(), Blocks.DEEPSLATE_IRON_ORE.properties());
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

        if (block instanceof DeepslateOreBlock ore && tintIndex == 1) {
            return ore.getType().getTintColor();
        }
        return -1;
    }

    public static int getColorForItemStack(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof DeepslateOreBlock ore && index == 1) {
            return ore.getType().getTintColor();
        }

        return -1;
    }

}
