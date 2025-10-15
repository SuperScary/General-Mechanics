package general.mechanics.world.modifiers;

import general.mechanics.GM;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class CoreConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> DRAKIUM_ORE_KEY = registerKey("drakium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRAKIUM_END_ORE_KEY = registerKey("drakium_ore_end");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRAKIUM_NETHER_ORE_KEY = registerKey("drakium_ore_nether");

    public static void bootstrap (BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        //List<OreConfiguration.TargetBlockState> overworldDrakiumOres = List.of(OreConfiguration.target(stoneReplaceables, CoreBlocks.DRAKIUM_ORE.block().defaultBlockState()), OreConfiguration.target(deepslateReplaceables, CoreBlocks.DEEPSLATE_DRAKIUM_ORE.block().defaultBlockState()));
        //List<OreConfiguration.TargetBlockState> endDrakiumOres = List.of(OreConfiguration.target(endReplaceables, CoreBlocks.END_STONE_DRAKIUM_ORE.block().defaultBlockState()));
        //List<OreConfiguration.TargetBlockState> netherDrakiumOres = List.of(OreConfiguration.target(netherReplaceables, CoreBlocks.NETHER_DRAKIUM_ORE.block().defaultBlockState()));

        //register(context, DRAKIUM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldDrakiumOres, 6));
        //register(context, DRAKIUM_END_ORE_KEY, Feature.ORE, new OreConfiguration(endDrakiumOres, 4));
        //register(context, DRAKIUM_NETHER_ORE_KEY, Feature.ORE, new OreConfiguration(netherDrakiumOres, 8));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey (String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, GM.getResource(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
