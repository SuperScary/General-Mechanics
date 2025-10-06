package dimensional.core.api.entity.block;

import dimensional.core.api.attributes.Attribute;
import dimensional.core.api.energy.CoreEnergyStorage;
import dimensional.core.api.energy.PoweredBlock;
import dimensional.core.api.util.data.Keys;
import dimensional.core.api.util.data.PropertyComponent;
import dimensional.core.registries.CoreComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class BasePoweredBlockEntity extends BaseBlockEntity implements PoweredBlock {

    private final CoreEnergyStorage energyStorage;
    private ArrayList<PropertyComponent<?>> dataComponents = new ArrayList<>();

    public BasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive) {
        this(type, pos, blockState, capacity, maxReceive, Attribute.Builder.of(Keys.POWER, 0));
    }

    public BasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, Attribute.IntValue capacity, Attribute.IntValue maxReceive, Attribute.IntValue current) {
        super(type, pos, blockState);
        this.energyStorage = new CoreEnergyStorage(capacity.get(), maxReceive.get(), maxReceive.get(), current.getAsInt());
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.saveClientData(tag, registries);
        tag.put(Keys.POWER, energyStorage.serializeNBT(registries));
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        energyStorage.deserializeNBT(registries, IntTag.valueOf(tag.getInt(Keys.POWER)));
    }

    @Override
    public CoreEnergyStorage getEnergyStorage () {
        return energyStorage;
    }

    public void updateBlockState (BlockState state) {
        assert level != null;
        level.setBlockAndUpdate(getBlockPos(), state);
    }

    @Override
    public InteractionResult disassemble (Player player, Level level, BlockHitResult hitResult, ItemStack stack, @Nullable ItemStack existingData) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = (BaseEntityBlock<?>) state.getBlock();
        var itemstack = getEither(existingData, new ItemStack(block));
        if (level instanceof ServerLevel) {
            itemstack.set(CoreComponents.ENERGY_STORED, getEnergyStorage().getEnergyStored());
            itemstack.set(CoreComponents.ENERGY_MAX, getEnergyStorage().getMaxEnergyStored());
        }
        return super.disassemble(player, level, hitResult, stack, itemstack);
    }

    @Override
    public void setData (ItemStack stack) {
        if (stack.has(CoreComponents.ENERGY_STORED) && stack.has(CoreComponents.ENERGY_MAX)) {
            var stored = stack.get(CoreComponents.ENERGY_STORED);
            var max = stack.get(CoreComponents.ENERGY_MAX);
            getEnergyStorage().setStored(stored);
            getEnergyStorage().setMaxStorage(max);
        }
        super.setData(stack);
    }

}
