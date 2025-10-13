package general.mechanics.world.modifiers;

import general.mechanics.GM;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CoreBiomeModifiers {

    public static final ResourceKey<BiomeModifier> DRAKIUM_ORE = registerKey("drakium_ore");
    public static final ResourceKey<BiomeModifier> DRAKIUM_ORE_END = registerKey("drakium_ore_end");
    public static final ResourceKey<BiomeModifier> DRAKIUM_ORE_NETHER = registerKey("drakium_ore_nether");

    public static void bootstrap (BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(DRAKIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.DRAKIUM_ORE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(DRAKIUM_ORE_END, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.DRAKIUM_END_ORE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(DRAKIUM_ORE_NETHER, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(CorePlacedFeatures.DRAKIUM_NETHER_ORE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GM.getResource(name));
    }

}
