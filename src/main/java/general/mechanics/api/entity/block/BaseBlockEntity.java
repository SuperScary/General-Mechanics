package general.mechanics.api.entity.block;

import general.mechanics.api.entity.IWrenchable;
import general.mechanics.api.inventory.CoreInventory;
import general.mechanics.api.inventory.CoreItemStackHandler;
import general.mechanics.api.util.ContentDropper;
import general.mechanics.api.util.data.BlockData;
import general.mechanics.api.util.data.Keys;
import general.mechanics.components.InventoryComponent;
import general.mechanics.registries.CoreComponents;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBlockEntity extends BlockEntity implements MenuProvider, BlockData, CoreInventory, IWrenchable {
    @Setter
    @Getter
    private boolean settingsPanelOpen = false;

    private final CoreItemStackHandler inventory = createInventory();

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract CoreItemStackHandler createInventory();

    public abstract AbstractContainerMenu menu (int id, Inventory playerInventory, Player player);

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int id, @NotNull Inventory inventory, @NotNull Player player) {
        return menu(id, inventory, player);
    }

    @Override
    protected void saveAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        saveClientData(tag, registries);
    }

    @Override
    protected void loadAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        loadClientData(tag, registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket () {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag (HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        tag.put(Keys.INVENTORY, inventory.serializeNBT(registries));
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        inventory.deserializeNBT(registries, tag.getCompound(Keys.INVENTORY));
    }

    @Override
    public void handleUpdateTag (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        loadClientData(tag, lookupProvider);
    }

    @Override
    public void clearContents () {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void drops (ItemStackHandler inventory) {
        if (inventory == null) return;
        var container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < getInventory().getSlots(); i++) {
            if (inventory.getStackInSlot(i).is(Items.AIR)) {
                container.setItem(i, ItemStack.EMPTY);
            } else {
                container.setItem(i, inventory.getStackInSlot(i));
            }
        }
        assert level != null;
        ContentDropper.spawnDrops(level, worldPosition, container);
    }

    @Override
    public void saveToItem (@NotNull ItemStack stack, HolderLookup.@NotNull Provider registries) {
        super.saveToItem(stack, registries);
    }

    @Override
    public InteractionResult disassemble (Player player, Level level, BlockHitResult hitResult, ItemStack stack, @Nullable ItemStack existingData) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = (BaseEntityBlock<?>) state.getBlock();
        var itemstack = getEither(existingData, new ItemStack(block));

        if (level instanceof ServerLevel) {
            List<ItemStack> inventory = new ArrayList<>();
            for (int i = 0; i < getInventory().getSlots(); i++) {
                inventory.add(i, getInventory().getStackInSlot(i));
            }

            InventoryComponent inventoryComponent = new InventoryComponent(inventory);
            itemstack.set(CoreComponents.INVENTORY, inventoryComponent);
            ContentDropper.drop(level, pos, itemstack);
        }

        block.destroyBlockByWrench(player, state, level, pos, false);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public InteractionResult rotateOnAxis (Level level, BlockHitResult hitResult, BlockState state, Direction direction) {
        if (!level.isClientSide()) {
            var currentDirection = state.getValue(BlockStateProperties.FACING);
            if (direction != Direction.UP && direction != Direction.DOWN) {
                level.setBlockAndUpdate(hitResult.getBlockPos(), state.setValue(BlockStateProperties.FACING, currentDirection.getClockWise()));
            } else {
                // UP AND DOWN HANDLING
                level.setBlockAndUpdate(hitResult.getBlockPos(), state.setValue(BlockStateProperties.FACING, currentDirection.getClockWise(Direction.Axis.X)));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    /**
     * Sets stored data level from via {@link DataComponents}
     * @param stack the itemstack containing data.
     */
    public void setData (ItemStack stack) {
        if (stack.has(CoreComponents.INVENTORY)) {
            var inventoryComponent = stack.get(CoreComponents.INVENTORY);
            for (int i = 0; i < getInventory().getSlots(); i++) {
                assert inventoryComponent != null;
                var itemstack = inventoryComponent.inventory().get(i);
                if (itemstack.is(ItemStack.EMPTY.getItem())) {
                    getInventory().setStackInSlot(i, ItemStack.EMPTY);
                } else {
                    getInventory().setStackInSlot(i, itemstack);
                }
            }
        }
    }

    /**
     * Saves and updates the block state to sync with Server and Client.
     */
    public void saveAndUpdate (Level level, BlockPos pos, BlockState state) {
        assert level != null;
        level.setBlockAndUpdate(pos, state);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        setChanged();
    }

    @Override
    public CoreItemStackHandler getInventory () {
        return inventory;
    }

    /**
     * Called every tick. Main tick method for base powered block entities.
     */
    public abstract void tick (Level level, BlockPos pos, BlockState state);

    /**
     * Returns first object if not null, otherwise returns second object.
     */
    public <T> T getEither (T obj, T obj2) {
        return obj != null ? obj : obj2;
    }

}
