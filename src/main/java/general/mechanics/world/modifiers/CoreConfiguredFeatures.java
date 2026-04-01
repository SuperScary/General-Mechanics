package general.mechanics.world.modifiers;

import general.mechanics.GM;
import general.mechanics.api.block.base.OreBlock;
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

import general.mechanics.api.item.element.ElementType;
import java.util.HashMap;
import java.util.List;

public class CoreConfiguredFeatures {
    
    public static final HashMap<String, ResourceKey<ConfiguredFeature<?, ?>>> ORE_CONFIGURED_FEATURES = new HashMap<>();

    static {
        for (ElementType type : ElementType.values()) {
            if (!type.isAlloy() && type.isNatural()) {
                final String baseName = type.name().toLowerCase() + "_ore";
                final String deepslateName = "deepslate_" + baseName;
                final String netherName = "nether_" + baseName;

                ORE_CONFIGURED_FEATURES.put("block.gm." + baseName, registerKey(baseName));
                ORE_CONFIGURED_FEATURES.put("block.gm." + deepslateName, registerKey(deepslateName));
                ORE_CONFIGURED_FEATURES.put("block.gm." + netherName, registerKey(netherName));
            }
        }
    }

    public static void bootstrap (BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceable = new BlockMatchTest(Blocks.NETHERRACK);
        //RuleTest endReplaceable = new BlockMatchTest(Blocks.END_STONE);
        
        for (OreBlock oreBlock : CoreBlocks.getOreBlocks()) {
            if (!oreBlock.getType().isNatural()) return;
            final String baseDesc = oreBlock.getDescriptionId();
            final String typeName = oreBlock.getType().name().toLowerCase();
            final String deepslateDesc = "block.gm.deepslate_" + typeName + "_ore";
            final String netherDesc = "block.gm.nether_" + typeName + "_ore";

            register(context,
                    ORE_CONFIGURED_FEATURES.get(baseDesc),
                    Feature.ORE,
                    new OreConfiguration(List.of(OreConfiguration.target(stoneReplaceable, oreBlock.defaultBlockState())), 6));

            register(context,
                    ORE_CONFIGURED_FEATURES.get(deepslateDesc),
                    Feature.ORE,
                    new OreConfiguration(List.of(OreConfiguration.target(deepslateReplaceable, oreBlock.getDeepslateOreBlock().defaultBlockState())), 6));

            register(context,
                    ORE_CONFIGURED_FEATURES.get(netherDesc),
                    Feature.ORE,
                    new OreConfiguration(List.of(OreConfiguration.target(netherReplaceable, oreBlock.getNetherOreBlock().defaultBlockState())), 6));
        }

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey (String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, GM.getResource(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
