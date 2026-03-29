package general.mechanics.world.modifiers;

import general.mechanics.GM;
import general.mechanics.api.block.base.OreBlock;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import general.mechanics.api.item.element.ElementType;
import java.util.HashMap;
import java.util.List;

public class CorePlacedFeatures {

    public static final HashMap<String, ResourceKey<PlacedFeature>> ORE_PLACED_FEATURES = new HashMap<>();
    
    static {
        for (ElementType type : ElementType.values()) {
            if (!type.isAlloy() && type.isNatural()) {
                final String baseName = type.name().toLowerCase() + "_ore";
                final String deepslateName = "deepslate_" + baseName;
                final String netherName = "nether_" + baseName;

                ORE_PLACED_FEATURES.put("block.gm." + baseName, registerKey(baseName));
                ORE_PLACED_FEATURES.put("block.gm." + deepslateName, registerKey(deepslateName));
                ORE_PLACED_FEATURES.put("block.gm." + netherName, registerKey(netherName));
            }
        }
    }

    public static void bootstrap (BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        for (OreBlock oreBlock : CoreBlocks.getOreBlocks()) {
            final String baseDesc = oreBlock.getDescriptionId();
            final String typeName = oreBlock.getType().name().toLowerCase();
            final String deepslateDesc = "block.gm.deepslate_" + typeName + "_ore";
            final String netherDesc = "block.gm.nether_" + typeName + "_ore";

            var placement = CoreOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)));

            register(context,
                    ORE_PLACED_FEATURES.get(baseDesc),
                    configuredFeatures.getOrThrow(CoreConfiguredFeatures.ORE_CONFIGURED_FEATURES.get(baseDesc)),
                    placement);

            register(context,
                    ORE_PLACED_FEATURES.get(deepslateDesc),
                    configuredFeatures.getOrThrow(CoreConfiguredFeatures.ORE_CONFIGURED_FEATURES.get(deepslateDesc)),
                    placement);

            register(context,
                    ORE_PLACED_FEATURES.get(netherDesc),
                    configuredFeatures.getOrThrow(CoreConfiguredFeatures.ORE_CONFIGURED_FEATURES.get(netherDesc)),
                    placement);
        }
    }

    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, GM.getResource(name));
    }

    private static void register (BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}