package general.mechanics.datagen.loot;

import com.google.common.collect.ImmutableMap;
import general.mechanics.GM;
import general.mechanics.api.block.base.DeepslateOreBlock;
import general.mechanics.api.block.base.NetherOreBlock;
import general.mechanics.api.block.base.OreBlock;
import general.mechanics.api.item.element.ElementType;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreElements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DropProvider extends BlockLootSubProvider {

    private final Map<Block, Function<Block, LootTable.Builder>> overrides = createOverrides();

    @NotNull
    private ImmutableMap<Block, Function<Block, LootTable.Builder>> createOverrides() {
        ImmutableMap.Builder<Block, Function<Block, LootTable.Builder>> builder = ImmutableMap.builder();

        // Add ice blocks that drop nothing
        builder.put(CoreBlocks.ICE2.block(), oreBlock(CoreBlocks.ICE2.block(), ItemStack.EMPTY.getItem()));
        builder.put(CoreBlocks.ICE3.block(), oreBlock(CoreBlocks.ICE3.block(), ItemStack.EMPTY.getItem()));
        builder.put(CoreBlocks.ICE4.block(), oreBlock(CoreBlocks.ICE4.block(), ItemStack.EMPTY.getItem()));
        builder.put(CoreBlocks.ICE5.block(), oreBlock(CoreBlocks.ICE5.block(), ItemStack.EMPTY.getItem()));
        builder.put(CoreBlocks.ICE6.block(), oreBlock(CoreBlocks.ICE6.block(), ItemStack.EMPTY.getItem()));
        builder.put(CoreBlocks.ICE7.block(), oreBlock(CoreBlocks.ICE7.block(), ItemStack.EMPTY.getItem()));

        // Dynamically generate ore drops from ElementType mapping (base ores, deepslate, nether)
        for (OreBlock oreBlock : CoreBlocks.getOreBlocks()) {
            addOreDrop(builder, oreBlock);
        }
        for (DeepslateOreBlock oreBlock : CoreBlocks.getDeepslateOreBlocks()) {
            addOreDrop(builder, oreBlock);
        }
        for (NetherOreBlock oreBlock : CoreBlocks.getNetherOreBlocks()) {
            addOreDrop(builder, oreBlock, 1);
        }

        return builder.build();
    }

    private void addOreDrop(ImmutableMap.Builder<Block, Function<Block, LootTable.Builder>> builder, Block oreBlock) {
        addOreDrop(builder, oreBlock, 3);
    }

    private void addOreDrop(ImmutableMap.Builder<Block, Function<Block, LootTable.Builder>> builder, Block oreBlock, int dropCount) {
        ElementType type = null;
        if (oreBlock instanceof OreBlock ore) {
            type = ore.getType();
        } else if (oreBlock instanceof DeepslateOreBlock ore) {
            type = ore.getType();
        } else if (oreBlock instanceof NetherOreBlock ore) {
            type = ore.getType();
        }
        if (type != null) {
            var elementDef = CoreElements.getElementByType(type);
            if (elementDef != null) {
                var dustItem = elementDef.get().getDustItem().asItem();
                builder.put(oreBlock, oreBlock(oreBlock, dustItem, dropCount));
            }
        }
    }

    public DropProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(entry -> entry.getLootTable().location().getNamespace().equals(GM.MODID))
                .toList();
    }

    @Override
    public void generate() {
        for (var block : getKnownBlocks()) {
            add(block, overrides.getOrDefault(block, this::defaultBuilder).apply(block));
        }

    }

    private LootTable.Builder defaultBuilder(Block block) {
        LootPoolSingletonContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(entry).when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private Function<Block, LootTable.Builder> oreBlock(Block block, Item itemDropped) {
        return b -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(itemDropped)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .apply(ApplyBonusCount.addUniformBonusCount(getEnchantment(Enchantments.FORTUNE)))
                        .apply(ApplyExplosionDecay.explosionDecay()));
    }

    private Function<Block, LootTable.Builder> oreBlock(Block block, Item itemDropped, int drop) {
        return b -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(itemDropped)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(drop)))
                        .apply(ApplyBonusCount.addUniformBonusCount(getEnchantment(Enchantments.FORTUNE)))
                        .apply(ApplyExplosionDecay.explosionDecay()));
    }

    private void dropSelfWithState(Block block, Property<?>[] properties) {
        CopyBlockState.Builder blockStateBuilder = CopyBlockState.copyState(block);
        for (Property<?> property : properties) {
            blockStateBuilder.copy(property);
        }
        LootPoolSingletonContainer.Builder<?> itemLootBuilder = LootItem.lootTableItem(block);
        itemLootBuilder.apply(blockStateBuilder);
        LootPool.Builder lootPoolBuilder = LootPool.lootPool();
        lootPoolBuilder.setRolls(ConstantValue.exactly(1.0f));
        lootPoolBuilder.add(itemLootBuilder);

        lootPoolBuilder = applyExplosionCondition(block, lootPoolBuilder);
        LootTable.Builder lootTableBuilder = LootTable.lootTable();
        lootTableBuilder.withPool(lootPoolBuilder);
        this.add(block, lootTableBuilder);
    }

    protected final Holder<Enchantment> getEnchantment(ResourceKey<Enchantment> key) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

}
