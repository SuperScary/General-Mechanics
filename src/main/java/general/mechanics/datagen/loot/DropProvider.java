package general.mechanics.datagen.loot;

import com.google.common.collect.ImmutableMap;
import general.mechanics.GM;
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
        return ImmutableMap.<Block, Function<Block, LootTable.Builder>>builder()
                .put(CoreBlocks.ICE2.block(), oreBlock(CoreBlocks.ICE2.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.ICE3.block(), oreBlock(CoreBlocks.ICE3.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.ICE4.block(), oreBlock(CoreBlocks.ICE4.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.ICE5.block(), oreBlock(CoreBlocks.ICE5.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.ICE6.block(), oreBlock(CoreBlocks.ICE6.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.ICE7.block(), oreBlock(CoreBlocks.ICE7.block(), ItemStack.EMPTY.getItem()))
                .put(CoreBlocks.LITHIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.LITHIUM_ORE_BLOCK.block(), CoreElements.LITHIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.BERYLLIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.BERYLLIUM_ORE_BLOCK.block(), CoreElements.BERYLLIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.SODIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.SODIUM_ORE_BLOCK.block(), CoreElements.SODIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.MAGNESIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.MAGNESIUM_ORE_BLOCK.block(), CoreElements.MAGNESIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.ALUMINUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.ALUMINUM_ORE_BLOCK.block(), CoreElements.ALUMINUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.SILICON_ORE_BLOCK.block(), oreBlock(CoreBlocks.ALUMINUM_ORE_BLOCK.block(), CoreElements.SILICON_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.PHOSPHORUS_ORE_BLOCK.block(), oreBlock(CoreBlocks.PHOSPHORUS_ORE_BLOCK.block(), CoreElements.PHOSPHORUS_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.POTASSIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.POTASSIUM_ORE_BLOCK.block(), CoreElements.POTASSIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.CALCIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.CALCIUM_ORE_BLOCK.block(), CoreElements.CALCIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.SCANDIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.SCANDIUM_ORE_BLOCK.block(), CoreElements.SCANDIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.TITANIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.TITANIUM_ORE_BLOCK.block(), CoreElements.TITANIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.VANADIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.VANADIUM_ORE_BLOCK.block(), CoreElements.VANADIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.CHROMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.CHROMIUM_ORE_BLOCK.block(), CoreElements.CHROMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.MANGANESE_ORE_BLOCK.block(), oreBlock(CoreBlocks.MANGANESE_ORE_BLOCK.block(), CoreElements.MANGANESE_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.IRON_ORE_BLOCK.block(), oreBlock(CoreBlocks.IRON_ORE_BLOCK.block(), CoreElements.IRON_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.COBALT_ORE_BLOCK.block(), oreBlock(CoreBlocks.COBALT_ORE_BLOCK.block(), CoreElements.COBALT_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.NICKEL_ORE_BLOCK.block(), oreBlock(CoreBlocks.NICKEL_ORE_BLOCK.block(), CoreElements.NICKEL_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.COPPER_ORE_BLOCK.block(), oreBlock(CoreBlocks.COPPER_ORE_BLOCK.block(), CoreElements.COPPER_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.ZINC_ORE_BLOCK.block(), oreBlock(CoreBlocks.ZINC_ORE_BLOCK.block(), CoreElements.ZINC_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.GALLIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.GALLIUM_ORE_BLOCK.block(), CoreElements.GALLIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.RUBIDIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.RUBIDIUM_ORE_BLOCK.block(), CoreElements.RUBIDIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.STRONTIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.STRONTIUM_ORE_BLOCK.block(), CoreElements.STRONTIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.YTTRIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.YTTRIUM_ORE_BLOCK.block(), CoreElements.YTTRIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.ZIRCONIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.ZIRCONIUM_ORE_BLOCK.block(), CoreElements.ZIRCONIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.NIOBIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.NIOBIUM_ORE_BLOCK.block(), CoreElements.NIOBIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.MOLYBDENUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.MOLYBDENUM_ORE_BLOCK.block(), CoreElements.MOLYBDENUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.RUTHENIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.RUTHENIUM_ORE_BLOCK.block(), CoreElements.RUTHENIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.RHODIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.RHODIUM_ORE_BLOCK.block(), CoreElements.RHODIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.PALLADIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.PALLADIUM_ORE_BLOCK.block(), CoreElements.PALLADIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.SILVER_ORE_BLOCK.block(), oreBlock(CoreBlocks.SILVER_ORE_BLOCK.block(), CoreElements.SILICON_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.CADMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.CADMIUM_ORE_BLOCK.block(), CoreElements.CADMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.INDIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.INDIUM_ORE_BLOCK.block(), CoreElements.INDIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.TIN_ORE_BLOCK.block(), oreBlock(CoreBlocks.TIN_ORE_BLOCK.block(), CoreElements.TIN_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.CESIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.CESIUM_ORE_BLOCK.block(), CoreElements.CESIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.BARIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.BARIUM_ORE_BLOCK.block(), CoreElements.BARIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.LANTHANUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.LANTHANUM_ORE_BLOCK.block(), CoreElements.LANTHANUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.CERIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.CERIUM_ORE_BLOCK.block(), CoreElements.CERIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.PRASEODYMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.PRASEODYMIUM_ORE_BLOCK.block(), CoreElements.PRASEODYMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.NEODYMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.NEODYMIUM_ORE_BLOCK.block(), CoreElements.NEODYMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.SAMARIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.SAMARIUM_ORE_BLOCK.block(), CoreElements.SAMARIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.EUROPIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.EUROPIUM_ORE_BLOCK.block(), CoreElements.EUROPIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.GADOLINIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.GADOLINIUM_ORE_BLOCK.block(), CoreElements.GADOLINIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.TERBIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.TERBIUM_ORE_BLOCK.block(), CoreElements.TERBIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.DYSPROSIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.DYSPROSIUM_ORE_BLOCK.block(), CoreElements.DYSPROSIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.HOLMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.HOLMIUM_ORE_BLOCK.block(), CoreElements.HOLMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.ERBIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.ERBIUM_ORE_BLOCK.block(), CoreElements.ERBIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.THULIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.THULIUM_ORE_BLOCK.block(), CoreElements.THULIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.YTTERBIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.YTTERBIUM_ORE_BLOCK.block(), CoreElements.YTTERBIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.LUTETIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.LUTETIUM_ORE_BLOCK.block(), CoreElements.LUTETIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.HAFNIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.HAFNIUM_ORE_BLOCK.block(), CoreElements.HAFNIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.TANTALUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.TANTALUM_ORE_BLOCK.block(), CoreElements.TANTALUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.TUNGSTEN_ORE_BLOCK.block(), oreBlock(CoreBlocks.TUNGSTEN_ORE_BLOCK.block(), CoreElements.TUNGSTEN_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.RHENIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.RHENIUM_ORE_BLOCK.block(), CoreElements.RHENIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.OSMIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.OSMIUM_ORE_BLOCK.block(), CoreElements.OSMIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.IRIDIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.IRIDIUM_ORE_BLOCK.block(), CoreElements.IRIDIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.PLATINUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.PLATINUM_ORE_BLOCK.block(), CoreElements.PLATINUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.GOLD_ORE_BLOCK.block(), oreBlock(CoreBlocks.GOLD_ORE_BLOCK.block(), CoreElements.GOLD_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.MERCURY_ORE_BLOCK.block(), oreBlock(CoreBlocks.MERCURY_ORE_BLOCK.block(), CoreElements.MERCURY_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.THALLIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.THALLIUM_ORE_BLOCK.block(), CoreElements.THALLIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.LEAD_ORE_BLOCK.block(), oreBlock(CoreBlocks.LEAD_ORE_BLOCK.block(), CoreElements.LEAD_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.BISMUTH_ORE_BLOCK.block(), oreBlock(CoreBlocks.BISMUTH_ORE_BLOCK.block(), CoreElements.BISMUTH_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.FRANCIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.FRANCIUM_ORE_BLOCK.block(), CoreElements.FRANCIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.RADIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.RADIUM_ORE_BLOCK.block(), CoreElements.RADIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.ACTINIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.ACTINIUM_ORE_BLOCK.block(), CoreElements.ACTINIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.THORIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.THORIUM_ORE_BLOCK.block(), CoreElements.THORIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.PROTACTINIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.PROTACTINIUM_ORE_BLOCK.block(), CoreElements.PROTACTINIUM_INGOT.get().getDustItem().asItem(), 3))
                .put(CoreBlocks.URANIUM_ORE_BLOCK.block(), oreBlock(CoreBlocks.URANIUM_ORE_BLOCK.block(), CoreElements.URANIUM_INGOT.get().getDustItem().asItem(), 3))
                .build();
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
