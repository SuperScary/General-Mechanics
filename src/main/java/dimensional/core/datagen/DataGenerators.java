package dimensional.core.datagen;

import dimensional.core.DimensionalCore;
import dimensional.core.datagen.data.SoundProvider;
import dimensional.core.datagen.language.CoreEnLangProvider;
import dimensional.core.datagen.loot.CoreLootTableProvider;
import dimensional.core.datagen.models.BlockModelProvider;
import dimensional.core.datagen.models.CoreItemModelProvider;
import dimensional.core.datagen.recipes.CraftingRecipes;
import dimensional.core.datagen.recipes.MachineRecipes;
import dimensional.core.datagen.recipes.SmeltingRecipes;
import dimensional.core.datagen.tags.CoreBlockTagGenerator;
import dimensional.core.datagen.tags.CoreItemTagGenerator;
import dimensional.core.datagen.world.WorldGenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@EventBusSubscriber(modid = DimensionalCore.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gather (@NotNull GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var pack = generator.getVanillaPack(true);
        var existingFileHelper = event.getExistingFileHelper();
        var localization = new CoreEnLangProvider(generator);

        // WORLD GENERATION
        pack.addProvider(output -> new WorldGenProvider(output, registries));

        // SOUNDS
        pack.addProvider(packOutput -> new SoundProvider(packOutput, existingFileHelper));

        // LOOT TABLE
        pack.addProvider(bindRegistries(CoreLootTableProvider::new, registries));

        // POI
        //pack.addProvider(packOutput -> new FMPoiTagGenerator(packOutput, registries, existingFileHelper));

        // TAGS
        var blockTagsProvider = pack.addProvider(pOutput -> new CoreBlockTagGenerator(pOutput, registries, existingFileHelper));
        pack.addProvider(pOutput -> new CoreItemTagGenerator(pOutput, registries, blockTagsProvider.contentsGetter(), existingFileHelper));

        // MODELS & STATES
        pack.addProvider(pOutput -> new BlockModelProvider(pOutput, existingFileHelper));
        pack.addProvider(pOutput -> new CoreItemModelProvider(pOutput, existingFileHelper));

        // RECIPES
        pack.addProvider(bindRegistries(CraftingRecipes::new, registries));
        pack.addProvider(bindRegistries(SmeltingRecipes::new, registries));
        pack.addProvider(bindRegistries(MachineRecipes::new, registries));

        // LOCALIZATION MUST RUN LAST
        pack.addProvider(output -> localization);
    }

    @Contract(pure = true)
    private static <T extends DataProvider> DataProvider.@NotNull Factory<T> bindRegistries (BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> factory, CompletableFuture<HolderLookup.Provider> factories) {
        return pOutput -> factory.apply(pOutput, factories);
    }

}
