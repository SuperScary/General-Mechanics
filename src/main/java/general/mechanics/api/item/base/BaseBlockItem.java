package general.mechanics.api.item.base;

import general.mechanics.api.block.base.BaseBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BaseBlockItem extends BlockItem {

    private final BaseBlock blockType;

    public BaseBlockItem (Block id, Properties properties) {
        super(id, properties);
        this.blockType = (BaseBlock) id;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public final void appendHoverText (@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> toolTip, @NotNull TooltipFlag tooltipFlag) {
        this.addCheckedInformation(stack, context, toolTip, tooltipFlag);
    }

    @OnlyIn(Dist.CLIENT)
    public final void addCheckedInformation (ItemStack stack, TooltipContext context, List<Component> toolTip, TooltipFlag tooltipFlag) {
        this.blockType.appendHoverText(stack, context, toolTip, tooltipFlag);
    }

    @Override
    public boolean isBookEnchantable (final @NotNull ItemStack stack_1, final @NotNull ItemStack stack_2) {
        return false;
    }

    @Override
    public @NotNull String getDescriptionId (@NotNull ItemStack stack) {
        return this.blockType.getDescriptionId();
    }

}
