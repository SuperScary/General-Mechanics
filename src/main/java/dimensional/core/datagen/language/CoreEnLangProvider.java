package dimensional.core.datagen.language;

import dimensional.core.DimensionalCore;
import dimensional.core.api.util.IDataProvider;
import dimensional.core.registries.CoreBlocks;
import dimensional.core.registries.CoreItems;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CoreEnLangProvider extends LanguageProvider implements IDataProvider {

    public CoreEnLangProvider(DataGenerator generator) {
        super(generator.getPackOutput(), DimensionalCore.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addManualStrings();
        addSubtitles();

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
        add("gui.dimensionalcore.progress", "Progress: "); // the space is important!!!!
        add("gui.dimensionalcore.idle", "Idle");
        add("gui.dimensionalcore.gui.settings.right", "Right Click to Expand");
        add("gui.dimensionalcore.gui.settings.left", "Left Click to Expand");
        add("gui.dimensionalcore.gui.settings", "Settings");
        add("gui.dimensionalcore.itemlist", "Slot %s: %sx %s");
        add("gui.dimensionalcore.press_shift", "Hold §e[SHIFT]§r for more info.");
        add("gui.dimensionalcore.upgrade_tooltip", "Compatible Upgrades");
        add("gui.dimensionalcore.upgrade_tooltip.item", "§7§o- §7§o%s §7§ox%s");
    }

    protected void addSubtitles() {
        add("subtitles.dimensionalcore.plastic_block_place", "Block Placed");
        add("subtitles.dimensionalcore.plastic_block_break", "Block Break");
    }
}
