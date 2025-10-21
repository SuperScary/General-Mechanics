package general.mechanics.api.fluid;

import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.item.ItemDefinition;
import lombok.Getter;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Aggregates all parts of a fluid registration in one place.
 */
public final class FluidDefinition {

    @Getter
    private final String englishName;

    @Getter
    private final Supplier<FluidType> type;

    @Getter
    private final Supplier<FlowingFluid> source;

    @Getter
    private final Supplier<FlowingFluid> flowing;

    @Getter
    private final BlockDefinition<LiquidBlock> block;

    @Getter
    private final ItemDefinition<BucketItem> bucket;

    public FluidDefinition(String englishName, Supplier<FluidType> type, Supplier<FlowingFluid> source, Supplier<FlowingFluid> flowing, BlockDefinition<LiquidBlock> block, ItemDefinition<BucketItem> bucket) {
        this.englishName = Objects.requireNonNull(englishName, "englishName");
        this.type = Objects.requireNonNull(type, "type");
        this.source = Objects.requireNonNull(source, "source");
        this.flowing = Objects.requireNonNull(flowing, "flowing");
        this.block = Objects.requireNonNull(block, "block");
        this.bucket = Objects.requireNonNull(bucket, "bucket");
    }

    public FluidStack getStack() {
        return getStack(1000);
    }
    
    public FluidStack getStack(int amount) {
        return new FluidStack(this.block.block().fluid, amount);
    }

}


