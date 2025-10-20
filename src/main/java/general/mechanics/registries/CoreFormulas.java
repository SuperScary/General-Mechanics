package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.formula.Formula;
import general.mechanics.api.formula.IFormulaProvider;
import general.mechanics.api.item.element.ElementType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoreFormulas {

    public static final DeferredRegister<Formula> REGISTRY = DeferredRegister.create(CoreRegistries.FORMULAS_REGISTRY, GM.MODID);

    protected static void registerDefaultFormulas() {
        register(() -> new Formula(Items.COAL, ElementType.CARBON.getSymbol()));
        register(() -> new Formula(Items.CHARCOAL, ElementType.CARBON.getSymbol()));
        register(() -> new Formula(Blocks.COAL_BLOCK, ElementType.CARBON.getSymbol()));
        register(() -> new Formula(Blocks.COAL_ORE, ElementType.CARBON.getSymbol()));
        register(() -> new Formula(Blocks.DEEPSLATE_COAL_ORE, ElementType.CARBON.getSymbol()));

        register(() -> new Formula(Items.IRON_INGOT, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Blocks.IRON_BLOCK, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Blocks.IRON_ORE, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Items.IRON_NUGGET, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Blocks.DEEPSLATE_IRON_ORE, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Blocks.RAW_IRON_BLOCK, ElementType.IRON.getSymbol()));
        register(() -> new Formula(Items.RAW_IRON, ElementType.IRON.getSymbol()));

        register(() -> new Formula(Items.GOLD_INGOT, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Blocks.GOLD_BLOCK, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Blocks.GOLD_ORE, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Blocks.DEEPSLATE_GOLD_ORE, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Blocks.NETHER_GOLD_ORE, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Blocks.RAW_GOLD_BLOCK, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Items.RAW_GOLD, ElementType.GOLD.getSymbol()));
        register(() -> new Formula(Items.GOLD_NUGGET, ElementType.GOLD.getSymbol()));

        register(() -> new Formula(Blocks.COPPER_ORE, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.DEEPSLATE_COPPER_ORE, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.RAW_COPPER_BLOCK, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Items.RAW_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Items.COPPER_INGOT, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_COPPER_BLOCK, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_CHISELED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_CUT_COPPER, ElementType.COPPER.getSymbol()));

        register(() -> new Formula(Blocks.WAXED_EXPOSED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_EXPOSED_CHISELED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_EXPOSED_CUT_COPPER, ElementType.COPPER.getSymbol()));

        register(() -> new Formula(Blocks.WAXED_WEATHERED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_WEATHERED_CHISELED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_WEATHERED_CUT_COPPER, ElementType.COPPER.getSymbol()));

        register(() -> new Formula(Blocks.WAXED_OXIDIZED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_OXIDIZED_CHISELED_COPPER, ElementType.COPPER.getSymbol()));
        register(() -> new Formula(Blocks.WAXED_OXIDIZED_CUT_COPPER, ElementType.COPPER.getSymbol()));

        for (var block : BuiltInRegistries.BLOCK) {
            if (block instanceof WeatheringCopperFullBlock) {
                register(() -> new Formula(block, ElementType.COPPER.getSymbol()));
            }
        }
    }

    static void register(FormulaFactory formulaFactory) {
        var factory = formulaFactory.create();
        var namespaced = factory.itemLike().asItem().getDescriptionId();
        register(namespaced, factory);
    }

    static void register(String name, Formula formula) {
        REGISTRY.register(name, () -> formula);
    }

    public static List<Formula> getFormulas() {
        var list = new ArrayList<Formula>();
        for (var entry : REGISTRY.getEntries()) {
            list.add(entry.get());
        }
        return Collections.unmodifiableList(list);
    }

    public static Component getFormulaForItem(ItemLike item) {
        return getFormulaForItem(item.asItem());
    }

    public static Component getFormulaForItem(Item item) {
        if (item instanceof IFormulaProvider provider) {
            return provider.getType().getFormulaComponent();
        }

        var targetItem = item.asItem();
        for (var entry : REGISTRY.getEntries()) {
            var formula = entry.get();
            if (formula.itemLike().asItem() == targetItem) {
                return formula.getFormulaComponent();
            }
        }

        return null;
    }

    public static void register(IEventBus modEventBus) {
        registerDefaultFormulas();
        REGISTRY.register(modEventBus);
    }

    @FunctionalInterface
    interface FormulaFactory {
        Formula create();
    }

}
