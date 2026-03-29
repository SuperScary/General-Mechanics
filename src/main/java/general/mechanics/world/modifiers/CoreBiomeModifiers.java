package general.mechanics.world.modifiers;

import general.mechanics.GM;
import general.mechanics.registries.CoreBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import general.mechanics.api.item.element.ElementType;
import java.util.HashMap;

public class CoreBiomeModifiers {

    public static final HashMap<String, ResourceKey<BiomeModifier>> ORE_BIOME_MODIFIERS = new HashMap<>();

    static {
        for (ElementType type : ElementType.values()) {
            if (!type.isAlloy() && type.isNatural()) {
                final String baseName = type.name().toLowerCase() + "_ore";
                final String deepslateName = "deepslate_" + baseName;
                final String netherName = "nether_" + baseName;

                ORE_BIOME_MODIFIERS.put("block.gm." + baseName, registerKey(baseName));
                ORE_BIOME_MODIFIERS.put("block.gm." + deepslateName, registerKey(deepslateName));
                ORE_BIOME_MODIFIERS.put("block.gm." + netherName, registerKey(netherName));
            }
        }
    }

    public static void bootstrap (BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        registerAllOres(context, placedFeatures, biomes);
    }

    private static void registerAllOres(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        for (var oreBlock : CoreBlocks.getOreBlocks()) {
            final String baseDesc = oreBlock.getDescriptionId();
            final String typeName = oreBlock.getType().name().toLowerCase();
            final String deepslateDesc = "block.gm.deepslate_" + typeName + "_ore";
            final String netherDesc = "block.gm.nether_" + typeName + "_ore";

            context.register(ORE_BIOME_MODIFIERS.get(baseDesc), new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.ORE_PLACED_FEATURES.get(baseDesc))),
                    GenerationStep.Decoration.UNDERGROUND_ORES
            ));

            context.register(ORE_BIOME_MODIFIERS.get(deepslateDesc), new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.ORE_PLACED_FEATURES.get(deepslateDesc))),
                    GenerationStep.Decoration.UNDERGROUND_ORES
            ));

            context.register(ORE_BIOME_MODIFIERS.get(netherDesc), new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTags.IS_NETHER),
                    HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.ORE_PLACED_FEATURES.get(netherDesc))),
                    GenerationStep.Decoration.UNDERGROUND_ORES
            ));
        }
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GM.getResource(name));
    }

}
