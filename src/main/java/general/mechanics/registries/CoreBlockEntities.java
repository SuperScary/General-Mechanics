package general.mechanics.registries;

import com.google.common.base.Preconditions;
import general.mechanics.GM;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.entity.DeferredBlockEntityType;
import general.mechanics.api.entity.block.BaseBlockEntity;
import general.mechanics.api.entity.block.BaseEntityBlock;
import general.mechanics.entity.block.HeatingElementBlockEntity;
import general.mechanics.entity.block.MatterFabricatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CoreBlockEntities {

    private static final List<DeferredBlockEntityType<?>> BLOCK_ENTITY_TYPES = new ArrayList<>();

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, GM.MODID);

    public static final DeferredBlockEntityType<MatterFabricatorBlockEntity> MATTER_FABRICATOR = create("fabricator", MatterFabricatorBlockEntity.class, MatterFabricatorBlockEntity::new, CoreBlocks.MATTER_FABRICATOR);

    public static final DeferredBlockEntityType<HeatingElementBlockEntity> HEATING_ELEMENT = create("heating_element", HeatingElementBlockEntity.class, HeatingElementBlockEntity::new, CoreBlocks.INDUSTRIAL_HEATING_ELEMENT);

    /**
     * Get all block entity types whose implementations extend the given base class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> List<BlockEntityType<? extends T>> getSubclassesOf (Class<T> baseClass) {
        var result = new ArrayList<BlockEntityType<? extends T>>();
        for (var type : BLOCK_ENTITY_TYPES) {
            if (baseClass.isAssignableFrom(type.blockEntityClass())) {
                result.add((BlockEntityType<? extends T>) type.get());
            }
        }
        return result;
    }

    /**
     * Get all block entity types whose implementations implement the given interface.
     */
    public static List<BlockEntityType<?>> getImplementorsOf (Class<?> iface) {
        var result = new ArrayList<BlockEntityType<?>>();
        for (var type : BLOCK_ENTITY_TYPES) {
            if (iface.isAssignableFrom(type.blockEntityClass())) {
                result.add(type.get());
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private static <T extends BaseBlockEntity> DeferredBlockEntityType<T> create (String id, Class<T> entityClass, BlockEntityFactory<T> factory, BlockDefinition<? extends BaseEntityBlock<?>>... blockDefinitions) {
        Preconditions.checkArgument(blockDefinitions.length > 0);
        var deferred = REGISTRY.register(id, () -> {
            AtomicReference<BlockEntityType<T>> typeHolder = new AtomicReference<>();
            BlockEntityType.BlockEntitySupplier<T> supplier = (blockPos, blockState) -> factory.create(typeHolder.get(), blockPos, blockState);

            var blocks = Arrays.stream(blockDefinitions).map(BlockDefinition::block).toArray(BaseEntityBlock[]::new);
            var type = BlockEntityType.Builder.of(supplier, blocks).build(null);
            typeHolder.setPlain(type);

            for (var block : blocks) {
                BaseEntityBlock<T> baseBlock = (BaseEntityBlock<T>) block;
                baseBlock.setBlockEntity(entityClass, type);
            }

            return type;
        });

        var result = new DeferredBlockEntityType<>(entityClass, deferred);
        BLOCK_ENTITY_TYPES.add(result);
        return result;
    }

    @FunctionalInterface
    interface BlockEntityFactory<T extends BaseBlockEntity> {
        T create (BlockEntityType<T> type, BlockPos pos, BlockState state);
    }

}
