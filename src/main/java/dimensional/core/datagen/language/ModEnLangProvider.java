package dimensional.core.datagen.language;

import dimensional.core.DimensionalCore;
import dimensional.core.api.util.IDataProvider;
import dimensional.core.registries.CoreBlocks;
import dimensional.core.registries.CoreItems;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider implements IDataProvider {

    public ModEnLangProvider(DataGenerator generator) {
        super(generator.getPackOutput(), DimensionalCore.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addManualStrings();

        // ITEMS
        for (var item : CoreItems.getItems()) {
            add(item.asItem(), item.getEnglishName());
        }

        // BLOCKS
        for (var block : CoreBlocks.getBlocks()) {
            add(block.block(), block.getEnglishName());
        }
    }

    protected void addManualStrings() {
        add("itemGroup." + DimensionalCore.MODID, DimensionalCore.NAME);
    }
}
