package general.mechanics.datagen.language;

import general.mechanics.GM;
import general.mechanics.api.upgrade.UpgradeBase;
import general.mechanics.api.util.IDataProvider;
import general.mechanics.registries.CoreBlocks;
import general.mechanics.registries.CoreElements;
import general.mechanics.api.item.element.metallic.ElementItem;
import general.mechanics.registries.CoreItems;
import general.mechanics.registries.CoreUpgrades;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CoreEnLangProvider extends LanguageProvider implements IDataProvider {

    public CoreEnLangProvider(DataGenerator generator) {
        super(generator.getPackOutput(), GM.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addManualStrings();
        addSubtitles();
        elements();

        // ITEMS
        for (var item : CoreItems.getItems()) {
            add(item.asItem(), item.getEnglishName());
        }

        // ELEMENT ITEMS
        for (var element : CoreElements.getElements()) {
            add(element.asItem(), element.getEnglishName());
        }

        // BLOCKS
        for (var block : CoreBlocks.getBlocks()) {
            add(block.block(), block.getEnglishName());
        }

        // UPGRADES
        for (var upgrade : CoreUpgrades.getUpgrades()) {
            add(upgrade.get(), upgrade.getEnglishName());
        }

        // Upgrade descriptions
        for (var upgrade : CoreUpgrades.getUpgrades()) {
            UpgradeBase base = (UpgradeBase) upgrade.get();
            add(base.getDescriptionId() + ".desc", base.getEnglishDescription());
        }
    }

    protected void addManualStrings() {
        add("itemGroup." + GM.MODID, GM.NAME);
        add("gui.gm.progress", "Progress: "); // the space is important!!!!
        add("gui.gm.idle", "Idle");
        add("gui.gm.settings.right", "Right Click to Expand");
        add("gui.gm.settings.left", "Left Click to Expand");
        add("gui.gm.settings", "Settings");
        add("gui.gm.itemlist", "Slot %s: %sx %s");
        add("gui.gm.press_shift", "Hold §e[SHIFT]§r for more info.");
        add("gui.gm.upgrade_tooltip", "Compatible Upgrades");
        add("gui.gm.upgrade_tooltip.item", "§7§o- §7§o%s §7§ox%s");
        add("gui.gm.redstone_mode", "Redstone Mode: %s");
        add("gui.gm.redstone_mode.low", "Low");
        add("gui.gm.redstone_mode.high", "High");
        add("gui.gm.redstone_mode.ignored", "Ignored");
        add("gui.gm.auto_export", "Auto Export");
        add("gui.gm.auto_import", "Auto Import");
        add("gui.gm.enabled", "Enabled");
        add("gui.gm.disabled", "Disabled");
        add("gui.gm.side_config", "Side Config");
        add("gui.gm.status", "Status: %s");
        add("gui.gm.status.active", "Active");
        add("gui.gm.status.inactive", "Inactive");
        add("gui.gm.status.error", "Error");
        add("gui.gm.close_button", "Close");
        add("gui.gm.info", "Info");
        add("gui.gm.locked", "Locked");
        add("gui.gm.unlocked", "Unlocked");
    }

    protected void addSubtitles() {
        add("subtitles.gm.plastic_block_place", "Block Placed");
        add("subtitles.gm.plastic_block_break", "Block Break");
    }
    
    protected void elements() {
        add("element.atomic_number", "Atomic Number: %s");
        add("element.atomic_symbol", "Atomic Symbol: %s");
    }
}
